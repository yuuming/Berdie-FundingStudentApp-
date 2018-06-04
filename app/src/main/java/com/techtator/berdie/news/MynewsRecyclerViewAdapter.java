package com.techtator.berdie.news;

import android.content.ClipData;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.techtator.berdie.Models.FBModel.FBNews;
import com.techtator.berdie.R;
import com.techtator.berdie.news.NewsFragment.OnListFragmentInteractionListener;

import java.text.SimpleDateFormat;

public class MynewsRecyclerViewAdapter extends RecyclerView.Adapter<MynewsRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    SimpleDateFormat sd = new SimpleDateFormat("MMM dd, yyyy"); //Change the format of TimeStampData
    private final OnListFragmentInteractionListener mListener;
    private final NewsPresenter presenter;
    private RecyclerView mRecycler;

    public MynewsRecyclerViewAdapter(OnListFragmentInteractionListener listener, NewsPresenter presenter) {
        this.presenter = presenter;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.fbNews = presenter.get(position);
        holder.mTitleView.setText(presenter.get(position).getTitle());
        String formattedStr = sd.format(presenter.get(position).getTimeStamp()); //get TimeStamp
        holder.mDateView.setText(formattedStr);//Set Date to textView
        if (holder.fbNews.getNewsType()==0) {
            holder.imageView.setImageResource(R.drawable.icons8_bell);
        } else if (holder.fbNews.getNewsType()==1){
            holder.imageView.setImageResource(R.drawable.graduationcap);
        }else if (holder.fbNews.getNewsType()==2){
            holder.imageView.setImageResource(R.drawable.icon_winner);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.fbNews);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return presenter.size();
    }

    @Override
    public void onClick(View v) { }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageView;
        public final TextView mTitleView;
        public final TextView mDateView;
        public FBNews fbNews;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageView = (ImageView) view.findViewById(R.id.news_icon);
            mTitleView = (TextView) view.findViewById(R.id.news_title);
            mDateView = (TextView) view.findViewById(R.id.news_date);

        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mIdView.getText() + "'";
//        }
    }
}
