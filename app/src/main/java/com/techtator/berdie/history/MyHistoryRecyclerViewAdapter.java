package com.techtator.berdie.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techtator.berdie.R;
import com.techtator.berdie.allHistory.TransactionHistoryEntity;

import java.text.SimpleDateFormat;


public class MyHistoryRecyclerViewAdapter extends RecyclerView.Adapter<MyHistoryRecyclerViewAdapter.ViewHolder> {

    private final String SYSTEM_SENDER_ID = "1";
    private final HistoryFragment.OnGoalListFragmentInteractionListener mListener;
    private final HistoryPresenter presenter;
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy");

    public MyHistoryRecyclerViewAdapter(HistoryPresenter presenter, HistoryFragment.OnGoalListFragmentInteractionListener listener) {
        mListener = listener;
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_history_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = presenter.get(position);
        String formattedStr = sdf.format(holder.mItem.getTimeStamp());
        if(holder.mItem.getSender().getId().equals(SYSTEM_SENDER_ID)) {
            holder.head.setText("Won the");
            holder.sender.setText("Raffle");
        } else {
            holder.head.setText("Donation from");
            holder.sender.setText(holder.mItem.getSender().getFirstName());
        }
        holder.date.setText(formattedStr);
        holder.amount.setText("$ " + Double.toString(holder.mItem.getAmount()));


//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onGoalListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return presenter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TransactionHistoryEntity mItem;
        public final TextView head, sender, date, amount;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            head = view.findViewById(R.id.text_head_description);
            sender = view.findViewById(R.id.sender_goal);
            date = view.findViewById(R.id.date_goal_history);
            amount = view.findViewById(R.id.amount_goal_history);
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mSenderTextView.getText() + "'";
//        }
    }
}
