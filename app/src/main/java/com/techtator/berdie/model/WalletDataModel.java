package com.techtator.berdie.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBWallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

public class WalletDataModel {
    public DatabaseReference mDatabase; /**/
    private HashMap<String,FBWallet> mWalletMap;
    public WalletDataModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mWalletMap = new HashMap<>();
    }

    public interface OnChangeListListener {
        public void notifyChangedList(List<FBWallet> list);
    }

    public interface OnChangeDataListener {
        public void notifyChangedData(FBWallet data);
    }

    public void setWalletById(final String _walletId, final OnChangeDataListener listener) {
        mDatabase = FirebaseDatabase.getInstance().getReference("wallet").child(_walletId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot d) {
                    String currentBalance = (valueOf(d.child("current_balance").getValue()));
                    String id = (String) d.child("id").getValue();
                    String userId = (String) d.child("user_id").getValue();
                    Double _currentBalance = Double.parseDouble(currentBalance);
                    FBWallet wallet = new FBWallet(id, userId, _currentBalance);
                    listener.notifyChangedData(wallet);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });
    }

    public FBLiveData<FBWallet> getWalletByUserId(String userId) {
        final FBLiveData<FBWallet> liveData =
                new FBLiveData<>(FirebaseDatabase.getInstance().getReference("wallet").orderByChild("user_id").equalTo(userId));
        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot d) {
                final ArrayList<FBWallet> walletList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : d.getChildren()) {
                    String id = (String) dataSnapshot.child("id").getValue();
                    String userId = (String) dataSnapshot.child("user_id").getValue();
                    double currentBalance = dataSnapshot.child("current_balance").getValue(Double.class);
                    FBWallet wallet = new FBWallet(id, userId, currentBalance);
                    walletList.add(wallet);
                }
                if (!walletList.isEmpty())
                    liveData.setValue(walletList.get(0));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });
        return liveData;
    }

    public void setWalletList(final OnChangeListListener listener) {

        mDatabase.child("wallet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<FBWallet> walletList = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Double currentBalance = Double.valueOf(String.valueOf(snapshot.child("current_balance").getValue()));
                    String id = (String) snapshot.child("id").getValue();
                    String userId = (String) snapshot.child("user_id").getValue();
                    walletList.add(new FBWallet(id, userId, currentBalance));
                }
                    listener.notifyChangedList(walletList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });
    }

    public String addWallet(String _userId, double _currentBalance){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("wallet");
        String _walletId = mDatabase.push().getKey();
        FBWallet fbWallet = new FBWallet(_walletId, _userId, _currentBalance);

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = fbWallet.toMap();
        childUpdates.put("/" + _walletId, values);

        mDatabase.updateChildren(childUpdates);

        return _walletId;
    };

    public void updateWallet(final FBWallet mWallet){
        mDatabase = FirebaseDatabase.getInstance().getReference("wallet");
        mDatabase.child(mWallet.getWalletId()).child("current_balance").setValue(mWallet.getCurrentBalance());
        mDatabase.child(mWallet.getWalletId()).child("user_id").setValue(mWallet.getUserId());
        mDatabase.child(mWallet.getWalletId()).child("id").setValue(mWallet.getWalletId());

    }




    public void deleteWallet(String _walletId){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("wallet").child(_walletId);
        mDatabase.removeValue();
    };

    public void refreshWalletMap() {
        setWalletList(new OnChangeListListener() {
            @Override
            public void notifyChangedList(List<FBWallet> list) {
                mWalletMap.clear();
                for (FBWallet u: list) {
                    mWalletMap.put(u.getWalletId(), u);
                }
            }
        });
    }
}
