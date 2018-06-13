package com.oybek.hippo.postmans;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oybek.hippo.queues.QueueController;
import com.oybek.hippo.things.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VkPostman extends Postman {
    @Value("${vk.token}")
    private String accessToken;

    @Value("${artificialPing}")
    private long artificialPing = 1000;

    private QueueController queueController;

    private long lastMessageId = 0;

    public VkPostman (QueueController queueController) {
        this.queueController = queueController;

        new Thread(new Runnable() {
            @Override
            public void run() {
                update();
            }
        }).start();
    }

    private void update() {
        while(true) {
            try {
                ObjectMapper mapper = new ObjectMapper();

                String responseString = get(String.format(
                        "https://api.vk.com/method/messages.get"
                        +"?last_message_id=%d"
                        +"&access_token=%s&v=3"
                        , lastMessageId
                        , accessToken));

                if (responseString == null)
                    return;

                JsonNode responseJson = mapper.readTree(responseString);

                if (responseJson.has("response")) {
                    for (JsonNode element : responseJson.get("response")) {
                        if (element.isObject()) {
                            Message msg = mapper.treeToValue(element, Message.class);

                            lastMessageId = Math.max(lastMessageId, msg.getId());

                            if (msg.getReadState() == 0) {
                                System.out.println(msg.toString());
                                queueController.getQueueToHippo().add(msg);
                            }
                        }
                    }
                }

                Message message = queueController.getQueueFromHippo().poll();
                if (message != null) {
                    get(String.format("https://api.vk.com/method/messages.send"
                            +"?v=3.0"
                            +"&user_id=%d"
                            +"&message=%s"
                            +"&attachment=%s"
                            +"&access_token=%s"
                            , message.getUid()
                            , message.getBody()
                            , message.getAttachmentt()
                            , accessToken));
                }

                Thread.sleep(artificialPing);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
