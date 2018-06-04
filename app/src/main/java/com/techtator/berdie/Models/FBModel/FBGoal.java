package com.techtator.berdie.Models.FBModel;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBGoal extends FBObject implements Serializable {
    private String id;
    private String userId;
    private String header;
    private String body;
    private double currentAmount;
    private boolean isAccomplished;
    private boolean isActive;
    private boolean isPrimary;
    private double amount;
    private Date timeStamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public boolean isAccomplished() {
        return isAccomplished;
    }

    public void setAccomplished(boolean accomplished) {
        isAccomplished = accomplished;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
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

    public FBGoal(){}

    public FBGoal(String id, String userId, String header, String body, double currentAmount, boolean isAccomplished, boolean isActive, boolean isPrimary, double amount, Date timeStamp) {
        this.id = id;
        this.userId = userId;
        this.header = header;
        this.body = body;
        this.currentAmount = currentAmount;
        this.isAccomplished = isAccomplished;
        this.isActive = isActive;
        this.isPrimary = isPrimary;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("user_id", userId);
        result.put("header", header);
        result.put("body", body);
        result.put("current_amount", currentAmount);
        result.put("is_accomplished", isAccomplished);
        result.put("is_active", isActive);
        result.put("is_primary", isPrimary);
        result.put("amount", amount);
        result.put("time_stamp", timeStamp.getTime());

        return result;
    }
}
