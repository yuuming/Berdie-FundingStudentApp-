package com.techtator.berdie.homeStudent;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.R;

/**
 * Created by yuminakamura on 2018-04-07.
 */

public class HomeStudentGoalRecyclerViewAdapter extends RecyclerView.Adapter<HomeStudentGoalRecyclerViewAdapter.ViewHolder> {

    private final HomeStudentFragment.OnListFragmentGoalInteractionListener mListener;
    private final HomeStudentPresenter presenter; // 2) create this

    public HomeStudentGoalRecyclerViewAdapter(HomeStudentFragment.OnListFragmentGoalInteractionListener listener, HomeStudentPresenter presenter) {
        mListener = listener;
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_goal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.fbGoal = presenter.getGoalPosition(position); // 3) mValue.get => presenter.get
        holder.goal_title_text.setText(presenter.getGoalPosition(position).getHeader()); // same
        holder.dollar_text.setText("$");
        holder.goal_current_amount.setText(Double.toString(presenter.getGoalPosition(position).getCurrentAmount()));
        holder.of_text.setText("of");
        holder.goal_amount.setText(Double.toString(presenter.getGoalPosition(position).getAmount())); //
        holder.progressBar.setProgress(presenter.getGoalProgress(presenter.getGoalPosition(position)));
        if (position == 0) {
            holder.heart_icon.setImageResource(R.drawable.heart02);
        }

        Log.d("goal item is", String.valueOf((holder.fbGoal).getBody()));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentGoalInteraction(holder.fbGoal);
                    Log.d("CLICKED!","clicked");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return presenter.goalSize();
    } // 4) mValues.size => presenter.size

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView goal_title_text;
        public final ImageView heart_icon;
        public final ProgressBar progressBar;
        public final TextView dollar_text;
        public final TextView goal_current_amount;
        public final TextView of_text;
        public final TextView goal_amount;
        public FBGoal fbGoal; // 0) change dummyitem to model we need

        public ViewHolder(View view) {
            super(view);
            mView = view;
            goal_title_text = (TextView) view.findViewById(R.id.goal_title_text);
            progressBar = (ProgressBar) view.findViewById(R.id.goal_bar);
            dollar_text = (TextView) view.findViewById(R.id.dollar_text);
            goal_current_amount = (TextView) view.findViewById(R.id.goal_current_amount);
            of_text = (TextView) view.findViewById(R.id.of_text);
            goal_amount = (TextView) view.findViewById(R.id.goal_amount);
            heart_icon = (ImageView) view.findViewById(R.id.heart_icon);
        }
//        @Override
//        public String toString() {
//            return super.toString() + " '" + scholarship_body.getText() + "'";
//        }
    }
}
