package com.techtator.berdie.allHistory;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techtator.berdie.Models.FBModel.FBTransactionHistory;
import com.techtator.berdie.allHistory.AllHistoryItemFragment.OnListFragmentInteractionListener;
import com.techtator.berdie.R;

import java.text.SimpleDateFormat;

/**
 * {@link RecyclerView.Adapter} that can display a {@link FBTransactionHistory} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAllHistoryItemRecyclerViewAdapter extends RecyclerView.Adapter<MyAllHistoryItemRecyclerViewAdapter.ViewHolder> {

    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy. hh:mm:ss");

    private final OnListFragmentInteractionListener mListener;
    private final AllHistoryPresenter presenter;

    public MyAllHistoryItemRecyclerViewAdapter(AllHistoryPresenter presenter, OnListFragmentInteractionListener listener) {
        this.presenter = presenter;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_allhistoryitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = presenter.get(position);
        holder.mIdView.setText("");
        if (presenter.get(position).getSender()!=null) {
            holder.mSenderTextView.setText(presenter.get(position).getSender().getFirstName() + " >");
        }
        if (presenter.get(position).getReceiver()!=null) {
            holder.mReceiverTextView.setText(presenter.get(position).getReceiver().getFirstName());
        }

        String formattedStr = sdf.format(presenter.get(position).getTimeStamp());
        holder.mDateView.setText(formattedStr);
        holder.mAmountView.setText(Double.toString(presenter.get(position).getAmount()));
        if (presenter.get(position).getSender().getId().equals("1")) {
            holder.mSenderTextView.setText("Win a Raffle");
            holder.mReceiverTextView.setText("");
        } else if (presenter.get(position).getReceiver().getId().equals("1")) {
            holder.mSenderTextView.setText("Buy a Raffle");
            holder.mReceiverTextView.setText("");
        } else if (presenter.get(position).isUp()) {
            holder.mArrowView.setImageResource(R.drawable.icons8_circled_up_right_96);
        } else {
            holder.mArrowView.setImageResource(R.drawable.icons8_circled_down_right_96);
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return presenter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mSenderTextView;
        public final TextView mReceiverTextView;
        public final TextView mDateView;
        public final TextView mAmountView;
        public final ImageView mArrowView;
        public TransactionHistoryEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id_textview);
            mSenderTextView = (TextView) view.findViewById(R.id.allhistory_sender_textview);
            mReceiverTextView = (TextView) view.findViewById(R.id.allhistory_reciever_textview);
            mDateView = (TextView) view.findViewById(R.id.textView_date);
            mAmountView = (TextView) view.findViewById(R.id.amount_textview);
            mArrowView = (ImageView) view.findViewById(R.id.allhistory_updown_imageview);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mSenderTextView.getText() + "'";
        }
    }
}
