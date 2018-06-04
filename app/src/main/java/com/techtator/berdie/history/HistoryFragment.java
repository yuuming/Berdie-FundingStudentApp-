package com.techtator.berdie.history;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.R;
import com.techtator.berdie.allHistory.TransactionHistoryEntity;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnGoalListFragmentInteractionListener}
 * interface.
 */
public class HistoryFragment extends Fragment implements HistoryContract.View {

    private int mColumnCount = 1;
    private OnGoalListFragmentInteractionListener mListener;
    private MyHistoryRecyclerViewAdapter mAdapter;
    private HistoryPresenter presenter;
    private static final String GOAL_OBJECT = "goal-object";
    private FBGoal goal;
    private TextView title, description, currentAmount, goalAmount, numberOfDonation;
    private ProgressBar progressBar;
    private ImageView primary;
    private RelativeLayout editButton;
    private RecyclerView historyList;

    public HistoryFragment() {
    }


    public static HistoryFragment newInstance(FBGoal goal) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(GOAL_OBJECT, goal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            goal = (FBGoal)  getArguments().getSerializable(GOAL_OBJECT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("History");

        // Set the adapter
        if (UserAuthManager.getInstance().getUserId() == null) return view;

        presenter = new HistoryPresenter(this, UserAuthManager.getInstance().getUserId(), goal.getId(), this);

        Context context = view.getContext();
        historyList = (RecyclerView) view.findViewById(R.id.goal_history_list);
        if (mColumnCount <= 1) {
            historyList.setLayoutManager(new LinearLayoutManager(context));
        } else {
            historyList.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        historyList.addItemDecoration(itemDecoration);
        mAdapter = new MyHistoryRecyclerViewAdapter(presenter, mListener);
        historyList.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = (TextView) view.findViewById(R.id.goal_title_goal_history);
        description = (TextView) view.findViewById(R.id.goal_description_goal_history);
        currentAmount = (TextView) view.findViewById(R.id.goal_current_amount_goal_history);
        goalAmount = (TextView) view.findViewById(R.id.goal_amount_goal_history);
        numberOfDonation = (TextView) view.findViewById(R.id.number_of_donations);
        progressBar = (ProgressBar) view.findViewById(R.id.goal_bar_history);
        primary = (ImageView) view.findViewById(R.id.heart_icon_goal_history);
        editButton = (RelativeLayout) view.findViewById(R.id.edit_button_goal_history);
        if(goal.isAccomplished()==true) {
            editButton.setVisibility(View.GONE);
        } else {
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onGoalListFragmentInteraction(goal);
                }
            });
        }

        title.setText(goal.getHeader());
        description.setText(goal.getBody());
        currentAmount.setText(Integer.toString((int)goal.getCurrentAmount()));
        goalAmount.setText(Integer.toString((int)goal.getAmount()));
        progressBar.setProgress(presenter.getGoalProgress(goal));
        if(goal.isPrimary()==true) {
            primary.setImageResource(R.drawable.heart02);
        }

    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGoalListFragmentInteractionListener) {
            mListener = (OnGoalListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void notifyHistoriesChanged(List<TransactionHistoryEntity> list) {
        int number = list.size();
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getSender().getId().equals("1")) {
                number = number - 1;
            }
        }
        if(number==0) {
            numberOfDonation.setVisibility(View.GONE);
            historyList.setVisibility(View.GONE);
        } else if(number==1) {
            numberOfDonation.setText("1 donation");
        } else {
            numberOfDonation.setText(Integer.toString(number) + " donations");
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyGoalChanged(FBGoal fbGoal) {
        title.setText(fbGoal.getHeader());
        description.setText(fbGoal.getBody());
        currentAmount.setText(Integer.toString((int)fbGoal.getCurrentAmount()));
        goalAmount.setText(Integer.toString((int)fbGoal.getAmount()));
        progressBar.setProgress(presenter.getGoalProgress(fbGoal));
        if(fbGoal.isPrimary()==true) {
            primary.setImageResource(R.drawable.heart02);
        }
    }


    public interface OnGoalListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onGoalListFragmentInteraction(FBGoal goal);
    }
}
