package com.pikchillytechnologies.tagtaxiservice.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pikchillytechnologies.tagtaxiservice.R;
import com.pikchillytechnologies.tagtaxiservice.helperfile.FirebaseRef;
import com.pikchillytechnologies.tagtaxiservice.helperfile.HelperFile;
import com.pikchillytechnologies.tagtaxiservice.model.User;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerificationActivity extends AppCompatActivity {

    // Variable Declaration
    @BindView(R.id.button_Resend)
    Button mResend_Button;
    @BindView(R.id.textView_PhoneNumber)
    TextInputEditText mPhone_TextInputEditText;
    @BindView(R.id.textView_CountryCode)
    TextInputEditText mCountryCode_TextInputEditText;
    @BindView(R.id.textView_OTP)
    TextInputEditText mOTP_TextInputEditText;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    // Temp number for verification to be removed later
    String tempPhoneNum = "+911112223333";
    String tempCode = "123456";

    String tempPhoneNum2 = "+912223334444";
    String tempCode2 = "123456";

    private HelperFile mHelperFile;
    private String mPhone;
    private Bundle mUserDetail;
    private String mCodeSent;
    private String mCountryCode;
    private CountDownTimer mCodeCount;
    private Boolean mCodeSentFlag = false;
    // Variable to store the code that was sent which can then be matched with the code that user enters
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            Log.d("VerificationCodeSuccess", "Code Sent Successfully");

            mProgressBar.setVisibility(View.GONE);

            // Show the message that code has been sent to the user
            mHelperFile.screenToast(VerificationActivity.this, R.string.code_sent, Toast.LENGTH_LONG);

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Log.d("VerificationCodeFailed", "Failed:", e);

            if (e instanceof FirebaseAuthInvalidCredentialsException) {

                // Invalid request
                mHelperFile.screenToast(VerificationActivity.this, R.string.code_not_sent, Toast.LENGTH_LONG);

            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                mHelperFile.screenToast(VerificationActivity.this, R.string.sms_quota, Toast.LENGTH_LONG);
            }

            // If code is not sent Enable the Resend button to let user try to send it again.
            codeCountDown(60000, 1);
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            mCodeSent = s;
            mCodeSentFlag = true;
        }
    };
    private String mUserType;
    private String mUserPhoneNumber;
    private FirebaseRef mFirebaseRef;
    private FirebaseAuth mAuthRef;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mUserProfileRef;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference mUserRef;
    private User mUser;
    private ValueEventListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        ButterKnife.bind(this);

        mFirebaseRef = new FirebaseRef();
        mHelperFile = new HelperFile();
        mUserDetail = getIntent().getExtras();

        if (mUserDetail != null) {
            // Get Phone Number entered by user in Sign In Activity
            mPhone = mUserDetail.getString("Phone", "NA");

            // Set the phone number in Phone field
            mPhone_TextInputEditText.setText(mPhone);
        } else {
            Toast.makeText(VerificationActivity.this, "Phone Number could not be retrieved. Please try again!", Toast.LENGTH_LONG).show();
        }

        // Call Function to send verification code
        sendVerificationCode();

    }

    @OnClick(R.id.button_Back)
    public void onBackButtonClick() {
        mHelperFile.screenIntent(VerificationActivity.this, SignInActivity.class);
    }

    @OnClick(R.id.button_Resend)
    public void onResendButtonClick() {
        // Call Send verification code function to send the code again
        sendVerificationCode();
    }

    @OnClick(R.id.button_Confirm)
    public void onConfirmButtonClick() {
        if (mCodeSentFlag) {
            mProgressBar.setVisibility(View.VISIBLE);
            verifySignInCode();
        } else {
            mHelperFile.screenToast(VerificationActivity.this, R.string.wait_for_code, Toast.LENGTH_LONG);
        }
    }

    /**
     * Function to send the Verification Code
     */
    private void sendVerificationCode() {

        // Get the country code
        mCountryCode = String.valueOf(mCountryCode_TextInputEditText.getText());

        // Get the phone number
        mPhone = String.valueOf(mPhone_TextInputEditText.getText());

        // Check if the phone number entered is not empty and is a valid phone number
        if (mPhone.isEmpty() || mPhone.length() < 10) {

            mPhone_TextInputEditText.setText(R.string.valid_phone_msg);
            mPhone_TextInputEditText.requestFocus();
            return;

        }

        // Function to send OTP Code to the phone number entered
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                //mCountryCode + mPhone,        // Phone number to verify
                tempPhoneNum,                   //
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    /**
     * Function for Countdown timer
     */
    public void codeCountDown(long duration, long interval) {

        mResend_Button.setEnabled(false);
        mResend_Button.setBackgroundResource(R.drawable.button_gray_bg);

        mCodeCount = new CountDownTimer(duration, interval) {
            @Override
            public void onTick(long millisUntilFinished) {

                long seconds = millisUntilFinished / 1000;//convert to seconds

                seconds = seconds % 60;//seconds can be between 0-60, so we use the % operator to get the remainder
                String time = seconds + " second";
                mResend_Button.setText(time);

            }

            @Override
            public void onFinish() {

                mResend_Button.setEnabled(true);
                mResend_Button.setText(R.string.resend);
                mResend_Button.setBackgroundResource(R.drawable.button_orange_bg);
            }
        };

        mCodeCount.start();
    }

    /**
     * Function to verify the Code that the user entered
     */
    private void verifySignInCode() {

        String mOTPCode = String.valueOf(mOTP_TextInputEditText.getText());
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mCodeSent, mOTPCode);
        signInWithPhoneAuthCredential(credential);
    }

    /**
     * Function to match the credentials of user and let user log in in case of successful sign in
     */
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mFirebaseRef.getmAuthRef().signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    signInUser();

                } else {

                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        mProgressBar.setVisibility(View.GONE);

                        // The verification code entered was invalid
                        mHelperFile.screenToast(VerificationActivity.this, R.string.wrong_code, Toast.LENGTH_LONG);
                    }
                }
            }
        });
    }

    public void signInUser() {

        mCurrentUser = mFirebaseRef.getmAuthRef().getCurrentUser();

        if (mCurrentUser != null) {
            mUserPhoneNumber = mCurrentUser.getPhoneNumber();
        }

        mUserRef = mFirebaseRef.getmUserRef().child(mUserPhoneNumber);

        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChildren() && dataSnapshot.getChildrenCount() > 0) {

                    mUserType = String.valueOf(dataSnapshot.child("mUserType").getValue());

                    if (mUserType.equals("Rider")) {

                        mHelperFile.screenIntent(VerificationActivity.this, HomeActivity.class, mUserPhoneNumber);
                    }
                } else {

                    mUser = new User("Guest", "", mUserPhoneNumber, "", "", "", "", "", "Rider");
                    mUserRef.setValue(mUser);

                    mProgressBar.setVisibility(View.GONE);
                    mHelperFile.screenIntent(VerificationActivity.this, HomeActivity.class, mUserPhoneNumber);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.d("TAGUser:", databaseError.getDetails());
            }
        });
    }
}
