package cn.maxiangshun.file.util;

import com.google.common.collect.Maps;

import java.io.File;
import java.time.LocalDate;
import java.util.Map;

/**
 * @author Marcel.Maxs
 * @description 文件操作工具类
 * @date 2023/5/15
 */
public class FileUtils {
    private FileUtils() {
    }

    private static final Map<String, Integer> FILE_TYPE_MAP = Maps.newHashMap();

    static {
        //初始化文件类型信息
        FILE_TYPE_MAP.put("txt", 0);

        FILE_TYPE_MAP.put("png", 2);
        FILE_TYPE_MAP.put("jpg", 2);
        FILE_TYPE_MAP.put("jpeg", 2);
        FILE_TYPE_MAP.put("bmp", 2);
        FILE_TYPE_MAP.put("gif", 2);
        FILE_TYPE_MAP.put("ico", 2);
        FILE_TYPE_MAP.put("psd", 2);

        FILE_TYPE_MAP.put("avi", 3);
        FILE_TYPE_MAP.put("mp4", 3);
        FILE_TYPE_MAP.put("wmv", 3);
        FILE_TYPE_MAP.put("mpeg", 3);
        FILE_TYPE_MAP.put("flv", 3);
        FILE_TYPE_MAP.put("mov", 3);
        FILE_TYPE_MAP.put("rm", 3);
    }

    /**
     * 根据文件后缀获取文件类型编号
     * @param suffix 文件后缀 如: txt
     */
    public static Integer getFileType(String suffix) {
        // 如果文件类型不匹配, 则默认为文件类型
        Integer typeId = FILE_TYPE_MAP.getOrDefault(suffix, 1);
        return typeId;
    }

    /**
     * 文件上传到指定文件夹
     */
    public static String uploadFile(String path, String fileName, byte[] bytes) {
        // 如果目录不存在, 则创建文件夹
        filePathCreate(path);
        // 文件全路径
        String fileFullPath = filePathJoin(path, fileName);
        File file = new File(fileFullPath);
        try {
            org.apache.commons.io.FileUtils.writeByteArrayToFile(file, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileFullPath;
    }

    /**
     * 获取当前年月需要的文件夹路径
     */
    public static String getYmdPath() {
        LocalDate now = LocalDate.now();
        return now.getYear() + File.separator +
                now.getMonthValue() + File.separator +
                now.getDayOfMonth() + File.separator;
    }

    /**
     * 拼接文件路径
     */
    public static String filePathJoin(String... paths) {
        StringBuilder sb = new StringBuilder();
        for (String path : paths) {
            if (!path.startsWith(File.separator)) {
                sb.append(File.separator);
            }
            sb.append(path);
            if(path.endsWith(File.separator)){
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }

    /**
     * 父目录不存在则创建
     */
    public static void filePathCreate(String path) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            boolean mkResult = file.getParentFile().mkdirs();
            if (!mkResult) {
                throw new RuntimeException("创建文件夹失败");
            }
        }
    }

}
