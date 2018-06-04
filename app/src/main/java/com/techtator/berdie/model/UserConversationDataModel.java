package com.techtator.berdie.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBTicket;
import com.techtator.berdie.Models.FBModel.FBUserConversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserConversationDataModel{

    public DatabaseReference mDatabase; /**/
    public UserConversationDataModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public interface OnChangeListListener {
        public void notifyChangedList(List<FBUserConversation> list);
    }

    public interface OnChangeDataListener {
        public void notifyChangedData(FBUserConversation data);
    }

    public void setUserConversationById(String _conversationId, final OnChangeDataListener listener) {
        mDatabase = FirebaseDatabase.getInstance().getReference("user_conversation").child(_conversationId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String conversationId = (String) dataSnapshot.child("conversation_id").getValue();
                String userId = (String) dataSnapshot.child("user_id").getValue();

                FBUserConversation userConversation = new FBUserConversation(userId, conversationId);

                Log.d("", "=== getUserConversationById ===");
                Log.d("--- userId ---", userConversation.getUserId());
                Log.d("--- conversationId ---", userConversation.getConversationId());
                Log.d("", "================================");

                listener.notifyChangedData(userConversation);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });
    }

    public void setUserConversationList(final OnChangeListListener listener) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("user_conversation");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<FBUserConversation> userConversationList = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String conversationId = (String) snapshot.child("conversation_id").getValue();
                    String userId = (String)snapshot.child("user_id").getValue();

                    userConversationList.add(new FBUserConversation(userId, conversationId));
                }

                Log.d("", "==== getUserconversationList ===");
                for (int i = 0; i < userConversationList.size(); i++) {
                    Log.d("--- conversationId ---", userConversationList.get(i).getConversationId());
                    Log.d("--- userId ---", userConversationList.get(i).getUserId());
                }
                Log.d("", "=================================");

                listener.notifyChangedList(userConversationList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });
        return;
    }

    public void addUserConversation(String _userId, String _conversationId){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("user_conversation");
        FBUserConversation us = new FBUserConversation(_userId, _conversationId);

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = us.toMap();
        childUpdates.put("/" + _conversationId, values);
        mDatabase.updateChildren(childUpdates);
    };

    public void updateUserConversation(String _userId, String _conversationId){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("user_conversation").child(_conversationId);
        mDatabase.child("conversation_id").setValue(_conversationId);
        mDatabase.child("user_id").setValue(_userId);
    };

    public void deleteUserConversation(String _conversationId){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("user_conversation").child(_conversationId);
        mDatabase.removeValue();
    };
}
