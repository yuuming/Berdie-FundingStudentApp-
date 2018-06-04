package com.techtator.berdie.accountSetting;

import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.techtator.berdie.R;
import com.techtator.berdie.model.AccountSettingsDataModel;


public class AccountSettingFragment extends Fragment implements AccountSettingContract.View {

    private TextView firstName;
    private TextView lastName;
    private TextView eMail;
    private TextView phone;
    private TextView address;
    private TextView city;
    private TextView zipCode;
    private TextView state;
    private Button btnSaveSettings;
    //WALLET
    private EditText balanceToBeAdded;
    private TextView walletBalance;
    private Button btnAddBalance;

    public static AccountSettingFragment newInstance() {
        AccountSettingFragment fragment = new AccountSettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_settings, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Account Setting");
        return view;

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firstName = view.findViewById(R.id.settings_first_name);
        lastName = view.findViewById(R.id.settings_last_name);
        eMail = view.findViewById(R.id.settings_email);
        phone = view.findViewById(R.id.settings_phone);
        address = view.findViewById(R.id.settings_address);
        city = view.findViewById(R.id.settings_city);
        zipCode = view.findViewById(R.id.settings_zip_code);
        state = view.findViewById(R.id.settings_state);

        final AccountSettingPresenter presenter = new AccountSettingPresenter(this);

        //updates userProfile
        btnSaveSettings = view.findViewById(R.id.settings_btn_save);
        //textbox for adding funds
        balanceToBeAdded = view.findViewById(R.id.settings_add_wallet_balance);
        //current balance
        walletBalance = view.findViewById(R.id.settings_wallet_balance);
        //adds the entered balance
        btnAddBalance = view.findViewById(R.id.settings_btn_add_balance);
        btnAddBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    presenter.updateWalletBalance(walletBalance.getText().toString(), balanceToBeAdded.getText().toString());
                    Toast.makeText(getContext(), "Wallet Balance Updated!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "An Error Occured!", Toast.LENGTH_SHORT).show();
                    Log.e("updateWalletBalance",e.getMessage());
                }

            }
        });
        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    presenter.updateAccountSettings(firstName.getText().toString(), lastName.getText().toString(), eMail.getText().toString(), phone.getText().toString(), address.getText().toString(), city.getText().toString(), zipCode.getText().toString(), state.getText().toString());
                    Toast.makeText(getContext(), "Account Information Updated!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error Occured!", Toast.LENGTH_SHORT).show();
                    Log.e("updateAccountSettings", e.getMessage());
                }

            }
        });

    }

    @Override
    public void onError() {

    }

    @Override
    public void notifyFirstNameChange(String firstName) {
        this.firstName.setText(firstName);
    }

    @Override
    public void notifyLastNameChange(String lastName) {
        this.lastName.setText(lastName);
    }

    @Override
    public void notifyEmailChange(String email) {
        this.eMail.setText(email);
    }

    @Override
    public void notifyPhoneChange(String phone) {
        this.phone.setText(phone);
    }

    @Override
    public void notifyAddressChange(String address) {
        this.address.setText(address);
    }

    @Override
    public void notifyCityChange(String city) {
        this.city.setText(city);
    }

    @Override
    public void notifyZipCodeChange(String zipCode) {
        this.zipCode.setText(zipCode);
    }

    @Override
    public void notifyStateChange(String state) {
        this.state.setText(state);
    }

    @Override
    public void notifyBalanceChange(String balance) {
        this.walletBalance.setText(balance);
    }


}
