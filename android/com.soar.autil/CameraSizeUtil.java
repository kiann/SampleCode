package com.soar.camerapre.util;

import android.hardware.Camera;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lsoar on 2018/1/16.
 */

public class CameraSizeUtil {
    public static final int RANGE_16_9 = 1;

    public static Camera.Size getBestSize(List<Camera.Size> supportSizes, int range) {
        Collections.sort(supportSizes, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size o1, Camera.Size o2) {
                if (o1.width * o1.height > o2.width * o2.height) {
                    return -1;
                } else if (o1.width * o1.height < o2.width * o2.height) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        Camera.Size bestSize = supportSizes.get(0);
        double capped = 1.9;
        double lower = 1.5;
        if (range == RANGE_16_9) {
            capped = 1.8;
            lower = 1.7;
        }
        for (Camera.Size size : supportSizes) {
            if (size.width / size.height > lower && size.width / size.height < capped) {
                bestSize = size;
                break;
            }
        }
        return bestSize;
    }
}
