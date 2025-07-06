package com.example.trackly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView greetingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        greetingTextView = findViewById(R.id.greetingTextView);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            // Not signed in, redirect to SignInActivity
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        } else {
            // User is signed in
            String name = currentUser.getDisplayName();
            if (name == null || name.isEmpty()) {
                // If display name is not set, fallback to email
                name = currentUser.getEmail();
            }
            greetingTextView.setText("Welcome, " + name + "!");
        }
    }
}
