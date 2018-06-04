package com.techtator.berdie.model;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.techtator.berdie.Models.FBModel.FBUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDataModel {

    public DatabaseReference mDatabase; /**/
    private Map<String, FBUser> userMap;
    // Reference to an image file in Cloud Storage

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://berdie-development-7e9a8.appspot.com");


    public UserDataModel() {
        userMap = new HashMap<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public interface OnFoundDataListener {
        public void notifyFoundUser(FBUser data);

        public void notifyNotFoundUser();

        public void notifyError(String info);
    }

    public void findUserById(String uid, final OnFoundDataListener listener) {

        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(uid);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot d) {
                if (d.getValue() == null) {
                    listener.notifyNotFoundUser();
                    return;
                }
                String id = (String) d.getKey();
                String firstName = d.child("first_name").getValue(String.class);
                String lastName = d.child("last_name").getValue(String.class);
                String communityId = d.child("community_id").getValue(String.class);
                String dateOfBirth = d.child("date_of_birth").getValue(String.class);
                String geotag = d.child("geotag").getValue(String.class);
                boolean isActive = d.child("is_active").getValue(Boolean.class);
                String password = d.child("password").getValue(String.class);
                String phone = d.child("phone").getValue(String.class);
                Date timeStamp = new Date(d.child("time_stamp").getValue(Long.class));
                String userRole = d.child("user_role").getValue(String.class);
                String walletId = d.child("wallet_id").getValue(String.class);
                String email = d.child("email").getValue(String.class);
                String profilePic = d.child("profile_pic").getValue(String.class);
                FBUser mUser = new FBUser(id, firstName, lastName, communityId, dateOfBirth, geotag, isActive, password, phone, timeStamp, userRole, walletId, profilePic, email);
                listener.notifyFoundUser(mUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("getMessages", databaseError.getMessage());
                listener.notifyError(databaseError.getMessage());
            }
        });

        return;
    }

    public FBLiveData<List<FBUser>> getUsers() {
        final FBLiveData<List<FBUser>> liveData = new FBLiveData<>(FirebaseDatabase.getInstance().getReference("users"));
        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<FBUser> userList = new ArrayList<>();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    String id = (String) d.getKey();
                    String firstName = d.child("first_name").getValue(String.class);
                    String lastName = d.child("last_name").getValue(String.class);
                    String communityId = d.child("community_id").getValue(String.class);
                    String dateOfBirth = d.child("date_of_birth").getValue(String.class);
                    String geotag = d.child("geotag").getValue(String.class);
                    boolean isActive = d.child("is_active").getValue(Boolean.class);
                    String password = d.child("password").getValue(String.class);
                    String phone = d.child("phone").getValue(String.class);
                    Date timeStamp = new Date(d.child("time_stamp").getValue(Long.class));
                    String userRole = d.child("user_role").getValue(String.class);
                    String walletId = d.child("wallet_id").getValue(String.class);
                    String email = d.child("email").getValue(String.class);
                    String profilePic = d.child("profile_pic").getValue(String.class);
                    FBUser mUser = new FBUser(id, firstName, lastName, communityId, dateOfBirth, geotag, isActive, password, phone, timeStamp, userRole, walletId, profilePic, email);
                    userList.add(mUser);
                }

                liveData.setValue(userList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("getMessages", databaseError.getMessage());
            }
        });
        return liveData;
    }

    public FBLiveData<FBUser> setUserById(String _userId) {
        final FBLiveData<FBUser> liveData = new FBLiveData<>(FirebaseDatabase.getInstance().getReference("users").child(_userId));

        storageRef = storageRef.child("users/" + _userId + "profile_pic/");

        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot d) {

                if (d.getValue() == null) return;
                String id= (String) d.getKey();
                String firstName = d.child("first_name").getValue(String.class);
                String lastName = d.child("last_name").getValue(String.class);
                String communityId = d.child("community_id").getValue(String.class);
                String dateOfBirth = d.child("date_of_birth").getValue(String.class);
                String geotag = d.child("geotag").getValue(String.class);
                boolean isActive = d.child("is_active").getValue(Boolean.class);
                String password = d.child("password").getValue(String.class);
                String phone = d.child("phone").getValue(String.class);
                Date timeStamp = new Date(d.child("time_stamp").getValue(Long.class));
                String userRole = d.child("user_role").getValue(String.class);
                String walletId = d.child("wallet_id").getValue(String.class);
                String email = d.child("email").getValue(String.class);
                String profilePic = d.child("profile_pic").getValue(String.class);

                FBUser mUser = new FBUser(id, firstName, lastName, communityId, dateOfBirth, geotag, isActive, password, phone, timeStamp, userRole, walletId, profilePic, email);
                liveData.setValue(mUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("getUserById", databaseError.getMessage());
            }
        });

        return liveData;
    }

    public FBLiveData<List<FBUser>> setStudentsByLimitedNumber(final int number) {

        //When you get all -> number: 0

        final FBLiveData<List<FBUser>> liveData =
                new FBLiveData<>(FirebaseDatabase.getInstance().getReference("users").orderByChild("user_role").equalTo("1"));
        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                final ArrayList<FBUser> studentList = new ArrayList<>();
                for (DataSnapshot d : snapshot.getChildren()) {
                    if (d.child("is_active").getValue(Boolean.class) == true) {
                        String id = (String) d.child("id").getValue();
                        String firstName = d.child("first_name").getValue(String.class);
                        String lastName = d.child("last_name").getValue(String.class);
                        String communityId = d.child("community_id").getValue(String.class);
                        String dateOfBirth = d.child("date_of_birth").getValue(String.class);
                        String geotag = d.child("geotag").getValue(String.class);
                        boolean isActive = d.child("is_active").getValue(Boolean.class);
                        String password = d.child("password").getValue(String.class);
                        String phone = d.child("phone").getValue(String.class);
                        Date timeStamp = new Date(d.child("time_stamp").getValue(Long.class));
                        String userRole = d.child("user_role").getValue(String.class);
                        String walletId = d.child("wallet_id").getValue(String.class);
                        String email = d.child("email").getValue(String.class);
                        String profilePic = d.child("profile_pic").getValue(String.class);

                        FBUser fbUser = new FBUser(id, firstName, lastName, communityId, dateOfBirth, geotag, isActive, password, phone, timeStamp, userRole, walletId, profilePic, email);
                        studentList.add(fbUser);
                        if (number>0 && studentList.size() == number) {
                            break;
                        }
                    }
                }
                Collections.shuffle(studentList);
                liveData.setValue(studentList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });
        return liveData;
    }

    public FBLiveData<List<FBUser>> setStudentsBySearchedNameLimitedNumber(final String name, final int number) {

        //When you get all -> number: 0

        final FBLiveData<List<FBUser>> liveData =
                new FBLiveData<>(FirebaseDatabase.getInstance().getReference("users").orderByChild("user_role").equalTo("1"));

        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final ArrayList<FBUser> studentList = new ArrayList<>();
                for (DataSnapshot d : snapshot.getChildren()) {
                    if (d.child("first_name").getValue(String.class).toLowerCase().contains(name.toLowerCase()) ||
                            d.child("last_name").getValue(String.class).toLowerCase().contains(name.toLowerCase())) {
                        String id = (String) d.getKey();
                        String firstName = d.child("first_name").getValue(String.class);
                        String lastName = d.child("last_name").getValue(String.class);
                        String communityId = d.child("community_id").getValue(String.class);
                        String dateOfBirth = d.child("date_of_birth").getValue(String.class);
                        String geotag = d.child("geotag").getValue(String.class);
                        boolean isActive = d.child("is_active").getValue(Boolean.class);
                        String password = d.child("password").getValue(String.class);
                        String phone = d.child("phone").getValue(String.class);
                        Date timeStamp = new Date(d.child("time_stamp").getValue(Long.class));
                        String userRole = d.child("user_role").getValue(String.class);
                        String walletId = d.child("wallet_id").getValue(String.class);
                        String email = d.child("email").getValue(String.class);
                        String profilePic = d.child("profile_pic").getValue(String.class);
                        FBUser user = new FBUser(id, firstName, lastName, communityId, dateOfBirth, geotag, isActive, password, phone, timeStamp, userRole, walletId, email, profilePic);
                        studentList.add(user);
                        if (number>0 && studentList.size() == number) {
                            break;
                        }
                    }
                }
                liveData.setValue(studentList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error", databaseError.getMessage());
            }
        });

        return liveData;
    }


    public void updateUser(String _userId, String _firstName, String _lastName, String _communityId, String _dateOfBirth, String _geotag, boolean _isActive, String _password, String _phone, Date _timeStamp, String _userRole, String _walletId, String email, String profilePic) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(_userId);
        mDatabase.child("first_name").setValue(_firstName);
        mDatabase.child("last_name").setValue(_lastName);
        mDatabase.child("community_id").setValue(_communityId);
        mDatabase.child("date_of_birth").setValue(_dateOfBirth);
        mDatabase.child("geotag").setValue(_geotag);
        mDatabase.child("is_active").setValue(_isActive);
        mDatabase.child("password").setValue(_password);
        mDatabase.child("phone").setValue(_phone);
        mDatabase.child("time_stamp").setValue(_timeStamp.getTime());
        mDatabase.child("user_role").setValue(_userRole);
        mDatabase.child("wallet_id").setValue(_walletId);
        mDatabase.child("email").setValue(email);
        mDatabase.child("profile_pic").setValue(profilePic);
    }

    public void deleteUser(String _userId) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(_userId);
        mDatabase.removeValue();
    }

    public void addUser(String _userId, String _firstName, String _lastName, String _communityId, String _dateOfBirth, String _geotag, boolean _isActive, String _password, String _phone, String _userRole, String _walletId, String email, String profilePic) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        Date _timeStamp = new Date();

        FBUser _user = new FBUser(_userId, _firstName, _lastName, _communityId, _dateOfBirth, _geotag, _isActive, _password, _phone, _timeStamp, _userRole, _walletId, profilePic, email);
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = _user.toMap();
        childUpdates.put("/" + _userId, values);
        mDatabase.updateChildren(childUpdates);
    }

    public void refleshUserMap(LifecycleOwner owner) {
        getUsers().observe(owner, new Observer<List<FBUser>>() {
            @Override
            public void onChanged(@Nullable List<FBUser> list) {
                userMap.clear();
                for (FBUser u : list) {
                    userMap.put(u.getId(), u);
                }
            }
        });
    }

    public FBUser getUserById(String userId) {
        return userMap.get(userId);
    }

}
