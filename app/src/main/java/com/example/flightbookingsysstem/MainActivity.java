package com.example.flightbookingsysstem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private AutoCompleteTextView start, destination;
    private Button nextP;
    private ArrayList<String> arrayPlace = new ArrayList<>();
    private String startItem = null, destinationItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        start = findViewById(R.id.edtFirstAuto);
        destination = findViewById(R.id.edtSecondAuto);
        nextP = findViewById(R.id.btnselectFlight);

        populatePlaces();

        setupAutoCompleteTextView(start);
        setupAutoCompleteTextView(destination);

        nextP.setOnClickListener(v -> handleNextButtonClick());
    }

    private void populatePlaces() {
        arrayPlace.add("Kolkata");
        arrayPlace.add("Mumbai");
        arrayPlace.add("Hyderabad");
        arrayPlace.add("Bangalore");
        arrayPlace.add("Pune");
        arrayPlace.add("Delhi");
        arrayPlace.add("Chennai");
    }

    private void setupAutoCompleteTextView(AutoCompleteTextView autoCompleteTextView) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayPlace);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1);

        autoCompleteTextView.setOnClickListener(v -> autoCompleteTextView.showDropDown());
        autoCompleteTextView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) autoCompleteTextView.showDropDown();
        });

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = arrayPlace.get(position);
            if (autoCompleteTextView == start) {
                startItem = selectedItem;
                start.setText(startItem);
                if (startItem.equals(destinationItem)) {
                    destination.setText("");
                    destinationItem = null;
                    Toast.makeText(this, "Destination cleared as it matches the start point.", Toast.LENGTH_SHORT).show();
                }
            } else if (autoCompleteTextView == destination) {
                destinationItem = selectedItem;
                destination.setText(destinationItem);
                if (destinationItem.equals(startItem)) {
                    start.setText("");
                    startItem = null;
                    Toast.makeText(this, "Start point cleared as it matches the destination.", Toast.LENGTH_SHORT).show();
                }
            }
            hideKeyboard(autoCompleteTextView);
        });
    }


    private void handleNextButtonClick() {
        if (startItem == null || destinationItem == null) {
            Toast.makeText(this, "Please select both start and destination", Toast.LENGTH_SHORT).show();
        } else if (!startItem.equals(destinationItem)) {
            Intent intent = new Intent(MainActivity.this, FlightChoose.class);
            intent.putExtra("start", startItem);
            intent.putExtra("destination", destinationItem);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Start and destination cannot be the same", Toast.LENGTH_SHORT).show();
        }
    }


    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
