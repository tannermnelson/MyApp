package com.clementsnelson.smoothriders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.smoothriders.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentReference;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private ArrayAdapter<Ride> adapter;
    private Button logoutButton, createRideButton, findRideButton, refreshButton;
    private static final String RIDE = "Rides";
    private ProgressBar progressBar;

    private FirebaseFirestore mDbProfile = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ListView rideRequestList = findViewById(R.id.requestList);
        adapter = new ArrayAdapter<Ride>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<Ride>()
        );
        //adapter = new RideAdapter(this, new ArrayList<Ride>());
        rideRequestList.setAdapter(adapter);

        rideRequestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                // String currentName = adapterView.getItemAtPosition(position).toString();
                Ride selectedRide = (Ride) adapterView.getItemAtPosition(position);
                String requesterEmail = selectedRide.getRequesterEmail();
                String pickupLocation = selectedRide.getPickupLocation();
                String destinationLocation = selectedRide.getDestinationLocation();
                String rideDescription = selectedRide.getrideDescription();
                String rideTime = selectedRide.getRideTime();
                String rideTip = selectedRide.getRideTip();

                Intent i = new Intent(getApplicationContext(), RideInformation.class);
                i.putExtra("email", requesterEmail);
                i.putExtra("pickup", pickupLocation);
                i.putExtra("destination", destinationLocation);
                i.putExtra("description", rideDescription);
                i.putExtra("time", rideTime);
                i.putExtra("tip", rideTip);


                startActivity(i);
            }
        });

        createRideButton = (Button) findViewById(R.id.createARideButton);
        createRideButton.setOnClickListener(this);

        findRideButton = (Button) findViewById(R.id.findARideButton);
        refreshButton = (Button) findViewById(R.id.refreshButton);

        // Get progress bar reference
        progressBar = findViewById(R.id.progressBar);

        // Get logoutButton obj reference
        logoutButton = (Button) findViewById(R.id.logoutButton);

        // Create onClick Listener for logout button
        findRideButton.setOnClickListener(this);
        refreshButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);

        // Get current user obj by searching db by User ID.
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        // Get final obj references to fillable text views
        final TextView greetingTextView = (TextView) findViewById(R.id.greeting);
        //final TextView fullNameTextView = (TextView) findViewById(R.id.fullName);
        //final TextView emailTextView = (TextView) findViewById(R.id.email);
        //final TextView ageTextView = (TextView) findViewById(R.id.age);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Create User obj
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) { // User profile exists
                    // Get user information from userProfile
                    String fullName = userProfile.fullName;
                    String email = userProfile.email;
                    String age = userProfile.age;

                    // Set the Text Views to user profile info
                    greetingTextView.setText("Welcome, " + fullName + "!");
                    //fullNameTextView.setText(fullName);
                    //emailTextView.setText(email);
                    //ageTextView.setText(age);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { // User doesn't exist
                Toast.makeText(ProfileActivity.this, "Something bad happened!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.createARideButton: // If user clicks the register TextView
                startActivity(new Intent(ProfileActivity.this, CreateRideRequest.class));
                break;

            case R.id.findARideButton: // If user clicks the sign in button
                //startActivity(new Intent(this, Rideslist.class));
                break;

            case R.id.refreshButton:
                progressBar.setVisibility(View.VISIBLE);
                mDbProfile.collection(RIDE)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                ArrayList<Ride> rides = new ArrayList<>();
                                for(QueryDocumentSnapshot document: queryDocumentSnapshots){
                                    Ride r = document.toObject(Ride.class);
                                    rides.add(r);
                                    Log.d(RIDE, document.getId() + " =>" + document.getData());
                                }
                                adapter.clear();
                                adapter.addAll(rides);
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                //startActivity(new Intent(this, usersRides.class));
                break;

            case R.id.logoutButton: // If user clicks the register TextView
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                break;

        }
    }

}