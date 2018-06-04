package com.techtator.berdie.buyRaffle;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBRaffle;
import com.techtator.berdie.Models.FBModel.FBWallet;
import com.techtator.berdie.model.RaffleDataModel;
import com.techtator.berdie.model.TicketBoughtDataModel;
import com.techtator.berdie.model.TransactionHistoryDataModel;
import com.techtator.berdie.model.WalletDataModel;

import java.util.LinkedList;
import java.util.List;

public class BuyRafflePresenter implements BuyRaffleContract.Presenter {
    private BuyRaffleContract.View view;
    RaffleDataModel raffleDataModel;
    WalletDataModel walletDataModel;
    TicketBoughtDataModel ticketBoughtDataModel;
    TransactionHistoryDataModel transactionDataModel;
    private final List<FBRaffle> raffleList;
    private FBWallet mWallet;

    public BuyRafflePresenter(BuyRaffleContract.View view, LifecycleOwner lifecycleOwner) {
        this.view = view;
        raffleList = new LinkedList<>();
        raffleDataModel = new RaffleDataModel();
        raffleDataModel.setRaffleList(new RaffleDataModel.OnChangeListListener() {
            @Override
            public void notifyChangedList(List<FBRaffle> list) {
                raffleList.addAll(list);
            }
        });
        walletDataModel = new WalletDataModel();
        walletDataModel.getWalletByUserId(UserAuthManager.getInstance().getUserId()).observe(lifecycleOwner, new Observer<FBWallet>() {
            @Override
            public void onChanged(@Nullable FBWallet fbWallet) {
                mWallet = fbWallet;
            }
        });
        transactionDataModel = new TransactionHistoryDataModel();
        ticketBoughtDataModel = new TicketBoughtDataModel();
    }




    @Override
    public FBRaffle get(int position) {
        return raffleList.get(position);
    }

    @Override
    public int size() {
        return raffleList.size();
    }
}
