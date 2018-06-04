package com.techtator.berdie.homeStudent;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.Models.FBModel.FBRaffle;
import com.techtator.berdie.Models.FBModel.FBRaffleResult;
import com.techtator.berdie.Models.FBModel.FBScholarship;
import com.techtator.berdie.Models.FBModel.FBTicketsBought;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.model.GoalDataModel;
import com.techtator.berdie.model.RaffleDataModel;
import com.techtator.berdie.model.RaffleResultDataModel;
import com.techtator.berdie.model.ScholarshipDataModel;
import com.techtator.berdie.model.TicketBoughtDataModel;
import com.techtator.berdie.model.UserDataModel;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yuminakamura on 2018-04-01.
 */

public class HomeStudentPresenter implements HomeStudentContract.Presenter{
    private final ScholarshipDataModel scholarshipDataModel;
    private final TicketBoughtDataModel ticketBoughtDataModel;
    private final List<FBTicketsBought> ticketsBoughtList;

    private HomeStudentContract.View view;

    GoalDataModel goalDataModel;
    UserDataModel userDataModel;
    RaffleDataModel raffleDataModel;

    private final List<FBScholarship> scholarshipList; // 6)
    private final List<FBGoal> goalList;
    RaffleResultDataModel raffleResultDataModel;
    private final List<FBUser> mWinners; // 6)
    private FBRaffle mTopRaffle;

    public HomeStudentPresenter(final HomeStudentContract.View v, LifecycleOwner lifecycleOwner) {
        this.view = v;
        this.goalDataModel = new GoalDataModel();
        this.userDataModel = new UserDataModel();
        this.ticketBoughtDataModel = new TicketBoughtDataModel();
        this.userDataModel.refleshUserMap(lifecycleOwner);
        ticketsBoughtList = new LinkedList<>();

        this.raffleDataModel = new RaffleDataModel();

        //scholarship
        scholarshipList = new LinkedList<>();
        scholarshipDataModel = new ScholarshipDataModel();
        LiveData<List<FBScholarship>> livedata = scholarshipDataModel.getScholarshipList();
        livedata.observe(lifecycleOwner, new Observer<List<FBScholarship>>() {
            @Override
            public void onChanged(@Nullable List<FBScholarship> list) {
                scholarshipList.clear();
                scholarshipList.addAll(list);
                view.notifySchoolDataChanged();
            }
        });
        //goal
        goalList = new LinkedList<>();
        goalDataModel = new GoalDataModel();
        goalDataModel.getAllGoalsByUserId(UserAuthManager.getInstance().getUserId()).observe(lifecycleOwner, new Observer<List<FBGoal>>() {
            @Override
            public void onChanged(@Nullable List<FBGoal> list) {
                goalList.clear();
                goalList.addAll(list);
                view.notifyGoalDataChanged();
            }
        });

        // TODO raffleId to be specified
        raffleDataModel.getTopRaffle().observe(lifecycleOwner, new Observer<FBRaffle>() {
            @Override
            public void onChanged(@Nullable FBRaffle fbRaffle) {
                mTopRaffle = fbRaffle;
                String text = String.format("$%.0f", fbRaffle.getAmountCollected());
                view.notifyTopGoalChange(text);
            }
        });

        ticketBoughtDataModel.getTicketsBoughtsList().observe(lifecycleOwner, new Observer<List<FBTicketsBought>>() {
            @Override
            public void onChanged(@Nullable List<FBTicketsBought> list) {
                ticketsBoughtList.addAll(list);
                int num = 0;
                for (FBTicketsBought t : list){
                    if (t.getUser_id().equals(UserAuthManager.getInstance().getUserId())) {
                        num++;
                    }
                }
                view.notifyNumberOfTicketsChange(String.valueOf(num));
            }
        });

        mWinners = new LinkedList<>();
        raffleResultDataModel = new RaffleResultDataModel();
        raffleResultDataModel.getRaffleResultList().observe(lifecycleOwner, new Observer<List<FBRaffleResult>>() {
            @Override
            public void onChanged(@Nullable List<FBRaffleResult> list) {
                mWinners.clear();
                for (FBRaffleResult rr : list) {
                    FBUser user = userDataModel.getUserById(rr.getUserId());
                    if (user!=null) {
                        mWinners.add(user);
                    }
                }
                view.notifyWinnersChanged(mWinners);
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
                            long diff = mTopRaffle.getDueDate().getTime() - (now/1000);
                            diff = diff;
                            long second = diff % 60;
                            long min = (diff/60) % 60;
                            long hour = (diff/3600) % 24;
                            long day = (diff/3600/24) % 30;
                            timeText = String.format("%02d:%02d:%02d:%02d", day, hour, min, second);
                        }
                        view.notifyRaffleRestOfTimeInterval(timeText);
                    }
                });
            }
        };
        Timer t = new Timer();
        t.scheduleAtFixedRate(task, 0, 1000);
    }


    public int getGoalProgress(FBGoal goal){
        return goalDataModel.getProgressPercentage(goal);
    }

    @Override
    public FBScholarship get(int position) {  // 8
        return scholarshipList.get(position);
    }

    @Override
    public int size() { // 9
        return scholarshipList.size();
    }

    @Override
    public FBGoal getGoalPosition(int position) {
        return goalList.get(position);
    }

    @Override
    public int goalSize() {
        return goalList.size();
    }


    @Override
    public int sizeOfWinners() {
        return mWinners.size();
    }

    @Override
    public FBUser getWinner(int position) {
        return mWinners.get(position);
    }
}
