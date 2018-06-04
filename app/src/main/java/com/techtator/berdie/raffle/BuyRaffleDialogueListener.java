package com.techtator.berdie.raffle;

/**
 * Created by cemserin on 2018-04-12.
 */

public interface BuyRaffleDialogueListener {

    void increment();
    void decrement();
    void buyTickets(String userId, int numberOfTickets);

}
