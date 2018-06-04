package com.techtator.berdie.accountSetting;

import com.techtator.berdie.Models.FBModel.FBAccountSettings;

/**
 * Created by bominkim on 2018-04-04.
 */

public interface AccountSettingContract {
    interface View {
        void onSuccess();
        void onError();
        void notifyFirstNameChange(String firstName);
        void notifyLastNameChange(String lastName);
        void notifyEmailChange(String email);
        void notifyPhoneChange(String phone);
        void notifyAddressChange(String address);
        void notifyCityChange(String city);
        void notifyZipCodeChange(String zipCode);
        void notifyStateChange(String state);
        void notifyBalanceChange(String balance);

    }

    interface Presenter {
        FBAccountSettings getAccountSettings(int i);
        int size();
    }
}
