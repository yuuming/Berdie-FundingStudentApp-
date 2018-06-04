package com.techtator.berdie.Models.FBModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBWallet  extends FBObject{

    private String walletId;
    private String userId;
    private double currentBalance;


    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public FBWallet() {
    }

    public FBWallet(String walletId, String userId, double currentBalance) {
        this.walletId = walletId;
        this.userId = userId;
        this.currentBalance = currentBalance;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", walletId);
        result.put("user_id", userId);
        result.put("current_balance", currentBalance);

        return result;
    }

}
