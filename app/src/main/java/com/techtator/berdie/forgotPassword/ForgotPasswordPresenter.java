package com.techtator.berdie.forgotPassword;

import com.techtator.berdie.Database.DataModel;
import com.techtator.berdie.model.UserDataModel;

/**
 * Created by yuminakamura on 2018-04-01.
 */

public class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter{

    private ForgotPasswordContract.View view;
    UserDataModel userDataModel;

    public ForgotPasswordPresenter(ForgotPasswordContract.View view, UserDataModel userDataModel) {
        this.view = view;
        this.userDataModel = userDataModel;
    }

}
