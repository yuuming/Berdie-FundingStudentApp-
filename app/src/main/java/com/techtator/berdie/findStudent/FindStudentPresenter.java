package com.techtator.berdie.findStudent;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.techtator.berdie.Models.FBModel.FBProfile;
import com.techtator.berdie.model.ProfileDataModel;
import com.techtator.berdie.model.UserDataModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by user on 2018-04-01.
 */

public class FindStudentPresenter implements FindStudentContract.Presenter {
    private FindStudentContract.View view;
    UserDataModel userDataModel;
    ProfileDataModel profileDataModel;
    List<ProfileEntity> mValues;
    List<ProfileEntity> initialList;


    public FindStudentPresenter(final FindStudentContract.View view, LifecycleOwner lifecycleOwner) {
        this.view = view;
        this.mValues = new LinkedList<>();
        this.initialList = new LinkedList<>();
        userDataModel = new UserDataModel();
        userDataModel.refleshUserMap(lifecycleOwner);
        profileDataModel = new ProfileDataModel();
        profileDataModel.getProfileList().observe(lifecycleOwner, new Observer<List<FBProfile>>() {
            @Override
            public void onChanged(@Nullable List<FBProfile> list) {
                mValues.clear();
                initialList.clear();
                for (FBProfile profile : list) {
                    ProfileEntity profileEntity = new ProfileEntity(profile.getId(), userDataModel.getUserById(profile.getUserId()), profile.getMajor(), profile.getUniversity(), profile.getBody());
                    mValues.add(profileEntity);
                    initialList.add(profileEntity);
                }
                view.notifyDataChanged();
            }
        });
    }



    @Override
    public ProfileEntity getProfile(int position) {  // 8
        return mValues.get(position);
    }

    @Override
    public int size() { // 9
        return mValues.size();
    }

    public void searchText(String text) {
        //mValues.clear();
        mValues = profileDataModel.findProfileEntityByWord(text, mValues);
        view.notifyDataChanged();

    }

    public void restoreProfileEntityList() {
        mValues.clear();
        mValues.addAll(initialList);
    }

}

