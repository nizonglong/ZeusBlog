package com.nzl.server.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @classname: FileService
 * @description:
 * @author: nizonglong
 * @date: 2019/9/18 21:34
 * @version: 1.0
 */
public interface FileService {
    /**
     * 上传一个文件
     *
     * @param file CommonsMultipartFile
     * @return 上传文件后新的文件名
     */
    String uploadOne(MultipartFile file, String path);

    /**
     * 删除文件
     * 1:删除成功
     * 0:删除失败
     * -1:文件不存在
     *
     * @param filePath
     * @return
     */
    int deleteFile(String filePath);
}
