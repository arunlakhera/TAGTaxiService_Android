package com.pikchillytechnologies.tagtaxiservice.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.pikchillytechnologies.tagtaxiservice.R;
import com.pikchillytechnologies.tagtaxiservice.helperfile.HelperFile;
import com.pikchillytechnologies.tagtaxiservice.model.Booking;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookingDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.textView_ScreenTitle)
    TextView mScreenTitle_TextView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.textView_NameDetail)
    TextView mNameTextView;
    @BindView(R.id.textView_SourceAddressDetail)
    TextView mSourceAddressTextView;
    @BindView(R.id.textView_DestinationAddressDetail)
    TextView mDestinationAddressTextView;
    @BindView(R.id.textView_TravelDateDetail)
    TextView mTravelDateDetailTextView;
    @BindView(R.id.textView_ReturnDateDetail)
    TextView mReturnDateDetailTextView;
    @BindView(R.id.textView_PassengersDetail)
    TextView mPassengersDetailTextView;
    @BindView(R.id.textView_RoundTripDetail)
    TextView mRoundTripDetailTextView;
    @BindView(R.id.textView_PhoneNumberDetail)
    TextView mPhoneNumberTextView;
    @BindView(R.id.textView_ReturnOnLabel)
    TextView mReturnOnLabelTextView;

    private HelperFile mHelperFile;
    private Booking mBookingDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        ButterKnife.bind(this);
        mHelperFile = new HelperFile();

        mBookingDetail = getIntent().getParcelableExtra("booking");

        mNavigationView.setNavigationItemSelectedListener(this);
        mScreenTitle_TextView.setText(R.string.screen_booking_detail);

        View mMenuHeader;
        mMenuHeader = mNavigationView.getHeaderView(0);
        ((TextView) mMenuHeader.findViewById(R.id.textView_Phone_Number_Nav)).setText(mBookingDetail.getmUserPhoneNumber());

        updateUI();
    }

    // Update User Interface
    public void updateUI(){

        mNameTextView.setText("GUEST");
        mSourceAddressTextView.setText(mBookingDetail.getmPickupAddress());
        mDestinationAddressTextView.setText(mBookingDetail.getmDropAddress());

        if(mBookingDetail.getmRoundTrip().equals("Yes")){
            mReturnOnLabelTextView.setVisibility(View.VISIBLE);
            mReturnDateDetailTextView.setVisibility(View.VISIBLE);
            mReturnDateDetailTextView.setText(mBookingDetail.getmReturningOnDate());
        }else{
            mReturnOnLabelTextView.setVisibility(View.INVISIBLE);
            mReturnDateDetailTextView.setVisibility(View.INVISIBLE);
        }

        mTravelDateDetailTextView.setText(mBookingDetail.getmTravellingOnDate());

        mPassengersDetailTextView.setText(mBookingDetail.getmNumberOfPassengers());
        mRoundTripDetailTextView.setText(mBookingDetail.getmRoundTrip());
        mPhoneNumberTextView.setText(mBookingDetail.getmUserPhoneNumber());

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
        mHelperFile.screenIntent(BookingDetailActivity.this, BookingStatusActivity.class, mBookingDetail.getmUserPhoneNumber());
    }

    /**
     * Function added to get the menu item selected
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Call Navigate function for the item selected
        mHelperFile.menuOptionSelected(item,BookingDetailActivity.this,mBookingDetail.getmUserPhoneNumber());

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