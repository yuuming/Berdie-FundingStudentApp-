package com.techtator.berdie.findStudent;

import com.techtator.berdie.Models.FBModel.FBUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018-04-22.
 */

public class ProfileEntity implements Serializable {
    private String id;
    private FBUser user;
    private String major;
    private String university;
    private String body;

    public ProfileEntity() {}
    public ProfileEntity(String id, FBUser user, String major, String university, String body) {
        this.id = id;
        this.user = user;
        this.major = major;
        this.university = university;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FBUser getUser() {
        return user;
    }

    public void setUser(FBUser user) {
        this.user = user;
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

//    public static ArrayList<ProfileEntity> findProfileEntityByWord(String word, List<ProfileEntity> list) {
//        ArrayList<ProfileEntity> filteredList = new ArrayList<>();
//
//        for(int i = 0; i<list.size(); i++) {
//            if(list.get(i).getUser().getFirstName().contains(word) || list.get(i).getUniversity().contains(word)|| list.get(i).getBody().contains(word)) {
//                filteredList.add(list.get(i));
//            }
//        }
//        return filteredList;
//    }

    @Override
    public String toString() {
        return "ProfileEntity{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", major='" + major + '\'' +
                ", university='" + university + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
