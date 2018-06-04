package com.techtator.berdie.editGoal;

import android.util.Log;
import android.widget.Toast;

import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.model.GoalDataModel;

import java.util.Date;

public class EditGoalPresenter implements EditGoalContract.Presenter {
    final private EditGoalContract.View view;
    FBGoal mGoal;
    GoalDataModel goalDataModel;

    public EditGoalPresenter(final EditGoalContract.View v, String id) {
        this.view = v;
        this.goalDataModel = new GoalDataModel();
        goalDataModel.setGoalById(id, new GoalDataModel.OnChangeDataListener() {
            @Override
            public void notifyChangedData(FBGoal data) {
                v.setTitle(data.getHeader());
                v.setAmount(data.getAmount());
                v.setBody(data.getBody());
            }
        });

    }

    public int getGoalProgress(FBGoal goal){
        return goalDataModel.getProgressPercentage(goal);
    }

    @Override
    public void updateGoal(String id, String userId, String header, String body, double currentAmount, boolean isAccomplished, boolean isActive, boolean isPrimary, double amount, Date timeStamp) {
        goalDataModel.updateGoal(id, userId, header, body, currentAmount, isAccomplished, isActive, isPrimary, amount, timeStamp);
    }

}
