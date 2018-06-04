package com.techtator.berdie.home_donor;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techtator.berdie.Models.FBModel.FBProfile;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.R;

import com.techtator.berdie.StudentProfile.StudentProfileFragment;
import com.techtator.berdie.findStudent.FindStudentFragment;
import com.techtator.berdie.findStudent.ProfileEntity;
import com.techtator.berdie.homeStudent.HomeStudentPresenter;
import com.techtator.berdie.homeStudent.HomeStudentRecyclerViewAdapter;
import com.techtator.berdie.raffle.RaffleFragment;
import com.techtator.berdie.raffleDonation.RaffleDonationFragment;


public class HomeDonorFragment extends Fragment implements HomeDonorContract.View {


    private HomeDonorPresenter presenter = new HomeDonorPresenter(this, this);
    private OnFragmentInteractionListener mListener;

    private HomeDonorContract.View view;

    private TextView myDonateAmount;
    private Button donateRaffleBTN;
    private RelativeLayout student_tap;
    private HomeDonorRecyclerViewAdapter recyclerViewAdapter;


    public HomeDonorFragment() {
        // Required empty public constructor
    }


    public static HomeDonorFragment newInstance() {
        HomeDonorFragment fragment = new HomeDonorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_donor, container, false);
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewAdapter = new HomeDonorRecyclerViewAdapter(mListener, presenter);

        myDonateAmount = (TextView) view.findViewById(R.id.total_my_donation_amount);
        donateRaffleBTN = (Button) view.findViewById(R.id.donor_home_donate_icon);
        student_tap = (RelativeLayout) view.findViewById(R.id.home_donor_student_tap);

        //donate button
        donateRaffleBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_donor_container, RaffleDonationFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        //find students
        student_tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_donor_container, FindStudentFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        //set recyclerView
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.donor_home_recyclerView);
        recyclerViewAdapter = new HomeDonorRecyclerViewAdapter(mListener, presenter);
        recyclerView.setAdapter(recyclerViewAdapter);


    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }


    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void notifyStudentDataChanged() {
        if (recyclerViewAdapter != null) {
            recyclerViewAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void notifyDonorAmountChanged(String totalDonationAmount) {

    }

    @Override
    public void goToStudentProfile(FBProfile fbProfile, FBUser fbUser) {

        ProfileEntity profileEntity = new ProfileEntity(fbProfile.getId(), fbUser, fbProfile.getMajor(),fbProfile.getUniversity(),fbProfile.getBody());
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_donor_container, StudentProfileFragment.newInstance(profileEntity))
                .addToBackStack(null)
                .commit();

    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(FBUser fbUser);
    }
}
