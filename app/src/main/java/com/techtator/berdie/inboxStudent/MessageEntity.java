package com.techtator.berdie.inboxStudent;

import android.support.annotation.Nullable;

import com.techtator.berdie.Models.FBModel.FBUser;

import java.util.Date;

/**
 * Created by user on 2018-04-18.
 */

public class MessageEntity {

    private String messageId;
    private String conversationId;
    @Nullable
    private FBUser sender;
    @Nullable
    private FBUser receiver;
    private String body;
    private Date timeStamp;

    public void setSender(@Nullable FBUser sender) {
        this.sender = sender;
    }

    @Nullable
    public FBUser getReceiver() {
        return receiver;
    }

    public void setReceiver(@Nullable FBUser receiver) {
        this.receiver = receiver;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    private boolean isRead;

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

    public FBUser getSender() {
        return sender;
    }

    public void setSender(String senderId) {
        this.sender = sender;
    }

    public FBUser getReceiverId() {
        return receiver;
    }

    public void setReceiverId(FBUser receiverId) {
        this.receiver = receiverId;
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

    public MessageEntity() {
    }

    public MessageEntity(String messageId, String conversationId, FBUser sender, FBUser receiver, String body, Date timeStamp, boolean isRead) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
        this.timeStamp = timeStamp;
        this.isRead = isRead;
    }




}