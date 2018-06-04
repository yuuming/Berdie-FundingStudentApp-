package com.techtator.berdie.topPage;

import com.techtator.berdie.Database.DataModel;
import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.model.UserDataModel;

public class TopPagePresenter implements TopPageContract.Presenter {
    private TopPageContract.View view;
    UserDataModel userDataModel;

    public TopPagePresenter(TopPageContract.View v, UserDataModel userDataModel) {
        this.view = v;
        this.userDataModel = userDataModel;

    }

    @Override
    public void onLogin(final String uid) {
        this.userDataModel.findUserById(uid, new UserDataModel.OnFoundDataListener() {
            @Override
            public void notifyFoundUser(FBUser data) {
                UserAuthManager.getInstance().setMyself(data);
                view.onExistingUser(data);
            }

            @Override
            public void notifyNotFoundUser() {
                view.onNoExistanceUser(uid);
            }

            @Override
            public void notifyError(String info) {
                view.onError();
            }
        });
    }

    @Override
    public void onLoginWithoutView(final String uid) {
        this.userDataModel.findUserById(uid, new UserDataModel.OnFoundDataListener() {
            @Override
            public void notifyFoundUser(FBUser data) {
                UserAuthManager.getInstance().setMyself(data);
            }

            @Override
            public void notifyNotFoundUser() {

            }

            @Override
            public void notifyError(String info) {

            }
        });
    }

}

