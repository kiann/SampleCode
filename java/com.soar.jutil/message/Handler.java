package com.soar.message;

public class Handler {
    private Looper looper;
    private MessagePool messagePool;

    public Handler() {
        looper = Looper.myLooper();
        messagePool = looper.messagePool;
    }

    public void handlerMessage(Message message) {

    }

    public void dispatchMessage(Message message) {
        handlerMessage(message);
    }

    public void sendMessage(Message message) {
        message.target = this;
        messagePool.enqueueMessage(message);
    }
}
