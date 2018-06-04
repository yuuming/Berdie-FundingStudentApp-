package com.techtator.berdie.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.Models.FBModel.FBUserRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRoleDataModel {
    public DatabaseReference mDatabase; /**/
    public UserRoleDataModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public interface OnChangeListListener {
        public void notifyChangedList(List<FBUserRole> list);
    }

    public interface OnChangeDataListener {
        public void notifyChangedData(FBUserRole data);
    }

    public void setUserRoleById(String _id, final OnChangeDataListener listener){

        mDatabase = FirebaseDatabase.getInstance().getReference("user_role").child(_id);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                snapshot.getChildren();
                String id = (String) snapshot.child("id").getValue();
                String role = (String) snapshot.child("role").getValue();

                final FBUserRole userRole = new FBUserRole();
                userRole.setId(id);
                userRole.setRole(role);

                Log.d("", "==========getUserRoleById===========");
                Log.d("--- id ---", userRole.getId());
                Log.d("---role---", userRole.getRole());
                Log.d("", "====================================");

                listener.notifyChangedData(userRole);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });

    }

    public void setUserRoleList(final OnChangeListListener listener) {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("user_role").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final ArrayList<FBUserRole> userRoleList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = (String) dataSnapshot.child("id").getValue();
                    String role = (String) dataSnapshot.child("role").getValue();
                    userRoleList.add(new FBUserRole(id, role));
                }
                Log.d("", "====================================");
                for(int i = 0; i<userRoleList.size(); i++) {
                    Log.d("--- id ---", userRoleList.get(i).getId());
                    Log.d("---role---", userRoleList.get(i).getRole());
                }
                Log.d("", "====================================");

                listener.notifyChangedList(userRoleList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });

    }

    public void addUserRole(String _id, String _role){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FBUserRole userRole = new FBUserRole(_id, _role);

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = userRole.toMap();
        childUpdates.put("/" + _id, values);

        mDatabase.child("user_role").updateChildren(childUpdates);

    }
    public void updateUserRole(String _id, String _role){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("user_role").child(_id).child("id").setValue(_id);
        mDatabase.child("user_role").child(_id).child("role").setValue(_role);
    }
    public void deleteUserRole(String _id){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("user_role").child(_id).setValue(null);

    }

}
