package com.techtator.berdie.homeStudent;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.techtator.berdie.Models.FBModel.FBGoal;
import com.squareup.picasso.Picasso;
import com.techtator.berdie.Models.FBModel.FBScholarship;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.R;
import com.techtator.berdie.goal.GoalFragment;
import com.techtator.berdie.raffle.RaffleFragment;
import com.techtator.berdie.scholarship.ScholarshipFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class HomeStudentFragment extends Fragment implements HomeStudentContract.View {

    private OnListFragmentInteractionListener mListener;
    private OnListFragmentGoalInteractionListener goalListener;
    private HomeStudentRecyclerViewAdapter recyclerViewAdapter;
    private HomeStudentGoalRecyclerViewAdapter goalAdapter;
    private HomeStudentPresenter presenter;

    private TextView minimum_amount,time_text,numOfTickets;
    private ImageView winner1, winner2, winner3;
//    private GridLayout mWinnersGrid;

    public HomeStudentFragment() {
    }

    public static HomeStudentFragment newInstance() {
        HomeStudentFragment fragment = new HomeStudentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.home_studentfragment_item_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.home);
        presenter = new HomeStudentPresenter(this, this);

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        HomeStudentPresenter presenter = new HomeStudentPresenter();

        final TextView my_ticket_text = (TextView) view.findViewById(R.id.home_my_ticket);
        final ImageView home_ticket_icon = (ImageView)  view.findViewById(R.id.home_ticket_icon);
        final Button plus_icon = (Button) view.findViewById(R.id.home_plus_icon);

        TextView this_month_raff = (TextView) view.findViewById(R.id.this_month_raff);
        TextView minimum_guarantee = (TextView) view.findViewById(R.id.home_minimum_guarantee);
        minimum_amount = (TextView) view.findViewById(R.id.home_minimum_amount);
        ImageView time_icon = (ImageView) view.findViewById(R.id.home_time_icon);
        time_text = (TextView) view.findViewById(R.id.time_texts);
        numOfTickets = (TextView) view.findViewById(R.id.home_ticket_num);

        // winners
//        mWinnersGrid = (GridLayout) view.findViewById(R.id.winnder_icon_grid);


        final ImageView goal_icon = (ImageView) view.findViewById(R.id.goal_icon);
        final TextView goal_text = (TextView) view.findViewById(R.id.student_home_goal_title);
        final View line_goal = (View) view.findViewById(R.id.line_goal);
        final ImageView hat_icon = (ImageView) view.findViewById(R.id.hat_icon);
        final TextView scholarship_text = (TextView) view.findViewById(R.id.scholarship);
        final View line_school = (View) view.findViewById(R.id.line_school);
        final ImageView goal_right_icon = (ImageView) view.findViewById(R.id.goal_right_icon);
        final ImageView scholarship_right_icon = (ImageView) view.findViewById(R.id.scholarship_right_icon);
        winner1 = (ImageView) view.findViewById(R.id.winner_pic1);
        winner2 = (ImageView) view.findViewById(R.id.winner_pic2);
        winner3 = (ImageView) view.findViewById(R.id.winner_pic3);

        //goal recyclerView
        RecyclerView goalRecyclerView = (RecyclerView) view.findViewById(R.id.student_home_goal_recyclerView);
        goalAdapter = new HomeStudentGoalRecyclerViewAdapter(goalListener, presenter);
//        goalAdapter = new HomeStudentGoalRecyclerViewAdapter(goalListener, new HomeStudentPresenter(this));

        goalRecyclerView.setAdapter(goalAdapter);



        //recyclerView
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.student_home_recyclerView);
        recyclerViewAdapter = new HomeStudentRecyclerViewAdapter(mListener, presenter);

        recyclerView.setAdapter(recyclerViewAdapter);


        goal_right_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, GoalFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        scholarship_right_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, ScholarshipFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        plus_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, RaffleFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });




    };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        }
        if (context instanceof OnListFragmentGoalInteractionListener) {
            goalListener = (OnListFragmentGoalInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }




    @Override
    public void notifySchoolDataChanged() {
        if (recyclerViewAdapter != null) {
            recyclerViewAdapter.notifyDataSetChanged();

        }
    }


    @Override
    public void notifyGoalDataChanged() {
        if(goalAdapter != null) {
            goalAdapter.notifyDataSetChanged();

        }
    }



    @Override
    public void notifyTopGoalChange(String minimumAmountText) {
        System.out.println("========= notifyTopGoalChange");
        minimum_amount.setText(minimumAmountText);
    }

    @Override
    public void onResume() {
        super.onResume();

        // reset all fragments in history
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }

        System.out.println("========= onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("========= onPause");
    }
    @Override
    public void notifyWinnersChanged(List<FBUser> users) {
        Collections.shuffle(users);

        for (FBUser fbUser: users) {
            int i = users.indexOf(fbUser);

            if (i==0) {
                Picasso.get()
                        .load(fbUser.getProfilePic())
                        .placeholder(R.drawable.icons8_gender_neutral_user)
                        .error(R.drawable.icons8_gender_neutral_user)
                        .into(winner1);
            } else if (i==1) {
                Picasso.get()
                        .load(fbUser.getProfilePic())
                        .placeholder(R.drawable.icons8_gender_neutral_user)
                        .error(R.drawable.icons8_gender_neutral_user)
                        .into(winner2);
            } else if (i==2) {
                Picasso.get()
                        .load(fbUser.getProfilePic())
                        .placeholder(R.drawable.icons8_gender_neutral_user)
                        .error(R.drawable.icons8_gender_neutral_user)
                        .into(winner3);
            }
        }
    }


//    @Override
//    public void notifyWinnersChanged(List<FBUser> users) {
//        mWinnersGrid.setColumnCount(users.size());
//        for (FBUser user : users) {
//            ImageView imageView = new ImageView(getContext());
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(15, 15, 15, 15);
//            Picasso.get()
//                    .load(user.getProfilePic())
//                    .placeholder(R.drawable.icons8_gender_neutral_user)
//                    .error(R.drawable.icons8_gender_neutral_user)
//                    .into(imageView);
//
//            mWinnersGrid.addView(imageView);
//        }
//    }

    @Override
    public void notifyRaffleRestOfTimeInterval(String timeText) {
        time_text.setText(timeText);
    }

    @Override
    public void notifyNumberOfTicketsChange(String numberOfTicketsText) {
        numOfTickets.setText(numberOfTicketsText);
    }


    @Override
    public void onSuccess() {
    }

    @Override
    public void onError() {
    }


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(FBScholarship item);
    }
    public interface OnListFragmentGoalInteractionListener {
        void onListFragmentGoalInteraction(FBGoal item);
    }
};
