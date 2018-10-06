package com.pikchillytechnologies.tagtaxiservice.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pikchillytechnologies.tagtaxiservice.R;
import com.pikchillytechnologies.tagtaxiservice.adapter.BookingStatusAdapter;
import com.pikchillytechnologies.tagtaxiservice.helperfile.FirebaseRef;
import com.pikchillytechnologies.tagtaxiservice.helperfile.HelperFile;
import com.pikchillytechnologies.tagtaxiservice.model.Booking;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookingStatusActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    @BindView(R.id.textView_ScreenTitle)
    TextView mScreenTitle_TextView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    private Bundle mUserBundle;
    private String mUserPhoneNumber;
    private HelperFile mHelperFile;
    ArrayList<Booking> mUserBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_status);

        ButterKnife.bind(this);
        mHelperFile = new HelperFile();

        mUserBundle = getIntent().getExtras();

        if(mUserBundle != null){
            mUserPhoneNumber = mUserBundle.getString("Phone");
        }else{
            Toast.makeText(BookingStatusActivity.this,"No Phone Number Try Again!", Toast.LENGTH_LONG).show();
        }

        mNavigationView.setNavigationItemSelectedListener(this);
        mScreenTitle_TextView.setText(R.string.screen_booking_status);

        updateUI();

    }

    public void updateUI(){

        RecyclerView mBookingRecyclerView = findViewById(R.id.recyclerView_BookingStatus);
        
        mUserBookings = new ArrayList<>();

        mUserBookings.add(new Booking(mUserPhoneNumber,"1101 Ashok Nagar, New Delhi", "Rajendra Nagar, Dehradun", "2", "Yes", "01-10-2018", "02-10-2018", "Sedan - Toyota Etios", "10am", "Pending",""));
        mUserBookings.add(new Booking(mUserPhoneNumber,"1101 Ashok Nagar, New Delhi", "Rajendra Nagar, Dehradun", "2", "Yes", "10-10-2018", "12-10-2018", "SUV - Toyota Innova", "10am", "Pending",""));
        mUserBookings.add(new Booking(mUserPhoneNumber,"1101 Ashok Nagar, New Delhi", "Rajendra Nagar, Dehradun", "2", "Yes", "21-10-2018", "22-10-2018", "Small - Maruti Ritz", "10am", "Pending",""));
        mUserBookings.add(new Booking(mUserPhoneNumber,"1101 Ashok Nagar, New Delhi", "Rajendra Nagar, Dehradun", "2", "Yes", "01-11-2018", "02-11-2018", "SUV", "10am", "Pending",""));

        BookingStatusAdapter mBookingAdapter = new BookingStatusAdapter(mUserBookings);
        // Attach the adapter to the recyclerview to populate items
        mBookingRecyclerView.setAdapter(mBookingAdapter);

        // Set layout manager to position the items
        mBookingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.button_Back)
    public void onBackButtonClick(){
        mHelperFile.screenIntent(BookingStatusActivity.this, HomeActivity.class, mUserPhoneNumber);
    }

    @OnClick(R.id.button_Menu)
    public void onMenuClick(){
        if (mDrawer.isDrawerOpen(Gravity.START)) {
            mDrawer.closeDrawer(Gravity.START);
        } else {
            mDrawer.openDrawer(Gravity.START);
        }
    }

    /**
     * Function added to get the menu item selected
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Call Navigate function for the item selected
        mHelperFile.menuOptionSelected(item,BookingStatusActivity.this);

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
