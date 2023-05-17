package cn.maxiangshun.file.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件服务-文件基本信息表(file_info) PO
 * @author marcel.maxs
 * @since 2023-05-12
 */
@Data
@TableName(value = "file_info")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
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
     * 文件前缀名称(xxx_时间戳)
     */
    private String filePrefixName;
    
    /**
     * 文件后缀名称(png)
     */
    private String fileSuffixName;
    
    /**
     * 文件类型(0:简单文本; 1:文件; 2:图片; 3:视频; ...)
     */
    private Integer fileCategory;
    
    /**
     * 文件全路径(https://xxx.com/租户id/2023/05/xxx.png
     */
    private String filePath;
    
    /**
     * 文件相对路径(/租户id/2023/05/xxx_时间戳.png)
     */
    private String fileRelativePath;
    
    /**
     * 文件大小(字节)
     */
    private Long fileSize;
    
    /**
     * 创建人id
     */
    private String creatorId;
    
    /**
     * 租户id
     */
    private String tenantId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 删除人id
     */
    private String removeUserId;
    
    /**
     * 文件删除时间
     */
    private LocalDateTime removeTime;
    
    /**
     * 删除标记(0:否; 1:是)
     */
    private Integer delFlag;
    


}


