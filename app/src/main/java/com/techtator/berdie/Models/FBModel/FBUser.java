package com.techtator.berdie.Models.FBModel;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cemserin on 2018-03-06.
 */

public class FBUser extends FBObject implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private String communityId;
    private String dateOfBirth;
    private String geotag;
    private  boolean isActive;
    private String password;
    private String phone;
    private Date timeStamp;
    private String userRole;
    private String walletId;
    private String profilePic;
    private String email;

    public FBUser() {
    }

    public FBUser(String id, String firstName, String lastName, String communityId, String dateOfBirth, String geotag, boolean isActive, String password, String phone, Date timeStamp, String userRole, String walletId, String profilePic, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.communityId = communityId;
        this.dateOfBirth = dateOfBirth;
        this.geotag = geotag;
        this.isActive = isActive;
        this.password = password;
        this.phone = phone;
        this.timeStamp = timeStamp;
        this.userRole = userRole;
        this.walletId = walletId;
        this.profilePic = profilePic;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGeotag() {
        return geotag;
    }

    public void setGeotag(String geotag) {
        this.geotag = geotag;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("first_name",firstName);
        result.put("last_name",lastName);
        result.put("community_id",communityId);
        result.put("date_of_birth",dateOfBirth);
        result.put("geotag",geotag);
        result.put("is_active",isActive);
        result.put("password",password);
        result.put("phone",phone);
        result.put("time_stamp",timeStamp.getTime());
        result.put("user_role",userRole);
        result.put("wallet_id",walletId);
        result.put("email", email);
        result.put("profile_pic", profilePic);
        return result;
    }

    @Override
    public String toString() {
        return "FBUser{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", communityId='" + communityId + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", geotag='" + geotag + '\'' +
                ", isActive=" + isActive +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", userRole='" + userRole + '\'' +
                ", walletId='" + walletId + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
