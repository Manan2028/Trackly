package com.example.trackly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        EditText fullNameEditText = findViewById(R.id.fullName);
        EditText birthDateEditText = findViewById(R.id.birthDate);
        EditText emailEditText = findViewById(R.id.email);
        EditText passwordEditText = findViewById(R.id.password);
        Button signupButton = findViewById(R.id.signupBtn);
        TextView signInRedirect = findViewById(R.id.signInRedirect);

        signupButton.setOnClickListener(v -> {
            String fullName = fullNameEditText.getText().toString().trim();
            String birthDate = birthDateEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (fullName.isEmpty() || birthDate.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                UserProfile userProfile = new UserProfile(fullName, birthDate, email);

                                db.collection("users").document(user.getUid())
                                        .set(userProfile)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(SignUpActivity.this, "Sign-up successful!", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        })
                                        .addOnFailureListener(e -> Toast.makeText(SignUpActivity.this, "Failed to save user profile: " + e.getMessage(), Toast.LENGTH_LONG).show());
                            }
                        } else {
                            String errorMessage = "Unknown error occurred";
                            if (task.getException() != null && task.getException().getMessage() != null) {
                                errorMessage = task.getException().getMessage();
                            }
                            Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
        });

        signInRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
        });
    }
}
