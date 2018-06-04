package com.techtator.berdie.allHistory;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBTransactionHistory;
import com.techtator.berdie.Models.FBModel.FBWallet;
import com.techtator.berdie.model.TransactionHistoryDataModel;
import com.techtator.berdie.model.UserDataModel;
import com.techtator.berdie.model.WalletDataModel;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class AllHistoryPresenter implements AllHistoryContract.Presenter {
    private final WalletDataModel walletDataModel;
    private AllHistoryContract.View view;
    List<TransactionHistoryEntity> mReciverValues;
    List<TransactionHistoryEntity> mSenderValues;
    List<TransactionHistoryEntity> mMergedValues;

    TransactionHistoryDataModel transactionHistoryDataModel;
    UserDataModel userDataModel;

    public AllHistoryPresenter(AllHistoryContract.View v, String uid, LifecycleOwner owner) {
        this.view = v;
        this.mReciverValues = new LinkedList<>();
        mMergedValues = Collections.synchronizedList(new LinkedList<TransactionHistoryEntity>());
        mSenderValues = new LinkedList<>();
        transactionHistoryDataModel = new TransactionHistoryDataModel();

        walletDataModel = new WalletDataModel();
        walletDataModel.getWalletByUserId(uid).observe(owner, new Observer<FBWallet>() {
            @Override
            public void onChanged(@Nullable FBWallet fbWallet) {
                view.notifyTotalAmountChanged(fbWallet.getCurrentBalance());
            }
        });

        userDataModel = new UserDataModel();
        userDataModel.refleshUserMap(owner);
        transactionHistoryDataModel.getTransactionHistoriesByReciverUserId(uid).observe(owner, new Observer<List<FBTransactionHistory>>() {
            @Override
            public void onChanged(@Nullable List<FBTransactionHistory> list) {
                mReciverValues.clear();
                for (FBTransactionHistory th: list) {
                    TransactionHistoryEntity entity = new TransactionHistoryEntity(th.getTransactionId(), userDataModel.getUserById(th.getSenderId()), userDataModel.getUserById(th.getReceiverId()), UserAuthManager.getInstance().getUser(), th.getAmount(), th.getTimeStamp());
                    mReciverValues.add(entity);
                }
                Log.d("TRANSACTION", "received reciever");
                mMergedValues.clear();
                mMergedValues.addAll(mReciverValues);
                mMergedValues.addAll(mSenderValues);
                Collections.sort(mMergedValues, new Comparator<TransactionHistoryEntity>() {
                    @Override
                    public int compare(TransactionHistoryEntity t0, TransactionHistoryEntity t1) {
                        return t1.getTimeStamp().compareTo(t0.getTimeStamp());
                    }
                });
                view.notifyDataChanged();
            }
        });
        transactionHistoryDataModel.getTransactionHistoriesBySenderUserId(uid).observe(owner, new Observer<List<FBTransactionHistory>>() {
            @Override
            public void onChanged(@Nullable List<FBTransactionHistory> list) {
                mSenderValues.clear();
                for (FBTransactionHistory th: list) {
                    TransactionHistoryEntity entity = new TransactionHistoryEntity(th.getTransactionId(), userDataModel.getUserById(th.getSenderId()), userDataModel.getUserById(th.getReceiverId()), UserAuthManager.getInstance().getUser(), th.getAmount(), th.getTimeStamp());
                    mSenderValues.add(entity);
                }
                Log.d("TRANSACTION", "received sender");
                mMergedValues.clear();
                mMergedValues.addAll(mReciverValues);
                mMergedValues.addAll(mSenderValues);
                Collections.sort(mMergedValues, new Comparator<TransactionHistoryEntity>() {
                    @Override
                    public int compare(TransactionHistoryEntity t0, TransactionHistoryEntity t1) {
                        return t1.getTimeStamp().compareTo(t0.getTimeStamp());
                    }
                });
                view.notifyDataChanged();
            }
        });

    }

    @Override
    public TransactionHistoryEntity get(int position) {
        return mMergedValues.get(position);
    }

    @Override
    public int size() {
        return mMergedValues.size();
    }
}
