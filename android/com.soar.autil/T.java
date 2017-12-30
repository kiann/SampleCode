package com.soar.core.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 78326 on 2017.8.25.
 */

public class T {
    private T() {
    }

    public static void showShort(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(Context context, int text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
