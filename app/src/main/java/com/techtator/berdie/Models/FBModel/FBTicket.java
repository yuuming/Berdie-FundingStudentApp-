package com.techtator.berdie.Models.FBModel;


import java.util.HashMap;
import java.util.Map;
import com.google.firebase.database.Exclude;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBTicket extends FBObject {
    private String ticketId;
    private String userId;
    private double price;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public FBTicket(){}

    public FBTicket(String ticketId, String userId, double price) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.price = price;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ticket_id", ticketId);
        result.put("user_id", userId);
        result.put("price", price);

        return result;
    }
}
