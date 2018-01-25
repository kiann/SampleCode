package com.soar.camerapre.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Field;

/**
 * Created by lsoar on 2018/1/25.
 */

public class SharedPreferencesUtil {
    private SharedPreferences sharedPreferences;

    private SharedPreferencesUtil() {
    }

    public static SharedPreferencesUtil instance(Context context, String name) {
        SharedPreferencesUtil util = new SharedPreferencesUtil();
        util.sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return util;
    }

    public <T> T get(Class<T> clazz) {
        try {
            T obj = clazz.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getType().toString().endsWith("String")) {
                    field.set(obj, sharedPreferences.getString(field.getName(), null));
                } else if (field.getType().toString().endsWith("int")) {
                    field.set(obj, sharedPreferences.getInt(field.getName(), 0));
                } else if (field.getType().toString().endsWith("boolean")) {
                    field.set(obj, sharedPreferences.getBoolean(field.getName(), false));
                } else if (field.getType().toString().endsWith("long")) {
                    field.set(obj, sharedPreferences.getLong(field.getName(), 0));
                } else if (field.getType().toString().endsWith("float")) {
                    field.set(obj, sharedPreferences.getFloat(field.getName(), 0));
                } else if (field.getType().toString().endsWith("double")) {
                    field.set(obj, Double.valueOf(sharedPreferences.getString(field.getName(), "0")));
                }
            }
            return obj;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void set(Object obj) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getType().toString().endsWith("String")) {
                    editor.putString(field.getName(), (String) field.get(obj));
                } else if (field.getType().toString().endsWith("int")) {
                    editor.putInt(field.getName(), field.getInt(obj));
                } else if (field.getType().toString().endsWith("boolean")) {
                    editor.putBoolean(field.getName(), field.getBoolean(obj));
                } else if (field.getType().toString().endsWith("long")) {
                    editor.putLong(field.getName(), field.getLong(obj));
                } else if (field.getType().toString().endsWith("float")) {
                    editor.putFloat(field.getName(), field.getFloat(obj));
                } else if (field.getType().toString().endsWith("double")) {
                    editor.putString(field.getName(), String.valueOf(field.getDouble(obj)));
                }
            }
            editor.commit();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
