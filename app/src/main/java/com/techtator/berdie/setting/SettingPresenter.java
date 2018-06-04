package com.techtator.berdie.setting;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.model.UserDataModel;

/**
 * Created by user on 2018-04-09.
 */

public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View view;
    UserDataModel userDataModel;


    public SettingPresenter(final SettingContract.View view, String uid, LifecycleOwner owner) {
        this.view = view;
        this.userDataModel = new UserDataModel();
        userDataModel.setUserById(uid).observe(owner, new Observer<FBUser>() {
            @Override
            public void onChanged(@Nullable FBUser fbUser) {
                view.notifyUserChanged(fbUser);
            }
        });
    }
}
