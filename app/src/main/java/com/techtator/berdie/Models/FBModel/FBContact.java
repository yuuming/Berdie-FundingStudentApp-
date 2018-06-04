package com.techtator.berdie.Models.FBModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBContact extends FBObject{
    private String id;
    private String link;
    public void addContact(String _id,String _link){}
    public void updateContact(String _id,String _link){}
    public void deleteContact(String _id){}


    public FBContact(){}


    public FBContact(String id, String link) {
        this.id = id;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("link", link);

        return result;
    }


}
