package com.pikchillytechnologies.tagtaxiservice.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pikchillytechnologies.tagtaxiservice.R;
import com.pikchillytechnologies.tagtaxiservice.helperfile.HelperFile;
import com.google.firebase.auth.FirebaseAuth;
import com.pikchillytechnologies.tagtaxiservice.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends FragmentActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.textView_ScreenTitle)
    TextView mScreenTitle_TextView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.button_Back)
    Button mBackButton;

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private LatLng userLocation;
    private Location mLastKnownLocation;
    private GoogleMap mMap;
    private HelperFile mHelperFile;
    private Bundle mUserBundle;
    private String mUserPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        mUserBundle = getIntent().getExtras();
        mUserPhoneNumber = mUserBundle.getString("Phone");

        Toast.makeText(HomeActivity.this,"Welcome:" + mUserPhoneNumber,Toast.LENGTH_LONG).show();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mHelperFile = new HelperFile();
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Call function to Set values
        setValues();

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

    // Action to perform when Book Ride is clicked
    @OnClick(R.id.button_BookRide)
    public void onBookRideClick(){
        mHelperFile.screenIntent(HomeActivity.this, BookRideActivity.class,mUserPhoneNumber);
    }

    // Action to perform when Booking status is clicked
    @OnClick(R.id.button_BookingStatus)
    public void onBookingStatusClick(){
        mHelperFile.screenIntent(HomeActivity.this, BookingStatusActivity.class);
    }

    /**
     * Function to take action based on users allow / deny of service
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                }
            }
        }
    }

    /**
     * Function added to get the menu item selected
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Call Navigate function for the item selected
        mHelperFile.menuOptionSelected(item, HomeActivity.this);

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
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        //mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // Listen to location changes of user
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                // Add a marker in Sydney and move the camera
                userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                // Clear map of any other Marker
                mMap.clear();

                // Add Marker with current location of user
                mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

                Log.i("Provider:", provider + "-" + status);
            }

            @Override
            public void onProviderEnabled(String provider) {

                Log.i("Provider Enabled:", provider);
            }

            @Override
            public void onProviderDisabled(String provider) {

                Log.i("Provider Disabled:", provider);
            }
        };

        // Check for permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Ask for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

            mLastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            userLocation = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
        }

        if (Build.VERSION.SDK_INT < 23) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        }
    }

    /**
     * Function to Set Values in views
     */
    public void setValues() {
        mBackButton.setVisibility(View.INVISIBLE);
        mNavigationView.setNavigationItemSelectedListener(this);
        mScreenTitle_TextView.setText(R.string.screen_home);
    }
}
