package com.techtator.berdie.Models.FBModel;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBRaffleDistribution extends FBObject {
    private String id;
    private String raffleId;
    private double percentage;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRaffleId() {
        return raffleId;
    }

    public void setRaffleId(String raffleId) {
        this.raffleId = raffleId;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public FBRaffleDistribution() {

    }

    public FBRaffleDistribution(String id, String raffleId, double percentage) {
        this.id = id;
        this.raffleId = raffleId;
        this.percentage = percentage;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("raffle_id", raffleId);
        result.put("percentage", percentage);

        return result;
    }
}
