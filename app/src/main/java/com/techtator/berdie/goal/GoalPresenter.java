package com.techtator.berdie.goal;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.model.GoalDataModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 2018-04-01.
 */

public class GoalPresenter implements GoalContract.Presenter {
    private GoalContract.View view;
    GoalDataModel goalDataModel;
    List<FBGoal> mValues;
    List<FBGoal> onGoingGoals;
    List<FBGoal> accomplishedGoals;

    public GoalPresenter(final GoalContract.View view, LifecycleOwner owner) {
        this.view = view;
        this.mValues = new LinkedList<>();
        this.onGoingGoals = new LinkedList<>();
        this.accomplishedGoals = new LinkedList<>();
        goalDataModel = new GoalDataModel();

        goalDataModel.getAllGoalsByUserId(UserAuthManager.getInstance().getUserId()).observe(owner, new Observer<List<FBGoal>>() {
            @Override
            public void onChanged(@Nullable List<FBGoal> list) {
                mValues.clear();
                mValues.addAll(list);
                onGoingGoals.clear();
                onGoingGoals.addAll(goalDataModel.mapGoals(mValues, false));
                accomplishedGoals.clear();
                accomplishedGoals.addAll(goalDataModel.mapGoals(mValues, true));
                view.notifyDataChanged();
            }
        });
    }

    public FBGoal getGoalFromOngoingList(int position) {
        return onGoingGoals.get(position);
    }

    public FBGoal getGoalFromAccomplishedList(int position) {
        return accomplishedGoals.get(position);
    }

    public int getGoalProgress(FBGoal goal){
        return goalDataModel.getProgressPercentage(goal);
    }

    public void changePrimaryGoal(List<FBGoal> goalList, int position) {
        goalDataModel.changePrimaryGoal(goalList, position);
    }


    @Override
    public FBGoal getGoal(int position) {  // 8
        return mValues.get(position);
    }

    @Override
    public int size() { // 9
        return mValues.size();
    }

}