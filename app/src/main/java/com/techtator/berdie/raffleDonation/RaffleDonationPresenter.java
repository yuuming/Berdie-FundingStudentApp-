package com.techtator.berdie.raffleDonation;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBRaffle;
import com.techtator.berdie.Models.FBModel.FBWallet;
import com.techtator.berdie.model.FBLiveData;
import com.techtator.berdie.model.RaffleDataModel;
import com.techtator.berdie.model.TransactionHistoryDataModel;
import com.techtator.berdie.model.WalletDataModel;

public class RaffleDonationPresenter implements RaffleDonationContract.Presenter {
    private RaffleDonationContract.View view;
    RaffleDataModel mRaffleDataModel;
    TransactionHistoryDataModel mTransactionHistoryDataModel;
    WalletDataModel mWalletDataModel;
    FBRaffle mTopRaffle;
    FBLiveData<FBWallet> mWallet;

    public RaffleDonationPresenter(RaffleDonationContract.View view, LifecycleOwner owner) {
        this.view = view;
        this.mRaffleDataModel = new RaffleDataModel();
        this.mTransactionHistoryDataModel = new TransactionHistoryDataModel();
        this.mWalletDataModel = new WalletDataModel();
        mRaffleDataModel.getTopRaffle().observe(owner, new Observer<FBRaffle>() {
            @Override
            public void onChanged(@Nullable FBRaffle fbRaffle) {
                mTopRaffle = fbRaffle;
            }
        });
        mWallet = mWalletDataModel.getWalletByUserId(UserAuthManager.getInstance().getUserId());

    }
}
