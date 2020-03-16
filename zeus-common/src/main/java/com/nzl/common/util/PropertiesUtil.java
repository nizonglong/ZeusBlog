package com.nzl.common.util;

import com.nzl.common.exception.ZeusException;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author: nizonglong
 * @date: 2020/3/16 13:45
 * @desc:
 * @version: 0.1
 **/
@NoArgsConstructor
public class PropertiesUtil {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * PROP
     */
    private static final Properties PROP = new Properties();

    /**
     * 读取配置文件
     *
     * @param fileName
     * @return void
     * @author dolyw.com
     * @date 2018/8/31 17:29
     */
    public static void readProperties(String fileName) {
        InputStream in = null;
        try {
            in = PropertiesUtil.class.getResourceAsStream("/" + fileName);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            PROP.load(bf);
        } catch (IOException e) {
            logger.error("PropertiesUtil工具类读取配置文件出现IOException异常:{}", e.getMessage());
            throw new ZeusException("PropertiesUtil工具类读取配置文件出现IOException异常:" + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("PropertiesUtil工具类读取配置文件出现IOException异常:{}", e.getMessage());
                throw new ZeusException("PropertiesUtil工具类读取配置文件出现IOException异常:" + e.getMessage());
            }
        }
    }

    /**
     * 根据key读取对应的value
     *
     * @param key
     * @return java.lang.String
     * @author dolyw.com
     * @date 2018/8/31 17:29
     */
    public static String getProperty(String key) {
        return PROP.getProperty(key);
    }
}
