package com.techtator.berdie.Models.FBModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBHelp extends FBObject{

    private String id;
    private String header;
    private String body;
    private String picture;

    public FBHelp(){}

    public FBHelp(String id, String header, String body, String picture) {
        this.id = id;
        this.header = header;
        this.body = body;
        this.picture = picture;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("header", header);
        result.put("body", body);
        result.put("picture", picture);

        return result;
    }
}
