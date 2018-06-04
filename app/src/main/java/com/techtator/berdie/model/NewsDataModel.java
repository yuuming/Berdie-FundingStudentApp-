package com.techtator.berdie.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBNews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;


public class NewsDataModel {
    public DatabaseReference mDatabase; /**/
    public NewsDataModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public LiveData<FBNews> getNewsById(String _newsId) {
        final MutableLiveData<FBNews> liveData = new MutableLiveData<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("news").child(_newsId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) return;
                String body = (String)dataSnapshot.child("body").getValue();
                boolean isActive = (boolean) dataSnapshot.child("is_active").getValue();
                String newsId = (String) dataSnapshot.child("news_id").getValue();
                String newsPicture = (String) dataSnapshot.child("news_picture").getValue();
                String title = (String) dataSnapshot.child("title").getValue();
                int newsType =  Integer.parseInt(dataSnapshot.child("news_type").getValue().toString());
                Date timeStamp = new Date(dataSnapshot.child("time_stamp").getValue(Long.class));

                FBNews news = new FBNews(newsId,title, body, newsPicture, isActive, newsType, timeStamp);

                Log.d("", "=== get NewsById ===");
                Log.d("--- body ---", news.getBody());
                Log.d("--- isActive ---", valueOf(news.isActive()));
                Log.d("--- newsId ---", news.getNewsId());
                Log.d("--- newsPicture ---", news.getNewsPicture());
                Log.d("--- title ---", news.getTitle());
                Log.d("", "======================");

                liveData.setValue(news);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });

        return liveData;
    }

    public MutableLiveData<List<FBNews>> setNewsList(){
        final MutableLiveData<List<FBNews>> liveData = new MutableLiveData<>();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("news").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<FBNews> newsList = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String body = (String) snapshot.child("body").getValue();
                    boolean isActive = (boolean) snapshot.child("is_active").getValue();
                    String newsId = (String) snapshot.child("news_id").getValue();
                    String newsPicture = (String) snapshot.child("news_picture").getValue();
                    String title = (String) snapshot.child("title").getValue();
                    String test = snapshot.child("news_type").getValue().toString();
                    int newsType =  Integer.parseInt(snapshot.child("news_type").getValue().toString());
                    Date timeStamp = new Date(snapshot.child("time_stamp").getValue(Long.class));

                    FBNews news = new FBNews(newsId,title, body, newsPicture, isActive, newsType, timeStamp);
                    newsList.add(news);

                }
                liveData.setValue(newsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });

        return liveData;
    }

    public LiveData<List<FBNews>> getActiveNewsList() {
        final MutableLiveData<List<FBNews>> liveData = new MutableLiveData<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("news");
        mDatabase.orderByChild("is_active").equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<FBNews> newsList = new ArrayList<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if((boolean) snapshot.child("is_active").getValue()){
                        String body = (String) snapshot.child("body").getValue();
                        boolean isActive = (boolean) snapshot.child("is_active").getValue();
                        String newsId = (String) snapshot.child("news_id").getValue();
                        String newsPicture = (String) snapshot.child("news_picture").getValue();
                        String title = (String) snapshot.child("title").getValue();
                        String test = snapshot.child("news_type").getValue().toString();
                        int newsType =  Integer.parseInt(snapshot.child("news_type").getValue().toString());
                        Date timeStamp = new Date(snapshot.child("time_stamp").getValue(Long.class));
                        FBNews news = new FBNews(newsId,title, body, newsPicture, isActive, newsType, timeStamp);
                        newsList.add(news);
                    }
                }
                // change the order: new -> old
                Collections.sort(newsList, new Comparator<FBNews>() {
                    public int compare(FBNews a, FBNews b) {
                        if (a.getTimeStamp() == null || b.getTimeStamp() == null)
                            return 0;
                        if(a.getTimeStamp().getTime() > b.getTimeStamp().getTime())
                            return -1;
                        if(a.getTimeStamp().getTime() < b.getTimeStamp().getTime())
                            return 1;
                        return a.getTimeStamp().compareTo(b.getTimeStamp());
                    }
                });
                liveData.setValue(newsList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });

        return liveData;
    }

    public void addNews(String _newsId, String _title, String _body, String _newsPicture, boolean _isActive, int _newsType) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("news");
        Date _timeStamp = new Date();
        FBNews fbNews = new FBNews(_newsId, _title, _body, _newsPicture, _isActive, _newsType, _timeStamp);

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = fbNews.toMap();
        childUpdates.put("/" + _newsId, values);

        mDatabase.updateChildren(childUpdates);
    }

    public void updateNews(String _newsId, String _title, String _body, String _newsPicture, boolean _isActive, int _newsType, Date _timeStamp) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("news").child(_newsId);
        mDatabase.child("news_id").setValue(_newsId);
        mDatabase.child("title").setValue(_title);
        mDatabase.child("body").setValue(_body);
        mDatabase.child("news_picture").setValue(_newsPicture);
        mDatabase.child("is_active").setValue(_isActive);
        mDatabase.child("news_type").setValue(_newsType);
        mDatabase.child("time_stamp").setValue(_timeStamp.getTime());
    }

    public void deleteNews(String _newsId) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("news").child(_newsId);
        mDatabase.removeValue();
    }
}
