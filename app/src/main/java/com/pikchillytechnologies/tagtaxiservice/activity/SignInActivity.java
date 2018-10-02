package com.pikchillytechnologies.tagtaxiservice.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pikchillytechnologies.tagtaxiservice.R;
import com.pikchillytechnologies.tagtaxiservice.helperfile.HelperFile;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {

    // Variable Declaration
    @BindView(R.id.textView_PhoneNumber)
    TextInputEditText mPhone_TextInputEditText;
    private HelperFile mHelperFile;
    private String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);

        // Initialize Variables
        mHelperFile = new HelperFile();

    }

    @OnClick(R.id.button_SignIn)
    public void onSignInClick() {

        // Get the phone number entered by user in the Phone field
        mPhone = String.valueOf(mPhone_TextInputEditText.getText());

        // Check if the phone number entered is of 10 digits else show message
        if (mPhone.length() == 10) {

            mHelperFile.screenIntent(SignInActivity.this, VerificationActivity.class, mPhone);

        } else {

            mHelperFile.screenToast(SignInActivity.this, R.string.valid_phone_msg, Toast.LENGTH_LONG);
        }
    }
}
