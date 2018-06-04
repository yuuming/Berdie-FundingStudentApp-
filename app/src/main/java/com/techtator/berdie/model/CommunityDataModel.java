package com.techtator.berdie.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBAccountSettings;
import com.techtator.berdie.Models.FBModel.FBCommunity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommunityDataModel{
    public DatabaseReference mDatabase; /**/
    public CommunityDataModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public interface OnChangeListListener {
        public void notifyChangedList(List<FBCommunity> list);
    }

    public interface OnChangeDataListener {
        public void notifyChangedData(FBCommunity data);
    }

    public void setCommunities(final OnChangeListListener listener){
        mDatabase.child("community").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<FBCommunity> communityList = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()){

                    String communityId = d.child("id").getValue(String.class);
                    String name = d.child("name").getValue(String.class);
                    String geotag = d.child("geotag").getValue(String.class);
                    String address = d.child("address").getValue(String.class);
                    FBCommunity mCommunity = new FBCommunity(communityId,name,geotag,address);
                    communityList.add(mCommunity);
                }

                listener.notifyChangedList(communityList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("getCommunities",databaseError.getMessage());
            }
        });
    }

    public FBCommunity getCommunityById(String _communityId){
        return null;
    }

    public void addCommunity(String communityId, String name, String geotag, String address){
        FBCommunity community = new FBCommunity(communityId,name,geotag,address);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("communitz");
        FBCommunity fbCommunity = new FBCommunity(communityId,name,geotag,address);
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = fbCommunity.toMap();
        childUpdates.put("/" + communityId, values);
        mDatabase.updateChildren(childUpdates);
    }

    public void  updateCommunity(String communityId, String name, String geotag, String address){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("community").child(communityId);
        mDatabase.child("community_id").setValue(communityId);
        mDatabase.child("name").setValue(name);
        mDatabase.child("geotag").setValue(geotag);
        mDatabase.child("address").setValue(address);
    }

    public void deleteCommunity(String communityId){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("community").child(communityId);
        mDatabase.removeValue();
    }


}
