package com.techtator.berdie.addGoal;

import java.util.Date;

/**
 * Created by user on 2018-04-01.
 */

public interface AddGoalContract {
    interface View {
        void onSuccess();

        void onError();
    }

    interface Presenter {
        void addGoal(String id, String userId, String header, String body, double currentAmount, boolean isAccomplished, boolean isActive, boolean isPrimary, double amount, Date timeStamp);
    }
}

