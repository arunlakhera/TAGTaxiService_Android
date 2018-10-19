package com.pikchillytechnologies.tagtaxiservice.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

public class BookingStatusActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.textView_ScreenTitle)
    TextView mScreenTitle_TextView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.recyclerView_BookingStatus)
    RecyclerView mBookingRecyclerView;

    private Bundle mUserBundle;
    private String mUserPhoneNumber;
    private HelperFile mHelperFile;
    ArrayList<Booking> mUserBookings;
    BookingStatusAdapter mBookingAdapter;

    private FirebaseRef mDatabaseRef;
    private DatabaseReference mUserBookingRef;
    private DatabaseReference mBookingRef;
    private String mBookingId;
    Booking mBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_status);

        ButterKnife.bind(this);
        mHelperFile = new HelperFile();

        mUserBundle = getIntent().getExtras();

        mDatabaseRef = new FirebaseRef();
        mUserBookingRef = mDatabaseRef.getmUserBookingRef();
        mBookingRef = mDatabaseRef.getmBookingRef();

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

        mUserBookings = new ArrayList<>();

        mUserBookingRef.child(mUserPhoneNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userBookingSnapshot) {

                if(userBookingSnapshot.getChildrenCount() < 1){
                    Toast.makeText(BookingStatusActivity.this,"No User Bookings ", Toast.LENGTH_LONG).show();

                }else{

                    for(DataSnapshot userBookingList : userBookingSnapshot.getChildren()){

                        mBookingId = userBookingList.getKey();

                        if(mBookingId != null){

                            mBookingRef.child(mBookingId).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    mBooking = dataSnapshot.getValue(Booking.class);

                                    mUserBookings.add(new Booking(mBookingId,mBooking.getmUserPhoneNumber(),mBooking.getmPickupAddress(),mBooking.getmDropAddress(),mBooking.getmNumberOfPassengers(),mBooking.getmRoundTrip(),mBooking.getmTravellingOnDate(),mBooking.getmReturningOnDate(),mBooking.getmVehicleType(),mBooking.getmPickupTime(),mBooking.getmBookingStatus(),mBooking.getmReason()));
                                    mBookingAdapter = new BookingStatusAdapter(mUserBookings);

                                    // Attach the adapter to the recyclerview to populate items
                                    mBookingRecyclerView.setAdapter(mBookingAdapter);

                                    // Set layout manager to position the items
                                    mBookingRecyclerView.setLayoutManager(new LinearLayoutManager(BookingStatusActivity.this));

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                    Log.e("TAGUserBookingError:", databaseError.getDetails());
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAGUserBookingError:", databaseError.getDetails());
            }
        });
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
