package com.techtator.berdie.signup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.Toast;

import com.techtator.berdie.R;
import com.techtator.berdie.model.UserDataModel;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity implements SignUpContract.View {

    private SignUpContract.Presenter mPresenter;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mPresenter = new SignUpPresenter(this);

        final String userId = getIntent().getStringExtra("USER_ID");

        // initiate the date picker and a button
        final EditText dateView = (EditText) findViewById(R.id.signup_birthday);
        // perform click event on edit text
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(SignUpActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dateView.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
            //TODO int to String for save the data to firebase!
        });
        final EditText firstnameView = (EditText) findViewById(R.id.signup_firstname);
        final EditText lasttnameView = (EditText) findViewById(R.id.signup_lastname);
        final EditText emailView = (EditText) findViewById(R.id.signup_email_edit);
        final EditText phoneView = (EditText) findViewById(R.id.signup_phone);
        final Spinner typeSpinner = (Spinner) findViewById(R.id.signup_spinner);

        Button button = (Button) findViewById(R.id.signup_signup_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = firstnameView.getText().toString();
                String lastname = lasttnameView.getText().toString();
                String date = dateView.getText().toString();
                String email = emailView.getText().toString();
                String phone = phoneView.getText().toString();
                String selected = typeSpinner.getSelectedItem().toString();
                Long role = selected.contains("donor") ? 0L : 1L;
                if (mPresenter.createUser(userId, role, firstname, lastname, date, email, phone) ) {
                    Intent data = new Intent(getIntent().getAction());
                    data.putExtra("USER_ROLE", String.valueOf(role));
                    setResult(Activity.RESULT_OK, data);
                    finish();
                } else {

                }
            }
        });
    }

    @Override
    public void onSuccess() {
        Toast.makeText(SignUpActivity.this, "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError() {
        Toast.makeText(SignUpActivity.this, "invalid!!!", Toast.LENGTH_SHORT).show();
    }
}
