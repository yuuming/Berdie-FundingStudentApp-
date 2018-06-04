package com.techtator.berdie.Models.FBModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBNews extends FBObject{

    private String newsId;
    private String title;
    private String body;
    private String newsPicture;
    private int newsType;
    private Date timeStamp;
    private boolean isActive;


    public FBNews(){}

    public FBNews(String newsId, String title, String body, String newsPicture, boolean isActive, int newsType, Date timeStamp) {
        this.newsId = newsId;
        this.title = title;
        this.body = body;
        this.newsPicture = newsPicture;
        this.newsType = newsType;
        this.timeStamp = timeStamp;
        this.isActive = isActive;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNewsPicture() {
        return newsPicture;
    }

    public void setNewsPicture(String newsPicture) {
        this.newsPicture = newsPicture;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("news_id", newsId);
        result.put("title", title);
        result.put("body", body);
        result.put("news_picture", newsPicture);
        result.put("news_type", newsType);
        result.put("time_stamp", timeStamp.getTime());
        result.put("isActive", isActive);

        return result;
    }

}
