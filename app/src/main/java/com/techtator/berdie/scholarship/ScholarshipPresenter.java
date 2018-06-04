package com.techtator.berdie.scholarship;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.techtator.berdie.Models.FBModel.FBScholarship;
import com.techtator.berdie.model.ScholarshipDataModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by bominkim on 2018-04-10.
 */

public class ScholarshipPresenter implements ScholarshipContract.Presenter{

    private ScholarshipContract.View view;
    ScholarshipDataModel scholarshipDataModel;
    List<FBScholarship> scholarshipList;
    LiveData<List<FBScholarship>> liveData;

    public ScholarshipPresenter(final ScholarshipContract.View view, LifecycleOwner owner) {
        this.view = view;
        this.scholarshipDataModel = new ScholarshipDataModel();

        scholarshipList = new LinkedList<>();

//        liveData = scholarshipDataModel.getScholarshipList();
        liveData = scholarshipDataModel.getActiveScholarshipList();
        liveData.observe(owner, new Observer<List<FBScholarship>>() {
            @Override
            public void onChanged(@Nullable List<FBScholarship> list) {
                scholarshipList.clear();
                scholarshipList.addAll(list);
                view.notifyScholarshipDataChanged();
            }
        });
    }

    //
    public FBScholarship getScholarshipList(int position) {
        return scholarshipList.get(position);
    }

    @Override
    public FBScholarship get(int position) {
        return scholarshipList.get(position);
    }

    @Override
    public int size() {
        return scholarshipList.size();
    }

    @Override
    public void searchText(String text, LifecycleOwner owner) {
        scholarshipDataModel.getActiveScholarshipsBySearchedWord(text).observe(owner, new Observer<List<FBScholarship>>() {
            @Override
            public void onChanged(@Nullable List<FBScholarship> list) {
                scholarshipList.clear();
                scholarshipList.addAll(list);
                view.notifyScholarshipDataChanged();
            }
        });
    }

    @Override
    public void restoreScholarshipList(LifecycleOwner owner) {
//        LiveData<List<FBScholarship>> liveData = scholarshipDataModel.getScholarshipList();
        LiveData<List<FBScholarship>> liveData = scholarshipDataModel.getActiveScholarshipList();
        liveData.observe(owner, new Observer<List<FBScholarship>>() {
            @Override
            public void onChanged(@Nullable List<FBScholarship> list) {
                scholarshipList.clear();
                scholarshipList.addAll(list);
                view.notifyScholarshipDataChanged();
            }
        });
    }
}
