package com.techtator.berdie.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBTransactionHistory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionHistoryDataModel{
    public DatabaseReference mDatabase;

    public interface OnChangeDataListener {
        public void notifyChangedData(FBTransactionHistory data);
    }

    public FBLiveData<List<FBTransactionHistory>> setTransactionHistories(){
        final FBLiveData<List<FBTransactionHistory>> liveData = new FBLiveData<>(FirebaseDatabase.getInstance().getReference("transaction_history"));
        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<FBTransactionHistory> transactionHistoryList= new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    String transactionId = (String) d.child("transaction_id").getValue();
                    String senderId= d.child("sender_id").getValue(String.class);
                    String receiverId = d.child("receiver_id").getValue(String.class);
                    double amount= d.child("amount").getValue(Double.class);
                    Date timeStamp= new Date(d.child("time_stamp").getValue(Long.class));
                    FBTransactionHistory mTransactionHistory = new FBTransactionHistory(transactionId,senderId,receiverId,amount,timeStamp);

                    transactionHistoryList.add(mTransactionHistory);
                }

                liveData.setValue(transactionHistoryList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("getTransactionHistories",databaseError.getMessage());
            }
        });

        return liveData;
    }

    public FBLiveData<List<FBTransactionHistory>> getTransactionHistoriesByReciverUserId(String userId) {
        final FBLiveData<List<FBTransactionHistory>> liveData = new FBLiveData<>(FirebaseDatabase.getInstance().getReference("transaction_history").orderByChild("receiver_id").equalTo(userId));
        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<FBTransactionHistory> transactionHistoryList= new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    String transactionId = (String) d.child("transaction_id").getValue();
                    String senderId= d.child("sender_id").getValue(String.class);
                    String receiverId = d.child("receiver_id").getValue(String.class);
                    double amount= d.child("amount").getValue(Double.class);
                    Date timeStamp= new Date(d.child("time_stamp").getValue(Long.class));
                    FBTransactionHistory mTransactionHistory = new FBTransactionHistory(transactionId,senderId,receiverId,amount,timeStamp);

                    transactionHistoryList.add(mTransactionHistory);
                }

                liveData.setValue(transactionHistoryList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("getTransactionHistories",databaseError.getMessage());
            }
        });

        return liveData;
    }

    public FBLiveData<List<FBTransactionHistory>> getTransactionHistoriesBySenderUserId(String userId) {
        final FBLiveData<List<FBTransactionHistory>> liveData = new FBLiveData<>(FirebaseDatabase.getInstance().getReference("transaction_history").orderByChild("sender_id").equalTo(userId));
        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<FBTransactionHistory> transactionHistoryList= new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    String transactionId = (String) d.child("transaction_id").getValue();
                    String senderId= d.child("sender_id").getValue(String.class);
                    String receiverId = d.child("receiver_id").getValue(String.class);
                    double amount= d.child("amount").getValue(Double.class);
                    Date timeStamp= new Date(d.child("time_stamp").getValue(Long.class));
                    FBTransactionHistory mTransactionHistory = new FBTransactionHistory(transactionId,senderId,receiverId,amount,timeStamp);

                    transactionHistoryList.add(mTransactionHistory);
                }

                liveData.setValue(transactionHistoryList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("getTransactionHistories",databaseError.getMessage());
            }
        });

        return liveData;
    }

    public FBLiveData<List<FBTransactionHistory>> setTransactionHistoryById(String _transactionHistoryId, final OnChangeDataListener listener){
        final FBLiveData<List<FBTransactionHistory>> liveData = new FBLiveData<>(FirebaseDatabase.getInstance().getReference("transaction_history").child(_transactionHistoryId));
        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot d) {
                String transactionId = (String) d.child("transaction_id").getValue();
                String senderId= d.child("sender_id").getValue(String.class);
                String receiverId = d.child("receiver_id").getValue(String.class);
                double amount= d.child("amount").getValue(Double.class);
                Date timeStamp= new Date(d.child("time_stamp").getValue(Long.class));
                FBTransactionHistory mTransactionHistory = new FBTransactionHistory(transactionId,senderId,receiverId,amount,timeStamp);
                listener.notifyChangedData(mTransactionHistory);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("getTransactionById",databaseError.getMessage());
            }
        });

        return liveData;
    }

    public void updateTransactionHistory(String _transactionId, String _senderId, String _receiverId, double _amount, Date timeStamp){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("transaction_history").child(_transactionId);
        mDatabase.child("sender_id").setValue(_senderId);
        mDatabase.child("receiver_id").setValue(_receiverId);
        mDatabase.child("amount").setValue(_amount);
        mDatabase.child("time_stamp").setValue(timeStamp.getTime());
    }

    public void addTransactionHistory(String _transactionId, String _senderId, String _receiverId, double _amount){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("transaction_history");
        _transactionId = mDatabase.push().getKey();
        Date timeStamp = new Date();
        FBTransactionHistory fbTransactionHistory = new FBTransactionHistory(_transactionId,_senderId,_receiverId,_amount,timeStamp);
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = fbTransactionHistory.toMap();
        childUpdates.put("/" + _transactionId, values);
        mDatabase.updateChildren(childUpdates);
    }

    public void deleteTransactionHistory(String _transactionId){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("transaction_history").child(_transactionId);
        mDatabase.removeValue();
    }
}
