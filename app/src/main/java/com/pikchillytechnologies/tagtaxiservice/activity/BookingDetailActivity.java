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

import com.google.firebase.auth.FirebaseAuth;
import com.pikchillytechnologies.tagtaxiservice.R;
import com.pikchillytechnologies.tagtaxiservice.helperfile.HelperFile;

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

    private HelperFile mHelperFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        ButterKnife.bind(this);
        mHelperFile = new HelperFile();

        mNavigationView.setNavigationItemSelectedListener(this);
        mScreenTitle_TextView.setText(R.string.screen_booking_detail);

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
        mHelperFile.screenIntent(BookingDetailActivity.this, BookingStatusActivity.class);
    }

    /**
     * Function added to get the menu item selected
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Call Navigate function for the item selected
        mHelperFile.menuOptionSelected(item,BookingDetailActivity.this);

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