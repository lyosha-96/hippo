package com.oybek.hippo.lake;

import com.oybek.hippo.queues.QueueController;
import com.oybek.hippo.things.Message;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Component
public class MegaHippo {
    @Value("${brain.file}")
    private String brainFile;

    private static final Logger logger = LoggerFactory.getLogger(MegaHippo.class);

    private QueueController queueController;
    private HashMap<Long, Hippo> hippos;

    public MegaHippo(QueueController queueController) {
        this.queueController = queueController;
        hippos = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                work();
            }
        }).start();
    }

    public static void send(ScriptObjectMirror messageScriptObj) {
        Message message = Message.fromScriptObjectMirror(messageScriptObj);

        logger.info("Send message: " + message.toString());

        // url encode hippo's response
        try {
            message.setBody(URLEncoder.encode(message.getBody(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // put hippo's reply to outgoing queue
        QueueController.getQueueFromHippo().add(message);
    }

    public void work() {
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

                logger.info("Got message: " + msg.toString());

                hippos.putIfAbsent(msg.getUid(), new Hippo(this.brainFile));

                // get reaction of hippo to message
                hippos.get(msg.getUid()).work(msg);
            }
        }
    }
}
