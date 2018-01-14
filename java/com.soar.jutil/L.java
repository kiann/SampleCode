package com.soar.locallay.util;
/**
 *  日志工具类
 */
public class L {
    private static String className;

    private static String methodName;

    private static String createLog(String log) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(className);
        buffer.append(".");
        buffer.append(methodName);
        buffer.append("]");
        buffer.append(log);

        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        className = className.substring(0,className.length()-5);
        methodName = sElements[1].getMethodName();
    }

    public static void d(String msg) {
        getMethodNames(new Throwable().getStackTrace());
        System.out.println(createLog(msg));
    }
    public static void e(String msg) {
        getMethodNames(new Throwable().getStackTrace());
        System.err.println(createLog(msg));
    }
}
