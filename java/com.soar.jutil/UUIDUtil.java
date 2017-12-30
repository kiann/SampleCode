package com.soar.util;

import java.util.UUID;

/**
 * Created by 78326 on 2017.9.3.
 */
public class UUIDUtil {
    public static String get(){
        UUID uuid = UUID.randomUUID();
        String uuidStr =  uuid.toString();
        uuidStr = uuidStr.replace("-", "");
        return uuidStr;
    }
}
