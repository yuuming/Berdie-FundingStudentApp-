package com.techtator.berdie.goal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.R;

/**
 * Created by user on 2018-04-01.
 */

public class GoalFragment extends Fragment implements GoalContract.View {

    public final static int ONGOING_LIST_VIEW = 1;
    public final static int ACCOMPLISHED_LIST_VIEW = 2;

    GoalPresenter presenter;
    private OnGoalInteractionListener mListener;
    private GoalRecyclerViewAdapter onGoingListAdapter;
    private GoalRecyclerViewAdapter accomplishedListAdapter;
    LinearLayout accomplishedListSection;
    FloatingActionButton addGoalButton;

    public GoalFragment() {
    }

    public static GoalFragment newInstance() {
        GoalFragment fragment = new GoalFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new GoalPresenter(this, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goal_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Goal");

        accomplishedListSection = (LinearLayout) view.findViewById(R.id.accomplished_goal_section);
        addGoalButton = (FloatingActionButton) view.findViewById(R.id.add_goal_button_goal_list);
        addGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickAddGoal();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


       // Set the adapter
        RecyclerView onGoingRecyclerView = (RecyclerView) view.findViewById(R.id.goal_list_ongoing);
        RecyclerView accomplishedRecyclerView = (RecyclerView) view.findViewById(R.id.goal_list_accomplished);
        onGoingRecyclerView.addItemDecoration(GoalItemDecoration.createDefaultDecoration(50));
        accomplishedRecyclerView.addItemDecoration(GoalItemDecoration.createDefaultDecoration(50));

        onGoingListAdapter = new GoalRecyclerViewAdapter(presenter, mListener, ONGOING_LIST_VIEW);
        accomplishedListAdapter = new GoalRecyclerViewAdapter(presenter, mListener, ACCOMPLISHED_LIST_VIEW);

        onGoingRecyclerView.setAdapter(onGoingListAdapter);
        accomplishedRecyclerView.setAdapter(accomplishedListAdapter);

        onGoingRecyclerView.setNestedScrollingEnabled(false);
        accomplishedRecyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GoalFragment.OnGoalInteractionListener) {
            mListener = (GoalFragment.OnGoalInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnGoalFragmentInteractionListener");
        }
    }


    @Override
    public void notifyDataChanged() {
        if(presenter.accomplishedGoals.size()==0){
            accomplishedListSection.setVisibility(LinearLayout.INVISIBLE);

        } else {
            accomplishedListSection.setVisibility(LinearLayout.VISIBLE);
        }
        onGoingListAdapter.notifyDataSetChanged();
        accomplishedListAdapter.notifyDataSetChanged();
    }


    public interface OnGoalInteractionListener {
        void onGoalHistory(FBGoal item);
        void onClickAddGoal();
    }

}