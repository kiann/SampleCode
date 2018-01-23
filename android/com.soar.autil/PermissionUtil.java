package com.pd.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liuchenghe on 2018/1/16.
 */

public class PermissionUtil {
    private static int fd;

    public static String[] checkFirst(Activity context, String[] permissions) {
        if (Build.VERSION.SDK_INT < 23) {
            return new String[0];
        }
        List<String> needRequest = new ArrayList<>();
        for (String str : permissions) {

            int ret = context.checkSelfPermission(str);
            if (ret != PackageManager.PERMISSION_GRANTED) {
                needRequest.add(str);
            }
        }
        return needRequest.toArray(new String[0]);
    }

    public static int request(Activity activity, String[] permissions) {
        fd++;
        activity.requestPermissions(permissions, fd);
        return fd;
    }

    public static Result dealResult(Activity activity, String[] permissions, int[] grantResults) {
        Result result = new Result();
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                temp.add(permissions[i]);
                if (!activity.shouldShowRequestPermissionRationale(permissions[i])) {
                    result.notAskAgain = true;
                }
            }
        }
        result.permissions = temp.toArray(new String[0]);
        return result;
    }

    public static class Result {
        public boolean notAskAgain;
        public String[] permissions;
    }
}
