package com.example.epicco2app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.epicco2app.ui.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailId, password;
    Button btnLogin;
    TextView tvLogin;

    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        btnLogin = findViewById(R.id.button_login);
        tvLogin = findViewById(R.id.loginText);
        /// Checking if the user is already logged in
        mAuthListener = firebaseAuth -> {
            FirebaseUser mUser = mAuth.getCurrentUser();
            if (mUser != null) {
                Toast.makeText(LoginActivity.this, "Olet kirjautunut sisään", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(LoginActivity.this, "Kirjaudu sisään", Toast.LENGTH_SHORT).show();
            }
        };
        /*
        After the login button is clicked, the app checks if the password and email are provided.
        After that the Firebase authenticator checks if the user information is valid and logs the user in.
         */
        btnLogin.setOnClickListener(v -> {
            String email = emailId.getText().toString();
            String pwd = password.getText().toString();
            if (email.isEmpty()) {
                emailId.setError("Syötä sähköposti, kiitos");
                emailId.requestFocus();
            } else if (pwd.isEmpty()) {
                password.setError("Syötä salasana, kitos");
                password.requestFocus();
            } else if (email.isEmpty() && pwd.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Syötä sähköposti ja salasana, kiitos", Toast.LENGTH_SHORT).show();

            } else if (!email.isEmpty() && !pwd.isEmpty()) {

                mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Kirjautuminen epäonnistui, yritä uudelleen", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent toHome = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(toHome);
                        }
                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, "Tapahtui virhe, yritä uudelleen", Toast.LENGTH_SHORT).show();
            }
        });
        // This listener changes the activity from login to sign in, if the text button is pressed.
        tvLogin.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, SignInActivity.class);
            startActivity(i);
        });
    }


}