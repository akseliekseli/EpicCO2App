package com.example.epicco2app.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.epicco2app.HomeActivity;
import com.example.epicco2app.IODatabase;
import com.example.epicco2app.LoginActivity;
import com.example.epicco2app.R;
import com.example.epicco2app.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignInActivity extends AppCompatActivity {
    EditText emailId, password, nameT, phoneT;
    Button btnLogin;
    TextView tvLogin;
    IODatabase io;
    FirebaseAuth mAuth;
    private String email;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        io = IODatabase.getInstance();
        emailId = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        nameT = findViewById(R.id.editTextTextPersonName);
        phoneT = findViewById(R.id.editTextPhone);
        btnLogin = findViewById(R.id.button_login);
        tvLogin = findViewById(R.id.loginText);
        /*
        Onclicklistener for our button that checks if user has entered email and and valid password and then
        creates an account with Firebase's createUserWithEmailAndPassword
        After that the app changes to home screen.
        If the email or password are invalid or user info is not provided, the app notifies the user with Toast.
         */
        btnLogin.setOnClickListener(v -> {
            email = emailId.getText().toString();
            pwd = password.getText().toString();
            final String pho = phoneT.getText().toString();
            final String nam = nameT.getText().toString();
            if (email.isEmpty()){
                emailId.setError("Syötä sähköposti, kiitos");
                emailId.requestFocus();
            }
            else if(pwd.isEmpty()){
                password.setError("Syötä salasana, kiitos");
                password.requestFocus();
            }
            else if(email.isEmpty() && pwd.isEmpty()){
                Toast.makeText(SignInActivity.this, "Syötä sähköposti ja salasana, kiitos", Toast.LENGTH_SHORT).show();

            }
            else if(!email.isEmpty() && !pwd.isEmpty()) {
                // Checking that the password is valid
                Pattern pattern;
                Matcher matcher;
                pattern = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})");
                matcher = pattern.matcher(pwd);
                if (!matcher.matches()) {
                    Toast.makeText(SignInActivity.this, "Salasanan pitää olla vähintään 12 merkkiä pitkä ja sisältää yhden numeron, ison ja pienen kirjaimen sekä yhden erikoismerkin", Toast.LENGTH_SHORT).show();
                }
                else if(!pho.isEmpty() && !nam.isEmpty()){
                    mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SignInActivity.this, task -> {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this, "Rekisteröityminen ei onnistunut, yritä uudelleen", Toast.LENGTH_LONG).show();
                        } else {

                            /// Creating user node to database using Firebases's user ID as an ID
                            String new_user = mAuth.getCurrentUser().getUid();
                            String new_email = mAuth.getCurrentUser().getEmail();
                            User n_user = new User();
                            n_user.setName(nam);
                            n_user.setEmail(email);
                            n_user.setPhone(pho);
                            io.createUser(new_user,n_user);
                            startActivity(new Intent(SignInActivity.this, HomeActivity.class));

                        }
                    });
                }
                else{
                    Toast.makeText(SignInActivity.this, "Syötä nimesi ja puhelinnumerosi", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(SignInActivity.this, "Tapahtui virhe rekisteröityessä", Toast.LENGTH_SHORT).show();
            }
        });
        /// Changing from Signin screen to Login screen
        tvLogin.setOnClickListener(v -> {
            Intent i = new Intent(SignInActivity.this, LoginActivity.class);
            startActivity(i);
        });
    }

}