package com.techtator.berdie.Models.FBModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBRaffle  extends FBObject{
    private String raffleId;
    private String rafflePicture;
    private int minimumNumberOfWinners;
    private double amountCollected;
    private Date dueDate;
    private boolean isActive;

    public String getRaffleId() {
        return raffleId;
    }

    public void setRaffleId(String raffleId) {
        this.raffleId = raffleId;
    }

    public String getRafflePicture() {
        return rafflePicture;
    }

    public void setRafflePicture(String rafflePicture) {
        this.rafflePicture = rafflePicture;
    }

    public int getMinimumNumberOfWinners() {
        return minimumNumberOfWinners;
    }

    public void setMinimumNumberOfWinners(int minimumNumberOfWinners) {
        this.minimumNumberOfWinners = minimumNumberOfWinners;
    }

    public double getAmountCollected() {
        return amountCollected;
    }

    public void setAmountCollected(double amountCollected) {
        this.amountCollected = amountCollected;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public FBRaffle() {
    }

    public FBRaffle(String raffleId, String rafflePicture, int minimumNumberOfWinners, double amountCollected, Date dueDate, boolean isActive) {
        this.raffleId = raffleId;
        this.rafflePicture = rafflePicture;
        this.minimumNumberOfWinners = minimumNumberOfWinners;
        this.amountCollected = amountCollected;
        this.dueDate = dueDate;
        this.isActive = isActive;
    }

    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("raffle_id", raffleId);
        result.put("raffle_picture", rafflePicture);
        result.put("minimum_number_of_winners", minimumNumberOfWinners);
        result.put("amountCollected", amountCollected);
        result.put("due_date", dueDate.getTime());
        result.put("is_active", isActive);

        return result;
    }

    @Override
    public String toString() {
        return "FBRaffle{" +
                "raffleId='" + raffleId + '\'' +
                ", rafflePicture='" + rafflePicture + '\'' +
                ", minimumNumberOfWinners=" + minimumNumberOfWinners +
                ", amountCollected=" + amountCollected +
                ", dueDate=" + dueDate +
                ", isActive=" + isActive +
                '}';
    }
}
