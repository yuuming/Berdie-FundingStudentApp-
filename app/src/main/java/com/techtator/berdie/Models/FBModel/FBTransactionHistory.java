package com.techtator.berdie.Models.FBModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBTransactionHistory  extends FBObject{

    private String transactionId;
    private String senderId;
    private String receiverId;
    private double amount;
    private Date timeStamp;

    public void addTransactionHistory(String _transactionId,String _senderId, String _receiverId,double _amount,Date _timeStamp){}

    public void updateTransactionHistory(String _transactionId,String _senderId, String _receiverId,double _amount,Date _timeStamp){}

    public void deleteTransactionHistory(String _transactionId){}

    public FBTransactionHistory() {
    }

    public FBTransactionHistory(String transactionId, String senderId, String receiverId, double amount, Date timeStamp) {
        this.transactionId = transactionId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
    
     public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("transaction_id", transactionId);
        result.put("sender_id", senderId);
        result.put("receiver_id", receiverId);
        result.put("amount", amount);
        result.put("time_stamp", timeStamp.getTime());

        return result;
    }
}
