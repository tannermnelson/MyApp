package com.clementsnelson.smoothriders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smoothriders.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RideInformation extends AppCompatActivity {

    private TextView driverLabel, descriptionLabel, tipLabel, pickupLocationLabel, destinationLabel, timeLabel;
    private TextView tip, time, rideDescription, driverName, pickupAddress, destination;
    private Button acceptButton;
    private FirebaseUser user;
    private FirebaseUser ride;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_information);

        Bundle extra = getIntent().getExtras();
//
//
//        // Get FireBase user instance
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        reference = FirebaseDatabase.getInstance().getReference("Rides");
//        userID = user.getUid();
//
//        // Get label references
//        driverLabel = (TextView) findViewById(R.id.DriverLabel);
//        descriptionLabel = (TextView) findViewById(R.id.rideDescriptionLabel);
//        tipLabel = (TextView) findViewById(R.id.tipLabel);
//        timeLabel = (TextView) findViewById(R.id.timeLabel);
//        pickupLocationLabel = (TextView) findViewById(R.id.pickupLabel);
//        destinationLabel = (TextView) findViewById(R.id.destinationLabel);
//
//        // Get fillable TextView references
//        driverName = (TextView) findViewById(R.id.DriverName);
//        rideDescription = (TextView) findViewById(R.id.rideDescription);
//        tip = (TextView) findViewById(R.id.tip);
//        time = (TextView) findViewById(R.id.time);
//        pickupAddress = (TextView) findViewById(R.id.pickupAddress);
//        destination = (TextView) findViewById(R.id.destination);
//
//        acceptButton = (Button) findViewById(R.id.acceptButton);
//
//        // driverName.setText(user.getDisplayName());

    }
}
