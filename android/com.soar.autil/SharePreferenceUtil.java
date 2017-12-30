package com.soar.core.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by lsoar on 2017/11/7.
 */

public class SharePreferenceUtil {
    private static SharePreferenceUtil util;
    private String name;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharePreferenceUtil(Context context, String name) {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharePreferenceUtil getUtil(Context context, String name) {
        if (util == null) {
            util = new SharePreferenceUtil(context, name);
        }
        if (util.name == null || !util.name.equals(name)) {
            util.sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            util.editor = util.sharedPreferences.edit();
        }
        return util;
    }

    public String get(String name) {
        return sharedPreferences.getString(name, "");
    }

    public void set(String name, String value) {
        editor.putString(name, value);
        editor.commit();
    }

    public <T> T get(Class<T> obj) {
        String item = sharedPreferences.getString(obj.getSimpleName(), "");
        return new Gson().fromJson(item, obj);
    }

    public void set(Object obj) {
        editor.putString(obj.getClass().getSimpleName(), new Gson().toJson(obj));
        editor.commit();
    }
}
