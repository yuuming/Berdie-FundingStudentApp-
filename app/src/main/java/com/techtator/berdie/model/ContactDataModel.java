package com.techtator.berdie.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBCommunity;
import com.techtator.berdie.Models.FBModel.FBContact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactDataModel{
    public DatabaseReference mDatabase; /**/
    public ContactDataModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public interface OnChangeListListener {
        public void notifyChangedList(List<FBContact> list);
    }

    public interface OnChangeDataListener {
        public void notifyChangedData(FBContact data);
    }

    public void setContactById(String _contactId, final OnChangeDataListener listener) {
        final FBContact contact = new FBContact();
        mDatabase = FirebaseDatabase.getInstance().getReference("contact").child(_contactId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                snapshot.getChildren();
                String id = (String) snapshot.child("id").getValue();
                String link = (String) snapshot.child("link").getValue();
                contact.setId(id);
                contact.setLink(link);

                Log.d("", "=======getContactById===============");
                Log.d("--- id ---", contact.getId());
                Log.d("---link---", contact.getLink());
                Log.d("", "====================================");

                listener.notifyChangedData(contact);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });
    }

    public void setContactList(final OnChangeListListener listener) {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("contact").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final ArrayList<FBContact> contactList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = (String) dataSnapshot.child("id").getValue();
                    String link = (String) dataSnapshot.child("link").getValue();
                    contactList.add(new FBContact(id, link));
                }
                Log.d("", "====================================");
                for(int i = 0; i<contactList.size(); i++) {
                    Log.d("--- id ---", contactList.get(i).getId());
                    Log.d("---link---", contactList.get(i).getLink());
                }
                Log.d("", "====================================");

                listener.notifyChangedList(contactList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });
    }

    public void addContact(String _contactId, String _link){
        //Contact ccc = new Contact("32432523","http://google.com");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FBContact contact = new FBContact(_contactId, _link);

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = contact.toMap();
        childUpdates.put("/" + _contactId, values);

        mDatabase.child("contact").updateChildren(childUpdates);

    }

    public void updateContact(String _contactId, String _link){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("contact").child(_contactId).child("id").setValue(_contactId);
        mDatabase.child("contact").child(_contactId).child("link").setValue(_link);
    }

    public void deleteContact(String _contactId){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("contact").child(_contactId).removeValue();
    }

}
