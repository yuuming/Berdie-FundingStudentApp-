package com.techtator.berdie.help;

import android.os.Parcel;

import com.techtator.berdie.Models.FBModel.FBHelp;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class HelpParentList extends ExpandableGroup<HelpChildList> {
    String header;

    public HelpParentList(String title, List<HelpChildList> items) {
        super(title, items);
        this.header = header;
    }

    protected HelpParentList(Parcel in) {
        super(in);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}
