package com.pikchillytechnologies.tagtaxiservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pikchillytechnologies.tagtaxiservice.R;
import com.pikchillytechnologies.tagtaxiservice.activity.BookingDetailActivity;
import com.pikchillytechnologies.tagtaxiservice.model.Booking;

import java.util.List;

public class BookingStatusAdapter extends RecyclerView.Adapter<BookingStatusAdapter.ViewHolder> {

    private List<Booking> mBooking;
    Context context;
    Booking booking;

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTravelReturnDateTextView;
        private TextView mPickupAddress;
        private TextView mDropAddress;
        private TextView mVehicleTypeTextView;
        private TextView mStatusTextView;
        LinearLayout layoutParent;

        private ViewHolder(View itemView){
            super(itemView);

            mTravelReturnDateTextView = itemView.findViewById(R.id.textView_TravelReturnDate);
            mPickupAddress = itemView.findViewById(R.id.textView_PickupAddress);
            mDropAddress = itemView.findViewById(R.id.textView_DropAddress);
            mVehicleTypeTextView = itemView.findViewById(R.id.textView_VehicleType);
            mStatusTextView = itemView.findViewById(R.id.textView_Status);
            layoutParent = itemView.findViewById(R.id.layout_Parent);

        }
    }

    public BookingStatusAdapter(List<Booking> booking){

        mBooking = booking;
    }

    @Override
    public BookingStatusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View bookingView = inflater.inflate(R.layout.booking_status_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(bookingView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // Get the data model based on position
        booking = mBooking.get(position);
        String travelDate;

        // Set item views based on your views and data model
        TextView travellingOnDateTextView = holder.mTravelReturnDateTextView;

        travelDate = booking.getmTravellingOnDate() + " To " + booking.getmReturningOnDate();

        travellingOnDateTextView.setText(travelDate);

        TextView pickupAddress = holder.mPickupAddress;
        pickupAddress.setText(booking.getmPickupAddress());

        TextView dropAddress = holder.mDropAddress;
        dropAddress.setText(booking.getmDropAddress());

        TextView vehicleType = holder.mVehicleTypeTextView;
        vehicleType.setText(booking.getmVehicleType());

        TextView statusTextView = holder.mStatusTextView;
        statusTextView.setText(booking.getmBookingStatus());

        holder.layoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"You clicked:" + position,Toast.LENGTH_LONG).show();

                Intent bookingDetailIntent = new Intent(context, BookingDetailActivity.class);
                context.startActivity(bookingDetailIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mBooking != null && mBooking.size() > 0){
            return mBooking.size();
        }else{
            return 0;
        }
    }
}
