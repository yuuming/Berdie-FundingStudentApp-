package com.techtator.berdie.Models.FBModel;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBMessage extends FBObject{

    private String messageId;
    private String conversationId;
    private String senderId;
    private String receiverId;
    private String body;
    private Date timeStamp;
    private boolean isRead;

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isSenderSystem() {
        return this.senderId.contains("_SYSTEM");
    }

    public FBMessage() {
    }

    public FBMessage(String messageId, String conversationId, String senderId, String receiverId, String body, Date timeStamp, boolean isRead) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.body = body;
        this.timeStamp = timeStamp;
        this.isRead = isRead;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("message_id", messageId);
        result.put("conversation_id", conversationId);
        result.put("sender_id", senderId);
        result.put("receiver_id", receiverId);
        result.put("body", body);
        result.put("time_stamp", timeStamp.getTime());
        result.put("is_read", isRead);

        return result;
    }

    @Override
    public String toString() {
        return "FBMessage{" +
                "messageId='" + messageId + '\'' +
                ", conversationId='" + conversationId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", body='" + body + '\'' +
                ", timeStamp=" + timeStamp +
                ", isRead=" + isRead +
                '}';
    }
}
