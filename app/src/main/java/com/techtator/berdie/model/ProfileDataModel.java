package com.techtator.berdie.model;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBProfile;
import com.techtator.berdie.findStudent.ProfileEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileDataModel {
    DatabaseReference mDatabase;

    public FBLiveData<List<FBProfile>> getProfileList() {
        final FBLiveData<List<FBProfile>> liveData = new FBLiveData<>(FirebaseDatabase.getInstance().getReference().child("profile"));
        mDatabase = FirebaseDatabase.getInstance().getReference();

        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final ArrayList<FBProfile> profileList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = (String) dataSnapshot.getKey();
                    String userId = (String) dataSnapshot.child("user_id").getValue();
                    String major = (String) dataSnapshot.child("major").getValue();
                    String university = (String) dataSnapshot.child("university").getValue();
                    String body = (String) dataSnapshot.child("body").getValue();
                    profileList.add(new FBProfile(id, userId, major, university, body));
                }

                liveData.setValue(profileList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });

        return liveData;
    }




    public FBLiveData<FBProfile> getProfileByUserId(String userId) {
        final FBLiveData<FBProfile> liveData =
                new FBLiveData<>(FirebaseDatabase.getInstance().getReference("profile").orderByChild("user_id").equalTo(userId));
        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot d) {
                final ArrayList<FBProfile> profileList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : d.getChildren()) {

                        String id = (String) dataSnapshot.getKey();
                        String userId = (String) dataSnapshot.child("user_id").getValue();
                        String major = (String) dataSnapshot.child("major").getValue();
                        String university = (String) dataSnapshot.child("university").getValue();
                        String body = (String) dataSnapshot.child("body").getValue();
                        profileList.add(new FBProfile(id, userId, major, university, body));
                        FBProfile profile = new FBProfile(id, userId, major, university, body);

                        profileList.add(profile);
                    }
                if (!profileList.isEmpty())
                liveData.setValue(profileList.get(0));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });

        return liveData;
    }

    public static ArrayList<ProfileEntity> findProfileEntityByWord(String word, List<ProfileEntity> list) {
        ArrayList<ProfileEntity> filteredList = new ArrayList<>();

        for(int i = 0; i<list.size(); i++) {
            if (list.get(i).getUser() == null) continue;
            if (list.get(i).getUser().getLastName() == null) continue;
            if (list.get(i).getBody() == null) continue;

            if(list.get(i).getUser().getFirstName().toLowerCase().contains(word) || list.get(i).getUniversity().toLowerCase().contains(word)|| list.get(i).getBody().toLowerCase().contains(word)) {
                filteredList.add(list.get(i));
            }
        }
        return filteredList;
    }


    public void addProfile(String _userId, String _major, String _university, String _body){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String _id = mDatabase.push().getKey();
        FBProfile profile = new FBProfile(_id, _userId, _major, _university, _body);

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = profile.toMap();
        childUpdates.put("/" + _id, values);

        mDatabase.child("profile").updateChildren(childUpdates);
    }

    public void updateProfile(String _id,String _userId, String _major, String _university, String _body){
        mDatabase = FirebaseDatabase.getInstance().getReference("profile");

        mDatabase.child(_id).child("id").setValue(_id);
        mDatabase.child(_id).child("user_id").setValue(_userId);
        mDatabase.child(_id).child("major").setValue(_major);
        mDatabase.child(_id).child("university").setValue(_university);
        mDatabase.child(_id).child("body").setValue(_body);
    }

    public void deleteProfile(String _id){
        mDatabase.child("profile").child(_id).removeValue();
    }

}
