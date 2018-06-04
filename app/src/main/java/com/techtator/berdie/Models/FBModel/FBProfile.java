package com.techtator.berdie.Models.FBModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBProfile  extends FBObject{

    private String id;
    private String userId;
    private String major;
    private String university;
    private String body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public FBProfile(){}

    public FBProfile(String id, String userId, String major, String university, String body) {
        this.id = id;
        this.userId = userId;
        this.major = major;
        this.university = university;
        this.body = body;
    }

    @Override
    public String toString() {
        return "FBProfile{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", major='" + major + '\'' +
                ", university='" + university + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("user_id", userId);
        result.put("major", major);
        result.put("university", university);
        result.put("body", body);

        return result;
    }
}
