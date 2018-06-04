package com.techtator.berdie.Models.FBModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBAccountSettings extends FBObject{
    private String settingsId;
    private  String userId;
    private String address;
    private  String city;
    private String zipCode;
    private String state;

    public void addAccountSettings(String _settingsId, String _userId, String _address, String _city, String _zipCode, String _state){}
    public void updateAccountSettings(String _settingsId, String _userId, String _address, String _city, String _zipCode, String _state){}
    public void deleteAccountSettings(String _settingsId){}


    public String getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(String settingsId) {
        this.settingsId = settingsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public FBAccountSettings() {

    }

    public FBAccountSettings(String settingsId, String userId, String address, String city, String zipCode, String state) {
        this.settingsId = settingsId;
        this.userId = userId;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
    }

    @Override
    public String toString() {
        return "FBAccountSettings{" +
                "settingsId='" + settingsId + '\'' +
                ", userId='" + userId + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("settings_id", settingsId);
        result.put("user_id", userId);
        result.put("address", address);
        result.put("city", city);
        result.put("zip_code", zipCode);
        result.put("state", state);

        return result;
    }

}
