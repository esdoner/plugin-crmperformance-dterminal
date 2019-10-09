package com.fr.plugin.performance.file.release;

/**
 * Description:none
 * @author yuwh
 * 2018/10/12
 */
public interface FileRelease {
    String RELEASE_PROPERTIES = "static/properties/release.properties";

    /**
     * 检查待发布的文件
     */
    void checkFile();

    /**
     * 验证发布空间
     */
    void verifyPath();

    /**
     * 备份
     */
    void backupFile();

    /**
     * 准备
     */
    void prepareFile();

    /**
     * 发布
     */
    void releaseFile();

    /**
     * 结果
     */
    void resultGather();
}
