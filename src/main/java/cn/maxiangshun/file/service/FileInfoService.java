package cn.maxiangshun.file.service;

import cn.maxiangshun.file.entity.po.FileInfo;
import cn.maxiangshun.file.entity.vo.UploadVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件服务-文件基本信息表(FileInfo) Service接口
 * @author marcel.maxs
 * @since 2023-05-12
 */
public interface FileInfoService extends IService<FileInfo> {

    /**
     * 单文件上传
     */
    UploadVO upload(MultipartFile file, String createUserId, HttpServletResponse response);

    /**
     * 多文件上传
     */
    List<UploadVO> multipleUpload(MultipartFile[] files, String createUserId, HttpServletResponse response);
}
