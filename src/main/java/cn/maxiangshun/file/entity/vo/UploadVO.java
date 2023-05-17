package cn.maxiangshun.file.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Marcel.Maxs
 * @description 上传后返回结果
 * @date 2023/5/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadVO {
    /**
     * 文件id
     */
    private String fileId;
    /**
     * 原文件名称(xxx.png)
     */
    private String fileSourceName;
    /**
     * 文件完整名称(xxx_时间戳.png)
     */
    private String fileFullName;
    /**
     * 文件全路径
     */
    private String filePath;
}
