package com.example.epicco2app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    APICaller apiCaller;
    IODatabase io;
    User currentUser;
    String userID;
    FirebaseAuth mAuth;
    TextView userNameText;
    TextView userNameEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        io = IODatabase.getInstance();

        userID = mAuth.getCurrentUser().getUid();
        // This is how you make a GET request for the API

        // Implementing menu system
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //Sets our own toolbar as actionbar

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Displays users email in drawer header
        userNameEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.navHeaderEmail);
        userNameEmail.setText(mAuth.getCurrentUser().getEmail());

        //If for example the user closes the app and reopens it, this part won't run.
        if (savedInstanceState == null) {
            //Automatically opens food fragment when logged in
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FoodFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_food);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_food:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FoodFragment()).commit();
                break;
            case R.id.nav_statistics:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new StatisticsFragment()).commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BMIFragment()).commit();
                break;
            case R.id.nav_logout:
                logOut();
                Toast.makeText(this, "Kirjaudu ulos", Toast.LENGTH_SHORT).show();
                Intent toLogin = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(toLogin);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // If user presses back button when drawer is open, drawer will close
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    public void logOut(){

        FirebaseAuth.getInstance().signOut();
    }

}