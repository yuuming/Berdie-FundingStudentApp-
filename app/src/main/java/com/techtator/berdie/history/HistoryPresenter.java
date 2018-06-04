package com.techtator.berdie.history;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.Models.FBModel.FBTransactionHistory;
import com.techtator.berdie.allHistory.TransactionHistoryEntity;
import com.techtator.berdie.model.GoalDataModel;
import com.techtator.berdie.model.TransactionHistoryDataModel;
import com.techtator.berdie.model.UserDataModel;

import java.util.LinkedList;
import java.util.List;

public class HistoryPresenter implements HistoryContract.Presenter {
    private HistoryContract.View view;
    TransactionHistoryDataModel transactionHistoryDataModel;
    List<TransactionHistoryEntity> mValues;
    UserDataModel userDataModel;
    GoalDataModel goalDataModel;

    public HistoryPresenter(final HistoryContract.View view, String uid, String goalId, LifecycleOwner owner) {
        this.view = view;
        this.mValues = new LinkedList<>();
        transactionHistoryDataModel = new TransactionHistoryDataModel();
        userDataModel = new UserDataModel();
        userDataModel.refleshUserMap(owner);
        goalDataModel = new GoalDataModel();

        transactionHistoryDataModel.getTransactionHistoriesByReciverUserId(uid).observe(owner, new Observer<List<FBTransactionHistory>>() {
            @Override
            public void onChanged(@Nullable List<FBTransactionHistory> list) {
                mValues.clear();
                for (FBTransactionHistory th: list) {
                    TransactionHistoryEntity entity = new TransactionHistoryEntity(th.getTransactionId(), userDataModel.getUserById(th.getSenderId()), userDataModel.getUserById(th.getReceiverId()), UserAuthManager.getInstance().getUser(), th.getAmount(), th.getTimeStamp());
                    mValues.add(entity);
                }
                view.notifyHistoriesChanged(mValues);
            }
        });

        goalDataModel.getGoalById(goalId).observe(owner, new Observer<FBGoal>() {
            @Override
            public void onChanged(@Nullable FBGoal fbGoal) {
                view.notifyGoalChanged(fbGoal);
            }
        });
    }

    public int getGoalProgress(FBGoal goal){
        return goalDataModel.getProgressPercentage(goal);
    }

    @Override
    public TransactionHistoryEntity get(int position) {
        return mValues.get(position);

    }

    @Override
    public int size() {
        return mValues.size();
    }
}
