package cn.maxiangshun.file.controller;

import cn.maxiangshun.file.entity.vo.UploadVO;
import cn.maxiangshun.file.service.FileInfoService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Marcel.Maxs
 * @description 文件服务Controller
 * @date 2023/5/12
 */
@RestController
@RequestMapping("/file-operation")
@Slf4j
public class FileController {

    @Resource
    private FileInfoService fileInfoService;

    /**
     * 单文件上传
     */
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("createUserId") String createUserId,
                         HttpServletResponse response) {
        UploadVO result = fileInfoService.upload(file, createUserId, response);
        return JSONObject.toJSONString(result);
    }

    /**
     * 多文件上传
     */
    @RequestMapping("/multiple-upload")
    public String multipleUpload(@RequestParam("files") MultipartFile[] files,
                                 @RequestParam("createUserId") String createUserId,
                                 HttpServletResponse response) {
        List<UploadVO> uploadVOList = fileInfoService.multipleUpload(files, createUserId, response);
        return JSONObject.toJSONString(uploadVOList);
    }
}
