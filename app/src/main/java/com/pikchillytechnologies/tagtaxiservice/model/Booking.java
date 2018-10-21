package com.pikchillytechnologies.tagtaxiservice.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Booking implements Parcelable {

    private String mBookingId;
    private String mUserPhoneNumber;
    private String mPickupAddress;
    private String mDropAddress;
    private String mNumberOfPassengers;
    private String mRoundTrip;
    private String mTravellingOnDate;
    private String mReturningOnDate;
    private String mVehicleType;
    private String mPickupTime;
    private String mBookingStatus;
    private String mReason;

    public Booking() {

    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mBookingId);
        out.writeString(mUserPhoneNumber);
        out.writeString(mPickupAddress);
        out.writeString(mDropAddress);
        out.writeString(mNumberOfPassengers);
        out.writeString(mRoundTrip);
        out.writeString(mTravellingOnDate);
        out.writeString(mReturningOnDate);
        out.writeString(mVehicleType);
        out.writeString(mPickupTime);
        out.writeString(mBookingStatus);
        out.writeString(mReason);

    }

    private Booking(Parcel in) {
        mBookingId = in.readString();
        mUserPhoneNumber = in.readString();
        mPickupAddress = in.readString();
        mDropAddress = in.readString();
        mNumberOfPassengers = in.readString();
        mRoundTrip = in.readString();
        mTravellingOnDate = in.readString();
        mReturningOnDate = in.readString();
        mVehicleType = in.readString();
        mPickupTime = in.readString();
        mBookingStatus = in.readString();
        mReason = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Booking> CREATOR
            = new Parcelable.Creator<Booking>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    public Booking(String bookingId,String userPhoneNumber, String pickupAddress, String dropAddress, String numberOfPassengers, String roundTrip, String travellingOnDate, String returningOnDate, String vehicleType, String pickupTime, String bookingStatus, String reason){

        this.mBookingId = bookingId;
        this.mUserPhoneNumber = userPhoneNumber;
        this.mPickupAddress = pickupAddress;
        this.mDropAddress = dropAddress;
        this.mNumberOfPassengers = numberOfPassengers;
        this.mRoundTrip = roundTrip;
        this.mTravellingOnDate = travellingOnDate;
        this.mReturningOnDate = returningOnDate;
        this.mVehicleType = vehicleType;
        this.mPickupTime = pickupTime;
        this.mBookingStatus = bookingStatus;
        this.mReason = reason;

    }

    public String getmBookingId() {
        return mBookingId;
    }

    public void setmBookingId(String mBookingId) {
        this.mBookingId = mBookingId;
    }

    public String getmUserPhoneNumber() {
        return mUserPhoneNumber;
    }

    public void setmUserPhoneNumber(String mUserPhoneNumber) {
        this.mUserPhoneNumber = mUserPhoneNumber;
    }

    public void setmBookingStatus(String mBookingStatus) {
        this.mBookingStatus = mBookingStatus;
    }

    public void setmReason(String mReason) {
        this.mReason = mReason;
    }

    public String getmPickupAddress() {
        return mPickupAddress;
    }

    public void setmPickupAddress(String mPickupAddress) {
        this.mPickupAddress = mPickupAddress;
    }

    public String getmDropAddress() {
        return mDropAddress;
    }

    public void setmDropAddress(String mDropAddress) {
        this.mDropAddress = mDropAddress;
    }

    public String getmNumberOfPassengers() {
        return mNumberOfPassengers;
    }

    public void setmNumberOfPassengers(String mNumberOfPassengers) {
        this.mNumberOfPassengers = mNumberOfPassengers;
    }

    public String getmRoundTrip() {
        return mRoundTrip;
    }

    public void setmRoundTrip(String mRoundTrip) {
        this.mRoundTrip = mRoundTrip;
    }

    public String getmTravellingOnDate() {
        return mTravellingOnDate;
    }

    public void setmTravellingOnDate(String mTravellingOnDate) {
        this.mTravellingOnDate = mTravellingOnDate;
    }

    public String getmReturningOnDate() {
        return mReturningOnDate;
    }

    public void setmReturningOnDate(String mReturningOnDate) {
        this.mReturningOnDate = mReturningOnDate;
    }

    public String getmVehicleType() {
        return mVehicleType;
    }

    public void setmVehicleType(String mVehicleType) {
        this.mVehicleType = mVehicleType;
    }

    public String getmPickupTime() {
        return mPickupTime;
    }

    public void setmPickupTime(String mPickupTime) {
        this.mPickupTime = mPickupTime;
    }

    public String getmBookingStatus() {
        return mBookingStatus;
    }

    public String getmReason() {
        return mReason;
    }
}


