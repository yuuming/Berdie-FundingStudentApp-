package com.techtator.berdie.setting;

import com.techtator.berdie.Models.FBModel.FBUser;

/**
 * Created by user on 2018-04-09.
 */

public interface SettingContract {
    public interface View {
        void notifyUserChanged(FBUser user);
    }

    public interface Presenter {
    }
}
