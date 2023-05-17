CREATE TABLE file_info
(
    file_id            varchar(40)   NOT NULL COMMENT '文件id'
        PRIMARY KEY,
    file_source_name   varchar(255)  NULL COMMENT '原文件名称(xxx.png)',
    file_full_name     varchar(255)  NULL COMMENT '文件完整名称(xxx_时间戳.png)',
    file_prefix_name   varchar(255)  NULL COMMENT '文件前缀名称(xxx_时间戳)',
    file_suffix_name   varchar(16)   NULL COMMENT '文件后缀名称(png)',
    file_category      tinyint(1)    NULL COMMENT '文件类型(0:简单文本; 1:文件; 2:图片; 3:视频; ...)',
    file_path          varchar(2048) NULL COMMENT '文件全路径(https://xxx.com/租户id/2023/05/xxx.png',
    file_relative_path varchar(2048) NULL COMMENT '文件相对路径(/租户id/2023/05/xxx_时间戳.png)',
    file_size          bigint        NULL COMMENT '文件大小(字节)',
    creator_id         varchar(40)   NULL COMMENT '创建人id',
    tenant_id          varchar(40)   NULL COMMENT '租户id',
    create_time        datetime      NULL COMMENT '创建时间',
    remove_user_id     varchar(40)   NULL COMMENT '删除人id',
    remove_time        datetime      NULL COMMENT '文件删除时间',
    del_flag           tinyint(1)    NULL COMMENT '删除标记(0:否; 1:是)'
)
COMMENT '文件服务-文件基本信息表';

