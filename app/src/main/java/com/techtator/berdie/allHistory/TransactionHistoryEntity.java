package com.techtator.berdie.allHistory;

import android.support.annotation.Nullable;

import com.techtator.berdie.Models.FBModel.FBUser;

import java.util.Date;

public class TransactionHistoryEntity {
    private String transactionId;
    @Nullable
    private FBUser sender;
    @Nullable
    private FBUser receiver;
    private double amount;
    private Date timeStamp;
    private boolean isUp;

    public TransactionHistoryEntity(String transactionId, FBUser sender, FBUser receiver, FBUser myself, double amount, Date timeStamp) {
        this.transactionId = transactionId;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.isUp = myself.getId().equals(receiver.getId());
    }

    public String getTransactionId() {
        return transactionId;
    }

    public FBUser getSender() {
        return sender;
    }

    public FBUser getReceiver() {
        return receiver;
    }

    public double getAmount() {
        return amount;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public boolean isUp() { return isUp; }
}
