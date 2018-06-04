package com.techtator.berdie.scholarship;

import android.arch.lifecycle.LifecycleOwner;

import com.techtator.berdie.Models.FBModel.FBScholarship;

/**
 * Created by bominkim on 2018-04-10.
 */

public interface ScholarshipContract {

    interface View{
        void notifyScholarshipDataChanged();
    }

    interface Presenter{
        FBScholarship get(int position);
        int size();
        void searchText(String text, LifecycleOwner owner);
        void restoreScholarshipList(LifecycleOwner owner);
    }
}
