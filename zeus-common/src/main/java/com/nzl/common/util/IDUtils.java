package com.nzl.common.util;

import java.util.Random;

/**
 * @package: com.nzl.common.util
 * @classname: IDUtils
 * @description: 各种id生成策略
 * @author: nizonglong
 * @date: 2019/06/24 9:48
 * @version: 0.1
 */
public class IDUtils {

    /**
     * 图片名生成
     */
    public static String genImageName() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        // 加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0

        return millis + String.format("%03d", end3);
    }

    /**
     * 商品id生成
     */
    public static long genItemId() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(99);
        //如果不足两位前面补0
        String str = millis + String.format("%02d", end2);
        return new Long(str);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(genItemId());
        }
    }
}
