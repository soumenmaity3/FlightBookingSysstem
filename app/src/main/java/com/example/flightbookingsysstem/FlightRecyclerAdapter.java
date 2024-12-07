package com.example.flightbookingsysstem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FlightRecyclerAdapter extends RecyclerView.Adapter<FlightRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<FlightModel> arrayList;

    public FlightRecyclerAdapter(Context context, ArrayList<FlightModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FlightModel flight = arrayList.get(position);

        holder.flightLogo.setImageResource(flight.getFlightLogo());
        holder.start.setText(flight.getStart());
        holder.destination.setText(flight.getDestination());
        holder.startTime.setText(flight.getStartTime());
        holder.endTime.setText(flight.getEndTime());
        holder.flightName.setText(flight.getFlightName());
        holder.flightInformation.setText(flight.getFlightNumber());
        holder.flightRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SeatChoose.class);
                intent.putExtra("StartP",flight.getStart());
                intent.putExtra("DestinationP",flight.getDestination());
                intent.putExtra("FlightName",flight.getFlightName());
                intent.putExtra("FlightNumber",flight.flightNumber);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView flightLogo;
        TextView start, destination, startTime, endTime,flightInformation,flightName;
        LinearLayout flightRow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            flightLogo = itemView.findViewById(R.id.flightLogo);
            start = itemView.findViewById(R.id.flightStart);
            destination = itemView.findViewById(R.id.flightDestination);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            flightRow = itemView.findViewById(R.id.flightRow);
            flightInformation=itemView.findViewById(R.id.flightInformation);
            flightName=itemView.findViewById(R.id.flightName);
        }
    }
}
