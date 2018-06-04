package com.techtator.berdie.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBRaffle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaffleDataModel {

    private DatabaseReference mDatabase;
    private Map<String,FBRaffle> raffleMap;

    public interface OnChangeListListener {
        void notifyChangedList(List<FBRaffle> list);
    }

    public RaffleDataModel() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.raffleMap = new HashMap<>();
    }

    public void setRaffleList(final OnChangeListListener listener) {
        mDatabase.child("raffle").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final ArrayList<FBRaffle> raffleList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String raffleId = (String) dataSnapshot.child("raffle_id").getValue();
                    String rafflePicture  = (String) dataSnapshot.child("raffle_picture").getValue();
                    long minimumNumberOfWinners = (Long)dataSnapshot.child("minimum_number_of_winners").getValue();
                    double amountCollected= dataSnapshot.child("amount_collected").getValue(Double.class);
                    Date dueDate = new Date(dataSnapshot.child("due_date").getValue(Long.class));
                    boolean isActive = (Boolean) dataSnapshot.child("is_active").getValue();
                    raffleList.add(new FBRaffle(raffleId, rafflePicture, (int)minimumNumberOfWinners, 222, dueDate, isActive));
                }
                listener.notifyChangedList(raffleList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });
    }

    private static Calendar getCalendarForNow() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    public FBLiveData<FBRaffle> getTopRaffle() {

        Date beginingDate, endDate;
        {
            Calendar calendar = getCalendarForNow();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            beginingDate = calendar.getTime();
        }
        {
            Calendar calendar = getCalendarForNow();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            endDate = calendar.getTime();
        }
        final FBLiveData<FBRaffle> livedata =
                new FBLiveData<>(FirebaseDatabase.getInstance().getReference("raffle").orderByChild("due_date").startAt(beginingDate.getTime()).endAt(endDate.getTime()));

        Log.d(getClass().getSimpleName(), String.format("%d %d", beginingDate.getTime(), endDate.getTime()));
        livedata.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final ArrayList<FBRaffle> raffleList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String raffleId = (String) dataSnapshot.child("raffle_id").getValue();
                    String rafflePicture = (String) dataSnapshot.child("raffle_picture").getValue();
                    long minimumNumberOfWinners = (Long) dataSnapshot.child("minimum_number_of_winners").getValue();
                    double amountCollected = dataSnapshot.child("amount_collected").getValue(Double.class);
                    Date dueDate = new Date(dataSnapshot.child("due_date").getValue(Long.class));
                    boolean isActive = (Boolean) dataSnapshot.child("is_active").getValue();
                    raffleList.add(new FBRaffle(raffleId, rafflePicture, (int) minimumNumberOfWinners, 222, dueDate, isActive));
                }

                Collections.sort(raffleList, new Comparator<FBRaffle>() {
                    @Override
                    public int compare(FBRaffle t0, FBRaffle t1) {
                        return Integer.compare(t0.getMinimumNumberOfWinners(), t1.getMinimumNumberOfWinners());
                    }
                });

                if (!raffleList.isEmpty())
                    livedata.setValue(raffleList.get(0));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });
        return livedata;
    }

    public void addRaffle(String _raffleId, String _rafflePicture, int _minimumNumberOfWinners, double _amountCollected, Date _dueDate, boolean _isActive){

        mDatabase = FirebaseDatabase.getInstance().getReference();
        FBRaffle raffle = new FBRaffle(_raffleId, _rafflePicture, _minimumNumberOfWinners, _amountCollected, _dueDate, _isActive);
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = raffle.toMap();
        childUpdates.put("/" + _raffleId, values);
        mDatabase.child("raffle").updateChildren(childUpdates);
    }

    public FBRaffle getRaffleById(String _raffleId){return raffleMap.get(_raffleId); }

    public void updateRaffle(String _raffleId,String _rafflePicture,int _minimumNumberOfWinners,double _amountCollected, Date _dueDate, boolean _isActive){
        mDatabase.child("raffle").child(_raffleId).child("raffle_picture").setValue(_rafflePicture);
        mDatabase.child("raffle").child(_raffleId).child("minimum_number_of_winners").setValue(_minimumNumberOfWinners);
        mDatabase.child("raffle").child(_raffleId).child("amount_collected").setValue(_amountCollected);
        mDatabase.child("raffle").child(_raffleId).child("due_date").setValue(_dueDate.getTime());
        mDatabase.child("raffle").child(_raffleId).child("is_active").setValue(_isActive);
    }

    public void deleteRaffle(String _raffleId){
        mDatabase.child("raffle").child(_raffleId).removeValue();
    }

    public void refreshRaffleMap() {
        setRaffleList(new OnChangeListListener() {
            @Override
            public void notifyChangedList(List<FBRaffle> list) {
                raffleMap.clear();
                for (FBRaffle u: list) {
                    raffleMap.put(u.getRaffleId(), u);
                }
            }
        });
    }
}
