package com.soar.message;

public class Looper {
    private static ThreadLocal<Looper> threadLocal = new ThreadLocal();
    MessagePool messagePool;

    private Looper() {
        messagePool = new MessagePool();
    }

    public static void prepare() {
        if (threadLocal.get() != null) {
            throw new RuntimeException("prepare methed only can be called once!");
        }
        threadLocal.set(new Looper());
    }

    public static void loop() {
        System.out.println("loop");
        Looper looper = myLooper();
        MessagePool pool = looper.messagePool;
        for (; ; ) {
            Message message = pool.next();
            if (message == null) {
                continue;
            }
            System.out.println("get2:" + message.what + ">" + message.object);
            message.target.dispatchMessage(message);
        }
    }

    public static Looper myLooper() {
        return threadLocal.get();
    }
}
