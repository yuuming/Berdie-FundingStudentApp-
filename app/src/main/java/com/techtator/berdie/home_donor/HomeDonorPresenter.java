package com.techtator.berdie.home_donor;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.techtator.berdie.Models.FBModel.FBProfile;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.findStudent.ProfileEntity;
import com.techtator.berdie.homeStudent.HomeStudentPresenter;
import com.techtator.berdie.model.FBLiveData;
import com.techtator.berdie.model.ProfileDataModel;
import com.techtator.berdie.model.UserDataModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HomeDonorPresenter implements HomeDonorContract.Presenter {

    private HomeDonorContract.View view;
    UserDataModel userDataModel;
    private final ProfileDataModel profileDataModel;

    private LifecycleOwner lifecycleOwner;
    private List<FBUser> studentsList;

    public HomeDonorPresenter(final HomeDonorContract.View view, LifecycleOwner lifecycleOwner) {

        this.view = view;
        this.userDataModel = new UserDataModel();
        this.userDataModel.refleshUserMap(lifecycleOwner);
        this.profileDataModel = new ProfileDataModel();
        this.lifecycleOwner = lifecycleOwner;


        //6 studentsList
        studentsList = new LinkedList<>();
        userDataModel = new UserDataModel();
        final LiveData<List<FBUser>> liveData = userDataModel.setStudentsByLimitedNumber(0);
        liveData.observe(lifecycleOwner, new Observer<List<FBUser>>() {
            @Override
            public void onChanged(@Nullable List<FBUser> list) {
                studentsList.clear();
                studentsList.addAll(list);
                view.notifyStudentDataChanged();
            }
        });

    }




    @Override
    public FBUser get(int position) {
        return studentsList.get(position);
    }

    @Override
    public int size() {
        return studentsList.size();
    }

    @Override
    public void goToStudentProfile(final FBUser fbUser) {
        this.profileDataModel.getProfileByUserId(fbUser.getId()).observe(this.lifecycleOwner, new Observer<FBProfile>() {
            @Override
            public void onChanged(@Nullable FBProfile fbProfile) {
                view.goToStudentProfile(fbProfile, fbUser);
            }
        });
    }
}
