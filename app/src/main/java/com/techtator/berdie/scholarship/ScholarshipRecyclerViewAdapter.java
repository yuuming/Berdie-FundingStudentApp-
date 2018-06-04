package com.techtator.berdie.scholarship;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techtator.berdie.Models.FBModel.FBScholarship;
import com.techtator.berdie.R;

import java.text.SimpleDateFormat;

/**
 * Created by bominkim on 2018-04-10.
 */

public class ScholarshipRecyclerViewAdapter extends RecyclerView.Adapter<ScholarshipRecyclerViewAdapter.ViewHolder>{

    private ScholarshipPresenter presenter;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
    private final ScholarshipFragment.OnScholarshipInteracionListener mListener;

    public ScholarshipRecyclerViewAdapter(ScholarshipPresenter presenter, ScholarshipFragment.OnScholarshipInteracionListener mListener) {
        this.presenter = presenter;
        this.mListener = mListener;
    }

    @Override
    public ScholarshipRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_scholarship_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ScholarshipRecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.mItem = presenter.getScholarshipList(position);


        holder.scholarshipT.setText(presenter.get(position).getHeader());
        holder.amountT.setText(String.valueOf(presenter.get(position).getScholarshipFee()));
        String formatDateStr = dateFormat.format(presenter.get(position).getDueDate());
        holder.dateT.setText(formatDateStr);

        Picasso.get().load(presenter.get(position).getPicture())
                .error(R.drawable.icons8_gender_neutral_user)
                .into(holder.imageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mListener) {
                    mListener.onScholarshipDetail(holder.mItem);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return presenter.scholarshipList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView scholarshipT, amountT, dateT;
        ImageView imageView;
        SearchView searchView;
        FBScholarship mItem;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            searchView = (SearchView)itemView.findViewById(R.id.searchView_scholarship);
            scholarshipT = (TextView)itemView.findViewById(R.id.textView_scholarshipS);
            amountT = (TextView) itemView.findViewById(R.id.textView_amountS);
            dateT = (TextView)itemView.findViewById(R.id.textView_dateS);
            imageView = (ImageView) itemView.findViewById(R.id.imageView_scholarshipS);



        }
    }
}
