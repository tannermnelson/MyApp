package com.clementsnelson.smoothriders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.smoothriders.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private TextView banner, registerUser;
    private EditText editTextFullName, editTextAge,editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private static final String TAG = "User";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        // Banner obj reference
        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        // Register user button obj reference
        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        // Edit text field obj references
        editTextFullName = findViewById(R.id.fullName);
        editTextAge = findViewById(R.id.age);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        // Progress bar obj reference
        progressBar = findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        // Get string values from edit text fields on register user click
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        // Sign up validation
        if (fullName.isEmpty()) {
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;
        }

        if (age.isEmpty()) {
            editTextAge.setError("Age is required!");
            editTextAge.requestFocus();
            return;
        }

        if (email.isEmpty()) { // basic email validation to check if empty.
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        // Checks for valid email requiring @ and .com or some other domain
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) { // basic password validation to check if empty
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        // Requires passwords be over 6 characters. (Firebase standard)
        if(password.length() < 6) {
            editTextPassword.setError("Min password should be at least 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        // If form is valid set the progress bar to visible
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            User user = new User(fullName, age, email);

                            FirebaseDatabase.getInstance().getReference(TAG)
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    // If user data is sent to the Firebase database
                                    if (task.isSuccessful()) {

                                        mDb.collection(TAG).add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                Toast.makeText(RegisterUser.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                            }
                                        });

                                        // If user is registered take them to login page
                                        startActivity(new Intent(RegisterUser.this, MainActivity.class));
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    // redirect to Login page
                                    else {
                                        Toast.makeText(RegisterUser.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }

                                }
                            });
                        }
                        else {
                            Toast.makeText(RegisterUser.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });


    }
}