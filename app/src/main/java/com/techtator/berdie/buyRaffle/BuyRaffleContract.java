package com.techtator.berdie.buyRaffle;

import com.techtator.berdie.Models.FBModel.FBRaffle;

public interface BuyRaffleContract {
    interface View{
        void onSuccess();
        void onError();
        void notifyUserTicketNumber(String numberOfTickets);
    }
    interface Presenter{
        FBRaffle get(int position);
        int size();

    }
}
