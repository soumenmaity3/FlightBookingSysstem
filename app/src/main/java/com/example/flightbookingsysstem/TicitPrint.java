package com.example.flightbookingsysstem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class TicitPrint extends AppCompatActivity {
TextView start,destination,mobile,ticNumber,name,flightName4,flightNumber4,seatNo1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(TicitPrint.this);
        setContentView(R.layout.activity_ticit_print);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainT), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        start=findViewById(R.id.forStart);
        destination=findViewById(R.id.forDestination);
        mobile=findViewById(R.id.mobileNumber);
        ticNumber=findViewById(R.id.ticketNumber);
        name=findViewById(R.id.fullName);
        flightName4=findViewById(R.id.flightName3);
        flightNumber4=findViewById(R.id.flightNumber3);
        seatNo1=findViewById(R.id.seatNo);

        Intent iData=getIntent();
        ArrayList<String> seat=getIntent().getStringArrayListExtra("SeatNo");
        String from=iData.getStringExtra("Start");
        String to=iData.getStringExtra("Destination");
        String fullName=iData.getStringExtra("fullName");
        String mobileNo=iData.getStringExtra("Mobile");
        String flightName3=iData.getStringExtra("FlightName");
        String flightNumber3=iData.getStringExtra("FlightNumber");

        flightNumber4.setText(flightNumber3);
        flightName4.setText(flightName3);
        start.setText(from);
        destination.setText(to);
        mobile.setText(mobileNo);
        name.setText(fullName);
        String tNumber=generateTickitNumber();
        ticNumber.setText(tNumber);
        if (seat != null) {
            String seatText = String.join(", ", seat); // Combine ArrayList into a single string
            seatNo1.setText(seatText);
        } else {
            seatNo1.setText("No seats selected");
        }

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