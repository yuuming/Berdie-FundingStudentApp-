package com.techtator.berdie.home_donor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBGoal;
import com.techtator.berdie.Models.FBModel.FBNews;
import com.techtator.berdie.Models.FBModel.FBProfile;
import com.techtator.berdie.Models.FBModel.FBScholarship;
import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.R;
import com.techtator.berdie.StudentProfile.StudentProfileFragment;
import com.techtator.berdie.about.AboutFragment;
import com.techtator.berdie.accountSetting.AccountSettingFragment;
import com.techtator.berdie.editProfile.EditProfileFragment;
import com.techtator.berdie.findStudent.FindStudentFragment;
import com.techtator.berdie.findStudent.ProfileEntity;
import com.techtator.berdie.help.HelpFragment;
import com.techtator.berdie.homeStudent.HomeStudentFragment;
import com.techtator.berdie.inboxStudent.InboxStudentFragment;
import com.techtator.berdie.message.MessageFragment;
import com.techtator.berdie.model.MessageDataModel;
import com.techtator.berdie.news.NewsFragment;
import com.techtator.berdie.newsDetail.NewsDetailFragment;
import com.techtator.berdie.profile.ProfileFragment;
import com.techtator.berdie.scholarship.ScholarshipFragment;
import com.techtator.berdie.scholarshipDetail.ScholarshipDetailFragment;
import com.techtator.berdie.setting.SettingFragment;
import com.techtator.berdie.studentDonation.StudentDonationFragment;
import com.techtator.berdie.thankYou.ThankYouFragment;
import com.techtator.berdie.topPage.TopPageActivity;

public class HomeDonorActivity extends AppCompatActivity
        implements
        StudentProfileFragment.OnFragmentInteractionListener,
        HomeDonorFragment.OnFragmentInteractionListener,
        FindStudentFragment.OnFindStudentInteractionListener,
        NewsFragment.OnListFragmentInteractionListener,
        SettingFragment.OnSettingInteractionListener,
        ProfileFragment.OnProfileInteractionListener,
        ScholarshipFragment.OnScholarshipInteracionListener,
        InboxStudentFragment.OnInboxMessageFragmentInteractionListener,
        StudentDonationFragment.OnDonationFragmentInteractionListener
        {

            MessageDataModel messageDataModel = new MessageDataModel();

            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_donor);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_donor_container, HomeDonorFragment.newInstance())
                .commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.donor_navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


            @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

                //==================================================
                String userId = UserAuthManager.getInstance().getUserId();
                MenuItem item = menu.findItem(R.id.menu_inbox);
                MenuItemCompat.setActionView(item, R.layout.actionbar_message_badge);
                View view = MenuItemCompat.getActionView(item);
                final TextView countBadge = (TextView)view.findViewById(R.id.message_count);
                countBadge.setClickable(false);
                messageDataModel.setNumberOfUnreadMessageByUserId(userId, new MessageDataModel.OnChangeNumberListener() {
                    @Override
                    public void notifyChangedNumber(int number) {
                        if (number == 0) {
                            countBadge.setVisibility(View.INVISIBLE);
                        } else {
                            countBadge.setText(String.valueOf(number));
                        }
                    }
                });

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_donor_container, InboxStudentFragment.newInstance())
                                .addToBackStack(null)
                                .commit();

                    }
                });

                ///===========================================================
        return true;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_donor_container, HomeDonorFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_student:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_donor_container, FindStudentFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_hat:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_donor_container, ScholarshipFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_bell:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_donor_container, NewsFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_inbox:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_donor_container, InboxStudentFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.menu_settings:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_donor_container, SettingFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.menu_help:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_donor_container, HelpFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.menu_about:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_donor_container, AboutFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.menu_logout:
                Toast.makeText(this, "You've logout this app now", Toast.LENGTH_SHORT).show();
//                Intent data = new Intent(getIntent().getAction());
//                setResult(RESULT_OK, data);
//                finish();
                Intent intent = new Intent(this, TopPageActivity.class);
                startActivity(intent);
                break;
//            case R.id.menu_find_student:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fragment_container, FindStudentFragment.newInstance())
//                        .addToBackStack(null)
//                        .commit();
//                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMessageFragmentInteraction(FBUser user) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_donor_container, MessageFragment.newInstance(user))
                .addToBackStack(null)
                .commit();
    }



    @Override
    public void onDonationFragmentInteraction(FBUser user, FBGoal goal) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_donor_container, StudentDonationFragment.newInstance(user, goal))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFragmentInteraction(FBUser fbUser) {

    }

    @Override
    public void onListFragmentInteraction(ProfileEntity item) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_donor_container, StudentProfileFragment.newInstance(item))
                .addToBackStack(null)
                .commit();
    }

            @Override
            public void onInboxListFragmentInteraction(FBUser user) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_donor_container, MessageFragment.newInstance(user))
                        .addToBackStack(null)
                        .commit();
            }

            //Jump to news detail
            @Override
            public void onListFragmentInteraction(FBNews item) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_donor_container, NewsDetailFragment.newInstance(item))
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onEditInProfileClick() {
                String userId = UserAuthManager.getInstance().getUserId();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_donor_container, EditProfileFragment.newInstance(userId))
                        .addToBackStack(null)
                        .commit();
            }


            @Override
            public void onScholarshipDetail(FBScholarship item) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_donor_container, ScholarshipDetailFragment.newInstance(item))
                        .addToBackStack(null)
                        .commit();
            }
            @Override
            public void onClickAccountSetting() {
                String userId = UserAuthManager.getInstance().getUserId();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_donor_container, AccountSettingFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onViewHistory() {

            }

            @Override
            public void onViewProfile() {
                String userId = UserAuthManager.getInstance().getUserId();

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_donor_container, ProfileFragment.newInstance(userId))
                        .addToBackStack(null)
                        .commit();

            }

            @Override
            public void onThankYouInteraction() {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_donor_container, ThankYouFragment.newInstance())
                        .addToBackStack(null)
                        .commit();

            }
        }
