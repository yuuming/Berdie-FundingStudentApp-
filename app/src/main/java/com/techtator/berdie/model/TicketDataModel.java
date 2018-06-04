package com.techtator.berdie.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBScholarship;
import com.techtator.berdie.Models.FBModel.FBTicket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketDataModel {

    private DatabaseReference mDatabase;

    public interface OnChangeListListener {
        public void notifyChangedList(List<FBTicket> list);
    }

    public interface OnChangeDataListener {
        public void notifyChangedData(FBTicket data);
    }


    public void setTicketList(final OnChangeListListener listener) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("ticket").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final ArrayList<FBTicket> ticketList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = (String) dataSnapshot.child("ticket_id").getValue();
                    String userId = (String) dataSnapshot.child("user_id").getValue();
                    Double price = Double.valueOf((String)dataSnapshot.child("price").getValue());
                    ticketList.add(new FBTicket(id, userId, price));
                }

                Log.d("", "====================================");
                for(int i = 0; i<ticketList.size(); i++) {
                    Log.d("--- user id ---", ticketList.get(i).getUserId());
                    Log.d("---id---", ticketList.get(i).getTicketId());
                }
                Log.d("", "====================================");

                listener.notifyChangedList(ticketList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });

    }


    public void addTicket(String _ticketId, String _userId, double _price){

        mDatabase = FirebaseDatabase.getInstance().getReference();
        FBTicket ticket = new FBTicket(_ticketId, _userId, _price);

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = ticket.toMap();
        childUpdates.put("/" + _ticketId, values);

        mDatabase.child("ticket").updateChildren(childUpdates);
    }

    public void updateTicket(String _ticketId, String _userId, double _price){

        mDatabase.child("ticket").child(_ticketId).child("ticket_id").setValue(_ticketId);
        mDatabase.child("ticket").child(_ticketId).child("price").setValue(_price);
        mDatabase.child("ticket").child(_ticketId).child("user_id").setValue(_userId);

    }
    public void deleteTicket(String _ticketId){
        mDatabase.child("ticket").child(_ticketId).removeValue();
    }

}
