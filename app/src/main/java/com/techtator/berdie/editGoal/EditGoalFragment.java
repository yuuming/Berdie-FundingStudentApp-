package com.techtator.berdie.editGoal;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.R;
import com.techtator.berdie.model.GoalDataModel;

public class EditGoalFragment extends Fragment implements EditGoalContract.View {

//    private EditGoalPresenter presenter = new EditGoalPresenter(this, new GoalDataModel());
    private EditGoalPresenter presenter;
    private EditText goalTitleE, goalBodyE;
    private TextView goalAmountNumT;
    private Button editGoalB, cancelB;
    private SeekBar seekBar;
    private static final String GOAL_OBJECT = "goal-object";
    private FBGoal goal;
    private String id;

    public static EditGoalFragment newInstance(FBGoal goal) {
        Bundle args = new Bundle();
        EditGoalFragment fragment = new EditGoalFragment();
        args.putSerializable(GOAL_OBJECT, goal);
        args.putString("goal_id", goal.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            goal = (FBGoal)  getArguments().getSerializable(GOAL_OBJECT);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_goal, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit Goal");
        id = getArguments().getString("goal_id");
        presenter = new EditGoalPresenter(this, id);

        goalTitleE = (EditText) view.findViewById(R.id.goal_title_edit);
        goalBodyE = (EditText) view.findViewById(R.id.editText_goalBody);
        goalAmountNumT = (TextView) view.findViewById(R.id.textView_goalAmountNum);
        editGoalB = (Button) view.findViewById(R.id.button_editGoal);
        cancelB = (Button)view.findViewById(R.id.button_cancel);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar_editGoal);
        seekBar.setMax(30000);
        goalTitleE.setText(goal.getHeader());
        goalAmountNumT.setText(String.valueOf(goal.getAmount())); /**/
        seekBar.setProgress(presenter.getGoalProgress(goal));
        goalBodyE.setText(goal.getBody());
        editGoalB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = goalTitleE.getText().toString();
                String body = goalBodyE.getText().toString();
                Double amount = Double.parseDouble(goalAmountNumT.getText().toString());
                String userId = UserAuthManager.getInstance().getUserId();
                presenter.updateGoal(id, userId, title, body, goal.getCurrentAmount(), goal.isAccomplished(), goal.isActive(), goal.isPrimary(), amount, goal.getTimeStamp());
                Toast.makeText(getActivity(), "Your goal has edited successfully!", Toast.LENGTH_LONG).show();
            }
        });

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getFragmentManager().popBackStack();
            }
        });


        return view;
    }

    public EditGoalFragment() {
        // Required empty public constructor
    }

    @Override
    public void setTitle(String header) {
        goalTitleE.setText(header);
    }

    @Override
    public void setAmount(final double amount) {
        goalAmountNumT.setText(String.valueOf(amount)); /**/

        seekBar.setProgress((int)amount);

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
    }

    @Override
    public void setBody(String body) {
        goalBodyE.setText(body);
    }
}
