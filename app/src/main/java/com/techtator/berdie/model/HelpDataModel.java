package com.techtator.berdie.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.Models.FBModel.FBHelp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HelpDataModel {
    public DatabaseReference mDatabase;
    public HelpDataModel(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public interface OnChangeListListener {
        public void notifyChangedList(List<FBHelp> list);
    }

    public interface OnChangeDataListener {
        public void notifyChangedData(FBHelp data);
    }

    public void setHelpById(String helpId, final OnChangeDataListener listener) {

        mDatabase = FirebaseDatabase.getInstance().getReference("help").child(helpId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String body = (String) dataSnapshot.child("body").getValue();
                String header = (String) dataSnapshot.child("header").getValue();
                String id = (String) dataSnapshot.child("id").getValue();
                String picture = (String) dataSnapshot.child("picture").getValue();

                FBHelp help = new FBHelp(id, header, body, picture);

                Log.d("", "=========getHelpId=======");
                Log.d("", help.getId());
                Log.d("", help.getHeader());
                Log.d("", help.getBody());
                Log.d("", help.getPicture());
                Log.d("", "=================");

                listener.notifyChangedData(help);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });
    }

    public void setHelpList(final OnChangeListListener listener) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("help").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final ArrayList<FBHelp> helpList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String body = (String) dataSnapshot.child("body").getValue();
                    String header = (String) dataSnapshot.child("header").getValue();
                    String id = (String) dataSnapshot.child("id").getValue();
                    String picture = (String) dataSnapshot.child("picture").getValue();

                    FBHelp help= new FBHelp(id, header, body, picture);
                    helpList.add(help);

                }
                listener.notifyChangedList(helpList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });
    }


    public void addHelp(String _id, String _header, String _body, String _picture){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("help");
        FBHelp fbHelp = new FBHelp(_id, _header, _body, _picture );

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = fbHelp.toMap();
        childUpdates.put("/" + _id, values);

        mDatabase.updateChildren(childUpdates);
    }
    public void updateHelp(String _id, String _header, String _body, String _picture){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("help").child(_id);
        mDatabase.child(_id).child("id").setValue(_id);
        mDatabase.child(_id).child("header").setValue(_header);
        mDatabase.child(_id).child("body").setValue(_body);
        mDatabase.child(_id).child("picture").setValue(_picture);

    }
    public void deleteHelp(String _id){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("help").child(_id);
        mDatabase.removeValue();
    }
}
