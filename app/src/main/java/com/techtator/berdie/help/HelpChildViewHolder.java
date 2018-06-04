package com.techtator.berdie.help;

import android.view.View;
import android.widget.TextView;

import com.techtator.berdie.Models.FBModel.FBNews;
import com.techtator.berdie.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class HelpChildViewHolder extends ChildViewHolder{

    public TextView mHelpBodyText;

    public HelpChildViewHolder(View itemView) {
        super(itemView);

        mHelpBodyText = (TextView) itemView.findViewById(R.id.help_listChild);
    }

    public void onBind(String Sousdoc){
        mHelpBodyText.setText(Sousdoc);
    }
}
