package com.techtator.berdie.Models.FBModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBAbout extends FBObject{ // Put properties here from class About

    private String id;
    private String logo;
    private String body;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public FBAbout() {

    }

    public FBAbout(String id, String logo, String body) {
        this.id = id;
        this.logo = logo;
        this.body = body;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("logo", logo);
        result.put("body", body);

        return result;
    }

}

