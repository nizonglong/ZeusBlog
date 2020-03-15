package com.nzl.common.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @classname: FileUtils
 * @description: 文件工具
 * @author: nizonglong
 * @date: 2019/9/11 9:22
 * @version: 1.0
 */
public class FileUtils {

    public static boolean writeTextFile(String path, String fileName, String content) {
        try {
            // 相对路径，如果没有则建立一个新的文件
            File file = new File(path + fileName);

            try (FileWriter writer = new FileWriter(file);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                // \r\n即为换行
                out.write(content);
                out.flush(); // 把缓存区内容压入文件

                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String readTextFile(String path) {
        StringBuilder sb = new StringBuilder();

        try (FileInputStream in = new FileInputStream(path);
             InputStreamReader inReader = new InputStreamReader(in, StandardCharsets.UTF_8);
             BufferedReader bufReader = new BufferedReader(inReader)) {

            String line;

            while ((line = bufReader.readLine()) != null) {
                sb.append(line).append("\r\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
