package com.techtator.berdie.accountSetting;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBAccountSettings;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.Models.FBModel.FBWallet;
import com.techtator.berdie.model.AccountSettingsDataModel;
import com.techtator.berdie.model.UserDataModel;
import com.techtator.berdie.model.WalletDataModel;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;

public class AccountSettingPresenter implements AccountSettingContract.Presenter {

    private AccountSettingContract.View view;
    AccountSettingsDataModel accountSettingsDataModel;
    WalletDataModel walletDataModel;
    UserDataModel userDataModel;
    FBUser user;
    List<FBAccountSettings> accountSettingsList;
    List<FBWallet> walletList;
    FBWallet mWallet;
    FBAccountSettings mAccountSettings;

    public AccountSettingPresenter(AccountSettingContract.View mView) {
        this.view = mView;
        accountSettingsDataModel = new AccountSettingsDataModel();
        walletDataModel = new WalletDataModel();
        accountSettingsList = new LinkedList<>();
        walletList = new LinkedList<>();
        userDataModel = new UserDataModel();
        userDataModel.findUserById(UserAuthManager.getInstance().getUserId(), new UserDataModel.OnFoundDataListener() {
            @Override
            public void notifyFoundUser(FBUser data) {
                user = data;
                view.notifyFirstNameChange(user.getFirstName());
                view.notifyLastNameChange(user.getLastName());
                view.notifyEmailChange(user.getEmail());
                view.notifyPhoneChange(user.getPhone());
            }

            @Override
            public void notifyNotFoundUser() {

            }

            @Override
            public void notifyError(String info) {

            }
        });
        accountSettingsDataModel.setAccountSettingsList(new AccountSettingsDataModel.OnChangeListListener() {
            @Override
            public void notifyChangedList(List<FBAccountSettings> list) {
                accountSettingsList.addAll(list);
                for(FBAccountSettings a : list){
                    if (a.getUserId().equals(UserAuthManager.getInstance().getUserId())){
                        mAccountSettings= a;
                        view.notifyAddressChange(mAccountSettings.getAddress());
                        view.notifyCityChange(mAccountSettings.getCity());
                        view.notifyZipCodeChange(mAccountSettings.getZipCode());
                        view.notifyStateChange(mAccountSettings.getState());
                    }
                }
            }
        });
        walletDataModel.setWalletList(new WalletDataModel.OnChangeListListener() {
            @Override
            public void notifyChangedList(List<FBWallet> list) {
                walletList.addAll(list);
                for (FBWallet w : list){
                    if(w.getUserId().equals(UserAuthManager.getInstance().getUserId())){
                        mWallet = w;
                        view.notifyBalanceChange(String.valueOf(mWallet.getCurrentBalance()));
                    }
                }
            }
        });
    }

    public void updateAccountSettings(String _firstName,String _lastName,String _email,String _phone,String _address,String  _city,String _zipcode,String _state){
        try{
            userDataModel.updateUser(user.getId(),_firstName,_lastName,user.getCommunityId(),user.getDateOfBirth(),user.getGeotag(),user.isActive(),user.getPassword(),_phone,user.getTimeStamp(),user.getUserRole(),user.getWalletId(),_email, user.getProfilePic());
            accountSettingsDataModel.updateAccountSettings(mAccountSettings.getSettingsId(),mAccountSettings.getUserId(),_address,_city,_zipcode,_state);
        }
        catch (Exception e){}

    }


    public void updateWalletBalance(String currentBalance, String balanceToBeAdded){
        try{
            double newValue = Double.valueOf(currentBalance) + Double.valueOf(balanceToBeAdded);
            mWallet.setCurrentBalance(newValue);
            walletDataModel.updateWallet(mWallet);
        }
        catch (Exception e){}
    }

    @Override
    public FBAccountSettings getAccountSettings(int i) {
        return accountSettingsList.get(i);
    }

    @Override
    public int size() {
        return accountSettingsList.size();
    }
}
