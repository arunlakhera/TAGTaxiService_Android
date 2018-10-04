package com.pikchillytechnologies.tagtaxiservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pikchillytechnologies.tagtaxiservice.R;
import com.pikchillytechnologies.tagtaxiservice.model.Booking;

import org.w3c.dom.Text;

import java.util.List;

public class BookingStatusAdapter extends RecyclerView.Adapter<BookingStatusAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTravellingOnDateTextView;
        public TextView mReturningOnDateTextView;
        public TextView mStatusTextView;
        public TextView mPickupAddress;
        public TextView mDropAddress;

        public ViewHolder(View itemView){
            super(itemView);

            mTravellingOnDateTextView = itemView.findViewById(R.id.textView_TravellingOnDate);
            mReturningOnDateTextView = itemView.findViewById(R.id.textView_ReturningOnDate);
            mStatusTextView = itemView.findViewById(R.id.textView_Status);
            mPickupAddress = itemView.findViewById(R.id.textView_PickupAddress);
            mDropAddress = itemView.findViewById(R.id.textView_DropAddress);

        }
    }

    private List<Booking> mBooking;

    public BookingStatusAdapter(List<Booking> booking){
        mBooking = booking;
    }

    @Override
    public BookingStatusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View bookingView = inflater.inflate(R.layout.booking_status_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(bookingView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // Get the data model based on position
        Booking booking = mBooking.get(position);

        // Set item views based on your views and data model
        TextView travellingOnDateTextView = holder.mTravellingOnDateTextView;
        travellingOnDateTextView.setText(booking.getmTravellingOnDate());

        TextView returningOnDateTextView = holder.mReturningOnDateTextView;
        returningOnDateTextView.setText(booking.getmReturningOnDate());

        TextView statusTextView = holder.mStatusTextView;
        statusTextView.setText(booking.getmBookingStatus());

        TextView pickupAddress = holder.mPickupAddress;
        pickupAddress.setText(booking.getmPickupAddress());

        TextView dropAddress = holder.mDropAddress;
        dropAddress.setText(booking.getmDropAddress());

    }

    @Override
    public int getItemCount() {
        return mBooking.size();
    }
}
