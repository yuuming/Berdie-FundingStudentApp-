package com.techtator.berdie.model;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBAbout;
import com.techtator.berdie.Models.FBModel.FBRaffle;
import com.techtator.berdie.Models.FBModel.FBTicketsBought;
import com.techtator.berdie.Models.FBModel.FBTransactionHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TicketBoughtDataModel {
    private DatabaseReference mDatabase; /**/
    private Map<String,FBTicketsBought> ticketsBoughtMap;

    public FBTicketsBought ticketsBoughts;

    public TicketBoughtDataModel() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.ticketsBoughtMap = new HashMap<>();
    }

    public interface OnChangeDataListener {
        public void notifyChangedData(FBTicketsBought data);
    }
    public interface OnChangeListListener {
        public void notifyChangedList(List<FBTicketsBought> list);
    }

    public void setTicketsBoughtsById(String userId, final OnChangeDataListener listener){
        mDatabase = FirebaseDatabase.getInstance().getReference("tickets_bought");
        mDatabase.orderByChild("user_id").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Double price = Double.parseDouble(String.valueOf(snapshot.child("price").getValue()));
                String raffle_id = (String)snapshot.child("raffle_id").getValue();
                String ticket_id = (String) snapshot.child("ticket_id").getValue();
                String user_id = (String) snapshot.child("user_id").getValue();
                String ticket_bought_id = (String) snapshot.child("ticket_bought_id").getValue();
                ticketsBoughts = new FBTicketsBought(ticket_bought_id,ticket_id, raffle_id, price, user_id);
                listener.notifyChangedData(ticketsBoughts);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });
    }

    public FBLiveData<List<FBTicketsBought>> getTicketsBoughtsList() {

        final FBLiveData<List<FBTicketsBought>> livedata =
                new FBLiveData<>(FirebaseDatabase.getInstance().getReference("tickets_bought"));

        livedata.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<FBTicketsBought> ticketBoughtList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Double price =  Double.parseDouble((String) String.valueOf(dataSnapshot.child("price").getValue()));
                    String raffle_id = (String)dataSnapshot.child("raffle_id").getValue();
                    String ticket_id = (String) dataSnapshot.child("ticket_id").getValue();
                    String user_id = (String) dataSnapshot.child("user_id").getValue();
                    String ticket_bought_id = (String) dataSnapshot.child("ticket_bought_id").getValue();

                    ticketBoughtList.add(new FBTicketsBought(ticket_bought_id,ticket_id, raffle_id, price, user_id));
                }
                livedata.setValue(ticketBoughtList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });

        return livedata;
    }

    public void addTicketBought(String ticket_bought_id, String ticket_id, String raffle_id, double _price, String user_id) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ticket_bought_id = mDatabase.push().getKey();
        ticket_id = ticket_bought_id;
        FBTicketsBought ticketsBought = new FBTicketsBought(ticket_bought_id, ticket_id, raffle_id, _price, user_id);
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = ticketsBought.toMap();
        childUpdates.put("/" + ticket_bought_id, values);
        mDatabase.child("tickets_bought").updateChildren(childUpdates);
    }

    public void updateTicketBought(String ticket_bought_id, String ticket_id, String raffle_id, double price, String user_id) {

        mDatabase = FirebaseDatabase.getInstance().getReference("tickets_bought");

        mDatabase.child(ticket_bought_id).child("price").setValue(price);
        mDatabase.child(ticket_bought_id).child("raffle_id").setValue(raffle_id);
        mDatabase.child(ticket_bought_id).child("ticket_id").setValue(ticket_id);
        mDatabase.child(ticket_bought_id).child("user_id").setValue(user_id);
        mDatabase.child(ticket_bought_id).child("ticket_bought_id").setValue(ticket_bought_id);

    }

    public void deleteTicketBought(String ticket_bought_id) {
        mDatabase = FirebaseDatabase.getInstance().getReference("tickets_bought").child(ticket_bought_id);
        mDatabase.removeValue();
    }

    public void refreshTicketsBoughtModelMap(LifecycleOwner owner) {
        getTicketsBoughtsList().observe(owner, new Observer<List<FBTicketsBought>>() {
            @Override
            public void onChanged(@Nullable List<FBTicketsBought> list) {
                ticketsBoughtMap.clear();
                for (FBTicketsBought u: list) {
                    ticketsBoughtMap.put(u.getTicket_bought_id(), u);
                }
            }
        });
    }
}
