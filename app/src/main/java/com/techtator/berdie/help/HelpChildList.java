package com.techtator.berdie.help;

import android.os.Parcel;
import android.os.Parcelable;

public class HelpChildList implements Parcelable {
    private String body;

    public HelpChildList(String body){
        this.body = body;
    }

    protected HelpChildList(Parcel in) {
        body = in.readString();
    }

    public static final Creator<HelpChildList> CREATOR = new Creator<HelpChildList>() {
        @Override
        public HelpChildList createFromParcel(Parcel in) {
            return new HelpChildList(in);
        }

        @Override
        public HelpChildList[] newArray(int size) {
            return new HelpChildList[size];
        }
    };

    public String getBody() {
        return body;
    }

    public void setBody(){
        this.body = body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
