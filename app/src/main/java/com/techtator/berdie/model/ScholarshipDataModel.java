package com.techtator.berdie.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBScholarship;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;


public class ScholarshipDataModel{
    private DatabaseReference mDatabase;

    public FBLiveData<List<FBScholarship>> getScholarshipList() {
        final FBLiveData<List<FBScholarship>> liveData =
                new FBLiveData<>(FirebaseDatabase.getInstance().getReference().child("scholarship"));
        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                final ArrayList<FBScholarship> scholarshipList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : data.getChildren()) {
                    String id = (String) dataSnapshot.child("id").getValue();
                    String body = (String) dataSnapshot.child("body").getValue();
                    Date dueDate= new Date(dataSnapshot.child("due_date").getValue(Long.class));
                    String header = (String) dataSnapshot.child("header").getValue();
                    String picture = (String) dataSnapshot.child("picture").getValue();
                    double scholarshipFee= dataSnapshot.child("scholarship_fee").getValue(Double.class);
                    String websiteLink = (String) dataSnapshot.child("website_link").getValue();
                    Date timeStamp= new Date(dataSnapshot.child("time_stamp").getValue(Long.class));
                    scholarshipList.add(new FBScholarship(id,body,dueDate,header,picture,scholarshipFee,websiteLink,timeStamp));
                }

                liveData.setValue(scholarshipList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });

        return liveData;
    }

    public FBLiveData<FBScholarship> getScholarshipById(final String id) {
        final FBLiveData<FBScholarship> liveData =
                new FBLiveData<>(FirebaseDatabase.getInstance().getReference().child("scholarship").child(id));
        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String id = (String) snapshot.child("id").getValue();
                String body = (String) snapshot.child("body").getValue();
                Date dueDate = new Date(snapshot.child("due_date").getValue(Long.class));
                String header = (String) snapshot.child("header").getValue();
                String picture = (String) snapshot.child("picture").getValue();
                double scholarshipFee = snapshot.child("scholarship_fee").getValue(Double.class);
                String websiteLink = (String) snapshot.child("website_link").getValue();
                Date timeStamp = new Date(snapshot.child("time_stamp").getValue(Long.class));
                FBScholarship scholarship = new FBScholarship(id, body, dueDate, header, picture, scholarshipFee, websiteLink, timeStamp);
                liveData.setValue(scholarship);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });

        return liveData;
    }

    public LiveData<List<FBScholarship>> getActiveScholarshipList() {
        mDatabase = FirebaseDatabase.getInstance().getReference("scholarship");
        final MutableLiveData<List<FBScholarship>> liveData = new MutableLiveData<>();

        mDatabase.orderByChild("due_date").startAt(new Date().getTime()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final ArrayList<FBScholarship> scholarshipList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = (String) dataSnapshot.child("id").getValue();
                    String body = (String) dataSnapshot.child("body").getValue();
                    Date dueDate= new Date(dataSnapshot.child("due_date").getValue(Long.class));
                    String header = (String) dataSnapshot.child("header").getValue();
                    String picture = (String) dataSnapshot.child("picture").getValue();
                    double scholarshipFee= dataSnapshot.child("scholarship_fee").getValue(Double.class);
                    String websiteLink = (String) dataSnapshot.child("website_link").getValue();
                    Date timeStamp= new Date(dataSnapshot.child("time_stamp").getValue(Long.class));
                    scholarshipList.add(new FBScholarship(id,body,dueDate,header,picture,scholarshipFee,websiteLink,timeStamp));
                }
                liveData.setValue(scholarshipList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });

        return liveData;
    }

    public LiveData<List<FBScholarship>> getActiveScholarshipsBySearchedWord(final String word) {
        mDatabase = FirebaseDatabase.getInstance().getReference("scholarship");
        final MutableLiveData<List<FBScholarship>> liveData = new MutableLiveData<>();

        mDatabase.orderByChild("due_date").startAt(new Date().getTime()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final ArrayList<FBScholarship> scholarshipList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("body").getValue(String.class).toLowerCase().contains(word.toLowerCase()) ||
                            dataSnapshot.child("header").getValue(String.class).toLowerCase().contains(word.toLowerCase()) ) {
                        String id = (String) dataSnapshot.child("id").getValue();
                        String body = (String) dataSnapshot.child("body").getValue();
                        Date dueDate = new Date(dataSnapshot.child("due_date").getValue(Long.class));
                        String header = (String) dataSnapshot.child("header").getValue();
                        String picture = (String) dataSnapshot.child("picture").getValue();
                        double scholarshipFee = dataSnapshot.child("scholarship_fee").getValue(Double.class);
                        String websiteLink = (String) dataSnapshot.child("website_link").getValue();
                        Date timeStamp = new Date(dataSnapshot.child("time_stamp").getValue(Long.class));
                        scholarshipList.add(new FBScholarship(id, body, dueDate, header, picture, scholarshipFee, websiteLink, timeStamp));
                    }
                }
                liveData.setValue(scholarshipList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });

        return liveData;
    }

    public void addScholarship(String _id, String _body, Date _dueDate, String _header, String _picture, double _scholarshipFee, String _websiteLink, Date _timeStamp){

        mDatabase = FirebaseDatabase.getInstance().getReference();
        FBScholarship scholarship = new FBScholarship(_id,_body,_dueDate, _header, _picture,_scholarshipFee, _websiteLink, _timeStamp);

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = scholarship.toMap();
        childUpdates.put("/" + _id, values);

        mDatabase.child("scholarship").updateChildren(childUpdates);
    }

    public void updateScholarship(String _id, String _body, Date _dueDate,String _header,String _picture, double _scholarshipFee, String _websiteLink, Date _timeStamp){

        mDatabase.child("scholarship").child(_id).child("id").setValue(_id);
        mDatabase.child("scholarship").child(_id).child("body").setValue(_body);
        mDatabase.child("scholarship").child(_id).child("due_date").setValue(_dueDate.getTime());
        mDatabase.child("scholarship").child(_id).child("header").setValue(_header);
        mDatabase.child("scholarship").child(_id).child("picture").setValue(_picture);
        mDatabase.child("scholarship").child(_id).child("scholarship_fee").setValue(_scholarshipFee);
        mDatabase.child("scholarship").child(_id).child("website_link").setValue(_websiteLink);
        mDatabase.child("scholarship").child(_id).child("time_stamp").setValue(_timeStamp.getTime());

    }
    public void deleteScholarship(String _id){
        mDatabase.child("scholarship").child(_id).removeValue();
    }
}