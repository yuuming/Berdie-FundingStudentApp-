package com.techtator.berdie.addGoal;

import com.techtator.berdie.model.GoalDataModel;

import java.util.Date;

/**
 * Created by user on 2018-04-01.
 */

public class AddGoalPresenter implements AddGoalContract.Presenter {
    private AddGoalContract.View view;
    GoalDataModel addGoalDataModel;

    public AddGoalPresenter(AddGoalContract.View view, GoalDataModel addGoalDataModel) {
        this.view = view;
        this.addGoalDataModel = addGoalDataModel;
    }

    @Override
    public void addGoal(String id, String userId, String header, String body, double currentAmount, boolean isAccomplished, boolean isActive, boolean isPrimary, double amount, Date timeStamp){
        addGoalDataModel.addGoal(userId, header, body, currentAmount, isAccomplished, isActive, isPrimary, amount, timeStamp);
    }
}
