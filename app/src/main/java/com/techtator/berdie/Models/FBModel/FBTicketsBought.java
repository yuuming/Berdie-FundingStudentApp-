package com.techtator.berdie.Models.FBModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuminakamura on 2018-03-30.
 */

public class FBTicketsBought extends FBObject{
    private String ticket_bought_id;
    private String ticket_id;
    private String raffle_id;
    private double price;
    private String user_id;

    public FBTicketsBought() {
    }

    public FBTicketsBought(String ticket_bought_id, String ticket_id, String raffle_id, double price, String user_id) {
        this.ticket_bought_id = ticket_bought_id;
        this.ticket_id = ticket_id;
        this.raffle_id = raffle_id;
        this.price = price;
        this.user_id = user_id;
    }

    public String getTicket_bought_id() {
        return ticket_bought_id;
    }

    public void setTicket_bought_id(String ticket_bought_id) {
        this.ticket_bought_id = ticket_bought_id;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getRaffle_id() {
        return raffle_id;
    }

    public void setRaffle_id(String raffle_id) {
        this.raffle_id = raffle_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("price", price);
        result.put("raffle_id", raffle_id);
        result.put("ticket_id", ticket_id);
        result.put("user_id", user_id);
        result.put("ticket_bought_id", ticket_bought_id);

        return result;
    }

}
