package com.clementsnelson.smoothriders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.smoothriders.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateRideRequest extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextDescription, editTextTime, editTextPickupLocation, editTextDestinationLocation, editTextRideTip;
    private Button createRideRequestBtn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride_request);

        mAuth = FirebaseAuth.getInstance();

        // EditText obj references
        editTextDescription = findViewById(R.id.rideDescription);
        editTextTime = findViewById(R.id.rideTime);
        editTextPickupLocation = findViewById(R.id.pickupLocation);
        editTextDestinationLocation = findViewById(R.id.destinationLocation);
        editTextRideTip = findViewById(R.id.rideTip);

        // Button obj reference
        createRideRequestBtn = findViewById(R.id.createRequest);
        // Set onClick listener for button
        createRideRequestBtn.setOnClickListener(this);

        // Progress bar obj reference
        progressBar = findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createRequest:
                createRideRequest();
                break;
        }
    }

    private void createRideRequest() {
        // Get string values from edit text fields on create request button click
        String rideDescription = editTextDescription.getText().toString().trim();
        String rideTime = editTextTime.getText().toString().trim();
        String pickupLocation = editTextPickupLocation.getText().toString().trim();
        String destinationLocation = editTextDestinationLocation.getText().toString().trim();
        String rideTip = editTextRideTip.getText().toString().trim();

        // Ride request validation
        if (rideDescription.isEmpty()) {
            editTextDescription.setError("A description is required!");
            editTextDescription.requestFocus();
            return;
        }

        if (rideTime.isEmpty()) {
            editTextTime.setError("A time is required!");
            editTextTime.requestFocus();
            return;
        }

        if (pickupLocation.isEmpty()) {
            editTextPickupLocation.setError("A pickup location is required!");
            editTextPickupLocation.requestFocus();
            return;
        }

        if (destinationLocation.isEmpty()) {
            editTextDestinationLocation.setError("A destination location is required!");
            editTextDescription.requestFocus();
            return;
        }

        // If form is valid set the progress bar to visible
        progressBar.setVisibility(View.VISIBLE);

        Ride ride = new Ride(rideDescription, rideTime, pickupLocation, destinationLocation, rideTip);

        FirebaseDatabase.getInstance().getReference("Rides")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(ride).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                // If ride data is sent to the Firebase database
                if (task.isSuccessful()) {
                    Toast.makeText(CreateRideRequest.this, "Ride has been created successfully!", Toast.LENGTH_LONG).show();
                    // If ride is created redirect to rides page (NOT SETUP YET)
                    startActivity(new Intent(CreateRideRequest.this, ProfileActivity.class));
                    progressBar.setVisibility(View.GONE);
                }
                // If ride is not added
                else {
                    Toast.makeText(CreateRideRequest.this, "Ride failed to create. Try Again!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }


}