package com.techtator.berdie.editProfile;

import android.content.Context;
import android.content.Intent;

import com.techtator.berdie.Models.FBModel.FBProfile;
import com.techtator.berdie.Models.FBModel.FBUser;

/**
 * Created by bominkim on 2018-04-03.
 */

public interface EditProfileContract {
    interface View {
        void onSuccess();
        void onError();
        void notifyUserChanged(FBUser user);
        void notifyProfileChanged(FBProfile profile);

    }

    interface Presenter {
        void updateProfile(String school, String major, String description, String userId);

        void updateUser(String profile_pic, String userId);

        }
    }
