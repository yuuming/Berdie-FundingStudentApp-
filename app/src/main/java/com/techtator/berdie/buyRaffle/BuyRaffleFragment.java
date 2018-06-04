package com.techtator.berdie.buyRaffle;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.Models.FBModel.FBWallet;
import com.techtator.berdie.R;
import com.techtator.berdie.model.WalletDataModel;

public class BuyRaffleFragment extends Fragment implements BuyRaffleContract.View {

    private TextView mNumberOfTickets;
    private TextView mTicketsToBuy;
    private Button mIncrement;
    private Button mDecrement;
    private Button mBuyTicketsButton;
    private int ticketNumber;
    private SeekBar mSeekBar;
    //long press stuff
    private static int REP_DELAY = 50;
    public Handler repeatUpdateHandler = new Handler();
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;
    private static String ticketNumberFromBundle;
    private double userFund;
    private FBWallet mWallet;
    DialogInterface.OnClickListener dialogClickListener;

    public BuyRaffleFragment() {
        // Required empty public constructor
    }

    public static BuyRaffleFragment newInstance() {
        BuyRaffleFragment fragment = new BuyRaffleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle a = this.getArguments();
        ticketNumberFromBundle = a.getString("ticket_number");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buy_raffle_ticket, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.raffle);
        return v;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        final BuyRafflePresenter presenter = new BuyRafflePresenter(this, this);
        presenter.walletDataModel.getWalletByUserId(UserAuthManager.getInstance().getUserId()).observe(this, new Observer<FBWallet>() {
            @Override
            public void onChanged(@Nullable FBWallet data) {
                mWallet = data;
                userFund = data.getCurrentBalance();
            }
        });
        dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        //get the total price (DONE)
                        //check user's wallet (DONE)
                        if(checkUserWallet()){
                            mWallet.setCurrentBalance(chargeUser(Double.valueOf(mTicketsToBuy.getText().toString())));
                            presenter.walletDataModel.updateWallet(mWallet);
                            userFund = chargeUser(Double.valueOf(mTicketsToBuy.getText().toString()));
                            presenter.transactionDataModel.addTransactionHistory("", UserAuthManager.getInstance().getUserId(),"1",Double.valueOf(mTicketsToBuy.getText().toString()));
                            Toast.makeText(getContext(), "Adding tickets...", Toast.LENGTH_SHORT).show();
                            addBoughtTickets(presenter,Integer.valueOf(mTicketsToBuy.getText().toString()));
                            Toast.makeText(getContext(), "Tickets added. Good luck!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(), "Not enough funds!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        mNumberOfTickets = view.findViewById(R.id.buy_raffle_user_ticket_number);
        mIncrement = view.findViewById(R.id.button_increment);
        mDecrement = (Button)view.findViewById(R.id.button_decrement);
        mSeekBar = view.findViewById(R.id.seekBar);
        mTicketsToBuy = (TextView)view.findViewById(R.id.txt_raffle_total);
        String noOfTickets = mTicketsToBuy.getText().toString();
        mBuyTicketsButton = view.findViewById(R.id.buy_raffle_tickets_button);
        mNumberOfTickets.setText(ticketNumberFromBundle);
        ticketNumber = Integer.parseInt("1");
        mIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ticketNumber<100)
                ticketNumber++;
                mTicketsToBuy.setText(String.valueOf(ticketNumber));
            }
        });
        mDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ticketNumber > 0)
                ticketNumber--;
                mTicketsToBuy.setText(String.valueOf(ticketNumber));
            }
        });
        mBuyTicketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(String.valueOf(ticketNumber)+ "$ will be deducted from your wallet. Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });
        //long press stuff
        mIncrement.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mAutoIncrement = true;
                repeatUpdateHandler.post(new RptUpdater());
                return false;
            }
        });
        mDecrement.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mAutoDecrement = true;
                repeatUpdateHandler.post(new RptUpdater());
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


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTicketsToBuy.setText(String.valueOf(progress));
                ticketNumber = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private boolean checkUserWallet(){
        if(userFund - Double.valueOf(mTicketsToBuy.getText().toString()) < 0) {
            return false;
        }
        else {
            return true;
        }
    }

    private double chargeUser(double amount){
        return userFund - amount;
    }

    private void addBoughtTickets(BuyRafflePresenter p, int numberOfTickets){

        for (int i =0;i < numberOfTickets;i++){
            p.ticketBoughtDataModel.addTicketBought("","","001",1.0,UserAuthManager.getInstance().getUserId());
        }
    }

    private String decideRaffleWinner(BuyRafflePresenter presenter){

        //get the number of tickets for the current raffle.
        //FBLiveData<List<FBTicketsBought>> ticketList = presenter.ticketBoughtDataModel.getTicketsBoughtsList().observe(new Own);


        return null;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void notifyUserTicketNumber(String numberOfTickets) {
        mNumberOfTickets.setText(numberOfTickets);

    }
    public class RptUpdater implements Runnable {
        public void increment(){
            if (ticketNumber<100)
                ticketNumber++;
            mTicketsToBuy.setText(String.valueOf(ticketNumber));
            mSeekBar.setProgress(ticketNumber);
        }
        public void decrement(){
            if (ticketNumber > 1)
                ticketNumber--;
            mTicketsToBuy.setText(String.valueOf(ticketNumber));
            mSeekBar.setProgress(ticketNumber);
        }
        public void run() {
            if( mAutoIncrement ){
                increment();
                repeatUpdateHandler.postDelayed( new RptUpdater(), REP_DELAY );
            } else if( mAutoDecrement ){
                decrement();
                repeatUpdateHandler.postDelayed( new RptUpdater(), REP_DELAY );
            }
        }
    }


}

