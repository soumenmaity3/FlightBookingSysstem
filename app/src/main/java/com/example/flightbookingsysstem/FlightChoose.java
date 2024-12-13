package com.example.flightbookingsysstem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class FlightChoose extends AppCompatActivity {
    ArrayList<FlightModel> arrFlight = new ArrayList<>();

    @SuppressLint({"NotifyDataSetChanged","MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_choose);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainf), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent getPlace=getIntent();
        String startPlace=getPlace.getStringExtra("start");
        String destinationPlace=getPlace.getStringExtra("destination");

         TextView startFlight=findViewById(R.id.startFlight);
         TextView destinationFlight=findViewById(R.id.destinationFlight);

         startFlight.setText(startPlace);
         destinationFlight.setText(destinationPlace);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrFlight.add(new FlightModel(R.drawable.indigo,"Indigo  ",generateTickitNumber(), startPlace, destinationPlace, "2:30 PM", "6:00 PM"));
        arrFlight.add(new FlightModel(R.drawable.flight_fli,"Flight  ",generateTickitNumber(), startPlace, destinationPlace, "3:00 PM", "7:00 PM"));
        arrFlight.add(new FlightModel(R.drawable.airindia,"Airindia  ",generateTickitNumber(), startPlace, destinationPlace, "3:00 PM", "7:00 PM"));
        arrFlight.add(new FlightModel(R.drawable.delta_logo,"Delta  ",generateTickitNumber(), startPlace, destinationPlace, "3:00 PM", "7:00 PM"));
        arrFlight.add(new FlightModel(R.drawable.jetstar,"Jetstar  ",generateTickitNumber(), startPlace, destinationPlace, "3:00 PM", "7:00 PM"));
        arrFlight.add(new FlightModel(R.drawable.delta_logo,"Delta  ",generateTickitNumber(), startPlace, destinationPlace, "3:00 PM", "7:00 PM"));
        arrFlight.add(new FlightModel(R.drawable.jetblue,"Jetblue  ",generateTickitNumber(), startPlace, destinationPlace, "3:00 PM", "7:00 PM"));
        arrFlight.add(new FlightModel(R.drawable.airasia,"Airasia  ",generateTickitNumber(), startPlace, destinationPlace, "3:00 PM", "7:00 PM"));

        FlightRecyclerAdapter adapter = new FlightRecyclerAdapter(this, arrFlight);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public static String generateTickitNumber() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        // Generate 4 random digits for OTP
        for (int i = 0; i < 10; i++) {
            otp.append(random.nextInt(10)); // Append a random digit (0-9)
        }

        return otp.toString(); // Return OTP as a string
    }
}
