package com.techtator.berdie.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.Models.FBModel.FBProfile;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.R;
import com.techtator.berdie.goal.GoalPresenter;

/**
 * Created by bominkim on 2018-04-03.
 */

public class ProfileFragment extends Fragment implements ProfileContract.View{

    private ProfilePresenter presenter;
    private GoalPresenter goalPresenter;

    private ImageView profile_pic;
    private Button editProfileBTN;
    private TextView username;
    private TextView user_profile_text;
    private TextView university_text;
    private TextView major_text;
    private ProgressBar goal_bar;
    private TextView goal_title_text;
    private TextView goal_current_amount;
    private TextView goal_amount;
    private OnProfileInteractionListener mListener;
    private ImageView primary_heart;

    public ProfileFragment(){}

    public static ProfileFragment newInstance(String userId) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("USER_ID", userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");

        String userId = getArguments().getString("USER_ID");
        presenter  = new ProfilePresenter(this, userId, this);

        username = (TextView) view.findViewById(R.id.user_name);
        editProfileBTN = (Button) view.findViewById(R.id.profile_edit_button);
        user_profile_text = (TextView) view.findViewById(R.id.user_profile_text);
        university_text = (TextView) view.findViewById(R.id.university_text);
        major_text = (TextView) view.findViewById(R.id.major_text);
        goal_bar = (ProgressBar) view.findViewById(R.id.goal_bar);
        goal_title_text = (TextView) view.findViewById(R.id.goal_title_text);
        goal_current_amount = (TextView) view.findViewById(R.id.goal_current_amount);
        goal_amount = (TextView) view.findViewById(R.id.goal_amount);
        profile_pic = (ImageView) view.findViewById(R.id.pg_pic);
        primary_heart = (ImageView) view.findViewById(R.id.heart_icon);

        // editButton.setVisibility(View.INVISIBLE);
        editProfileBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onEditInProfileClick();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProfileInteractionListener ) {
            mListener = (OnProfileInteractionListener ) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }


    @Override
    public void notifyProfileChanged(FBProfile profile) {
        user_profile_text.setText(profile.getBody());
        university_text.setText(profile.getUniversity());
        major_text.setText(profile.getMajor());

    }

    @Override
    public void notifyUserChanged(FBUser user) {
        username.setText(user.getFirstName() + " " + user.getLastName());
        Picasso.get()
                .load(user.getProfilePic())
                .placeholder(R.drawable.icons8_gender_neutral_user)
                .error(R.drawable.icons8_gender_neutral_user)
                .into(profile_pic);
    }

    @Override
    public void notifyGoalPrimaryChanged(FBGoal goal) {
        goal_title_text.setText(goal.getHeader());
        goal_current_amount.setText(Double.toString(goal.getCurrentAmount()));
        goal_amount.setText(Double.toString(goal.getAmount()));
        goal_bar.setProgress(presenter.getGoalProgress(goal));
        primary_heart.setImageResource(R.drawable.heart02);

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }

    public interface OnProfileInteractionListener {
        void onEditInProfileClick();
    }

}
