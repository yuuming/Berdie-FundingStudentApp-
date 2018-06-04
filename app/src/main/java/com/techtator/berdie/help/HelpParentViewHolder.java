package com.techtator.berdie.help;

import android.view.View;
import android.view.ViewParent;
import android.widget.ImageButton;
import android.widget.TextView;

import com.techtator.berdie.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class HelpParentViewHolder extends GroupViewHolder {
    public TextView mTitleView;
    public ImageButton mParentDropDownArrow;

    public HelpParentViewHolder(View itemView) {
        super(itemView);

            mTitleView = (TextView) itemView.findViewById(R.id.help_title);
            mParentDropDownArrow = (ImageButton) itemView.findViewById(R.id.help_arrow);

    }

    public void setParentTitle(ExpandableGroup group){
        mTitleView.setText(group.getTitle());
    }
}
