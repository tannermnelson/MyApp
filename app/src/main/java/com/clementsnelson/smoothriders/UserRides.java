package com.clementsnelson.smoothriders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.smoothriders.R;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserRides extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user;
    private Button logoutBtn, refreshButton;
    private ProgressBar progressBar;
    private TextView emailLabel;
    private ArrayAdapter<Ride> adapter;
    private FirebaseFirestore mDbProfile = FirebaseFirestore.getInstance();
    private static final String RIDE = "Rides";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userrides);

        user = FirebaseAuth.getInstance().getCurrentUser();

        logoutBtn = (Button) findViewById(R.id.logoutButton);
        logoutBtn.setOnClickListener(this);

        refreshButton = (Button) findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        emailLabel = (TextView) findViewById(R.id.emailLabel);
        emailLabel.setText("Hello " + user.getEmail());

        ListView rideList = findViewById(R.id.rideList);
        adapter = new ArrayAdapter<Ride>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<Ride>()
        );
        rideList.setAdapter((ListAdapter) adapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logoutButton:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserRides.this, MainActivity.class));
                break;

            case R.id.refreshButton:
                getUserRides();
                break;
        }
    }

    private void getUserRides() {
        progressBar.setVisibility(View.VISIBLE);
        mDbProfile.collection(RIDE)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Ride> rides = new ArrayList<>();
                        for(QueryDocumentSnapshot document: queryDocumentSnapshots){
                            Ride r = document.toObject(Ride.class);
                            if (r.getRequesterEmail().equals(user.getEmail())) {
                                // The requesters email is added to the Rides document on creation
                                // with users email. This function works right now and lists all the
                                // rides that are posted by the user based on their email.

                                // We also need to add field in the RideInformation class where
                                // it pulls the isAccepted field and says whether a ride is accepted
                                // or not when showing the ride information.
                                rides.add(r);
                                Log.d(RIDE, document.getId() + " =>" + document.getData());
                            }
                        }
                        adapter.clear();
                        adapter.addAll(rides);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserRides.this, ProfileActivity.class));
    }
}
