package com.example.epicco2app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.epicco2app.ui.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    APICaller apiCaller;
    IODatabase io;
    User currentUser;
    String userID;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        io = IODatabase.getInstance();

        userID = mAuth.getCurrentUser().getUid();
        // This is how you make a GET request for the API
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //Sets our own toolbar as actionbar

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //some android magic
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // API singleton object
        apiCaller = APICaller.getInstance(this);
        // Making the test parameters
        ArrayList<String> params = new ArrayList<String>();
        params.add("omnivore");
        params.add("20");
        params.add("10");
        params.add("30");
        params.add("0");
        params.add("40");
        params.add("0");
        params.add("0");

        apiCaller.call(params, new APICaller.VolleyCallback() {
            // onSuccess method is called after the request is complete
            @Override
            public void onSuccess(JSONObject response){
                FoodLogObject foodLogObject = null;
                try {
                    foodLogObject = new FoodLogObject();
                    foodLogObject.setTotal(response.getInt("Total"));
                    io.addFoodToDB(userID, foodLogObject);
                    Log.v("Async", "API call successful");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        WeightLogObject weightLogObject = new WeightLogObject();
        weightLogObject.setWeight(80);
        io.addWeightToDB(userID, weightLogObject);
        // Not quite working
        io.getUserFoodData(userID, new IODatabase.FirebaseCallback() {
            @Override
            public void onSuccess(ArrayList<FoodLogObject> foodList) {
                Log.v("Async", "FoodData read successful");

            }
        });

        io.getUserWeight(userID, new IODatabase.WeightCallback() {
            @Override
            public void onSuccess(ArrayList<WeightLogObject> weight) {
                Log.v("Async", "WeightData read successful");
            }
        });


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
                        new SettingsFragment()).commit();
                break;
            case R.id.nav_logout:
                
                Toast.makeText(this, "Kirjaudu ulos", Toast.LENGTH_SHORT).show();
                Intent toLogin = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(toLogin);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // If user presses back button and drawer is open, drawer will close
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }
}