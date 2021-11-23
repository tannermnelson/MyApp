package com.clementsnelson.smoothriders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smoothriders.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RideInformation extends AppCompatActivity implements View.OnClickListener {

    // Initialize reference to User
    private FirebaseUser user;
    // Initialize references to objects in Activity file
    private TextView tip, time, rideDescription, driverName, pickupAddress, destination;
    private Button acceptButton, logoutButton;
    // Get database refernce from Firebase
    private DatabaseReference reference;
    // Initialize userID String for use in Greeting
    private String userID;
    // Create final key value with package name
    static final String key = "com.clemntsnelson.smoothriders";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_information);

        // Receive intent from ProfileActivity
        Intent i = getIntent();
        // Create Ride object selecctedRide from intent sent from ProfileActivity
        Ride selectedRide = (Ride) i.getSerializableExtra("Ride");

        // Get string values from selectedRide to input into label TextViews
        String requesterEmail = selectedRide.getRequesterEmail();
        String pickupLocation = selectedRide.getPickupLocation();
        String destinationLocation = selectedRide.getDestinationLocation();
        String description = selectedRide.getRideDescription();
        String rideTime = selectedRide.getRideTime();
        String rideTip = selectedRide.getRideTip();

        // Get FireBase user instance
        user = FirebaseAuth.getInstance().getCurrentUser();
        // Get unique ID of the current user
        userID = user.getUid();

        // Get reference to the Rides collection in Firebase
        reference = FirebaseDatabase.getInstance().getReference("Rides");

        // Get input TextView references
        driverName = (TextView) findViewById(R.id.DriverName);
        rideDescription = (TextView) findViewById(R.id.rideDescription);
        tip = (TextView) findViewById(R.id.tip);
        time = (TextView) findViewById(R.id.time);
        pickupAddress = (TextView) findViewById(R.id.pickupAddress);
        destination = (TextView) findViewById(R.id.rideDestination);

        // Fill the input TextView references with Strings from selectedRide
        driverName.setText(requesterEmail);
        pickupAddress.setText(pickupLocation);
        destination.setText(destinationLocation);
        rideDescription.setText(description);
        time.setText(rideTime);
        tip.setText(rideTip);

        // Get reference to logout button and create onClick listener
        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(this);

        // Get reference to the accept ride button and create onClick listener
        acceptButton = (Button) findViewById(R.id.acceptButton);
        acceptButton.setOnClickListener(this);
    } // end of onCreate method

    // Method for onClick listeners in onCreate
    @Override
    public void onClick(View v){
        // Change behavior of onClick method based on the View passed to listener
        switch (v.getId()) {
            // if user clicks the logout button
            case R.id.logoutButton:
                // Logout using FirebaseAuth built in method
                FirebaseAuth.getInstance().signOut();
                // Return the user to the login screen after they sign out
                startActivity(new Intent(RideInformation.this, MainActivity.class));
                break;

            // if user clicks the accept rides button
            case R.id.acceptButton:
                // Call the acceptRide method
                acceptRide();
                // Return the user to the view with all of the rides they have accepted
                startActivity(new Intent(RideInformation.this, UserAcceptedRides.class));
                // Create Toast for user that the Ride has be accepted
                Toast.makeText(RideInformation.this, "Ride has been accepted!", Toast.LENGTH_LONG).show();
                break;
        }
    } // end of onClick method

    // Method called when refresh button is clicked
    private void acceptRide() {
        // Receive intent from ProfileActivity
        Intent i = getIntent();
        // Create Ride object selecctedRide from intent sent from ProfileActivity
        Ride selectedRide = (Ride) i.getSerializableExtra("Ride");

        // In this function we need to update the isAccepted and driverEmail field
        // in document in Rides collection.
        // After we update those values our refresh buttons will work.
        selectedRide.setIsAccepted(true);
        selectedRide.setDriverEmail(user.getEmail());

    } // end of acceptRide method

} // end of RideInformation class
