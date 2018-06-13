package com.oybek.hippo.queues;

import com.oybek.hippo.things.Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class QueueController {
    static ConcurrentLinkedQueue<Message> queueToBot   = new ConcurrentLinkedQueue<>();
    static ConcurrentLinkedQueue<Message> queueFromBot = new ConcurrentLinkedQueue<>();

    public static ConcurrentLinkedQueue<Message> getQueueToHippo() {
        return queueToBot;
    }

    public static ConcurrentLinkedQueue<Message> getQueueFromHippo() {
        return queueFromBot;
    }
}

