package com.ttpu.chatapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class EditProfileActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;

    private DatabaseReference usersRef;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize views and Firebase
        usernameEditText = findViewById(R.id.edit_profile_username);
        passwordEditText = findViewById(R.id.edit_profile_password);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());
        }

        // Read the current user data from Firebase and display it in the EditTexts
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String username = dataSnapshot.child("username").getValue(String.class);
                    String password = dataSnapshot.child("password").getValue(String.class);
                    usernameEditText.setText(username);
                    passwordEditText.setText(password);
                } else {
                    Toast.makeText(EditProfileActivity.this, "Error: User data not found.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditProfileActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        // Set up the save button
        Button saveButton = findViewById(R.id.edit_profile_button);
        saveButton.setOnClickListener(v -> {
            String newUsername = usernameEditText.getText().toString();
            String newPassword = passwordEditText.getText().toString();

            if (TextUtils.isEmpty(newUsername)) {
                usernameEditText.setError("Please enter a username");
                return;
            }

            if (TextUtils.isEmpty(newPassword)) {
                passwordEditText.setError("Please enter a password");
                return;
            }

            // Update the user data in Firebase
            HashMap<String, Object> updates = new HashMap<>();
            updates.put("username", newUsername);
            updates.put("password", newPassword);
            usersRef.updateChildren(updates).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(EditProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}
