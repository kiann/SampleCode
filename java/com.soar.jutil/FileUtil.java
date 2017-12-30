package com.soar.htwl.util;

import java.io.File;

public class FileUtil {
    public static String getFileSuffix(File file) {
        return file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1);
    }

    public static String getFileSuffix(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
