package com.techtator.berdie.goal;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.R;

/**
 * Created by bominkim on 2018-04-05.
 */

public class GoalRecyclerViewAdapter extends RecyclerView.Adapter<GoalRecyclerViewAdapter.ViewHolder> {

    public final static int ONGOING_LIST_VIEW = 1;
    public final static int ACCOMPLISHED_LIST_VIEW = 2;

    private final GoalFragment.OnGoalInteractionListener mListener;
    private final GoalPresenter presenter;
    private final int listType;


    public GoalRecyclerViewAdapter(GoalPresenter presenter, GoalFragment.OnGoalInteractionListener listener, int listType) {
        this.presenter = presenter;
        mListener = listener;
        this.listType = listType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_goal_item, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(listType == ONGOING_LIST_VIEW) {
            holder.mItem = presenter.getGoalFromOngoingList(position);
        } else {
            holder.mItem = presenter.getGoalFromAccomplishedList(position);
        }

        holder.goalText.setText(holder.mItem.getHeader());
        holder.currentAmount.setText(Integer.toString((int)holder.mItem.getCurrentAmount()));
        holder.goalAmount.setText(Integer.toString((int)holder.mItem.getAmount()));
        holder.progressBar.setProgress(presenter.getGoalProgress(holder.mItem));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onGoalHistory(holder.mItem);
                }

            }
        });

        switch (listType) {
            case ONGOING_LIST_VIEW: {
                if (position == 0) {
                    holder.primary.setImageResource(R.drawable.heart02);
                } else {
                    holder.primary.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            presenter.changePrimaryGoal(presenter.onGoingGoals, position);
                        }
                    });
                }
                break;
            }
            case ACCOMPLISHED_LIST_VIEW: {
                holder.primary.setVisibility(View.INVISIBLE);
                holder.goalCard.setCardBackgroundColor(Color.parseColor("#9f9f9f"));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if(listType == ONGOING_LIST_VIEW) {
            return presenter.onGoingGoals.size();
        } else {
            return presenter.accomplishedGoals.size();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView goalText;
        public final ImageView primary;
        public final ProgressBar progressBar;
        public final TextView currentAmount;
        public final TextView goalAmount;
        public FBGoal mItem;
        public final View ongoingListView;
        public final View accomplishedListView;
        public final CardView goalCard;
        public final int layoutType;

        public ViewHolder(View view, int layoutType) {
            super(view);
            mView = view;
            goalText = (TextView) view.findViewById(R.id.goal_title_text);
            primary = (ImageView) view.findViewById(R.id.heart_icon);
            progressBar = (ProgressBar) view.findViewById(R.id.goal_bar);
            currentAmount = (TextView) view.findViewById(R.id.goal_current_amount);
            goalAmount = (TextView) view.findViewById(R.id.goal_amount);
            ongoingListView = view.findViewById(R.id.goal_list_ongoing);
            accomplishedListView = view.findViewById(R.id.goal_list_accomplished);
            goalCard = (CardView) view.findViewById(R.id.goal_card_view);
            this.layoutType = layoutType;
        }
    }

}
