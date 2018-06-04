package com.techtator.berdie.raffle;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.techtator.berdie.R;

/**
 * Created by cemserin on 2018-04-12.
 */

public class BuyRaffleDialog extends Dialog {

    private BuyRaffleDialogueListener listener ;

    public BuyRaffleDialog(@NonNull Context context) {
        super(context);
    }

    public BuyRaffleDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public BuyRaffleDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener)
    {
        super(context, cancelable, cancelListener);
    }
    public void setBuyRaffleListener(BuyRaffleDialogueListener _listener)
    {
        this.listener = _listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_buy_raffle_ticket);

        findViewById(R.id.button_increment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "PLUS", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.button_decrement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.decrement();
            }
        });
        findViewById(R.id.buy_raffle_tickets_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Buy Tickets!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
