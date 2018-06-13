package com.oybek.hippo.lake;

import com.oybek.hippo.queues.QueueController;
import com.oybek.hippo.things.Message;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Component
public class MegaHippo {
    private QueueController queueController;
    private HashMap<Long, Hippo> hippos;

    public MegaHippo(QueueController queueController) {
        this.queueController = queueController;
        hippos = new HashMap<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                work();
            }
        }).start();
    }

    public void work () {
        while (true) {
            // if no work ...
            if (queueController.getQueueToHippo().isEmpty()) {
                // ... sleep 0.5 second
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            } else {
                // ... get message from queue
                Message msg = queueController.getQueueToHippo().poll();

                hippos.putIfAbsent(msg.getUid(), new Hippo());

                // get reaction of hippo to message
                Message replyMsg = hippos.get(msg.getUid()).getReaction(msg);

                if (replyMsg != null) {
                    // url encode hippo's response
                    try {
                        replyMsg.setBody(URLEncoder.encode(replyMsg.getBody(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    // put hippo's reply to outgoing queue
                    queueController.getQueueFromHippo().add(replyMsg);
                }
            }
        }
    }
}
