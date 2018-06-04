package com.techtator.berdie.StudentProfile;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.Models.FBModel.FBProfile;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.findStudent.ProfileEntity;
import com.techtator.berdie.model.GoalDataModel;
import com.techtator.berdie.model.ProfileDataModel;
import com.techtator.berdie.model.UserDataModel;

public class StudentProfilePresenter implements StudentProfileContract.Presenter {

    private StudentProfileContract.View view;
    ProfileDataModel profileDataModel;
    UserDataModel userDataModel;
    GoalDataModel goalDataModel;
    ProfileEntity profileEntity;
    FBGoal goal;

    public StudentProfilePresenter(final StudentProfileContract.View view, ProfileEntity profileEntity, LifecycleOwner owner) {
        this.view = view;
        this.profileDataModel = new ProfileDataModel();
        this.goalDataModel = new GoalDataModel();
        this.profileEntity = profileEntity;
        userDataModel = new UserDataModel();

        profileDataModel.getProfileByUserId(profileEntity.getUser().getId()).observe(owner, new Observer<FBProfile>() {
            @Override
            public void onChanged(@Nullable FBProfile fbProfile) {
                view.notifyProfileChanged(fbProfile);
            }
        });

        userDataModel.setUserById(profileEntity.getUser().getId()).observe(owner, new Observer<FBUser>() {
            @Override
            public void onChanged(@Nullable FBUser fbUser) {
                view.notifyUserChanged(fbUser);
            }
        });

        goalDataModel.getPrimaryGoalByUserId(profileEntity.getUser().getId()).observe(owner, new Observer<FBGoal>() {
            @Override
            public void onChanged(@Nullable FBGoal fbGoal) {
                goal = fbGoal;
                view.notifyGoalPrimaryChanged(fbGoal);
            }
        });

    }

    public int getGoalProgress(FBGoal goal){
        return goalDataModel.getProgressPercentage(goal);
    }


}
