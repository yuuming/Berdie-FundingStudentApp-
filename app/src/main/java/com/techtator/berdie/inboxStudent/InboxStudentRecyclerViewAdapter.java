package com.techtator.berdie.inboxStudent;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.R;

public class InboxStudentRecyclerViewAdapter extends RecyclerView.Adapter<InboxStudentRecyclerViewAdapter.ViewHolder> {

    private final InboxStudentFragment.OnInboxMessageFragmentInteractionListener mListener;
    private final InboxStudentPresenter presenter;

    public InboxStudentRecyclerViewAdapter(InboxStudentPresenter presenter, InboxStudentFragment.OnInboxMessageFragmentInteractionListener listener) {
        mListener = listener;
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_inbox_student_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = presenter.getMessageEntities().get(position);
        final FBUser someone;
        if(holder.mItem.getReceiver().getId().equals(UserAuthManager.getInstance().getUserId())) {
            someone = holder.mItem.getSender();
        } else {
            someone = holder.mItem.getReceiver();
        }
        holder.name.setText(someone.getFirstName());
        Picasso.get()
                .load(someone.getProfilePic())
                .placeholder(R.drawable.icons8_gender_neutral_user)
                .error(R.drawable.icons8_gender_neutral_user)
                .into(holder.photo);
        holder.message.setText(holder.mItem.getBody());
        if(holder.mItem.getSender()==someone && holder.mItem.isRead()==false) {
            holder.message.setTypeface(null, Typeface.BOLD);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onInboxListFragmentInteraction(someone);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return presenter.messageEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final TextView message;
        public final ImageView photo;
        public final ImageView button;
        public MessageEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            this.name = view.findViewById(R.id.receiver_name_inbox);
            this.message = view.findViewById(R.id.message_body_inbox);
            this.photo = view.findViewById(R.id.photo_inbox);
            this.button = view.findViewById(R.id.to_detail_button_inbox);
        }
    }
}
