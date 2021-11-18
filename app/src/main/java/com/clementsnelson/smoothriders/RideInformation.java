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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.LazyStringArrayList;

import java.util.ArrayList;

public class RideInformation extends AppCompatActivity implements View.OnClickListener {

    private TextView driverLabel, descriptionLabel, tipLabel, pickupLocationLabel, destinationLabel, timeLabel;
    private TextView tip, time, rideDescription, driverName, pickupAddress, destination;
    private Button acceptButton, logoutButton;
    private FirebaseUser user;
    private FirebaseUser ride;
    private DatabaseReference reference;
    private String userID;

    static final String key = "com.clemntsnelson.smoothriders";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_information);

        //pull string info from intent
        String requesterEmail = getIntent().getExtras().get("email").toString();
        String pickupLocation = getIntent().getExtras().get("pickup").toString();
        String destinationLocation = getIntent().getExtras().get("destination").toString();
        String description = getIntent().getExtras().get("description").toString();
        String rideTime = getIntent().getExtras().get("time").toString();
        String rideTip = getIntent().getExtras().get("tip").toString();



        // Get FireBase user instance
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Rides");
        userID = user.getUid();

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

        // Get logout Button reference
        logoutButton = (Button) findViewById(R.id.logoutButton);
        // Get onClick listener for logoutButton
        logoutButton.setOnClickListener(this);

        acceptButton = (Button) findViewById(R.id.acceptButton);
        acceptButton.setOnClickListener(this);

        driverName.setText(requesterEmail);
        pickupAddress.setText(pickupLocation);
        destination.setText(destinationLocation);
        rideDescription.setText(description);
        time.setText(rideTime);
        tip.setText(rideTip);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.acceptButton: // If user clicks the register TextView
                //Bundle b = new Bundle();
                //b.putStringArray(key, new String[]{ride.getEmail()});

                //Intent i = new Intent(this, UserRides.class);
                //i.putExtra(b);

                // get method to add said request to this users acceptedRides list
                // remove this request from profileActivity display
                startActivity(new Intent(RideInformation.this, UserRides.class));

                Toast.makeText(RideInformation.this, "Ride has been accepted!", Toast.LENGTH_LONG).show();
                break;

            case R.id.logoutButton: // If user clicks the register TextView
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(RideInformation.this, MainActivity.class));
                break;

        }
    }
}
