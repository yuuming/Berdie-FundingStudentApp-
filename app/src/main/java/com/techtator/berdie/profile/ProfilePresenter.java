package com.techtator.berdie.profile;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.Models.FBModel.FBProfile;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.model.GoalDataModel;
import com.techtator.berdie.model.ProfileDataModel;
import com.techtator.berdie.model.UserDataModel;

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View view;
    ProfileDataModel profileDataModel;
    UserDataModel userDataModel;
    GoalDataModel goalDataModel;

    public ProfilePresenter(final ProfileContract.View view, String userId, LifecycleOwner owner) {
        this.view = view;
        this.profileDataModel = new ProfileDataModel();
        this.userDataModel = new UserDataModel();
        this.goalDataModel = new GoalDataModel();

        profileDataModel.getProfileByUserId(userId).observe(owner, new Observer<FBProfile>() {
            @Override
            public void onChanged(@Nullable FBProfile data) {
                view.notifyProfileChanged(data);
            }
        });

        userDataModel.setUserById(userId).observe(owner, new Observer<FBUser>() {
            @Override
            public void onChanged(@Nullable FBUser fbUser) {
                view.notifyUserChanged(fbUser);
            }
        });

        goalDataModel.getPrimaryGoalByUserId(userId).observe(owner, new Observer<FBGoal>() {
            @Override
            public void onChanged(@Nullable FBGoal data) {
                view.notifyGoalPrimaryChanged(data);

            }
        });
    }



    public int getGoalProgress(FBGoal goal){
        return goalDataModel.getProgressPercentage(goal);
    }
}
