package com.techtator.berdie.topPage;


import com.techtator.berdie.Models.FBModel.FBUser;

public interface TopPageContract {
    interface View {
        void onNoExistanceUser(String uid);
        void onExistingUser(FBUser data);
        void onError();
    }

    interface Presenter {

        void onLogin(String uid);
        void onLoginWithoutView(String uid);
    }
}
