package com.oybek.hippo.lake;

import com.oybek.hippo.things.Message;

public class Hippo {
    public Message getReaction(Message msg) {
        if (msg.getBody().equals("hello"))
            return msg.clone().setBody("hello bro!");
        else
            return null;
    }
}
