package com.pikchillytechnologies.tagtaxiservice.helperfile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.pikchillytechnologies.tagtaxiservice.R;
import com.pikchillytechnologies.tagtaxiservice.activity.BookRideActivity;
import com.pikchillytechnologies.tagtaxiservice.activity.BookingStatusActivity;
import com.pikchillytechnologies.tagtaxiservice.activity.HomeActivity;
import com.pikchillytechnologies.tagtaxiservice.activity.MyProfileActivity;
import com.pikchillytechnologies.tagtaxiservice.activity.SettingsActivity;
import com.pikchillytechnologies.tagtaxiservice.activity.SignInActivity;

public class HelperFile {

    /**
     * Function for Intent to navigate user from one screen to another
     * */
    public void screenIntent(Context context, Class<?> c){

        context.startActivity(new Intent(context, c));
    }

    /**
     * Function for Intent to navigate user from one screen to another with phone number
     */
    public void screenIntent(Context context, Class<?> c,String phone){

        Intent nextIntent = new Intent(context, c);
        nextIntent.putExtra("Phone",String.valueOf(phone));
        context.startActivity(nextIntent);
    }

    /**
     * Function for Toast Message
     * */
    public void screenToast(Context context,Integer msg,Integer duration){

        Toast.makeText(context,msg,duration).show();
    }

    /**
     * Function to Navigate to Menu item selected
     * */
    public void menuOptionSelected(MenuItem item, Context c, String userPhoneNumber){

        switch (item.getItemId()) {

            case R.id.item_book_home:
                screenIntent(c, HomeActivity.class,userPhoneNumber);
                break;
            case R.id.item_book_ride:
                screenIntent(c, BookRideActivity.class,userPhoneNumber);
                break;
            case R.id.item_booking_status:
                screenIntent(c, BookingStatusActivity.class,userPhoneNumber);
                break;
            case R.id.item_my_profile:
                screenIntent(c, MyProfileActivity.class,userPhoneNumber);
                break;
            case R.id.item_settings:
                screenIntent(c, SettingsActivity.class,userPhoneNumber);
                break;
            case R.id.item_logout:
                FirebaseAuth.getInstance().signOut();
                screenIntent(c, SignInActivity.class,userPhoneNumber);
                break;

        }
    }
}
