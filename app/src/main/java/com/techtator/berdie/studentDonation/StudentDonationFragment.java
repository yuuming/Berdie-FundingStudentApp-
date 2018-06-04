package com.techtator.berdie.studentDonation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.R;

public class StudentDonationFragment extends Fragment implements StudentDonationContract.View {

    private StudentDonationPresenter presenter;
    private StudentDonationFragment.OnDonationFragmentInteractionListener mListener;
    private TextView name;
    private Button donateButton;
    private Button cancelButton;
    private EditText amount;
    private static final String STUDENT = "student";
    private static final String GOAL_OBJECT = "goal";
    private FBUser student;
    private FBGoal goal;
    int donationAmount;

    public StudentDonationFragment() {
    }

    public static StudentDonationFragment newInstance(FBUser user, FBGoal goal) {
        Bundle args = new Bundle();
        StudentDonationFragment fragment = new StudentDonationFragment();
        args.putSerializable(STUDENT, user);
        args.putSerializable(GOAL_OBJECT, goal);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            student = (FBUser)  getArguments().getSerializable(STUDENT);
            goal = (FBGoal)  getArguments().getSerializable(GOAL_OBJECT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_student_donation, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Donation");

        name = (TextView) view.findViewById(R.id.name_donation_receiver);
        donateButton = (Button) view.findViewById(R.id.student_donate_button);
        cancelButton = (Button) view.findViewById(R.id.cancel_button_dontation_student);
        amount = (EditText) view.findViewById(R.id.amount_donation_to_student);
        name.setText(student.getFirstName() + " " + student.getLastName());
        donateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(amount.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please input the number", Toast.LENGTH_LONG).show();
                } else {
                        donationAmount = Integer.parseInt(amount.getText().toString());
                        FBUser donor = UserAuthManager.getInstance().getUser();
//                        if(presenter.wallet.getCurrentBalance() == 0){
//                            Toast.makeText(getContext(), "Please charge money from account setting." + presenter.wallet.getCurrentBalance(), Toast.LENGTH_LONG).show();
//                        } else if(presenter.wallet.getCurrentBalance() < Integer.parseInt(amount.getText().toString())){
//                            Toast.makeText(getContext(), "The amount you can donate is $" + presenter.wallet.getCurrentBalance(), Toast.LENGTH_LONG).show();
//                        } else {
                            presenter.donate(donationAmount, student, UserAuthManager.getInstance().getUser());
                            mListener.onThankYouInteraction();
//                        }
                }

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                manager.popBackStack();
            }
        });

        if (presenter==null)
            presenter = new StudentDonationPresenter(donationAmount, student, UserAuthManager.getInstance().getUser(), this, this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StudentDonationFragment.OnDonationFragmentInteractionListener) {
            mListener = (StudentDonationFragment.OnDonationFragmentInteractionListener) context;
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

    public interface OnDonationFragmentInteractionListener {
        void onThankYouInteraction();
    }
}
