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

import com.google.firebase.auth.FirebaseAuth;
import com.pikchillytechnologies.tagtaxiservice.R;
import com.pikchillytechnologies.tagtaxiservice.adapter.BookingStatusAdapter;
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
        }
        
        mNavigationView.setNavigationItemSelectedListener(this);
        mScreenTitle_TextView.setText(R.string.screen_booking_status);

        RecyclerView rvBooking = findViewById(R.id.recyclerView_BookingStatus);
        //Booking b1 = new Booking("Pickup", "Drop", "2", "Yes", "01-01-0001", "02-02-0002", "SUV", "10am", "Pending","");
        mUserBookings = new ArrayList<>();
        mUserBookings.add(new Booking("Pickup", "Drop", "2", "Yes", "01-01-0001", "02-02-0002", "SUV", "10am", "Pending",""));

        BookingStatusAdapter adapter = new BookingStatusAdapter(mUserBookings);
        // Attach the adapter to the recyclerview to populate items
        rvBooking.setAdapter(adapter);
        // Set layout manager to position the items
        rvBooking.setLayoutManager(new LinearLayoutManager(this));

    }

    @OnClick(R.id.button_Back)
    public void onBackButtonClick(){
        mHelperFile.screenIntent(BookingStatusActivity.this, HomeActivity.class,mUserPhoneNumber);
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
