package com.techtator.berdie.message;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techtator.berdie.R;
import com.techtator.berdie.inboxStudent.MessageEntity;

import java.text.SimpleDateFormat;

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder> {

    private MessagePresenter presenter;
    static int TYPE_RIGHT = 1;
    static int TYPE_LEFT = 2;
    static int TYPE_MIDDLE = 3;
    protected RecyclerViewReadyCallback recyclerViewReadyCallback;
    SimpleDateFormat sd = new SimpleDateFormat("EEE, MMM d, h:mm");

    public MessageRecyclerViewAdapter(MessagePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==TYPE_RIGHT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_message_item_right, parent, false);
        } else if(viewType==TYPE_LEFT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_message_item_left, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_message_item_middle, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = presenter.getMessageEntity(position);
        holder.text.setText(holder.mItem.getBody());
        String formattedStr = sd.format(holder.mItem.getTimeStamp());
        if(getItemViewType(position) == TYPE_RIGHT) {
            if(holder.mItem.isRead()) {
                formattedStr = "Read " + formattedStr;
            }
            Picasso.get().load(holder.mItem.getSender().getProfilePic())
                    .error(R.drawable.icons8_gender_neutral_user)
                    .into(holder.userPic);
        } else if(getItemViewType(position) == TYPE_LEFT) {
            Picasso.get().load(holder.mItem.getSender().getProfilePic())
                    .error(R.drawable.icons8_gender_neutral_user)
                    .into(holder.userPic);
        }
        holder.date.setText(formattedStr);
        presenter.makeMessageRead(presenter.getMessageEntities());

    }

    @Override
    public int getItemViewType(int position) {
        if (presenter.getMessageEntity(position).getSender()==presenter.me) {
            return TYPE_RIGHT;
        } else if(presenter.getMessageEntity(position).getSender()==presenter.someone) {
            return TYPE_LEFT;
        } else {
            return TYPE_MIDDLE;
        }
    }

    @Override
    public int getItemCount() {
        return presenter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        MessageEntity mItem;
        ImageView userPic;
        TextView text;
        TextView date;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            userPic = view.findViewById(R.id.messageItem_imageviewProfile);
            text = view.findViewById(R.id.messageItem_messageT);
            date = view.findViewById(R.id.sent_date_message);
//            recyclerViewReadyCallback.onLayoutReady();
        }

    }


    public interface RecyclerViewReadyCallback {
        void onLayoutReady();
    }


}
