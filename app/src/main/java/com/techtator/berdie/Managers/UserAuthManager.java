package com.techtator.berdie.Managers;


import com.techtator.berdie.Models.FBModel.FBUser;

import java.util.Date;

public class UserAuthManager {

    private static UserAuthManager instance;

    private FBUser myself;

    public static UserAuthManager getInstance() {
        if (instance==null) {
            instance = new UserAuthManager();
        }
        return instance;
    }

    public String getUserId() {
        if (myself==null) {
            return "Hecvdsy50GWuI1h30j8nVbXjeWD3";
        } else {
            return myself.getId();
        }
    }

    public String getPicture() {
        if (myself==null) {
            return "https://randomuser.me/api/portraits/men/37.jpg";
        } else {
            return String.valueOf(myself.getProfilePic());
        }
    }

    public String getFirstName() {
        if (myself==null) {
            return "Xavier";
        } else {
            return myself.getFirstName();
        }
    }

    public FBUser getUser() {
        if (myself==null) {
            return new FBUser("12397348023", "ceeeeeeem", "serinse", "407", "05.02.1990", "geotag", true, "admin123", "7786447329", new Date(), "1", "37485933", "https://randomuser.me/api/portraits/men/13.jpg", "serinocem@gmail.com");
        } else {
            return this.myself;
        }
    }

    public void setMyself(FBUser myself) {
        this.myself = myself;
    }
}
