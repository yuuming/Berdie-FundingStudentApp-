package com.techtator.berdie.home_donor;


import com.techtator.berdie.Models.FBModel.FBProfile;
import com.techtator.berdie.Models.FBModel.FBUser;

public interface HomeDonorContract {
    interface View {
        void onSuccess();
        void onError();
        void notifyStudentDataChanged();
        void notifyDonorAmountChanged(String totalDonationAmount);

        void goToStudentProfile(FBProfile fbProfile, FBUser fbUser);
    }

    interface Presenter {

        FBUser get(int position);
        int size();

        void goToStudentProfile(FBUser fbUser);
    }
}
