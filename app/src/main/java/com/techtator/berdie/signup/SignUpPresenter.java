package com.techtator.berdie.signup;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.model.GoalDataModel;
import com.techtator.berdie.model.ProfileDataModel;
import com.techtator.berdie.model.TransactionHistoryDataModel;
import com.techtator.berdie.model.UserDataModel;
import com.techtator.berdie.model.WalletDataModel;

import java.util.Date;

/**
 * Created by yuminakamura on 2018-04-01.
 */

public class SignUpPresenter implements SignUpContract.Presenter {
    private final ProfileDataModel profileDataModel;
    private final GoalDataModel goalDataModel;
    private final TransactionHistoryDataModel transactionHistoryDataModel;
    private SignUpContract.View view;
    UserDataModel userDataModel;
    WalletDataModel walletDataModel;

    public SignUpPresenter(SignUpContract.View view) {
        this.view = view;
        this.userDataModel = new UserDataModel();
        walletDataModel = new WalletDataModel();
        profileDataModel = new ProfileDataModel();
        goalDataModel = new GoalDataModel();
        transactionHistoryDataModel = new TransactionHistoryDataModel();
    }

    public boolean validInput(String firstname, String lastname, String birthdate, String email, String phone) {
        return !firstname.isEmpty() && !lastname.isEmpty() && !birthdate.isEmpty() && !email.isEmpty() && !phone.isEmpty();
    }

    @Override
    public boolean createUser(String userId, long selectedItemId, String firstname, String lastname, String date, String email, String phone) {
        if (validInput(firstname, lastname, date, email, phone)) {
            String walletId = walletDataModel.addWallet(userId, 1000.0);
            transactionHistoryDataModel.addTransactionHistory("", UserAuthManager.getInstance().getUserId(), UserAuthManager.getInstance().getUserId(), 1000.0);
            this.userDataModel.addUser(userId, firstname, lastname,
                    "communityid", date, "geotag", true,
                    "WE_DONT_USE", phone, String.valueOf(selectedItemId), walletId, email, "https://randomuser.me/api/portraits/men/37.jpg");

            profileDataModel.addProfile( userId, "", "", "");
            goalDataModel.addGoal(userId, "New Goal", "", 0, false, true, true, 0, new Date());

            view.onSuccess();
            return true;
        } else {
            view.onError();
            return false;
        }
    }
}
