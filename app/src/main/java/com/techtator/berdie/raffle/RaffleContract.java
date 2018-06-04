package com.techtator.berdie.raffle;

import com.techtator.berdie.Models.FBModel.FBRaffle;

/**
 * Created by user on 2018-04-01.
 */

public interface RaffleContract {
    interface View {
        void onSuccess();
        void onError();
        void notifyRemainingTimeChange(String remainingTimeText);
        void notifyTicketUnitPriceChange(String ticketUnitPriceText);
        void notifyNumberOfTicketsChange(String numberOfTicketsText);
    }

    interface Presenter {
        FBRaffle getRaffle(int i);
        int size();
    }
}
