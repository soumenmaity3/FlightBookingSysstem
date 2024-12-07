package com.example.flightbookingsysstem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class LikePayment extends AppCompatActivity {
    private EditText etCardNumber, etExpiryDate, etCvv, etCardHolderName;
    private TextView tvTotalAmount;
    private Button btnConfirmPayment;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_like_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainL), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etCardNumber = findViewById(R.id.etCardNumber);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        etCvv = findViewById(R.id.etCvv);
        etCardHolderName = findViewById(R.id.etCardHolderName);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        btnConfirmPayment = findViewById(R.id.btnConfirmPay);

        Intent getData=getIntent();
        String start=getData.getStringExtra("Start");
        ArrayList<String> seat=getData.getStringArrayListExtra("SeatNo");
        String destinationPlace=getData.getStringExtra("Destination");
        String flightName2=getData.getStringExtra("FlightName");
        String flightNumber2=getData.getStringExtra("FlightNumber");
        String fullName=getData.getStringExtra("fullName");
        String mobileNumber=getData.getStringExtra("Mobile");
        String price=getData.getStringExtra("Price");

        tvTotalAmount.setText(price);

        btnConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    // Simulate payment processing (you can add payment gateway logic here)
                    Toast.makeText(LikePayment.this, "Payment Successful!", Toast.LENGTH_LONG).show();
                    Intent inext=new Intent(LikePayment.this, TicitPrint.class);
                    inext.putExtra("Start",start);
                    inext.putExtra("SeatNo",seat);
                    inext.putExtra("Destination",destinationPlace);
                    inext.putExtra("fullName",fullName);
                    inext.putExtra("Mobile",mobileNumber);
                    inext.putExtra("FlightName",flightName2);
                    inext.putExtra("FlightNumber",flightNumber2);
                    startActivity(inext);
                }
            }
        });
    }

    // Method to validate input fields
    private boolean validateInputs() {
        String cardNumber = etCardNumber.getText().toString().trim();
        String expiryDate = etExpiryDate.getText().toString().trim();
        String cvv = etCvv.getText().toString().trim();
        String cardHolderName = etCardHolderName.getText().toString().trim();

        if (TextUtils.isEmpty(cardNumber) || cardNumber.length() != 16) {
            etCardNumber.setError("Enter a valid 16-digit card number");
            return false;
        }

        if (TextUtils.isEmpty(expiryDate) || !expiryDate.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            etExpiryDate.setError("Enter a valid expiry date (MM/YY)");
            return false;
        }

        if (TextUtils.isEmpty(cvv) || cvv.length() != 3) {
            etCvv.setError("Enter a valid 3-digit CVV");
            return false;
        }

        if (TextUtils.isEmpty(cardHolderName)) {
            etCardHolderName.setError("Enter cardholder's name");
            return false;
        }

        return true;

    }
}