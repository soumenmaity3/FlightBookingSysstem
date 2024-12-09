package com.example.flightbookingsysstem;

import static android.hardware.camera2.params.RggbChannelVector.RED;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class FromFillUp extends AppCompatActivity {
    TextView startp, destinationp, flightNumber, flightName;
    EditText fName, lName, mobileNo, otp;
    TextView price, seatNo, redText;
    int priceF = 5600;
    Button getOTP, ticit;
    String otp2, number;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_from_fill_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        startp = findViewById(R.id.startFlight1);
        destinationp = findViewById(R.id.destinationFlight1);
        fName = findViewById(R.id.passName);
        lName = findViewById(R.id.passLName);
        mobileNo = findViewById(R.id.passMobile);
        otp = findViewById(R.id.enterOTP);
        seatNo = findViewById(R.id.seatNo);
        price = findViewById(R.id.flightPrice);
        getOTP = findViewById(R.id.forOTP);
        ticit = findViewById(R.id.btnConfirmContinue);
        redText = findViewById(R.id.redText);
        flightNumber = findViewById(R.id.flightNumber2);
        flightName = findViewById(R.id.flightName2);

        Intent getData = getIntent();
        ArrayList<String> seat = getData.getStringArrayListExtra("Seat");
        int seatTotal = getData.getIntExtra("TotalSeat", 0);
        String start = getData.getStringExtra("Start");
        String destinationPlace = getData.getStringExtra("Destination");
        String flightName2 = getData.getStringExtra("FlightName");
        String flightNumber2 = getData.getStringExtra("FlightNumber");

        flightName.setText(flightName2);
        flightNumber.setText(flightNumber2);
        priceF = priceF * seatTotal;
        price.setText(String.valueOf(priceF));
        if (seat != null) {
            String seatText = String.join(", ", seat); // Combine ArrayList into a single string
            seatNo.setText(seatText);
        } else {
            seatNo.setText("No seats selected");
        }
        startp.setText(start != null ? start : "N/A");
        destinationp.setText(destinationPlace != null ? destinationPlace : "N/A");

//        otp2=generateOTP();
        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = mobileNo.getText().toString();
                if (number.isEmpty() || number.length() != 10) {
                    redText.setText("Enter a 10-digit number");
                    redText.setTextColor(Color.RED);
                } else {
                    otp2 = generateOTP();
                    otp.setText(otp2);
                }
            }
        });

        ticit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = fName.getText().toString();
                String last = lName.getText().toString();
                number = mobileNo.getText().toString();
                String otp5=otp.getText().toString();
                if (name.isEmpty() || last.isEmpty() || number.isEmpty()) {
                    Toast.makeText(FromFillUp.this, "Please Fill Up The From", Toast.LENGTH_SHORT).show();
                    redText.setTextColor(Color.RED);
                } else if(otp5.isEmpty()){
                    TextView forotpp=findViewById(R.id.redTextOtp);
                    forotpp.setText("Enter 4 digits otp");
                    forotpp.setTextColor(Color.RED);
                }else {
                    name = fName.getText().toString();
                    last = lName.getText().toString();
                    String fullName = name.concat(" ").concat(last);
                    number = mobileNo.getText().toString();
                    Intent iPrint = new Intent(FromFillUp.this, LikePayment.class);
                    iPrint.putExtra("Start", start);
                    iPrint.putExtra("Destination", destinationPlace);
                    iPrint.putExtra("fullName", fullName);
                    iPrint.putExtra("SeatNo", seat);
                    iPrint.putExtra("FlightName", flightName2);
                    iPrint.putExtra("FlightNumber", flightNumber2);
                    iPrint.putExtra("Mobile", number);
                    iPrint.putExtra("Price", String.valueOf(priceF));
                    startActivity(iPrint);
                }
            }
        });


    }


    public static String generateOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        // Generate 4 random digits for OTP
        for (int i = 0; i < 4; i++) {
            otp.append(random.nextInt(10)); // Append a random digit (0-9)
        }

        return otp.toString(); // Return OTP as a string
    }
}