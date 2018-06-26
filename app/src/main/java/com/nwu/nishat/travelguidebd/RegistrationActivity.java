package com.nwu.nishat.travelguidebd;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailInput, passwordInput, displayNameInput;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        register = findViewById(R.id.register_button);
        emailInput = findViewById(R.id.email);
        passwordInput = findViewById(R.id.password);
        displayNameInput = findViewById(R.id.username);

        if(mAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (email.isEmpty()) {
                    emailInput.setError("Email is required");
                    emailInput.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailInput.setError("Please enter a valid email");
                    emailInput.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    passwordInput.setError("Password is required");
                    passwordInput.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    passwordInput.setError("Minimum length of password should be 6");
                    passwordInput.requestFocus();
                    return;
                }

                registerUser( email, password);

            }
        });
    }


    //Create Account
    private void registerUser(String email,String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TESTING", "Sign up Successful" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Signed up Failed", Toast.LENGTH_SHORT).show();

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegistrationActivity.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();

                                emailInput.setError("Email already exists!");
                                emailInput.requestFocus();
                            }
                        }
                        else
                        {
                            userProfile();
                            Toast.makeText(RegistrationActivity.this, "Created Account", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Set UserDisplay Name
    private void userProfile()
    {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null)
        {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayNameInput.getText().toString().trim())
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TESTING", "User profile updated.");
                                Intent intent = new Intent( RegistrationActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
        }
    }




}
