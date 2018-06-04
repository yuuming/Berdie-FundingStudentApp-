package com.techtator.berdie.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.*;

import java.util.*;

public  class AccountSettingsDataModel{
    DatabaseReference mDatabase;
    public AccountSettingsDataModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public interface OnChangeListListener {
        public void notifyChangedList(List<FBAccountSettings> list);
    }

    public interface OnChangeDataListener {
        public void notifyChangedData(FBAccountSettings data);
    }

    public void setAccountSettingsList(final OnChangeListListener listener) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("account_settings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final ArrayList<FBAccountSettings> accountSettingsList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String settingsId = (String) dataSnapshot.child("settings_id").getValue();
                    String userId = (String) dataSnapshot.child("user_id").getValue();
                    String address = (String) dataSnapshot.child("address").getValue();
                    String city = (String) dataSnapshot.child("city").getValue();
                    String zipCode = (String) dataSnapshot.child("zip_code").getValue();
                    String state = (String) dataSnapshot.child("state").getValue();
                    accountSettingsList.add(new FBAccountSettings(settingsId, userId, address, city, zipCode, state));
                }
                listener.notifyChangedList(accountSettingsList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });

    }

    public void addAccountSettings(String _settingsId, String _userId, String _address, String _city, String _zipCode, String _state) {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        FBAccountSettings accountSettings = new FBAccountSettings(_settingsId, _userId, _address, _city, _zipCode, _state);

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = accountSettings.toMap();
        childUpdates.put("/" + _settingsId, values);

        mDatabase.child("account_settings").updateChildren(childUpdates);
    }

    public void updateAccountSettings(String _settingsId, String _userId, String _address, String _city, String _zipCode, String _state) {

        mDatabase.child("account_settings").child(_settingsId).child("user_id").setValue(_userId);
        mDatabase.child("account_settings").child(_settingsId).child("address").setValue(_address);
        mDatabase.child("account_settings").child(_settingsId).child("city").setValue(_city);
        mDatabase.child("account_settings").child(_settingsId).child("zip_code").setValue(_zipCode);
        mDatabase.child("account_settings").child(_settingsId).child("state").setValue(_state);
    }

    public void deleteAccountSettings(String _settingsId){
        mDatabase.child("account_settings").child(_settingsId).removeValue();
    }

}
