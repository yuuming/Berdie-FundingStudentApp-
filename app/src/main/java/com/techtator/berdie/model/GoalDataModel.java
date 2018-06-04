package com.techtator.berdie.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techtator.berdie.Models.FBModel.FBGoal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

public class GoalDataModel{
    public DatabaseReference mDatabase; /**/

    public interface OnChangeListListener {
        public void notifyChangedList(List<FBGoal> list);
    }

    public interface OnChangeDataListener {
        public void notifyChangedData(FBGoal data);
    }



    public void setGoalById(String goalId, final OnChangeDataListener listener) {
        mDatabase = FirebaseDatabase.getInstance().getReference("goal").child(goalId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String id = (String) dataSnapshot.child("id").getValue();
                String userId = (String) dataSnapshot.child("user_id").getValue();
                String header = (String) dataSnapshot.child("header").getValue();
                String body = (String) dataSnapshot.child("body").getValue();
                Double currentAmount = dataSnapshot.child("current_amount").getValue(Double.class);
                boolean isAccomplished = (boolean) dataSnapshot.child("is_accomplished").getValue();
                boolean isActive = (boolean) dataSnapshot.child("is_active").getValue();
                boolean isPrimary = (boolean) dataSnapshot.child("is_primary").getValue();
                Double amount = dataSnapshot.child("amount").getValue(Double.class);
                Date timeStamp= new Date(dataSnapshot.child("time_stamp").getValue(Long.class));
                FBGoal fbGoal = new FBGoal(id,userId,header,body,currentAmount,isAccomplished,isActive,isPrimary,amount,timeStamp);

                listener.notifyChangedData(fbGoal);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });

    }

    public FBLiveData<FBGoal> getGoalById(String goalId) {
        final FBLiveData<FBGoal> liveData = new FBLiveData<>(FirebaseDatabase.getInstance().getReference("goal").child(goalId));
        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String id = (String) dataSnapshot.child("id").getValue();
                String userId = (String) dataSnapshot.child("user_id").getValue();
                String header = (String) dataSnapshot.child("header").getValue();
                String body = (String) dataSnapshot.child("body").getValue();
                Double currentAmount = dataSnapshot.child("current_amount").getValue(Double.class);
                boolean isAccomplished = (boolean) dataSnapshot.child("is_accomplished").getValue();
                boolean isActive = (boolean) dataSnapshot.child("is_active").getValue();
                boolean isPrimary = (boolean) dataSnapshot.child("is_primary").getValue();
                Double amount = dataSnapshot.child("amount").getValue(Double.class);
                Date timeStamp= new Date(dataSnapshot.child("time_stamp").getValue(Long.class));
                FBGoal fbGoal = new FBGoal(id,userId,header,body,currentAmount,isAccomplished,isActive,isPrimary,amount,timeStamp);

                liveData.setValue(fbGoal);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return liveData;
    }


    public FBLiveData<List<FBGoal>> getGoalList() {
        final FBLiveData<List<FBGoal>> liveData = new FBLiveData<>(FirebaseDatabase.getInstance().getReference().child("goal"));
        liveData.setListener(
            new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    final ArrayList<FBGoal> goalList = new ArrayList<>();
                    List<FBGoal> nonPrimary = new LinkedList();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String id = (String) dataSnapshot.child("id").getValue();
                        String userId = (String) dataSnapshot.child("user_id").getValue();
                        String header = (String) dataSnapshot.child("header").getValue();
                        String body = (String) dataSnapshot.child("body").getValue();
                        Double currentAmount = Double.parseDouble((String)valueOf(dataSnapshot.child("current_amount").getValue()));
                        boolean isAccomplished = (boolean) dataSnapshot.child("is_accomplished").getValue();
                        boolean isActive = (boolean) dataSnapshot.child("is_active").getValue();
                        boolean isPrimary = (boolean) dataSnapshot.child("is_primary").getValue();
                        Double amount = Double.parseDouble((String)valueOf(dataSnapshot.child("amount").getValue()));
                        Date timeStamp= new Date(dataSnapshot.child("time_stamp").getValue(Long.class));

                        if(isPrimary==true) {
                            goalList.add(new FBGoal(id, userId, header, body, currentAmount,isAccomplished,isActive,isPrimary,amount,timeStamp));
                        }
                        nonPrimary.add(new FBGoal(id, userId, header, body, currentAmount,isAccomplished,isActive,isPrimary,amount,timeStamp));
                    }
                    goalList.addAll(nonPrimary);
                    liveData.setValue(goalList);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        return liveData;
    }

    public FBLiveData<List<FBGoal>> getAllGoalsByUserId(String userId){
        mDatabase = FirebaseDatabase.getInstance().getReference("goal");
        final FBLiveData<List<FBGoal>> liveData = new FBLiveData<>(FirebaseDatabase.getInstance().getReference().child("goal").orderByChild("user_id").equalTo(userId));
        liveData.setListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                final ArrayList<FBGoal> goalList = new ArrayList<>();
                final List<FBGoal> nonPrimary = new LinkedList();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = (String) dataSnapshot.child("id").getValue();
                    String userId = (String) dataSnapshot.child("user_id").getValue();
                    String header = (String) dataSnapshot.child("header").getValue();
                    String body = (String) dataSnapshot.child("body").getValue();
                    Double currentAmount = Double.parseDouble((String) valueOf(dataSnapshot.child("current_amount").getValue()));
                    boolean isAccomplished = (boolean) dataSnapshot.child("is_accomplished").getValue();
                    boolean isActive = (boolean) dataSnapshot.child("is_active").getValue();
                    boolean isPrimary = (boolean) dataSnapshot.child("is_primary").getValue();
                    Double amount = Double.parseDouble((String) valueOf(dataSnapshot.child("amount").getValue()));
                    Date timeStamp = new Date(dataSnapshot.child("time_stamp").getValue(Long.class));

                    if(isPrimary==true) {
                        goalList.add(new FBGoal(id, userId, header, body, currentAmount,isAccomplished,isActive,isPrimary,amount,timeStamp));
                    } else {
                        nonPrimary.add(new FBGoal(id, userId, header, body, currentAmount,isAccomplished,isActive,isPrimary,amount,timeStamp));
                    }
                }
                goalList.addAll(nonPrimary);
                liveData.setValue(goalList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });

        return liveData;
    }



    // get accomplished list : accomplished -> true
    // get onGoing list : accomplished -> false
    // primary goal will come first
    public void setFilteredGoalListByUserId(String userId, final boolean accomplished, final OnChangeListListener listener){
        mDatabase = FirebaseDatabase.getInstance().getReference("goal");
        mDatabase.orderByChild("user_id").equalTo(userId).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot snapshot) {

                final ArrayList<FBGoal> goalList = new ArrayList<>();
                final ArrayList<FBGoal> onGoingList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = (String) dataSnapshot.child("id").getValue();
                    String userId = (String) dataSnapshot.child("user_id").getValue();
                    String header = (String) dataSnapshot.child("header").getValue();
                    String body = (String) dataSnapshot.child("body").getValue();
                    Double currentAmount = Double.parseDouble((String) valueOf(dataSnapshot.child("current_amount").getValue()));
                    boolean isAccomplished = (boolean) dataSnapshot.child("is_accomplished").getValue();
                    boolean isActive = (boolean) dataSnapshot.child("is_active").getValue();
                    boolean isPrimary = (boolean) dataSnapshot.child("is_primary").getValue();
                    Double amount = Double.parseDouble((String) valueOf(dataSnapshot.child("amount").getValue()));
                    Date timeStamp = new Date(dataSnapshot.child("time_stamp").getValue(Long.class));
                    FBGoal fbGoal = new FBGoal(id, userId, header, body, currentAmount, isAccomplished, isActive, isPrimary, amount, timeStamp);
                    //get onGoing list
                    if(accomplished==false){
                        if((boolean)dataSnapshot.child("is_accomplished").getValue().equals(false)) {
                            onGoingList.add(fbGoal);
                        }
                    //get accomplished list
                    } else {
                        if((boolean)dataSnapshot.child("is_accomplished").getValue().equals(true)) {
                            goalList.add(fbGoal);
                        }
                    }
                }

                //when onGoingList.size()==1 -> the goal should be Primary goal
                if(onGoingList.size()==1) {
                    goalList.add(onGoingList.get(0));
                //separate primary goal and the others (put primary goal to goalList(0))
                } else if(onGoingList.size()>1) {
                    List<FBGoal> rest = new LinkedList();
                    for(int i = 0; i<onGoingList.size(); i++) {
                        FBGoal goal = onGoingList.get(i);
                        if(onGoingList.get(i).isPrimary()) {
                            goalList.add(goal);
                        } else {
                            rest.add(goal);
                        }
                    }
                    //add non primary goals to goalList
                    goalList.addAll(rest);
                }
                listener.notifyChangedList(goalList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });
    }

    public LiveData<FBGoal> getPrimaryGoalByUserId(String userId){
        mDatabase = FirebaseDatabase.getInstance().getReference("goal");
        final MutableLiveData<FBGoal> livedata = new MutableLiveData<>();

        mDatabase.orderByChild("user_id").equalTo(userId).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                FBGoal primaryGoal = new FBGoal();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if((boolean)dataSnapshot.child("is_accomplished").getValue().equals(false)&&(boolean)dataSnapshot.child("is_primary").getValue().equals(true)) {
                        String id = (String) dataSnapshot.child("id").getValue();
                        String userId = (String) dataSnapshot.child("user_id").getValue();
                        String header = (String) dataSnapshot.child("header").getValue();
                        String body = (String) dataSnapshot.child("body").getValue();
                        Double currentAmount = Double.parseDouble((String) valueOf(dataSnapshot.child("current_amount").getValue()));
                        boolean isAccomplished = (boolean) dataSnapshot.child("is_accomplished").getValue();
                        boolean isActive = (boolean) dataSnapshot.child("is_active").getValue();
                        boolean isPrimary = (boolean) dataSnapshot.child("is_primary").getValue();
                        Double amount = Double.parseDouble((String) valueOf(dataSnapshot.child("amount").getValue()));
                        Date timeStamp= new Date(dataSnapshot.child("time_stamp").getValue(Long.class));
                        primaryGoal = new FBGoal(id, userId, header, body, currentAmount, isAccomplished, isActive, isPrimary, amount, timeStamp);
                        livedata.setValue(primaryGoal);
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("error",databaseError.getMessage());
            }
        });

        return livedata;
    }

    public void addGoal(String _userId, String _header, String _body, double _currentAmount, boolean _isAccomplished, boolean _isActive, boolean _isPrimary, double _amount, Date timeStamp){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("goal");
        String _id = mDatabase.push().getKey();
        Date _timeStamp = new Date();

        FBGoal fbGoal = new FBGoal(_id, _userId, _header, _body, _currentAmount, _isAccomplished, _isActive, _isPrimary, _amount, _timeStamp );
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = fbGoal.toMap();
        childUpdates.put("/" + _id, values);

        mDatabase.updateChildren(childUpdates);
    }

    public void updateGoal(String _id, String _userId, String _header, String _body, double _currentAmount, boolean _isAccomplished, boolean _isActive, boolean _isPrimary, double _amount, Date _timeStamp){
        mDatabase = FirebaseDatabase.getInstance().getReference("goal");
        mDatabase.child(_id).child("id").setValue(_id);
        mDatabase.child(_id).child("user_id").setValue(_userId);
        mDatabase.child(_id).child("header").setValue(_header);
        mDatabase.child(_id).child("body").setValue(_body);
        mDatabase.child(_id).child("current_amount").setValue(_currentAmount);
        mDatabase.child(_id).child("is_accomplished").setValue(_isAccomplished);
        mDatabase.child(_id).child("is_active").setValue(_isActive);
        mDatabase.child(_id).child("is_primary").setValue(_isPrimary);
        mDatabase.child(_id).child("amount").setValue(_amount);
        mDatabase.child(_id).child("time_stamp").setValue(_timeStamp.getTime());
    }
    public void deleteGoal(String _id){
        mDatabase = FirebaseDatabase.getInstance().getReference("goal").child(_id);
        mDatabase.removeValue();
    }

    public int getProgressPercentage(FBGoal goal){
        double percentage = goal.getCurrentAmount()/goal.getAmount()*100;
        return (int)percentage;
    }

    public void changePrimaryGoal(List<FBGoal> goalList, int newPrimaryGoalPosition) {
        mDatabase = FirebaseDatabase.getInstance().getReference("goal");
        Map<String, Object> userUpdates = new HashMap<>();
        String currentPrimaryVariable = "/" + goalList.get(0).getId() + "/is_primary";
        String newPrimaryVariable = "/" + goalList.get(newPrimaryGoalPosition).getId() + "/is_primary";
        userUpdates.put(currentPrimaryVariable, false);
        userUpdates.put(newPrimaryVariable, true);
        mDatabase.updateChildren(userUpdates);
    }

    public void changePrimaryAttribute(FBGoal goal, boolean primary) {
        mDatabase = FirebaseDatabase.getInstance().getReference("goal");
        mDatabase.child(goal.getId()).child("is_primary").setValue(primary);
    }

    public List<FBGoal> mapGoals(List<FBGoal> goals, boolean accomplished) {
        final ArrayList<FBGoal> goalList = new ArrayList<>();
        final ArrayList<FBGoal> onGoingList = new ArrayList<>();
        for(int i = 0; i<goals.size(); i++) {
            //get onGoing list
            if(accomplished==false){
                if(goals.get(i).isAccomplished()==false) {
                    onGoingList.add(goals.get(i));
                }
            //get accomplished list
            } else {
                if(goals.get(i).isAccomplished()==true) {
                    goalList.add(goals.get(i));
                }
            }
        }
        //when onGoingList.size()==1 -> the goal should be Primary goal
        if(onGoingList.size()==1) {
            goalList.add(onGoingList.get(0));
        //separate primary goal and the others (put primary goal to goalList(0))
        } else if(onGoingList.size()>1) {
            List<FBGoal> rest = new LinkedList();
            for(int i = 0; i<onGoingList.size(); i++) {
                FBGoal goal = onGoingList.get(i);
                if(onGoingList.get(i).isPrimary()) {
                    goalList.add(goal);
                } else {
                    rest.add(goal);
                }
            }
            //add non primary goals to goalList
            goalList.addAll(rest);
        }
        return goalList;
    }

    public void createNewPrimaryGoalAutomatically(String _userId, double _currentAmount) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("goal");
        String _id = mDatabase.push().getKey();
        Date _timeStamp = new Date();

        FBGoal fbGoal = new FBGoal(_id, _userId, "New Goal", "", _currentAmount, false, true, true, 0, _timeStamp );
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> values = fbGoal.toMap();
        childUpdates.put("/" + _id, values);

        mDatabase.updateChildren(childUpdates);
    }

    int goalAndCurrentAmountDifference(FBGoal goal) {
        return (int) (goal.getAmount() - goal.getCurrentAmount());
    }

    void makeGoalAccomplished(FBGoal goal) {
        mDatabase = FirebaseDatabase.getInstance().getReference("goal");
        mDatabase.child(goal.getId()).child("is_accomplished").setValue(true);
    }

    void addMoneyToGoal(FBGoal goal, int amount) {
        mDatabase = FirebaseDatabase.getInstance().getReference("goal");
        mDatabase.child(goal.getId()).child("current_amount").setValue(amount);
    }

    // user's goal list ( primary goal should be the first )
    public void addMoneyToGoals(ArrayList<FBGoal> goalList, int amount) {

        for (int i = 0; i < goalList.size(); i++) {
            //distribute money to goals like waterfall
            //if the money is more than goalAmmount, make current amount full and go to the next goal
            while (amount > 0) {
                FBGoal goal = goalList.get(i);
                int distributedMoney = 0;
                int difference = goalAndCurrentAmountDifference(goal);
                if (difference > amount || (difference == 0 && i == 0)) {
                    distributedMoney = amount;
                    addMoneyToGoal(goal, (int)goal.getCurrentAmount() + distributedMoney);
                    if(!(i==0)) {
                        changePrimaryAttribute(goal, true);
                    }
                } else {
                    addMoneyToGoal(goal, difference);
                    makeGoalAccomplished(goal);
                    if(i==0) {
                        changePrimaryAttribute(goalList.get(0), false);
                    }
                    if (i == goalList.size() - 1) {
                        distributedMoney = difference;
                        createNewPrimaryGoalAutomatically(goal.getUserId(), amount + distributedMoney);
                    }
                }
                amount = amount - distributedMoney;
            }
        }
    }
}
