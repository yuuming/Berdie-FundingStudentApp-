package com.techtator.berdie.home_donor;

import android.arch.lifecycle.Observer;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.Models.FBModel.FBProfile;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.R;
import com.techtator.berdie.findStudent.ProfileEntity;
import com.techtator.berdie.model.FBLiveData;
import com.techtator.berdie.model.ProfileDataModel;
import com.techtator.berdie.model.UserDataModel;

import java.util.List;

public class HomeDonorRecyclerViewAdapter extends RecyclerView.Adapter<HomeDonorRecyclerViewAdapter.ViewHolder> {


//    ProfileDataModel profileDataModel = new ProfileDataModel();
    private final HomeDonorFragment.OnFragmentInteractionListener listener;
//    private final HomeDonorPresenter presenter;
    private final HomeDonorPresenter presenter;

    public HomeDonorRecyclerViewAdapter(HomeDonorFragment.OnFragmentInteractionListener listener, HomeDonorPresenter presenter) {
        this.listener = listener;
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_donor_home_item, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.fbUser = presenter.get(position);

            Picasso.get()
                .load(presenter.get(position).getProfilePic())
                .placeholder(R.drawable.icons8_gender_neutral_user)
                .error(R.drawable.icons8_gender_neutral_user)
                .into(holder.studentImage);

            Log.d("===%%%",presenter.get(position).getFirstName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (null != listener) {
//                    listener.onFragmentInteraction(holder.fbUser);
//                }
                presenter.goToStudentProfile(holder.fbUser);
            }
        });
    }

    @Override
    public int getItemCount() {
        return presenter.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public View mView;
        public ImageView studentImage;
        public FBUser fbUser;
//        public ProfileEntity profileEntity;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            studentImage = (ImageView) view.findViewById(R.id.home_donor_student_image);
        }
    }
}
