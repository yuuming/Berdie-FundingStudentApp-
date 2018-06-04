package com.techtator.berdie.setting;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.R;


public class SettingFragment extends Fragment implements SettingContract.View {

    private SettingContract.Presenter presenter;
    private TextView name ;
    private ImageView studentPicture;
    private Button accountSetting;
    private Button viewHistory;
    private OnSettingInteractionListener mListener;


    public SettingFragment() {}


    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new SettingPresenter(this, UserAuthManager.getInstance().getUserId(), this);

        View view =inflater.inflate(R.layout.fragment_setting, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Setting");

        name = (TextView) view.findViewById(R.id.student_name);
        studentPicture = (ImageView) view.findViewById(R.id.student_photo);
        accountSetting = (Button) view.findViewById(R.id.account_setting_button);
        viewHistory = (Button) view.findViewById(R.id.view_history_button);

        accountSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickAccountSetting();
            }
        });

        viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onViewHistory();
            }
        });
        studentPicture.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mListener.onViewProfile();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSettingInteractionListener) {
            mListener = (OnSettingInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }


    @Override
    public void notifyUserChanged(FBUser user) {
        name.setText(user.getFirstName() + " " + user.getLastName());
        Picasso.get()
                .load(user.getProfilePic())
                .placeholder(R.drawable.icons8_gender_neutral_user)
                .error(R.drawable.icons8_gender_neutral_user)
                .into(studentPicture);
    }

    public interface OnSettingInteractionListener {
        void onClickAccountSetting();
        void onViewHistory();
        void onViewProfile();
    }

}
