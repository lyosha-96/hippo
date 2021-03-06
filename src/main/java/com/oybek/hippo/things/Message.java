package com.oybek.hippo.things;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private long id;
    private long date;
    private long uid;
    private long readState;
    private String body;
    private Geo geo;
    private String attachment;

    @JsonProperty("mid")
    public long getId() { return id; }

    @JsonProperty("mid")
    public void setId(long id) { this.id = id; }

    @JsonProperty("date")
    public long getDate() { return date; }

    @JsonProperty("date")
    public void setDate(long date) { this.date = date; }

    @JsonProperty("uid")
    public long getUid() { return uid; }

    @JsonProperty("uid")
    public void setUid(long uid) { this.uid = uid; }

    @JsonProperty("read_state")
    public long getReadState() { return this.readState; }

    @JsonProperty("read_state")
    public void setReadState(long readState) { this.readState = readState; }

    @JsonProperty("body")
    public String getBody() { return body; }

    @JsonProperty("body")
    public Message setBody(String body) { this.body = body; return this; }

    @JsonProperty("geo")
    public Geo getGeo() { return this.geo; }

    @JsonProperty("geo")
    public void setGeo(Geo geo) { this.geo = geo; }

    public String getAttachmentt() {
        return attachment;
    }

    public Message setAttachmentt(String attachment) {
        this.attachment = attachment;
        return this;
    }

    public static Message fromScriptObjectMirror(ScriptObjectMirror o) {
        Message message = new Message();

        message.setId(((Number) o.get("mid")).longValue());
        message.setDate(((Number) o.get("date")).longValue());
        message.setUid(((Number) o.get("uid")).longValue());
        message.setReadState(((Number) o.get("read_state")).longValue());
        message.setBody(o.get("body").toString());

        if (o.hasMember("geo")) {
            message.setGeo((Geo) o.get("geo"));
        }

        if (o.hasMember("attachment")) {
            message.setAttachmentt( o.get("attachment").toString() );
        }

        return message;
    }

    @Override
    public Message clone() {
        Message message = new Message();
        message.setId(this.id);
        message.setDate(this.date);
        message.setUid(this.uid);
        message.setReadState(this.readState);
        message.setBody(this.body);
        message.setGeo(this.geo);
        message.setAttachmentt(this.attachment);
        return message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", date=" + date +
                ", uid=" + uid +
                ", readState=" + readState +
                ", body='" + body + '\'' +
                (geo != null ? ", geo=" + geo.toString() : "") +
                ", attachment='" + attachment + '\'' +
                '}';
    }
}

