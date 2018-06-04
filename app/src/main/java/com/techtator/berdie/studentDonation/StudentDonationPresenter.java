package com.techtator.berdie.studentDonation;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.Models.FBModel.FBWallet;
import com.techtator.berdie.model.GoalDataModel;
import com.techtator.berdie.model.MessageDataModel;
import com.techtator.berdie.model.TransactionHistoryDataModel;
import com.techtator.berdie.model.WalletDataModel;

import java.util.ArrayList;
import java.util.List;

public class StudentDonationPresenter implements StudentDonationContract.Presenter {
    private StudentDonationContract.View view;
    GoalDataModel goalDataModel;
    TransactionHistoryDataModel transactionHistoryDataModel;
    MessageDataModel messageDataModel;
    WalletDataModel counterWalletDataModel;
    WalletDataModel myWalletDataModel;
    FBGoal goal;
    FBWallet wallet;
    int donationAmount;
    ArrayList<FBGoal> goals = new ArrayList<>();;
    LifecycleOwner owner;
    FBUser myself;
    boolean executedDonate;
    boolean executedAddGoal;

    public StudentDonationPresenter(int donationAmount, FBUser user, FBUser myself, StudentDonationContract.View view, LifecycleOwner owner){
        this.donationAmount = donationAmount;
        this.view = view;
        this.goalDataModel = new GoalDataModel();
        this.transactionHistoryDataModel = new TransactionHistoryDataModel();
        this.messageDataModel = new MessageDataModel();
        this.counterWalletDataModel = new WalletDataModel();
        this.owner = owner;
        goal = goalDataModel.getPrimaryGoalByUserId(user.getId()).getValue();
        counterWalletDataModel.getWalletByUserId(user.getId()).observe(owner, new Observer<FBWallet>() {
            @Override
            public void onChanged(@Nullable FBWallet fBWallet) {
                wallet = fBWallet;
            }
        });
        this.myself = myself;
        myWalletDataModel = new WalletDataModel();
        executedDonate = false;
        executedAddGoal = false;
    }

    public void donate(final int amount, FBUser student, final FBUser donor) {
        // update goals // TODO use AddValueListener with SingleValue
        goalDataModel.getAllGoalsByUserId(student.getId()).observe(owner, new Observer<List<FBGoal>>() {
            @Override
            public void onChanged(@Nullable List<FBGoal> fbGoals) {
                if (executedAddGoal==false) {
                    goals.addAll(fbGoals);
                    goalDataModel.addMoneyToGoals(goals, amount);
                    executedAddGoal = true;
                }
            }
        });
        // add transactionHistory
        transactionHistoryDataModel.addTransactionHistory("", donor.getId(), student.getId(), amount);
        // notify the student(send message to the student)
        messageDataModel.sendDonationMessageFromSystem(donor, student, amount);
        // update wallet TODO use transaction
        myWalletDataModel.getWalletByUserId(myself.getId()).observe(owner, new Observer<FBWallet>() {
            @Override
            public void onChanged(@Nullable FBWallet wallet) {
                if (executedDonate==false) {
                    wallet.setCurrentBalance(wallet.getCurrentBalance() - amount);
                    myWalletDataModel.updateWallet(wallet);
                    executedDonate = true;
                }
            }
        });
    }

}
