package com.techtator.berdie.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBRaffleDistribution;
import com.techtator.berdie.Models.FBModel.FBRaffleResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

public class RaffleDistributionDataModel {
    public DatabaseReference mDatabase; /**/

    public interface OnChangeListListener {
        public void notifyChangedList(List<FBRaffleDistribution> list);
    }

    public interface OnChangeDataListener {
        public void notifyChangedData(FBRaffleDistribution data);
    }

    public void setRaffleDistributionById(String raffleDistributionId, final OnChangeDataListener listener) {

        mDatabase = FirebaseDatabase.getInstance().getReference("raffle_distribution").child(raffleDistributionId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FBRaffleDistribution raffleDistribution = new FBRaffleDistribution();
                String id = (String) dataSnapshot.child("id").getValue();
                String raffleId = (String) dataSnapshot.child("raffle_id").getValue();
                Double percentage = Double.parseDouble((String) String.valueOf(dataSnapshot.child("percentage").getValue()));
                raffleDistribution = new FBRaffleDistribution(id, raffleId, percentage);

                Log.d("", "======getRaffleDistributionID======");
                Log.d("-- id --", raffleDistribution.getId());
                Log.d("-- raffleId --", raffleDistribution.getRaffleId());
                Log.d("-- percentage --", valueOf(raffleDistribution.getPercentage()));

                Log.d("", "=================");

                listener.notifyChangedData(raffleDistribution);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });
    }

    public void setRaffleDistributionList(final OnChangeListListener listener) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("raffle_distribution").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final ArrayList<FBRaffleDistribution> raffleDistributionList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = (String) dataSnapshot.child("id").getValue();
                    String raffleId = (String) dataSnapshot.child("raffle_id").getValue();
                    Double percentage = Double.parseDouble((String) String.valueOf(dataSnapshot.child("percentage").getValue()));

                    raffleDistributionList.add(new FBRaffleDistribution(id, raffleId, percentage));
                }
                Log.d("", "=======RAFFLE DISTRIBUTION LIST ======");
                for (int i = 0; i < raffleDistributionList.size(); i++) {
                    Log.d("-- id --", raffleDistributionList.get(i).getId());
                    Log.d("-- raffleId --", raffleDistributionList.get(i).getRaffleId());
                    Log.d("-- percentage --", valueOf(raffleDistributionList.get(i).getPercentage()));
                }
                Log.d("", "===========================");

                listener.notifyChangedList(raffleDistributionList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });

    }

    public void addRaffleDistribution(String _id, String _raffleId, double _percentage){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FBRaffleDistribution raffleDistribution = new FBRaffleDistribution(_id, _raffleId, _percentage );

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = raffleDistribution.toMap();
        childUpdates.put("/" + _id, values);

        mDatabase.child("raffle_distribution").updateChildren(childUpdates);
    }
    public void updateRaffleDistribution(String _id, String _raffleId, double _percentage){

        mDatabase = FirebaseDatabase.getInstance().getReference("raffle_distribution");
        mDatabase.child(_id).child("id").setValue(_id);
        mDatabase.child(_id).child("raffle_id").setValue(_raffleId);
        mDatabase.child(_id).child("percentage").setValue(_percentage);

    }
    public void deleteRaffleDistribution(String _id){
        mDatabase = FirebaseDatabase.getInstance().getReference("raffle_distribution").child(_id);
        mDatabase.removeValue();
    }
}
