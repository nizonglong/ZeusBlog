package com.nzl.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @package: com.nzl.common.util
 * @classname: ExceptionUtil
 * @description: 异常信息工具类
 * @author: nizonglong
 * @date: 2019/06/24 9:47
 * @version: 0.1
 */
public class ExceptionUtil {

    /**
     * 获取异常的堆栈信息
     *
     * @param t
     * @return
     */
    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();

        try (PrintWriter pw = new PrintWriter(sw)) {
            t.printStackTrace(pw);
            return sw.toString();
        }
    }
}
