package com.soar.message;

import java.util.ArrayList;
import java.util.List;

public class MessagePool {
    List<Message> messageList;

    public MessagePool() {
        messageList = new ArrayList<>();
    }

    public Message next() {
        synchronized (messageList) {
            if (messageList.size() > 0) {
                System.out.println("get:");
                return messageList.remove(0);
            }
            return null;
        }
    }

    public void enqueueMessage(Message message) {
        synchronized (messageList) {
            System.out.println("add:" + message.what + ">" + message.object);
            messageList.add(message);
        }
    }
}
