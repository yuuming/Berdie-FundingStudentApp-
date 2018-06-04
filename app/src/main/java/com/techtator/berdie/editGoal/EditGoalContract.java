package com.techtator.berdie.editGoal;

import android.text.Editable;

import com.techtator.berdie.Models.FBModel.FBGoal;

import java.util.Date;

public interface EditGoalContract {
    interface View{
        void setTitle(String header);
        void setAmount(double amount);
        void setBody(String body);
    }

    interface Presenter{
        void updateGoal(String id, String userId, String header, String body, double currentAmount, boolean isAccomplished, boolean isActive, boolean isPrimary, double amount, Date timeStamp);
    }
}