/*
@IgnoreExtraProperties
public class Booking {

    private String mBookingId;
    private String mUserPhoneNumber;
    private String mPickupAddress;
    private String mDropAddress;
    private String mNumberOfPassengers;
    private String mRoundTrip;
    private String mTravellingOnDate;
    private String mReturningOnDate;
    private String mVehicleType;
    private String mPickupTime;
    private String mBookingStatus;
    private String mReason;

    public Booking() {

    }

    public Booking(String bookingId,String userPhoneNumber, String pickupAddress, String dropAddress, String numberOfPassengers, String roundTrip, String travellingOnDate, String returningOnDate, String vehicleType, String pickupTime, String bookingStatus, String reason){

        this.mBookingId = bookingId;
        this.mUserPhoneNumber = userPhoneNumber;
        this.mPickupAddress = pickupAddress;
        this.mDropAddress = dropAddress;
        this.mNumberOfPassengers = numberOfPassengers;
        this.mRoundTrip = roundTrip;
        this.mTravellingOnDate = travellingOnDate;
        this.mReturningOnDate = returningOnDate;
        this.mVehicleType = vehicleType;
        this.mPickupTime = pickupTime;
        this.mBookingStatus = bookingStatus;
        this.mReason = reason;

    }

    public String getmBookingId() {
        return mBookingId;
    }

    public void setmBookingId(String mBookingId) {
        this.mBookingId = mBookingId;
    }

    public String getmUserPhoneNumber() {
        return mUserPhoneNumber;
    }

    public void setmUserPhoneNumber(String mUserPhoneNumber) {
        this.mUserPhoneNumber = mUserPhoneNumber;
    }

    public void setmBookingStatus(String mBookingStatus) {
        this.mBookingStatus = mBookingStatus;
    }

    public void setmReason(String mReason) {
        this.mReason = mReason;
    }

    public String getmPickupAddress() {
        return mPickupAddress;
    }

    public void setmPickupAddress(String mPickupAddress) {
        this.mPickupAddress = mPickupAddress;
    }

    public String getmDropAddress() {
        return mDropAddress;
    }

    public void setmDropAddress(String mDropAddress) {
        this.mDropAddress = mDropAddress;
    }

    public String getmNumberOfPassengers() {
        return mNumberOfPassengers;
    }

    public void setmNumberOfPassengers(String mNumberOfPassengers) {
        this.mNumberOfPassengers = mNumberOfPassengers;
    }

    public String getmRoundTrip() {
        return mRoundTrip;
    }

    public void setmRoundTrip(String mRoundTrip) {
        this.mRoundTrip = mRoundTrip;
    }

    public String getmTravellingOnDate() {
        return mTravellingOnDate;
    }

    public void setmTravellingOnDate(String mTravellingOnDate) {
        this.mTravellingOnDate = mTravellingOnDate;
    }

    public String getmReturningOnDate() {
        return mReturningOnDate;
    }

    public void setmReturningOnDate(String mReturningOnDate) {
        this.mReturningOnDate = mReturningOnDate;
    }

    public String getmVehicleType() {
        return mVehicleType;
    }

    public void setmVehicleType(String mVehicleType) {
        this.mVehicleType = mVehicleType;
    }

    public String getmPickupTime() {
        return mPickupTime;
    }

    public void setmPickupTime(String mPickupTime) {
        this.mPickupTime = mPickupTime;
    }

    public String getmBookingStatus() {
        return mBookingStatus;
    }

    public String getmReason() {
        return mReason;
    }
}
*/