package com.techtator.berdie.raffleDonation;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBRaffle;
import com.techtator.berdie.Models.FBModel.FBWallet;
import com.techtator.berdie.R;
import com.techtator.berdie.buyRaffle.BuyRaffleFragment;
import com.techtator.berdie.model.FBLiveData;
import com.techtator.berdie.model.RaffleDataModel;

public class RaffleDonationFragment extends Fragment implements RaffleDonationContract.View {
    RaffleDonationPresenter presenter;
    private TextView mTotalFee;
    private Button mIncrement;
    private Button mDecrement;
    private Button mDonateToRaffle;
    private SeekBar mSeekBar;
    private static int REP_DELAY = 50;
    public Handler repeatUpdateHandler = new Handler();
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;
    DialogInterface.OnClickListener dialogClickListener;
    private int totalFee;
    private double donorFund;
    FBWallet mWallet;


    public RaffleDonationFragment() {
        // Required empty public constructor
    }


    public static RaffleDonationFragment newInstance() {
        RaffleDonationFragment fragment = new RaffleDonationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_raffle_donation, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTotalFee = view.findViewById(R.id.donor_amount_to_be_donated);
        mIncrement = view.findViewById(R.id.button_increment);
        mDecrement = view.findViewById(R.id.button_decrement);
        mDonateToRaffle = view.findViewById(R.id.donate_tickets_button);
        mSeekBar = view.findViewById(R.id.donor_seekBar);
        totalFee = Integer.valueOf(mTotalFee.getText().toString());
        presenter = new RaffleDonationPresenter(this,this);
        presenter.mWallet.observe(this, new Observer<FBWallet>() {
            @Override
            public void onChanged(@Nullable FBWallet fbWallet) {
                donorFund = fbWallet.getCurrentBalance();
                mWallet = fbWallet;
            }
        });
        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        if(donorFund >= totalFee){
                        Double currentAmount = presenter.mTopRaffle.getAmountCollected();
                        currentAmount += totalFee;
                        presenter.mTopRaffle.setAmountCollected(currentAmount);
                        presenter.mWalletDataModel.updateWallet(mWallet);
                        presenter.mTransactionHistoryDataModel.addTransactionHistory("", UserAuthManager.getInstance().getUserId(),"1",currentAmount);
                            Toast.makeText(getContext(), "Donation received. Thank you!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(), "Not enough funds!", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };
        mIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalFee<100)
                    totalFee++;
                mTotalFee.setText(String.valueOf(totalFee));
            }
        });
        mDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalFee > 0)
                    totalFee--;
                mTotalFee.setText(String.valueOf(totalFee));
            }
        });
        mIncrement.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mAutoIncrement = true;
                repeatUpdateHandler.post(new RaffleDonationFragment.RptUpdater());
                return false;
            }
        });
        mDecrement.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mAutoDecrement = true;
                repeatUpdateHandler.post(new RaffleDonationFragment.RptUpdater());
                return false;
            }
        });

        mIncrement.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if((event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL) && mAutoIncrement ){
                    mAutoIncrement = false;
                }
                return false;
            }
        });
        mDecrement.setOnTouchListener( new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if((event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL) && mAutoDecrement ){
                    mAutoDecrement= false;
                }
                return false;
            }
        });
        mDonateToRaffle = view.findViewById(R.id.donate_tickets_button);
        mDonateToRaffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(String.valueOf(totalFee)+"$ will be deducted from your wallet. Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


            }
        });
}

class RptUpdater implements Runnable {
        public void increment(){
            if (totalFee<100)
                totalFee++;
            mTotalFee.setText(String.valueOf(totalFee));
            mSeekBar.setProgress(totalFee);
        }
        public void decrement(){
            if (totalFee > 1)
                totalFee--;
            mTotalFee.setText(String.valueOf(totalFee));
            mSeekBar.setProgress(totalFee);
        }
        public void run() {
            if( mAutoIncrement ){
                increment();
                repeatUpdateHandler.postDelayed( new RaffleDonationFragment.RptUpdater(), REP_DELAY );
            } else if( mAutoDecrement ){
                decrement();
                repeatUpdateHandler.postDelayed( new RaffleDonationFragment.RptUpdater(), REP_DELAY );
            }
        }
    }


}


