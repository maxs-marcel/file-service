package cn.maxiangshun.file.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.maxiangshun.file.entity.po.FileInfo;
import cn.maxiangshun.file.entity.vo.UploadVO;
import cn.maxiangshun.file.mapper.FileInfoMapper;
import cn.maxiangshun.file.service.FileInfoService;
import cn.maxiangshun.file.util.FileUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 文件服务-文件基本信息表(FileInfo) Service接口实现
 * @author marcel.maxs
 * @since 2023-05-12
 */
@Service
@Slf4j
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {
    @Resource
    private FileInfoMapper fileInfoMapper;

    @Value("${file.upload.path}")
    private String uploadPath;
    @Value("${file.upload.domain}")
    private String domain;


    /**
     * 单文件上传
     */
    @Override
    public UploadVO upload(MultipartFile file, String createUserId, HttpServletResponse response) {
        // 构建文件信息
        FileInfo fileInfo = buildFileInfo(file, createUserId);
        fileInfoMapper.insert(fileInfo);

        return UploadVO.builder()
                .fileId(fileInfo.getFileId())
                .fileSourceName(file.getOriginalFilename())
                .fileFullName(fileInfo.getFileFullName())
                .filePath(fileInfo.getFilePath())
                .build();
    }

    /**
     * 多文件上传
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<UploadVO> multipleUpload(MultipartFile[] files, String createUserId, HttpServletResponse response) {
        List<FileInfo> fileInfoSaveList = Lists.newArrayList();
        List<UploadVO> resultVOList = Lists.newArrayList();
        for (MultipartFile file : files) {
            // 构建文件信息
            FileInfo fileInfo = buildFileInfo(file, createUserId);
            fileInfoSaveList.add(fileInfo);
            // 构建返回结果
            UploadVO uploadVO = UploadVO.builder()
                    .fileId(fileInfo.getFileId())
                    .fileSourceName(file.getOriginalFilename())
                    .fileFullName(fileInfo.getFileFullName())
                    .filePath(fileInfo.getFilePath())
                    .build();
            resultVOList.add(uploadVO);
        }
        this.saveBatch(fileInfoSaveList);
        return resultVOList;
    }

    /**
     * 构建文件信息
     */
    private FileInfo buildFileInfo(MultipartFile file, String createUserId){
        // 文件年月路径
        String ymdPath = FileUtils.getYmdPath();
        // 文件全路径(不包含文件名)
        String filePath = FileUtils.filePathJoin(uploadPath, ymdPath);
        // 获取文件字节
        byte[] bytes;
        int last = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        // 文件名前缀
        String prefix = file.getOriginalFilename().substring(0, last);
        // 文件名后缀
        String suffix = file.getOriginalFilename().substring(last + 1);
        // 目标文件名
        String targetPrefix = prefix + "_" + System.currentTimeMillis();
        // 文件名(文件原名称_时间戳_.后缀)
        String fileTargetName = targetPrefix + "." + suffix;
        // 文件全路径(包含文件名)
        String fullPath = domain + FileUtils.filePathJoin(ymdPath, fileTargetName);
        // 文件上传
        try {
            bytes = file.getBytes();
            FileUtils.uploadFile(filePath, fileTargetName, bytes);
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new RuntimeException(e);
        }
        // 文件信息入库
        return FileInfo.builder()
                .fileId(IdUtil.fastSimpleUUID())
                .fileSourceName(file.getOriginalFilename())
                .fileFullName(fileTargetName)
                .filePrefixName(targetPrefix)
                .fileSuffixName(suffix)
                .fileCategory(FileUtils.getFileType(suffix))
                .filePath(fullPath)
                .fileRelativePath(FileUtils.filePathJoin(ymdPath, fileTargetName))
                .fileSize(file.getSize())
                .createTime(LocalDateTime.now())
                .creatorId(createUserId)
                .delFlag(0)
                .build();
    }

    /**
     * 根据文件ids获取文件列表
     * @param fileIds 文件ids
     * @return 文件列表
     */
    @Override
    public List<UploadVO> getByIds(List<String> fileIds) {
        if (CollectionUtils.isEmpty(fileIds)) {
            return Lists.newArrayList();
        }
        List<FileInfo> fileInfoList = fileInfoMapper.selectBatchIds(fileIds);
        List<UploadVO> resultVOList = Lists.newArrayList();
        for (FileInfo fileInfo : fileInfoList) {
            UploadVO uploadVO = UploadVO.builder()
                    .fileId(fileInfo.getFileId())
                    .fileSourceName(fileInfo.getFileSourceName())
                    .fileFullName(fileInfo.getFileFullName())
                    .filePath(fileInfo.getFilePath())
                    .build();
            resultVOList.add(uploadVO);
        }
        return resultVOList;
    }
}
