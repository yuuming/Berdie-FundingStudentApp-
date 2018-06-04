package com.techtator.berdie.history;

import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.allHistory.TransactionHistoryEntity;

import java.util.List;

public interface HistoryContract {
    interface View {
        void notifyHistoriesChanged(List<TransactionHistoryEntity> list);

        void notifyGoalChanged(FBGoal fbGoal);
    }

    interface Presenter {
        TransactionHistoryEntity get(int position);
        int size();
    }
}
