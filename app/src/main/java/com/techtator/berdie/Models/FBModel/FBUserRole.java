package com.techtator.berdie.Models.FBModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBUserRole extends FBObject{
    private String id;
    private String role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public FBUserRole(){}

    public FBUserRole(String id, String role) {
        this.id = id;
        this.role = role;
    }

    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("role", role);

        return result;
    }
}
