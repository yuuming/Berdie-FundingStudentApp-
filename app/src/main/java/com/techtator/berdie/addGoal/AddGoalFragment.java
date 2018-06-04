package com.techtator.berdie.addGoal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.R;
import com.techtator.berdie.model.GoalDataModel;

import java.util.Date;

/**
 * Created by user on 2018-04-01.
 */

public class AddGoalFragment extends Fragment implements AddGoalContract.View {

    private AddGoalPresenter presenter = new AddGoalPresenter(this, new GoalDataModel());
    private EditText goalTitleE, goalBodyE;
    private TextView goalAmountNumT;
    private Button addGoalB, cancelB;
    private SeekBar seekBar;

    public static AddGoalFragment newInstance() {
        Bundle args = new Bundle();
        AddGoalFragment fragment = new AddGoalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_goal, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Goal");

        goalTitleE = (EditText) view.findViewById(R.id.goal_titleADD);
        goalBodyE = (EditText) view.findViewById(R.id.editText_goalBodyADD);
        goalAmountNumT = (TextView) view.findViewById(R.id.textView_goalAmountNumADD);
        addGoalB = (Button) view.findViewById(R.id.button_addGoalADD);
        cancelB = (Button) view.findViewById(R.id.button_cancelADD);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar_addGoalADD);
        seekBar.setMax(40000);

        addGoalB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = goalTitleE.getText().toString();
                Double amount = Double.parseDouble(goalAmountNumT.getText().toString());
                String body = goalBodyE.getText().toString();
                Date timeStamp = new Date();

                presenter.addGoal("", UserAuthManager.getInstance().getUserId(), title, body,0, false, true, false, amount, timeStamp );
                Toast.makeText(getActivity(), "Your new goal has added successfully!", Toast.LENGTH_LONG).show();

            }
        });

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progressChangedValue;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                goalAmountNumT.setText(String.valueOf(progressChangedValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(progressChangedValue);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

            return view;
    }


    @Override
    public void onSuccess() {
    }

    @Override
    public void onError() {
    }
}