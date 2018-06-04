package com.techtator.berdie.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBMessage;
import com.techtator.berdie.Models.FBModel.FBUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MessageDataModel{
    public DatabaseReference mDatabase;
    public MessageDataModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public interface OnChangeListListener {
        public void notifyChangedList(List<FBMessage> list);
    }

    public interface OnChangeDataListener {
        public void notifyChangedData(FBMessage data);
    }

    public interface OnChangeNumberListener {
        public void notifyChangedNumber(int number);
    }

    public void setMessages(final OnChangeListListener listener){
        mDatabase.child("message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<FBMessage> messageList = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    String messageId = (String) d.child("message_id").getValue();
                    String conversationId = d.child("conversation_id").getValue(String.class);
                    String senderId = d.child("sender_id").getValue(String.class);
                    String receiverId = d.child("receiver_id").getValue(String.class);
                    String body = d.child("body").getValue(String.class);
                    Date timeStamp= new Date(d.child("time_stamp").getValue(Long.class));
                    boolean isRead = (boolean) dataSnapshot.child("is_read").getValue();
                    FBMessage mMessage = new FBMessage(messageId,conversationId,senderId,receiverId,body,timeStamp,isRead);
                    messageList.add(mMessage);
                }
                listener.notifyChangedList(messageList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("getMessages",databaseError.getMessage());
            }
        });

    }

    //  when a piece of message is created // //
    // 1. generate 2 conversationIds
    // 2. generate 2 FBMessage

    public FBLiveData<List<FBMessage>> getConversationRoomsByUserId(final String userId) {
        final FBLiveData<List<FBMessage>> liveData = new FBLiveData<>(mDatabase.child("message").orderByChild("conversation_id").startAt(userId));

        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<FBMessage> messageListForInbox = new ArrayList<>();
                final ArrayList<FBMessage> allMessageListForUser = new ArrayList<>();
                messageListForInbox.clear();
                allMessageListForUser.clear();

                String regex = "^" + userId;
                Pattern p = Pattern.compile(regex);

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    String conversationId = d.child("conversation_id").getValue(String.class);
                    Matcher m = p.matcher(conversationId);
                    if (m.find()){
                        String messageId = (String) d.child("message_id").getValue();
                        String senderId = d.child("sender_id").getValue(String.class);
                        String receiverId = d.child("receiver_id").getValue(String.class);
                        String body = d.child("body").getValue(String.class);
                        Date timeStamp = new Date(d.child("time_stamp").getValue(Long.class));
                        boolean isRead = (boolean) d.child("is_read").getValue();
                        FBMessage mMessage = new FBMessage(messageId, conversationId, senderId, receiverId, body, timeStamp, isRead);
                        allMessageListForUser.add(mMessage);
                    }
                }
                // change the order: old -> new
                Collections.sort(allMessageListForUser, new Comparator<FBMessage>() {
                    public int compare(FBMessage a, FBMessage b) {
                        if (a.getTimeStamp() == null || b.getTimeStamp() == null)
                            return 0;
                        return a.getTimeStamp().compareTo(b.getTimeStamp());
                    }
                });
                // order: old -> new, get one
                LinkedHashMap<String, FBMessage> latestMessageList = new LinkedHashMap<>();
                latestMessageList.clear();
                for(int i = 0; i<allMessageListForUser.size(); i++) {
                    latestMessageList.put(allMessageListForUser.get(i).getConversationId(), allMessageListForUser.get(i));
                }
                for (FBMessage message : latestMessageList.values()) {
                    messageListForInbox.add(message);
                }
                // change the order: new -> old
                Collections.reverse(messageListForInbox);
                liveData.setValue(messageListForInbox);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("getMessages",databaseError.getMessage());
            }
        });
        return liveData;
    }

    public FBLiveData<List<FBMessage>> setMessageListByConversationId(final String conversationId) {
        final FBLiveData<List<FBMessage>> liveData = new FBLiveData<>(mDatabase.child("message").orderByChild("conversation_id").equalTo(conversationId));
        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<FBMessage> messageList = new ArrayList<>();
                messageList.clear();

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    String messageId = (String) d.child("message_id").getValue();
                    String conversationId = d.child("conversation_id").getValue(String.class);
                    String senderId = d.child("sender_id").getValue(String.class);
                    String receiverId = d.child("receiver_id").getValue(String.class);
                    String body = d.child("body").getValue(String.class);
                    Date timeStamp = new Date(d.child("time_stamp").getValue(Long.class));
                    boolean isRead = (boolean) d.child("is_read").getValue();
                    FBMessage mMessage = new FBMessage(messageId, conversationId, senderId, receiverId, body, timeStamp, isRead);
                    messageList.add(mMessage);
                }
                // change the order: old -> new
                Collections.sort(messageList, new Comparator<FBMessage>() {
                    public int compare(FBMessage a, FBMessage b) {
                        if (a.getTimeStamp() == null || b.getTimeStamp() == null)
                            return 0;
                        return a.getTimeStamp().compareTo(b.getTimeStamp());
                    }
                });
                liveData.setValue(messageList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("getMessages",databaseError.getMessage());
            }
        });
        return liveData;
    }

    public FBLiveData<FBMessage> getMessageById(String _messageId) {
        final FBLiveData<FBMessage> liveData = new FBLiveData<>(mDatabase.child("message").child(_messageId));
        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot d) {
                String messageId = (String) d.child("message_id").getValue();
                String conversationId = d.child("conversation_id").getValue(String.class);
                String senderId = d.child("sender_id").getValue(String.class);
                String receiverId = d.child("receiver_id").getValue(String.class);
                String body = d.child("body").getValue(String.class);
                Date timeStamp= new Date(d.child("time_stamp").getValue(Long.class));
                boolean isRead = (boolean) d.child("is_read").getValue();
                FBMessage message = new FBMessage(messageId,conversationId,senderId,receiverId,body,timeStamp, isRead);
                liveData.setValue(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("getMessageById",databaseError.getMessage());
            }
        });

        return liveData;
    }

    public void setNumberOfUnreadMessageByUserId(String userId, final OnChangeNumberListener listener) {
        mDatabase.child("message").orderByChild("receiver_id").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<FBMessage> messageList = new ArrayList<>();
                messageList.clear();
                int counter = 0;
                int fromSystem = 0;

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    if ((boolean) d.child("is_read").getValue() == false ) {
                        counter++;
                    }
                    if (((String)d.child("sender_id").getValue()).contains("SYSTEM")) {
                        fromSystem++;
                    }
                }
                listener.notifyChangedNumber(counter/2 + fromSystem);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("getMessages",databaseError.getMessage());
            }
        });
    }

    public void setMessageReadByMessageId(String messageId) {
        mDatabase = FirebaseDatabase.getInstance().getReference("message");
        mDatabase.child(messageId).child("is_read").setValue(true);
    }

    public void setSomeoneMessageReadByConversationId(String someoneId, final String myId) {
        mDatabase = FirebaseDatabase.getInstance().getReference("message");
        mDatabase.orderByChild("conversation_id").equalTo(someoneId + "-" + myId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    if(d.child("receiver_id").equals(myId)) {
                        if ((boolean) d.child("is_read").getValue() == false) {
                            mDatabase.child(d.child("message_id").getValue(String.class)).child("is_read").setValue(true);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void addMessage(String _senderId, String _receiverId, String _body){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("message");
        String _id1 = mDatabase.push().getKey();
        String _id2 = mDatabase.push().getKey();
        Date _timeStamp = new Date();

        String conversationId1 = _senderId + "-" + _receiverId;
        String conversationId2 =  _receiverId + "-" + _senderId;

        FBMessage fbMessage1 = new FBMessage(_id1, conversationId1, _senderId, _receiverId, _body, _timeStamp, false);
        FBMessage fbMessage2 = new FBMessage(_id2, conversationId2, _senderId, _receiverId, _body, _timeStamp, false);

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values1 = fbMessage1.toMap();
        Map<String, Object> values2 = fbMessage2.toMap();
        childUpdates.put("/" + _id1, values1);
        childUpdates.put("/" + _id2, values2);

        mDatabase.updateChildren(childUpdates);
    }

    public void sendDonationMessageFromSystem(FBUser _donor, FBUser _student, int amount) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("message");
        String _id = mDatabase.push().getKey();
        Date _timeStamp = new Date();
        String _body = "You've got the donation ($" + amount + ") from " + _donor.getFirstName();

        String conversationId =  _student.getId() + "-" + _donor.getId();

        FBMessage fbMessage = new FBMessage(_id, conversationId, _donor.getId() + "_SYSTEM", _student.getId(), _body, _timeStamp, false);

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = fbMessage.toMap();
        childUpdates.put("/" + _id, values);

        mDatabase.updateChildren(childUpdates);
    }

    public void  updateMessage(String _id, String _conversationId, String _senderId,String _receiverId, String _body, Date _timeStamp, boolean isRead){
        mDatabase = FirebaseDatabase.getInstance().getReference("message");
        mDatabase.child(_id).child("id").setValue(_id);
        mDatabase.child(_id).child("conversation_id").setValue(_conversationId);
        mDatabase.child(_id).child("sender_id").setValue(_senderId);
        mDatabase.child(_id).child("receiver_id").setValue(_receiverId);
        mDatabase.child(_id).child("body").setValue(_body);
        mDatabase.child(_id).child("time_stamp").setValue(_timeStamp.getTime());
        mDatabase.child(_id).child("is_read").setValue(isRead);

    }

    public void deleteMessage(String _messageId){
        mDatabase = FirebaseDatabase.getInstance().getReference("message");
        mDatabase.child(_messageId).removeValue();
    }
}
