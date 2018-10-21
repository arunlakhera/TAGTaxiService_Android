package com.pikchillytechnologies.tagtaxiservice.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pikchillytechnologies.tagtaxiservice.R;
import com.pikchillytechnologies.tagtaxiservice.helperfile.FirebaseRef;
import com.pikchillytechnologies.tagtaxiservice.helperfile.HelperFile;
import com.pikchillytechnologies.tagtaxiservice.model.Booking;

import java.util.Calendar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookRideActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.textView_ScreenTitle)
    TextView mScreenTitle_TextView;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.editText_PickupAddress)
    EditText mPickupAddressEditText;
    @BindView(R.id.editText_DropAddress)
    EditText mDropAddressEditText;

    @BindView(R.id.button_Yes)
    Button mYesButton;
    @BindView(R.id.button_No)
    Button mNoButton;
    @BindView(R.id.button_AM)
    Button mAMButton;
    @BindView(R.id.button_PM)
    Button mPMButton;
    @BindView(R.id.button_Small)
    Button mSmallButton;
    @BindView(R.id.button_Sedan)
    Button mSedanButton;
    @BindView(R.id.button_Suv)
    Button mSuvButton;
    @BindView(R.id.button_Pass_1)
    Button mPassenger_1;
    @BindView(R.id.button_Pass_2)
    Button mPassenger_2;
    @BindView(R.id.button_Pass_3)
    Button mPassenger_3;
    @BindView(R.id.button_Pass_4)
    Button mPassenger_4;
    @BindView(R.id.button_Pass_5)
    Button mPassenger_5;
    @BindView(R.id.button_Pass_6)
    Button mPassenger_6;
    @BindView(R.id.spinner_time)
    Spinner mTimeSpinner;
    @BindView(R.id.button_ReturningOnDate)
    Button mReturningOnDateButton;
    @BindView(R.id.button_TravellingOnDate)
    Button mTravellingOnDateButton;
    @BindView(R.id.textView_ReturningOn)
    TextView mReturningOnTextView;

    private HelperFile mHelperFile;
    private List<String> mTime;
    private ArrayAdapter<String> mTimeAdapter;
    private String mTimeSelected;

    private String mPickupAddress;
    private String mDropAddress;
    private String mNumberOfPassengers;
    private String mRoundTrip;
    private String mTravellingOnDate;
    private String mReturningOnDate;
    private String mVehicleType;
    private String mPickupTime;
    private String mAMPM;
    private Bundle mUserBundle;
    private String mUserPhoneNumber;
    private String mBookingId;

    private FirebaseRef mFirebaseRef;
    private DatabaseReference mBookingRef;
    private DatabaseReference mUserBookingRef;
    private Booking mBooking;
    private Boolean mBookingFlag;
    private Boolean mTravelDateFlag;
    private Boolean mReturnDateFlag;
    private String mBookingStatus;
    private String mReason;

    private DatePicker datePicker;
    private Calendar calendar;
    private int mCurrentYear;
    private int mCurrentMonth;
    private int mCurrentDay;
    Boolean saveFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ride);

        ButterKnife.bind(this);
        mHelperFile = new HelperFile();
        mFirebaseRef = new FirebaseRef();
        mBookingFlag = false;
        mRoundTrip = getResources().getString(R.string.yes);
        mTravelDateFlag = false;
        mReturnDateFlag = false;
        mBookingStatus = getResources().getString(R.string.pending_status);
        mReason = "";
        mAMPM = "";
        mNumberOfPassengers = "1";
        mPassenger_1.setBackgroundResource(R.drawable.btn_bg_green_round);
        mPassenger_1.setTextColor(getResources().getColor(R.color.colorWhite));

        mUserBundle = getIntent().getExtras();

        if (mUserBundle != null) {
            mUserPhoneNumber = mUserBundle.getString("Phone");
        }else{
            Toast.makeText(BookRideActivity.this,"No Phone Number Try Again!", Toast.LENGTH_LONG).show();
        }

        calendar = Calendar.getInstance();

        mCurrentYear = calendar.get(Calendar.YEAR);
        mCurrentMonth = calendar.get(Calendar.MONTH);
        mCurrentDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Spinner dropdown elements
        mTime = new ArrayList<>();

        View mMenuHeader;
        mMenuHeader = mNavigationView.getHeaderView(0);
        ((TextView) mMenuHeader.findViewById(R.id.textView_Phone_Number_Nav)).setText(mUserPhoneNumber);

        // Set the values
        setValues();

        for (int i = 1; i <= 12; i++) {
            mTime.add(String.valueOf(i));
        }

        mTimeAdapter = new ArrayAdapter<>(this, R.layout.spinner_time_item, mTime);
        mTimeSpinner.setAdapter(mTimeAdapter);

    }

    @OnClick(R.id.button_TravellingOnDate)
    public void onTravelDateClick() {
        setDate("TravelDate");
        mTravellingOnDate = String.valueOf(mTravellingOnDateButton.getText());
        setButton(mTravellingOnDateButton, R.drawable.btn_big_bg_green, Color.WHITE);

    }

    @OnClick(R.id.button_ReturningOnDate)
    public void onReturnDateClick() {
        setDate("ReturnDate");
        mReturningOnDate = String.valueOf(mReturningOnDateButton.getText());
        setButton(mReturningOnDateButton, R.drawable.btn_big_bg_green, Color.WHITE);
    }

    public void setDate(final String mDateButton) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                        if (mDateButton.equals("TravelDate")) {
                            mTravellingOnDateButton.setText(selectedDate);

                        } else if (mDateButton.equals("ReturnDate")) {
                            mReturningOnDateButton.setText(selectedDate);
                        }
                    }
                }, mCurrentYear, mCurrentMonth, mCurrentDay);
        datePickerDialog.show();
    }

    // Action to perform when Menu button is pressed
    @OnClick(R.id.button_Menu)
    public void onMenuClick() {

        if (mDrawer.isDrawerOpen(Gravity.START)) {
            mDrawer.closeDrawer(Gravity.START);
        } else {
            mDrawer.openDrawer(Gravity.START);
        }
    }

    // Action to perform when back button is pressed
    @OnClick(R.id.button_Back)
    public void onBackButtonClick() {
        mHelperFile.screenIntent(BookRideActivity.this, HomeActivity.class, mUserPhoneNumber);
    }

    // Action to perform when Book button is clicked
    @OnClick(R.id.button_Book)
    public void onBookButtonClick() {

        mPickupAddress = String.valueOf(mPickupAddressEditText.getText());
        mDropAddress = String.valueOf(mDropAddressEditText.getText());
        mTravellingOnDate = String.valueOf(mTravellingOnDateButton.getText());
        mReturningOnDate = String.valueOf(mReturningOnDateButton.getText());

        checkBookingData();

        if (mBookingFlag) {
            mHelperFile.screenToast(BookRideActivity.this, R.string.book_request, Toast.LENGTH_LONG);
            mHelperFile.screenIntent(BookRideActivity.this, BookingStatusActivity.class, mUserPhoneNumber);
        } else {
            mHelperFile.screenToast(BookRideActivity.this,R.string.booking_error,Toast.LENGTH_LONG);
        }
    }

    /**
     * Function to Update Booking in Firebase
     */
    public void checkBookingData() {

        saveFlag = true;

        if (mPickupAddress.trim().isEmpty() || mPickupAddress == null) {
            saveFlag = false;
        } else if (mDropAddress.trim().isEmpty() || mDropAddress == null) {
            saveFlag = false;
        } else if (mTravellingOnDate.isEmpty() || mTravellingOnDateButton == null) {
            saveFlag = false;
        } else if (mAMPM.isEmpty()) {
            saveFlag = false;
        }else if (mRoundTrip.equals(getResources().getString(R.string.yes))) {
            if (mReturningOnDate.isEmpty() || mReturningOnDate == null) {
                saveFlag = false;
            }
        }

        if (saveFlag) {
            updateFirebase();
        } else {
            mHelperFile.screenToast(BookRideActivity.this,R.string.booking_complete_error,Toast.LENGTH_LONG);
        }
    }

    public void updateFirebase() {

        try {
            mBookingId = String.valueOf(mFirebaseRef.getmBookingRef().push().getKey());

            // Save data in Booking table
            mBookingRef = mFirebaseRef.getmBookingRef().child(mBookingId);

            if(mRoundTrip.equals("No")){
                mReturningOnDate = "";
            }

            mBooking = new Booking(mBookingId,mUserPhoneNumber, mPickupAddress, mDropAddress, mNumberOfPassengers, mRoundTrip, mTravellingOnDate, mReturningOnDate, mVehicleType, mTimeSelected + mAMPM, mBookingStatus, mReason);
            mBookingRef.setValue(mBooking);

            // Save data in UserBooking Table
            mFirebaseRef.getmUserBookingRef().child(mUserPhoneNumber).child(mBookingId).setValue(getResources().getString(R.string.yes));

            mBookingFlag = true;
        } catch (Exception e) {
            Log.d("TagFirebaseError:", e.getLocalizedMessage());
            mBookingFlag = false;
        }
    }

    /**
     * Function to update Button appearance
     */
    public void updateButton(View view) {

        String buttonTag = String.valueOf(view.getTag());

        switch (buttonTag) {
            case "Yes":
                mRoundTrip = getResources().getString(R.string.yes);
                mTravelDateFlag = true;
                mReturnDateFlag = true;
                setButton(mYesButton, R.drawable.btn_big_bg_green, Color.WHITE);
                setButton(mNoButton, R.drawable.btn_big_bg_trans, Color.GRAY);
                mReturningOnDateButton.setEnabled(true);
                mReturningOnDateButton.setTextColor(Color.WHITE);
                mReturningOnDateButton.setAlpha((float) 1.0);
                mReturningOnTextView.setAlpha((float) 1.0);
                break;
            case "No":
                mRoundTrip = getResources().getString(R.string.no);
                mTravelDateFlag = true;
                mReturnDateFlag = false;
                mReturningOnDate = "";
                setButton(mNoButton, R.drawable.btn_big_bg_green, Color.WHITE);
                setButton(mYesButton, R.drawable.btn_big_bg_trans, Color.GRAY);
                mReturningOnDateButton.setEnabled(false);
                mReturningOnDateButton.setText(R.string.select_return_date);
                mReturningOnDateButton.setBackgroundResource(R.drawable.btn_big_bg_trans);
                mReturningOnDateButton.setTextColor(Color.DKGRAY);
                mReturningOnDateButton.setAlpha((float) 0.4);
                mReturningOnTextView.setAlpha((float) 0.4);
                break;
            case "Small":
                mVehicleType = getResources().getString(R.string.small);
                setButton(mSmallButton, R.drawable.btn_big_bg_green, Color.WHITE);
                setButton(mSedanButton, R.drawable.btn_big_bg_trans, Color.GRAY);
                setButton(mSuvButton, R.drawable.btn_big_bg_trans, Color.GRAY);
                break;
            case "Sedan":
                mVehicleType = getResources().getString(R.string.sedan);
                setButton(mSedanButton, R.drawable.btn_big_bg_green, Color.WHITE);
                setButton(mSmallButton, R.drawable.btn_big_bg_trans, Color.GRAY);
                setButton(mSuvButton, R.drawable.btn_big_bg_trans, Color.GRAY);
                break;
            case "Suv":
                mVehicleType = getResources().getString(R.string.suv);
                setButton(mSuvButton, R.drawable.btn_big_bg_green, Color.WHITE);
                setButton(mSmallButton, R.drawable.btn_big_bg_trans, Color.GRAY);
                setButton(mSedanButton, R.drawable.btn_big_bg_trans, Color.GRAY);
                break;
            case "AM":
                mAMPM = getResources().getString(R.string.am);
                setButton(mAMButton, R.drawable.btn_big_bg_green, Color.WHITE);
                setButton(mPMButton, R.drawable.btn_big_bg_trans, Color.GRAY);
                break;
            case "PM":
                mAMPM = getResources().getString(R.string.pm);
                setButton(mPMButton, R.drawable.btn_big_bg_green, Color.WHITE);
                setButton(mAMButton, R.drawable.btn_big_bg_trans, Color.GRAY);
                break;
            case "1":
                mNumberOfPassengers = getResources().getString(R.string.one);
                setButton(mPassenger_1, R.drawable.btn_bg_green_round, Color.WHITE);
                setButton(mPassenger_2, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_3, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_4, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_5, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_6, R.drawable.btn_bg_transp_round, Color.GRAY);
                break;
            case "2":
                mNumberOfPassengers = getResources().getString(R.string.two);
                setButton(mPassenger_1, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_2, R.drawable.btn_bg_green_round, Color.WHITE);
                setButton(mPassenger_3, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_4, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_5, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_6, R.drawable.btn_bg_transp_round, Color.GRAY);
                break;
            case "3":
                mNumberOfPassengers = getResources().getString(R.string.three);
                setButton(mPassenger_1, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_2, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_3, R.drawable.btn_bg_green_round, Color.WHITE);
                setButton(mPassenger_4, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_5, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_6, R.drawable.btn_bg_transp_round, Color.GRAY);
                break;
            case "4":
                mNumberOfPassengers = getResources().getString(R.string.four);
                setButton(mPassenger_1, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_2, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_3, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_4, R.drawable.btn_bg_green_round, Color.WHITE);
                setButton(mPassenger_5, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_6, R.drawable.btn_bg_transp_round, Color.GRAY);
                break;
            case "5":
                mNumberOfPassengers = getResources().getString(R.string.five);
                setButton(mPassenger_1, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_2, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_3, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_4, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_5, R.drawable.btn_bg_green_round, Color.WHITE);
                setButton(mPassenger_6, R.drawable.btn_bg_transp_round, Color.GRAY);
                break;
            case "6":
                mNumberOfPassengers = getResources().getString(R.string.six);
                setButton(mPassenger_1, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_2, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_3, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_4, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_5, R.drawable.btn_bg_transp_round, Color.GRAY);
                setButton(mPassenger_6, R.drawable.btn_bg_green_round, Color.WHITE);
                break;
        }
    }

    /**
     * Function to set background of button and text color
     */
    public void setButton(Button btn, Integer bgBtnID, Integer textColor) {

        btn.setBackgroundResource(bgBtnID);
        btn.setTextColor(textColor);

    }

    /**
     * Function added to get the menu item selected
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Call Navigate function for the item selected
        mHelperFile.menuOptionSelected(item, BookRideActivity.this, mUserPhoneNumber);

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

    /**
     * Function to set values
     */
    public void setValues() {

        mNavigationView.setNavigationItemSelectedListener(this);
        mScreenTitle_TextView.setText(R.string.screen_book_ride);
        mReturningOnDateButton.setEnabled(true);
        mTimeSpinner.setOnItemSelectedListener(this);
        mTimeSpinner.getBackground().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        mTimeSelected = String.valueOf(parent.getItemAtPosition(position));
        ((TextView) parent.getSelectedView()).setTextColor(getResources().getColor(R.color.colorWhite));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Log.i("TimeNotSelected", "Time was not selected");
    }
}
