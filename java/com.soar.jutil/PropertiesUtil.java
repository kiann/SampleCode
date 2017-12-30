package com.soar.util;

import com.google.gson.Gson;
import com.soar.domain.Common;
import com.soar.domain.entity.AdminUser;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 78326 on 2017.9.3.
 */
public class PropertiesUtil {
    public static AdminUser getFields() {
        File file = new File(PathUtil.getJARPath() + File.separator + Common.CONFIG_SFT_NAME);
        if (file == null || !file.exists() || !file.isFile()) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
            AdminUser user = new Gson().fromJson(properties.getProperty("admin"), AdminUser.class);
            return user;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean putFields(AdminUser user) {
        try {
            File file = new File(PathUtil.getJARPath() + File.separator + Common.CONFIG_SFT_NAME);
            Properties properties = new Properties();
            properties.setProperty("admin", new Gson().toJson(user));
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            properties.store(fileOutputStream, "");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
