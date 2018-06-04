package com.techtator.berdie.topPage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.techtator.berdie.BuildConfig;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.R;
import com.techtator.berdie.homeStudent.HomeStudentActivity;
import com.techtator.berdie.home_donor.HomeDonorActivity;
import com.techtator.berdie.home_donor.HomeDonorFragment;
import com.techtator.berdie.model.UserDataModel;
import com.techtator.berdie.signup.SignUpActivity;

import java.util.Arrays;

public class TopPageActivity extends AppCompatActivity implements TopPageContract.View{
    private static final int RC_SIGN_IN = 9001;
    private static final int RC_FILL_IN = 9002;
    private static final int RC_HOMESTUDENT_IN = 9003;
    private static final int RC_HOMEDONOR_IN = 9004;


    private TopPagePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new TopPagePresenter(this, new UserDataModel());

        startSignIn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) { // from AuthUI
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                mPresenter.onLogin(user.getUid());

            } else {
                // Sign in failed, check response for error code
                // ...
            }
        } else if (requestCode == RC_FILL_IN) {// from Signup Activity
            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                mPresenter.onLoginWithoutView(user.getUid());
                if (data.getStringExtra("USER_ROLE").equals("1")) {
                    Intent intent = new Intent(this, HomeStudentActivity.class);
                    startActivityForResult(intent, RC_HOMESTUDENT_IN);
                } else {
                    // donor !!!
                    Intent intent = new Intent(this, HomeDonorActivity.class);
                    startActivityForResult(intent, RC_HOMEDONOR_IN);
                }
            }
        } else if (requestCode == RC_HOMESTUDENT_IN) { // from HomeStudentActivity
            if (resultCode == RESULT_OK) {
                startSignIn();
            }
        }
    }

    private void startSignIn() {
        // Sign in with FirebaseUI

        Intent intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.PhoneBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build()
//                        new AuthUI.IdpConfig.FacebookBuilder().build(),
//                        new AuthUI.IdpConfig.TwitterBuilder().build())
                ))
                .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                .setLogo(R.drawable.logo)
                .setTheme(R.style.SignupCustomTheme)
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
    }


    @Override
    public void onNoExistanceUser(String uid) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("USER_ID", uid);
        startActivityForResult(intent, RC_FILL_IN);
    }

    @Override
    public void onExistingUser(FBUser data) {
        if (data.getUserRole().equals("1")) {
            Intent intent = new Intent(this, HomeStudentActivity.class);
            startActivityForResult(intent, RC_HOMESTUDENT_IN);
        } else {
            // donor !!!
            Intent intent = new Intent(this, HomeDonorActivity.class);
            startActivityForResult(intent, RC_HOMEDONOR_IN);
        }
    }

    @Override
    public void onError() {

    }
}
