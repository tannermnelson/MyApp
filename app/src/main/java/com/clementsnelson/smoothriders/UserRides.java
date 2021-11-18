package com.clementsnelson.smoothriders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.smoothriders.R;

import java.util.ArrayList;

public class UserRides extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user;
    private FirebaseFirestore mDbrides = FirebaseFirestore.getInstance();
    private Button logoutBtn, homeButton;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userrides);

        logoutBtn = (Button) findViewById(R.id.logoutButton);
        logoutBtn.setOnClickListener(this);

        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(this);

        ListView rideList = findViewById(R.id.rideList);
        Adapter adapter = new ArrayAdapter<Ride>(
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
            case R.id.homeButton:
                startActivity(new Intent(UserRides.this, ProfileActivity.class));
                break;


        }
    }
}
