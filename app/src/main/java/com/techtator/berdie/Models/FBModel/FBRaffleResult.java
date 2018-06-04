package com.techtator.berdie.Models.FBModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBRaffleResult extends FBObject{

    private String raffleId;
    private String userId;
    private String ticketId;
    private double amount;
    private String raffleResultId;


    public FBRaffleResult(){}


    public FBRaffleResult(String raffleId, String userId, String ticketId, double amount, String raffleResultId) {
        this.raffleId = raffleId;
        this.userId = userId;
        this.ticketId = ticketId;
        this.amount = amount;
        this.raffleResultId = raffleResultId;
    }

    public String getRaffleId() {
        return raffleId;
    }

    public void setRaffleId(String raffleId) {
        this.raffleId = raffleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRaffleResultId() {
        return raffleResultId;
    }

    public void setRaffleResultId(String raffleResultId) {
        this.raffleResultId = raffleResultId;
    }
    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("raffle_id", raffleId);
        result.put("user_id", userId);
        result.put("ticket_id", ticketId);
        result.put("amount", amount);
        result.put("raffleResultId", raffleResultId);

        return result;
    }

}
