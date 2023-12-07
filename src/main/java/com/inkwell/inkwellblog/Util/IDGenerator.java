package com.inkwell.inkwellblog.Util;

import java.util.UUID;

public class IDGenerator {
    public static String generateID(int length){
        // 根据随机UUID哈希值生成UID
        int hashCode = UUID.randomUUID().toString().hashCode();
        if (hashCode <0){
            hashCode=-hashCode;
        }
        // 0 代表前面补充0
        // 10 代表长度为10
        // d 代表参数为正数型
        return String.format("%0"+ length + "d", hashCode).substring(0,length);
    }
}
