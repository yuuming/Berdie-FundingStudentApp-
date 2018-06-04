package com.techtator.berdie.raffle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.techtator.berdie.R;
import com.techtator.berdie.buyRaffle.BuyRaffleFragment;

/**
 * Created by user on 2018-04-01.
 */

public class RaffleFragment extends Fragment implements RaffleContract.View {

    private TextView remainingTime;
    private TextView numberOfTickets;
    private TextView ticketUnitPrice;
    private TextView totalTicketNumber;
    private int ticketNumber;

    public static RaffleFragment newInstance() {
        RaffleFragment fragment = new RaffleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_raffle,container,false    );
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.raffle);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RafflePresenter presenter = new RafflePresenter(this, this);
        numberOfTickets = view.findViewById(R.id.raffle_number_of_tickets);
        remainingTime = view.findViewById(R.id.raffle_remaining_time);
        ticketUnitPrice = view.findViewById(R.id.raffle_ticket_unit_price);
        Button btnBuyTickets = view.findViewById(R.id.raffle_button_buy_now);
        final BuyRaffleFragment raffleFrag = new BuyRaffleFragment();
        btnBuyTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ticket_number",numberOfTickets.getText().toString());
                    raffleFrag.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, raffleFrag)
                         .addToBackStack(null)
                        .commit();
            }
        });
    }
    @Override
    public void onSuccess(){
    }

    @Override
    public void onError(){
    }

    @Override
    public void notifyRemainingTimeChange(String remainingTimeText) {
        remainingTime.setText(remainingTimeText);

    }

    @Override
    public void notifyTicketUnitPriceChange(String ticketUnitPriceText) {
        ticketUnitPrice.setText(ticketUnitPriceText);
    }

    @Override
    public void notifyNumberOfTicketsChange(String numberOfTicketsText) {
        numberOfTickets.setText(numberOfTicketsText);
    }


}