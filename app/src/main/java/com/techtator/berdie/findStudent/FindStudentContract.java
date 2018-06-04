package com.techtator.berdie.findStudent;

import android.arch.lifecycle.LifecycleOwner;

/**
 * Created by user on 2018-04-01.
 */

public interface FindStudentContract {
    interface View {
        void notifyDataChanged();
    }

    interface Presenter {
        ProfileEntity getProfile(int position);
        int size();
    }
}
