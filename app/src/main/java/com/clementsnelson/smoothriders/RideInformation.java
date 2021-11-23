package com.clementsnelson.smoothriders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smoothriders.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.LazyStringArrayList;

import java.util.ArrayList;

public class RideInformation extends AppCompatActivity implements View.OnClickListener {

    private TextView driverLabel, descriptionLabel, tipLabel, pickupLocationLabel, destinationLabel, timeLabel;
    private TextView tip, time, rideDescription, driverName, pickupAddress, destination;
    private Button acceptButton, logoutButton;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    static final String key = "com.clemntsnelson.smoothriders";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_information);

        Intent i = getIntent();
        Ride selectedRide = (Ride) i.getSerializableExtra("Ride");

        // Get string values from selectedRide
        String requesterEmail = selectedRide.getRequesterEmail();
        String pickupLocation = selectedRide.getPickupLocation();
        String destinationLocation = selectedRide.getDestinationLocation();
        String description = selectedRide.getRideDescription();
        String rideTime = selectedRide.getRideTime();
        String rideTip = selectedRide.getRideTip();

        // Get FireBase user instance
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Rides");
        userID = user.getUid();
        System.out.println(userID);

        // Get label references
        driverLabel = (TextView) findViewById(R.id.DriverLabel);
        descriptionLabel = (TextView) findViewById(R.id.rideDescriptionLabel);
        tipLabel = (TextView) findViewById(R.id.tipLabel);
        timeLabel = (TextView) findViewById(R.id.timeLabel);
        pickupLocationLabel = (TextView) findViewById(R.id.pickupLabel);
        destinationLabel = (TextView) findViewById(R.id.destinationLabel);

        // Get fillable TextView references
        driverName = (TextView) findViewById(R.id.DriverName);
        rideDescription = (TextView) findViewById(R.id.rideDescription);
        tip = (TextView) findViewById(R.id.tip);
        time = (TextView) findViewById(R.id.time);
        pickupAddress = (TextView) findViewById(R.id.pickupAddress);
        destination = (TextView) findViewById(R.id.rideDestination);

        // Fill the fillable TextView references
        driverName.setText(requesterEmail);
        pickupAddress.setText(pickupLocation);
        destination.setText(destinationLocation);
        rideDescription.setText(description);
        time.setText(rideTime);
        tip.setText(rideTip);

        // Get logout Button reference
        logoutButton = (Button) findViewById(R.id.logoutButton);
        // Get onClick listener for logoutButton
        logoutButton.setOnClickListener(this);

        acceptButton = (Button) findViewById(R.id.acceptButton);
        acceptButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.acceptButton:
                acceptRide();
                startActivity(new Intent(RideInformation.this, UserAcceptedRides.class));
                Toast.makeText(RideInformation.this, "Ride has been accepted!", Toast.LENGTH_LONG).show();
                break;

            case R.id.logoutButton: // If user clicks the register TextView
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(RideInformation.this, MainActivity.class));
                break;

        }
    }

    private void acceptRide() {
        Intent i = getIntent();
        Ride selectedRide = (Ride) i.getSerializableExtra("Ride");

        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        selectedRide.setIsAccepted(true);
        selectedRide.setDriverEmail(user.getEmail());

    }
}
