package com.techtator.berdie.homeStudent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techtator.berdie.Models.FBModel.FBScholarship;
import com.techtator.berdie.R;
import com.techtator.berdie.homeStudent.HomeStudentFragment.OnListFragmentInteractionListener;


public class HomeStudentRecyclerViewAdapter extends RecyclerView.Adapter<HomeStudentRecyclerViewAdapter.ViewHolder> {
    // 1) move mvalues to presetner

    private final OnListFragmentInteractionListener mListener;
    private final HomeStudentContract.Presenter presenter; // 2) create this

    public HomeStudentRecyclerViewAdapter(OnListFragmentInteractionListener listener, HomeStudentContract.Presenter presenter) {
        mListener = listener;
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_studentfragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.fbScholarship = presenter.get(position); // 3) mValue.get => presenter.get
        holder.scholarship_title.setText(presenter.get(position).getHeader()); // same
        holder.scholarship_body.setText(presenter.get(position).getBody()); //

        Picasso.get()
                .load(presenter.get(position).getPicture())
                .placeholder(R.drawable.icons8_gender_neutral_user)
                .error(R.drawable.icons8_gender_neutral_user)
                .into(holder.school_image_bg);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.fbScholarship);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return presenter.size();
    } // 4) mValues.size => presenter.size

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView scholarship_title;
        public final TextView scholarship_body;
        public final ImageView school_image_bg;
        public FBScholarship fbScholarship; // 0) change dummyitem to model we need

        public ViewHolder(View view) {
            super(view);
            mView = view;
            scholarship_title = (TextView) view.findViewById(R.id.scholarship_title);
            scholarship_body = (TextView) view.findViewById(R.id.scholarship_second_title);
            school_image_bg = (ImageView) view.findViewById(R.id.school_bg_image);
        }
//        @Override
//        public String toString() {
//            return super.toString() + " '" + scholarship_body.getText() + "'";
//        }
    }
}
