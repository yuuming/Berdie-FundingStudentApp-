package com.techtator.berdie.Models.FBModel;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FBScholarship extends FBObject {
    private String id;
    private String body;
    private Date dueDate;
    private String header;
    private String picture;
    private double scholarshipFee;
    private String websiteLink;
    private Date timeStamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public double getScholarshipFee() {
        return scholarshipFee;
    }

    public void setScholarshipFee(double scholarshipFee) {
        this.scholarshipFee = scholarshipFee;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public FBScholarship(String id, String body, Date dueDate, String header, String picture, double scholarshipFee, String websiteLink, Date timeStamp) {
        this.id = id;
        this.body = body;
        this.dueDate = dueDate;
        this.header = header;
        this.picture = picture;
        this.scholarshipFee = scholarshipFee;
        this.websiteLink = websiteLink;
        this.timeStamp = timeStamp;
    }

    public FBScholarship(){}

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("due_date", dueDate.getTime());
        result.put("header", header);
        result.put("body", body);
        result.put("picture", picture);
        result.put("scholarship_fee", scholarshipFee);
        result.put("website_link", websiteLink);
        result.put("time_stamp", timeStamp.getTime());

        return result;
    }
}







