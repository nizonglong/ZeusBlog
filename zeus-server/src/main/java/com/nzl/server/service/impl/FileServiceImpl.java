package com.nzl.server.service.impl;

import com.nzl.server.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @classname: FileServiceImpl
 * @description:
 * @author: nizonglong
 * @date: 2019/9/18 21:34
 * @version: 1.0
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    /**
     * 上传单个文件
     * @param file 文件
     * @param path 文件路径
     * @return 新文件名
     */
    @Override
    public String uploadOne(MultipartFile file, String path) {
        log.info("start uploadOne()");

        String fileName = file.getOriginalFilename();
        assert fileName != null;
        String uploadFileName = "zeus" + System.currentTimeMillis() + "z_b" + fileName;

        log.info("开始上传文件，上传的文件名：{},上传的路径：{},新文件名：{}", fileName, path, uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile = new File(path + "/" + uploadFileName);

        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            log.error("上传文件异常", e);
            return null;
        }

        return targetFile.getName();
    }

    @Override
    public int deleteFile(String filePath) {
        File fileDir = new File(filePath);
        // 文件不存在则返回 -1
        if (!fileDir.exists()) {
            return -1;
        }

        // 文件存在则删除
        if (fileDir.delete()) {
            // 删除成功 返回 1
            return 1;
        } else {
            // 删除失败 返回 0
            return 0;
        }
    }
}
