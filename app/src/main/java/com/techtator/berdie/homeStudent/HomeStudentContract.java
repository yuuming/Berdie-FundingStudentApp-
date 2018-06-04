package com.techtator.berdie.homeStudent;

import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.Models.FBModel.FBScholarship;
import com.techtator.berdie.Models.FBModel.FBUser;

import java.util.List;

/**
 * Created by yuminakamura on 2018-04-01.
 */

public interface HomeStudentContract {
    interface View {
        void onSuccess();
        void onError();
        void notifySchoolDataChanged();
        void notifyGoalDataChanged();

        void notifyTopGoalChange(String minimumAmountText);

        void notifyWinnersChanged(List<FBUser> users);
        void notifyRaffleRestOfTimeInterval(String timeText);
        void notifyNumberOfTicketsChange(String numberOfTicketsText);


    }

    interface Presenter {
        FBScholarship get(int position); // 5)
        int size();
        FBGoal getGoalPosition(int position);
        int goalSize();


        int sizeOfWinners();
        FBUser getWinner(int position);
    }
}
