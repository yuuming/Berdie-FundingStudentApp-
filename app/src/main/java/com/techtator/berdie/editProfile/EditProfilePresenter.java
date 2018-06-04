package com.techtator.berdie.editProfile;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.techtator.berdie.Models.FBModel.FBProfile;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.model.ProfileDataModel;
import com.techtator.berdie.model.UserDataModel;


public class EditProfilePresenter implements EditProfileContract.Presenter {

    private EditProfileContract.View view;
    ProfileDataModel profileDataModel;
    UserDataModel userDataModel;
    FBProfile fbProfile = new FBProfile();
    FBUser fbUser = new FBUser();




    public EditProfilePresenter(final EditProfileContract.View view, String userId, LifecycleOwner owner) {
        this.view = view;
        this.profileDataModel = new ProfileDataModel();
        this.userDataModel = new UserDataModel();

        profileDataModel = new ProfileDataModel();
        profileDataModel.getProfileByUserId(userId).observe(owner, new Observer<FBProfile>() {
            @Override
            public void onChanged(@Nullable FBProfile data) {
                fbProfile = data;
                view.notifyProfileChanged(data);
            }
        });

        userDataModel.setUserById(userId).observe(owner, new Observer<FBUser>() {
            @Override
            public void onChanged(@Nullable FBUser data) {
                fbUser = data;
                view.notifyUserChanged(data);
            }
        });
    }


    @Override
    public void updateUser(String profile_pic, String userId) {
        userDataModel.updateUser(userId,fbUser.getFirstName(),fbUser.getLastName(),fbUser.getCommunityId(),fbUser.getDateOfBirth(),fbUser.getGeotag(),fbUser.isActive(),fbUser.getPassword(),fbUser.getPhone(),fbUser.getTimeStamp(),fbUser.getUserRole(),fbUser.getWalletId(),fbUser.getEmail(),profile_pic);
    }

    @Override
    public void updateProfile(String school, String major, String description, String userId) {
        profileDataModel.updateProfile(fbProfile.getId(),userId,major,school,description);
    }
}
