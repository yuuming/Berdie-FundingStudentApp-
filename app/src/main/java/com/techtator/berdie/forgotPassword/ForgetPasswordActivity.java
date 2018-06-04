package com.techtator.berdie.forgotPassword;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.techtator.berdie.Database.DataModel;
import com.techtator.berdie.R;
import com.techtator.berdie.model.UserDataModel;

import org.w3c.dom.Text;

public class ForgetPasswordActivity extends AppCompatActivity implements ForgotPasswordContract.View {

    private EditText editEmail;
    private Button btnReset;
    private FirebaseAuth mAuth;

    private  ForgotPasswordContract.Presenter presenter = new ForgotPasswordPresenter(this, new UserDataModel());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editEmail = (EditText) findViewById(R.id.forget_email_edit);
        btnReset = (Button)findViewById(R.id.reset_button);

        mAuth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Enter your email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgetPasswordActivity.this,"Check email to reset your password!", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(ForgetPasswordActivity.this, "Fail to send reset password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }
}
