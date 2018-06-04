package com.techtator.berdie.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBRaffleResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RaffleResultDataModel {
    public DatabaseReference mDatabase; /**/
    public RaffleResultDataModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public FBLiveData<FBRaffleResult> getRaffleResultById(String _raffleId) {
        final FBLiveData<FBRaffleResult> liveData = new FBLiveData<>(FirebaseDatabase.getInstance().getReference("raffle_results").child(_raffleId));

        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                snapshot.getChildren();
                String raffleId = (String) snapshot.child("raffle_id").getValue();
                String userId = (String) snapshot.child("user_id").getValue();
                String ticketId = (String) snapshot.child("ticket_id").getValue();
                Double amount = Double.parseDouble((String)String.valueOf(snapshot.child("amount").getValue()));
                String raffleResultId = (String) snapshot.child("raffle_result_id").getValue();

                final FBRaffleResult raffleResult = new FBRaffleResult();
                raffleResult.setRaffleId(raffleId);
                raffleResult.setUserId(userId);
                raffleResult.setTicketId(ticketId);
                raffleResult.setAmount(amount);
                raffleResult.setRaffleResultId(raffleResultId);

                Log.d("", "=====getRaffleResultById=============");
                Log.d("-raffleId-", raffleResult.getRaffleId());
                Log.d("- userId -", raffleResult.getUserId());
                Log.d("-ticketId-", raffleResult.getTicketId());
                Log.d("- amount -", Double.toString(raffleResult.getAmount()));
                Log.d("- raffleResultId -", raffleResult.getRaffleResultId());
                Log.d("", "====================================");

                liveData.setValue(raffleResult);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });
        return liveData;
    }

    public FBLiveData<List<FBRaffleResult>> getRaffleResultList() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final FBLiveData<List<FBRaffleResult>> liveData =
                new FBLiveData<>(FirebaseDatabase.getInstance().getReference("raffle_results"));

        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final ArrayList<FBRaffleResult> raffleResultList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String raffleId = (String) dataSnapshot.child("raffle_id").getValue();
                    String userId = (String) dataSnapshot.child("user_id").getValue();
                    String ticketId = (String) dataSnapshot.child("ticket_id").getValue();
                    Double amount = Double.parseDouble((String)String.valueOf(dataSnapshot.child("amount").getValue()));
                    String raffleResultId = (String) dataSnapshot.child("raffle_result_id").getValue();
                    raffleResultList.add(new FBRaffleResult(raffleId, userId, ticketId, amount,raffleResultId));
                }

                liveData.setValue(raffleResultList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });

        return liveData;
    }

    public FBLiveData<List<FBRaffleResult>> setRaffleResultsByRaffleId(final String raffleId) {

        final FBLiveData<List<FBRaffleResult>> liveData =
                new FBLiveData<>(FirebaseDatabase.getInstance().getReference("raffle_results").orderByChild("raffle_id").equalTo(raffleId));


        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final ArrayList<FBRaffleResult> raffleResultList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String raffleId = (String) dataSnapshot.child("raffle_id").getValue();
                    String userId = (String) dataSnapshot.child("user_id").getValue();
                    String ticketId = (String) dataSnapshot.child("ticket_id").getValue();
                    Double amount = Double.parseDouble((String)String.valueOf(dataSnapshot.child("amount").getValue()));
                    String raffleResultId = (String) dataSnapshot.child("raffle_result_id").getValue();
                    raffleResultList.add(new FBRaffleResult(raffleId, userId, ticketId, amount,raffleResultId));
                }
                liveData.setValue(raffleResultList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });

        return liveData;
    }


    public void addRaffleResult(String _raffleId, String _userId, String _ticketId, double _amount, String _raffleResultId){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FBRaffleResult raffleResult = new FBRaffleResult(_raffleId, _userId, _ticketId, _amount, _raffleResultId );

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = raffleResult.toMap();
        childUpdates.put("/" + _raffleResultId, values);

        mDatabase.child("raffle_results").updateChildren(childUpdates);
    }
    public void updateRaffleResult(String _raffleId, String _userId, String _ticketId, double _amount, String _raffleResultId){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("raffle_results").child(_ticketId).child("raffle_id").setValue(_raffleId);
        mDatabase.child("raffle_results").child(_ticketId).child("user_id").setValue(_userId);
        mDatabase.child("raffle_results").child(_ticketId).child("ticket_id").setValue(_ticketId);
        mDatabase.child("raffle_results").child(_ticketId).child("amount").setValue(_amount);
        mDatabase.child("raffle_results").child(_ticketId).child("raffle_result_id").setValue(_raffleResultId);
    }
    public void deleteRaffleResult(String _raffleId){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("raffle_results").child(_raffleId).removeValue();
    }

}
