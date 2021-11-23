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
    private TextView greetingTextView;
    private ArrayAdapter<Ride> adapter;
    private Button logoutButton, createRideButton, ridesPosted, ridesAccepted, refreshButton;
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

                Ride selectedRide = (Ride) adapterView.getItemAtPosition(position);
                Intent i = new Intent(ProfileActivity.this, RideInformation.class);
                i.putExtra("Ride", selectedRide);

                startActivity(i);
            }
        });

        createRideButton = (Button) findViewById(R.id.createARideButton);
        createRideButton.setOnClickListener(this);

        ridesAccepted = (Button) findViewById(R.id.ridesAccepted);
        ridesAccepted.setOnClickListener(this);

        ridesPosted = (Button) findViewById(R.id.ridesPosted);
        ridesPosted.setOnClickListener(this);

        refreshButton = (Button) findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(this);

        // Get logoutButton obj reference
        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(this);

        // Get progress bar reference
        progressBar = findViewById(R.id.progressBar);

        // Get current user obj by searching db by User ID.
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        // Get final obj references to fillable text views
        greetingTextView = (TextView) findViewById(R.id.greeting);
        greetingTextView.setText("Hello " + user.getEmail());

//        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                // Create User obj
//                User userProfile = snapshot.getValue(User.class);
//
//                if (userProfile != null) { // User profile exists
//                    // Get user information from userProfile
//                    String fullName = userProfile.fullName;
//                    String email = userProfile.email;
//                    String age = userProfile.age;
//
//                    // Set the Text Views to user profile info
//                    greetingTextView.setText("Welcome, " + fullName + "!");
//                    //fullNameTextView.setText(fullName);
//                    //emailTextView.setText(email);
//                    //ageTextView.setText(age);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) { // User doesn't exist
//                Toast.makeText(ProfileActivity.this, "Something bad happened!", Toast.LENGTH_LONG).show();
//            }
//        });

    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.createARideButton: // If user clicks the create Rides button
                startActivity(new Intent(ProfileActivity.this, CreateRideRequest.class));
                break;

            case R.id.ridesAccepted: // If user clicks the Rides Accepted button
                startActivity(new Intent(this, UserAcceptedRides.class));
                break;

            case R.id.ridesPosted: // If user clicks the Rides Posted button
                startActivity(new Intent(this, UserRides.class));
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
                                    if (!r.getIsAccepted()) {
                                        rides.add(r);
                                        Log.d(RIDE, document.getId() + " =>" + document.getData());
                                    }
                                }
                                adapter.clear();
                                adapter.addAll(rides);
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                break;

            case R.id.logoutButton: // If user clicks the logout Button
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                break;

        }
    }

}