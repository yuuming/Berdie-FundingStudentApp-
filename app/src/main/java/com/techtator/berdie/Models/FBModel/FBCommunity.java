package com.techtator.berdie.Models.FBModel;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-05.
 */

public class FBCommunity extends FBObject{

    private String communityId;
    private String name;
    private String geotag;
    private String address;

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeotag() {
        return geotag;
    }

    public void setGeotag(String geotag) {
        this.geotag = geotag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public FBCommunity() {
    }
    
        public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("community_id", communityId);
        result.put("name", name);
        result.put("geotag", geotag);
        result.put("address", address);
        return result;
    }

    public FBCommunity(String communityId, String name, String geotag, String address) {
        this.communityId = communityId;
        this.name = name;
        this.geotag = geotag;
        this.address = address;
    }



}
