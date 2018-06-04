package com.techtator.berdie.StudentProfile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.Models.FBModel.FBProfile;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.R;
import com.techtator.berdie.findStudent.ProfileEntity;
import com.techtator.berdie.model.UserDataModel;

public class StudentProfileFragment extends Fragment implements StudentProfileContract.View{

    private StudentProfilePresenter presenter;
    private OnFragmentInteractionListener mListener;
    private static final String PROFILE_ENTITY_OBJECT = "profile-entity-object";
    private ProfileEntity profileEntity;
    private ImageView profile_pic;
    private LinearLayout messageButton;
    private Button donationButton;
    private TextView username;
    private TextView user_profile_text;
    private TextView university_text;
    private TextView major_text;
    private ProgressBar goal_bar;
    private TextView goal_title_text;
    private TextView goal_current_amount;
    private TextView goal_amount;
    private ImageView primary_heart;
    private UserDataModel userDataModel;


    public StudentProfileFragment() {}

    public static StudentProfileFragment newInstance(ProfileEntity profileEntity) {
        StudentProfileFragment fragment = new StudentProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(PROFILE_ENTITY_OBJECT, profileEntity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            profileEntity = (ProfileEntity)  getArguments().getSerializable(PROFILE_ENTITY_OBJECT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Student Profile");

        presenter  = new StudentProfilePresenter(this, profileEntity, this);

        userDataModel = new UserDataModel();
        userDataModel.refleshUserMap(this);

        username = (TextView) view.findViewById(R.id.student_profile_name_donor);
        messageButton = (LinearLayout) view.findViewById(R.id.profile_message_button_donor);
        donationButton = (Button) view.findViewById(R.id.profile_donation_button);
        user_profile_text = (TextView) view.findViewById(R.id.student_profile_text_donor);
        university_text = (TextView) view.findViewById(R.id.student_university_donor);
        major_text = (TextView) view.findViewById(R.id.student_major_donor);
        goal_bar = (ProgressBar) view.findViewById(R.id.goal_bar);
        goal_title_text = (TextView) view.findViewById(R.id.goal_title_text);
        goal_current_amount = (TextView) view.findViewById(R.id.goal_current_amount);
        goal_amount = (TextView) view.findViewById(R.id.goal_amount);
        profile_pic = (ImageView) view.findViewById(R.id.student_profile_pic_donor);
        primary_heart = (ImageView) view.findViewById(R.id.heart_icon);

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMessageFragmentInteraction(profileEntity.getUser());
            }
        });

        donationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDonationFragmentInteraction(profileEntity.getUser(), presenter.goal);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void notifyProfileChanged(FBProfile profile) {
        user_profile_text.setText(profile.getBody());
        university_text.setText(profile.getUniversity());
        major_text.setText(profile.getMajor());

    }

    public void notifyUserChanged(FBUser user) {
        username.setText(user.getFirstName() + " " + user.getLastName());
        Picasso.get()
                .load(user.getProfilePic())
                .placeholder(R.drawable.icons8_gender_neutral_user)
                .error(R.drawable.icons8_gender_neutral_user)
                .into(profile_pic);
    }

    public void notifyGoalPrimaryChanged(FBGoal goal) {
        goal_title_text.setText(goal.getHeader());
        goal_current_amount.setText(Double.toString(goal.getCurrentAmount()));
        goal_amount.setText(Double.toString(goal.getAmount()));
        goal_bar.setProgress(presenter.getGoalProgress(goal));
        primary_heart.setImageResource(R.drawable.heart02);

    }

    public interface OnFragmentInteractionListener {
        void onMessageFragmentInteraction(FBUser user);
        void onDonationFragmentInteraction(FBUser user, FBGoal goal);
    }
}
