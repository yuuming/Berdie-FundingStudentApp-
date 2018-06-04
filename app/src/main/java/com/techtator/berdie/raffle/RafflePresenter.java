package com.techtator.berdie.raffle;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBRaffle;
import com.techtator.berdie.Models.FBModel.FBTicketsBought;
import com.techtator.berdie.model.RaffleDataModel;
import com.techtator.berdie.model.TicketBoughtDataModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 2018-04-01.
 */

public class RafflePresenter implements RaffleContract.Presenter {
    private RaffleContract.View view;
    private final RaffleDataModel raffleDataModel;
    private final TicketBoughtDataModel ticketBoughtDataModel;
    private final List<FBRaffle> raffleList;
    private final List<FBTicketsBought> ticketsBoughtList;
    private FBRaffle mTopRaffle;

    public RafflePresenter(final RaffleContract.View view, LifecycleOwner owner) {
        this.view = view;
        this.raffleDataModel = new RaffleDataModel();
        this.ticketBoughtDataModel = new TicketBoughtDataModel();
        //get all the data in constructor
        raffleList = new LinkedList<>();
        raffleDataModel.setRaffleList(new RaffleDataModel.OnChangeListListener() {
            @Override
            public void notifyChangedList(List<FBRaffle> list) {
                raffleList.addAll(list);
            }
        });
        ticketsBoughtList = new LinkedList<>();

        ticketBoughtDataModel.getTicketsBoughtsList().observe(owner, new Observer<List<FBTicketsBought>>() {
            @Override
            public void onChanged(@Nullable List<FBTicketsBought> list) {
                ticketsBoughtList.clear();
                ticketsBoughtList.addAll(list);
                //seems to be working
                //List<String> winners = decideRaffleWinner(3);
                int num = 0;
                //take a look at this later
                for (FBTicketsBought t : list) {
                    if (t.getUser_id().equals(UserAuthManager.getInstance().getUserId())) {
                        num++;
                    }
                }
                view.notifyNumberOfTicketsChange(String.valueOf(num));
            }

        });

        raffleDataModel.getTopRaffle().observe(owner, new Observer<FBRaffle>() {
            @Override
            public void onChanged(@Nullable FBRaffle fbRaffle) {
                mTopRaffle = fbRaffle;
                //hardcoded that for now.
                view.notifyTicketUnitPriceChange("1$");
            }
        });


        final Handler handler = new Handler();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String timeText;
                        if (mTopRaffle == null || mTopRaffle.getDueDate() == null) {
                            timeText = "--:--:--:--";
                        } else {
                            long now = new Date().getTime();
                            long diff = mTopRaffle.getDueDate().getTime() - (now / 1000);
                            long second = diff % 60;
                            long min = (diff / 60) % 60;
                            long hour = (diff / 3600) % 24;
                            long day = (diff / 3600 / 24) % 30;
                            timeText = String.format("%02d:%02d:%02d:%02d", day, hour, min, second);
                        }
                        view.notifyRemainingTimeChange(timeText);
                    }
                });
            }
        };
        Timer t = new Timer();
        t.scheduleAtFixedRate(task, 0, 1000);
    }

    public List<String> decideRaffleWinner(int numberOfWinners) {
        if (ticketsBoughtList.size() > 0) {
            List<FBTicketsBought> boughtTickets = ticketsBoughtList;
            List<String> winners = new ArrayList<>();
            int topLimit = boughtTickets.size();
            for (int i = 0; i < numberOfWinners; i++) {
                Random rnd = new Random();
                int randomNumber = rnd.nextInt(topLimit);
                FBTicketsBought winnerTicket = boughtTickets.get(randomNumber);
                boughtTickets.remove(randomNumber);
                winners.add(winnerTicket.getUser_id());
            }

            return winners;
        } else
            return null;

    }

    @Override
    public FBRaffle getRaffle(int i) {
        return raffleList.get(i);
    }

    @Override
    public int size() {
        return raffleList.size();
    }
}
