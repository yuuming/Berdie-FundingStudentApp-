package com.techtator.berdie.profile;

import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.Models.FBModel.FBProfile;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.model.MessageDataModel;

/**
 * Created by bominkim on 2018-04-03.
 */

public interface ProfileContract {
    interface View {
        void onSuccess();
        void onError();
        void notifyProfileChanged(FBProfile profile);
        void notifyUserChanged(FBUser user);
        void notifyGoalPrimaryChanged(FBGoal goal);

    }

    interface Presenter {

    }
}
