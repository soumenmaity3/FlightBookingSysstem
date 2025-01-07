package com.example.flightbookingsysstem;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;

public class FromFillUp extends AppCompatActivity {
    EditText fName, lName, mobileNo, otp;
    TextView startp, destinationp, price, seatNo, redText,flightName,flightNumber;
    Button getOTP, ticit;
    String number,otp3,otp4;
    private static final String CHANNELID = "My_Channel";
    private static final int NOTIFICATIONID = 100;
    private static final int REQUEST_NOTIFICATION_PERMISSION = 1;
    private static final int REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_fill_up);

        // Initialize views
        fName = findViewById(R.id.passName);
        lName = findViewById(R.id.passLName);
        mobileNo = findViewById(R.id.passMobile);
        otp = findViewById(R.id.enterOTP);
        startp = findViewById(R.id.startFlight1);
        destinationp = findViewById(R.id.destinationFlight1);
        seatNo = findViewById(R.id.seatNo);
        price = findViewById(R.id.flightPrice);
        getOTP = findViewById(R.id.forOTP);
        ticit = findViewById(R.id.btnConfirmContinue);
        redText = findViewById(R.id.redText);
        flightName=findViewById(R.id.flightName2);
        flightNumber=findViewById(R.id.flightNumber2);

        // Request necessary permissions
        requestSmsPermission();
        requestNotificationPermission();
        // Get intent data
        Intent getData = getIntent();
        ArrayList<String> seat = getData.getStringArrayListExtra("Seat");
        int seatTotal = getData.getIntExtra("TotalSeat", 0);
        String start = getData.getStringExtra("Start");
        String flName=getData.getStringExtra("FlightName");
        String fNumber=getData.getStringExtra("FlightNumber");
        String destinationPlace = getData.getStringExtra("Destination");

        // Set data to views
        startp.setText(start != null ? start : "N/A");
        destinationp.setText(destinationPlace != null ? destinationPlace : "N/A");
        price.setText(String.valueOf(5600 * seatTotal));
        flightName.setText(flName);
        flightNumber.setText(fNumber);
        if (seat != null) {
            seatNo.setText(String.join(", ", seat));
        } else {
            seatNo.setText("No seats selected");
        }

        // Get OTP button click listener
        getOTP.setOnClickListener(v -> {
            number = mobileNo.getText().toString();
            if (number.length() == 10) {
                number = "+91" + mobileNo.getText().toString();
            }

            if (number.isEmpty() || number.length() != 13) {
                redText.setText("Enter a valid 10-digit mobile number!");
                redText.setTextColor(Color.RED);
            } else {
                otp3=generateOTP();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATION_PERMISSION);
                    } else {
                        showNotification();
                    }
                } else {
                    showNotification();
                }
            }
        });

        ticit.setOnClickListener(v -> {
            String firstName = fName.getText().toString();
            String lastName = lName.getText().toString();
            String mobile = mobileNo.getText().toString();
            String otpInput = otp.getText().toString();
            otp4=otp.getText().toString();
            if (firstName.isEmpty() || lastName.isEmpty() || mobile.isEmpty()) {
                Toast.makeText(this, "Please fill in all details!", Toast.LENGTH_SHORT).show();
            } else if (otpInput.isEmpty()) {
                redText.setText("Please enter the received OTP!");
                redText.setTextColor(Color.RED);
            } else if (!otp3.equals(otp4)) {
                redText.setText("Enter Correct OTP");
                redText.setTextColor(Color.RED);
            } else {
                // Navigate to LikePayment activity
                Intent confirmIntent = new Intent(this, LikePayment.class);
                confirmIntent.putExtra("Start", start);
                confirmIntent.putExtra("Destination", destinationPlace);
                confirmIntent.putExtra("fullName", firstName + " " + lastName);
                confirmIntent.putExtra("Mobile", mobile);
                confirmIntent.putExtra("Price", price.getText().toString());
                confirmIntent.putExtra("SeatNo", seat);
                startActivity(confirmIntent);
            }
        });
    }

    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 2);
            }
        }
    }

    private void showNotification() {
        // Retrieve the drawable and handle potential null value

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.airplane, null);
        if (drawable == null) {
            Toast.makeText(this, "Drawable not found", Toast.LENGTH_SHORT).show();
            return;  // If drawable not found, exit the function
        }

        Bitmap largeIcon = null;

        // Check if the drawable is a BitmapDrawable before casting
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            largeIcon = bitmapDrawable.getBitmap();
        }

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (nm == null) {
            Toast.makeText(this, "Notification Manager not available", Toast.LENGTH_SHORT).show();
            return; // If NotificationManager is null, exit the function
        }

        Notification notification;

        // For Android O (API 26) and above, create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNELID, "New Channel", NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
        }

        Intent iNoti = new Intent(getApplicationContext(), FromFillUp.class);
        iNoti.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Specify mutability flag based on the Android version
        PendingIntent pi;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pi = PendingIntent.getActivity(this, REQUEST_CODE, iNoti, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        } else {
            pi = PendingIntent.getActivity(this, REQUEST_CODE, iNoti, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        Notification.BigPictureStyle bigPictureStyle=new Notification.BigPictureStyle()
                .bigPicture( ((BitmapDrawable) (ResourcesCompat.getDrawable(getResources(), R.drawable.airplane, null))).getBitmap())
                .bigLargeIcon(largeIcon)
                .setBigContentTitle("OTP sent by Flight Booking App")
                .setSummaryText("OTP- "+otp3);

        Notification.InboxStyle inboxStyle=new Notification.InboxStyle()
                .addLine("OTP is "+otp3)

                .setBigContentTitle("OTP Message")
                .setSummaryText("Message from Flight Booking App");

        // Build the notification (with and without channels depending on the Android version)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Build notification for API 26+
            notification = new Notification.Builder(this, CHANNELID)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.airplane)  // Ensure this icon is valid for notifications
                    .setContentTitle("OTP Message From Flight Booking App")
                    .setContentText("OTP - "+otp3)
//                    .setContentIntent(pi)
                    .setAutoCancel(false)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true)  // Dismiss the notification after clicking it
                    .build();
        } else {
            // Build notification for below API 26
            notification = new Notification.Builder(this)
                    .setLargeIcon(largeIcon)
                    .setSmallIcon(R.drawable.airplane)
                    .setContentTitle("OTP Message From Flight Booking App")
                    .setContentText("This message is for OTP")
//                    .setContentIntent(pi)
                    .setAutoCancel(false)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true)
                    .build();
        }

        // Show the notification
        nm.notify(NOTIFICATIONID, notification);
    }
    public static String generateOTP() {
        return String.format("%04d", (int) (Math.random() * 10000));
    }
}
