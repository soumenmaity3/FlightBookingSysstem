package com.example.flightbookingsysstem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.Instant;
import java.util.ArrayList;

public class SeatChoose extends AppCompatActivity {
    TextView start, destination;
    private GridLayout seatGrid;
    private ArrayList<String> selectedSeats = new ArrayList<>();
    private Button confirmButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_choose);

        seatGrid = findViewById(R.id.seatGrid);
        confirmButton = findViewById(R.id.btnConfirm);
        start = findViewById(R.id.startFlight2);
        destination = findViewById(R.id.destinationFlight2);

        Intent getData = getIntent();
        String startPlace = getData.getStringExtra("StartP");
        String destinationPlace = getData.getStringExtra("DestinationP");
        String flightName = getData.getStringExtra("FlightName");
        String flightNumber = getData.getStringExtra("FlightNumber");
        start.setText(startPlace);
        destination.setText(destinationPlace);

        setupSeats();

        confirmButton.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(SeatChoose.this, "Please select at least one seat", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SeatChoose.this, "Seats Confirmed: " + selectedSeats, Toast.LENGTH_LONG).show();
                Intent inext = new Intent(SeatChoose.this, FromFillUp.class);
                int lengthSeat = selectedSeats.size();
                inext.putExtra("TotalSeat", lengthSeat);
                inext.putExtra("seatNo", selectedSeats);
                inext.putExtra("Start", startPlace);
                inext.putExtra("FlightName", flightName);
                inext.putExtra("FlightNumber", flightNumber);
                inext.putExtra("TotalSeat", selectedSeats.size());
                inext.putStringArrayListExtra("Seat", selectedSeats);
                inext.putExtra("Destination", destinationPlace);
                startActivity(inext);
            }
        });
    }


    private void setupSeats() {
        int totalSeats = 30;
        int seatsPerRow = 6;

        for (int i = 1; i <= totalSeats; i++) {
            Button seatButton = new Button(this);
            seatButton.setText("S" + i);
            seatButton.setTag(false); // Initially, the seat is not selected
            seatButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

            seatButton.setOnClickListener(v -> {
                boolean isSelected = (boolean) seatButton.getTag();
                if (isSelected) {
                    seatButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    selectedSeats.remove(seatButton.getText().toString());
                } else {
                    seatButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    selectedSeats.add(seatButton.getText().toString());
                }
                seatButton.setTag(!isSelected);
            });

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec((i - 1) % seatsPerRow, 1f);
            params.rowSpec = GridLayout.spec((i - 1) / seatsPerRow);
            params.setMargins(10, 10, 10, 10);
            seatGrid.addView(seatButton, params);
        }
    }
}
