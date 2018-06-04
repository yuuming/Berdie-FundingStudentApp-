package com.techtator.berdie.StudentProfile;

import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.Models.FBModel.FBProfile;
import com.techtator.berdie.Models.FBModel.FBUser;

public interface StudentProfileContract {
    interface View {
//        void notifyProfileEntityChanged();
        void notifyProfileChanged(FBProfile profile);
        void notifyUserChanged(FBUser user);
        void notifyGoalPrimaryChanged(FBGoal goal);
    }

    interface Presenter {
    }
}
