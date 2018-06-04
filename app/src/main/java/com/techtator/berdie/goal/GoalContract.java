package com.techtator.berdie.goal;

import com.techtator.berdie.Models.FBModel.FBGoal;

/**
 * Created by user on 2018-04-01.
 */

public interface GoalContract {
    interface View {
        void notifyDataChanged();
    }

    interface Presenter {
        FBGoal getGoal(int position); // 5)
        int size();
    }
}
