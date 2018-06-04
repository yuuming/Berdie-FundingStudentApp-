package com.techtator.berdie.findStudent;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techtator.berdie.R;

public class FindStudentRecyclerViewAdapter extends RecyclerView.Adapter<FindStudentRecyclerViewAdapter.ViewHolder> {

    private final FindStudentFragment.OnFindStudentInteractionListener mListener;
    private final FindStudentPresenter presenter;


    public FindStudentRecyclerViewAdapter(FindStudentPresenter presenter, FindStudentFragment.OnFindStudentInteractionListener listener) {
        mListener = listener;
        this.presenter = presenter;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_findstudent_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = presenter.getProfile(position);
        if (holder.mItem.getUser() == null) {
            Log.d("FindStudnet", "invalid profile data exists!!! should be removed!! " + holder.mItem.toString());
            return;
        }
        holder.name.setText(holder.mItem.getUser().getFirstName() + " " + holder.mItem.getUser().getLastName());
        holder.university.setText(holder.mItem.getUniversity());
        Picasso.get()
                .load(holder.mItem.getUser().getProfilePic())
                .placeholder(R.drawable.icons8_gender_neutral_user)
                .error(R.drawable.icons8_gender_neutral_user)
                .into(holder.photo);

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
    public long getItemId(int position) {
        return presenter.getProfile(position).getId().hashCode();
    }

    @Override
    public int getItemCount() {
        return presenter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final TextView university;
        public final ImageView photo;
        public ProfileEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = (TextView) view.findViewById(R.id.student_name_search);
            university = (TextView) view.findViewById(R.id.student_university_search);
            photo = (ImageView) view.findViewById(R.id.photo_find_student);
        }
    }

}
