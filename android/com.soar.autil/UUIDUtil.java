package com.soar.core.util;

import java.util.UUID;

/**
 * Created by lsoar on 2017/9/28.
 */

public class UUIDUtil {
    public static String get() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        uuidStr = uuidStr.replace("-", "");
        return uuidStr;
    }
}
