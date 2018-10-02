package com.pikchillytechnologies.tagtaxiservice.helperfile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseRef {


    private FirebaseAuth mAuthRef;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mUserRef;
    private DatabaseReference mBookingRef;

    public FirebaseRef(){

        mAuthRef = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        // User Table Reference in Firebase
        mUserRef = mDatabaseRef.child("Users/");
        mBookingRef = mDatabaseRef.child("Booking/");

    }

    public FirebaseAuth getmAuthRef() {
        return mAuthRef;
    }

    public DatabaseReference getmDatabaseRef() {
        return mDatabaseRef;
    }

    public DatabaseReference getmUserRef() {
        return mUserRef;
    }

    public DatabaseReference getmBookingRef() {
        return mBookingRef;
    }
}
