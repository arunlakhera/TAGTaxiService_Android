package com.pikchillytechnologies.tagtaxiservice.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pikchillytechnologies.tagtaxiservice.R;
import com.pikchillytechnologies.tagtaxiservice.helperfile.FirebaseRef;
import com.pikchillytechnologies.tagtaxiservice.helperfile.HelperFile;
import com.pikchillytechnologies.tagtaxiservice.model.Booking;
import com.pikchillytechnologies.tagtaxiservice.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.textView_ScreenTitle)
    TextView mScreenTitle_TextView;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.imageView_User_Image)
    ImageView mUserImage;
    @BindView(R.id.textView_UploadPhoto)
    TextView mUploadPhoto;

    @BindView(R.id.textView_UserName)
    TextInputEditText mUserNameTextView;
    @BindView(R.id.textView_Phone)
    TextInputEditText mPhoneTextView;
    @BindView(R.id.textView_Gender)
    TextInputEditText mGenderTextView;
    @BindView(R.id.textView_EmailId)
    TextInputEditText mEmailIdTextView;
    @BindView(R.id.textView_Address)
    TextInputEditText mAddressTextView;
    @BindView(R.id.textView_City)
    TextInputEditText mCityTextView;
    @BindView(R.id.textView_State)
    TextInputEditText mStateTextView;

    private HelperFile mHelperFile;
    private Bundle mUserBundle;
    private String mUserPhoneNumber;
    private FirebaseRef mFirebaseRef;
    private DatabaseReference mUserProfileRef;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        ButterKnife.bind(this);

        mHelperFile = new HelperFile();
        mUserBundle = getIntent().getExtras();
        mFirebaseRef = new FirebaseRef();

        if(mUserBundle != null){
            mUserPhoneNumber = mUserBundle.getString("Phone");
        }

        mUserProfileRef = mFirebaseRef.getmUserRef();

        mNavigationView.setNavigationItemSelectedListener(this);
        mScreenTitle_TextView.setText(R.string.screen_my_profile);

        View mMenuHeader;
        mMenuHeader = mNavigationView.getHeaderView(0);
        ((TextView) mMenuHeader.findViewById(R.id.textView_Phone_Number_Nav)).setText(mUserPhoneNumber);

        updateUI();
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Toast.makeText(this,"USER:" + mFirebaseRef.getmCurrentUser().getPhoneNumber(), Toast.LENGTH_LONG).show();

    }

    //Function to Update User Views with initial Values
    public void updateUI(){

       mUserProfileRef.child(mUserPhoneNumber).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot userDataSnapshot) {

               mUser = userDataSnapshot.getValue(User.class);
               mUserNameTextView.setText(mUser.getmName());
               mPhoneTextView.setText(mUser.getmPhoneNumber());
               mGenderTextView.setText(mUser.getmGender());
               mEmailIdTextView.setText(mUser.getmEmailId());
               mAddressTextView.setText(mUser.getmAddress());
               mCityTextView.setText(mUser.getmCity());
               mStateTextView.setText(mUser.getmState());

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               Log.d("TAGUSERERROR",databaseError.getDetails());
           }
       });
    }

    // Save Changes
    @OnClick(R.id.button_Save)
    public void onSaveButtonClick(){

        mUserProfileRef.child(mUserPhoneNumber).child("mName").setValue("Arun Sharma");
        mUserProfileRef.child(mUserPhoneNumber).child("mGender").setValue("Male");
        mUserProfileRef.child(mUserPhoneNumber).child("mEmailId").setValue("youremail@gmail.com");
        mUserProfileRef.child(mUserPhoneNumber).child("mAddress").setValue("25 Rajendra Nagar");
        mUserProfileRef.child(mUserPhoneNumber).child("mCity").setValue("Dehradun");
        mUserProfileRef.child(mUserPhoneNumber).child("mState").setValue("Uttarakhand");
        

    }

    // Action to perform when Menu button is pressed
    @OnClick(R.id.button_Menu)
    public void onMenuClick(){

        if (mDrawer.isDrawerOpen(Gravity.START)) {
            mDrawer.closeDrawer(Gravity.START);
        } else {
            mDrawer.openDrawer(Gravity.START);
        }
    }

    // Action to perform when back button is pressed
    @OnClick(R.id.button_Back)
    public void onBackButtonClick(View v) {
        mHelperFile.screenIntent(MyProfileActivity.this, HomeActivity.class,mUserPhoneNumber);
    }

    /**
     * Function added to get the menu item selected
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Call Navigate function for the item selected
        mHelperFile.menuOptionSelected(item,MyProfileActivity.this, mUserPhoneNumber);

        // Close the menu once any option is selected by user
        mDrawer.closeDrawer(Gravity.START);

        return true;
    }

    /**
     * Function to close the Menu when back button on mobile is pressed
     */
    @Override
    public void onBackPressed() {

        if (mDrawer.isDrawerOpen(Gravity.START)) {
            mDrawer.closeDrawer(Gravity.START);
        } else {
            super.onBackPressed();
        }
    }
}