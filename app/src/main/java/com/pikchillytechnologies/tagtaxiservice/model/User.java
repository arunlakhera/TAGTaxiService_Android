package com.pikchillytechnologies.tagtaxiservice.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User{

    private String mName;
    private String mEmailId;
    private String mPhoneNumber;
    private String mGender;
    private String mAddress;
    private String mCity;
    private String mState;
    private String mPhoto;
    private String mUserType;

    public User() {
        /*Blank default constructor essential for Firebase*/
    }

    public User(String name, String emailId, String phoneNumber, String gender, String address, String city, String state, String photo, String userType) {

        this.mName = name;
        this.mEmailId = emailId;
        this.mPhoneNumber = phoneNumber;
        this.mGender = gender;
        this.mAddress = address;
        this.mCity = city;
        this.mState = state;
        this.mPhoneNumber = photo;
        this.mUserType = userType;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmEmailId() {
        return mEmailId;
    }

    public void setmEmailId(String mEmailId) {
        this.mEmailId = mEmailId;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }

    public String getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    public String getmUserType() {
        return mUserType;
    }

    public void setmUserType(String mUserType) {
        this.mUserType = mUserType;
    }
}
