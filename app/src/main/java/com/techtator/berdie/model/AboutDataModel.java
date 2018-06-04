package com.techtator.berdie.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBAbout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AboutDataModel{
    public DatabaseReference mDatabase; /**/
    public FBAbout about;
    public AboutDataModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public interface OnChangeListListener {
        public void notifyChangedList(List<FBAbout> list);
    }

    public interface OnChangeDataListener {
        public void notifyChangedData(FBAbout data);
    }

    public void setAboutById(String _aboutId, final OnChangeDataListener listener) {
        mDatabase = FirebaseDatabase.getInstance().getReference("about").child(_aboutId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String body = (String) dataSnapshot.child("body").getValue();
                String id = (String) dataSnapshot.child("id").getValue();
                String logo = (String) dataSnapshot.child("logo").getValue();
                about = new FBAbout(id, logo, body);

                listener.notifyChangedData(about);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });
        return;
    }

    public void setAboutList(final OnChangeListListener listener){
        //GET ABOUT MODELS FROM FIREBASE AND ASSIGN INTO AN ARRAYLIST OF ABOUT.
        final ArrayList<FBAbout> aboutList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("about").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String body = (String) dataSnapshot.child("body").getValue();
                    String id = (String) dataSnapshot.child("id").getValue();
                    String logo = (String) dataSnapshot.child("logo").getValue();
                    aboutList.add(new FBAbout(id, logo, body));
                }
                Log.d("", "========getAboutList========");
                for(int i = 0; i<aboutList.size(); i++) {
                    Log.d("---body---", aboutList.get(i).getBody());
                    Log.d("--- id ---", aboutList.get(i).getId());
                    Log.d("---logo---", aboutList.get(i).getLogo());
                }
                Log.d("", "==============================");

                listener.notifyChangedList(aboutList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });
    }

    public void addAbout(String _id, String _logo, String _body) {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("about");
        FBAbout fbAbout = new FBAbout(_id, _logo, _body);

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = fbAbout.toMap();
        childUpdates.put("/" + _id, values);
        mDatabase.updateChildren(childUpdates);
    }

    public void updateAbout(String _id, String _logo, String _body) {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("about").child(_id);
        mDatabase.child("id").setValue(_id);
        mDatabase.child("body").setValue(_body);
        mDatabase.child("logo").setValue(_logo);
    }

    public void deleteAbout(String _id) {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("about").child(_id);
        mDatabase.removeValue();
    }
}
