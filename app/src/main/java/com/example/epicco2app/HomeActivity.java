package com.example.epicco2app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import org.json.JSONObject;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    APICaller apiCaller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This is how you make a GET request for the API

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
            public void onSuccess(String response) {
                System.out.println(response);
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
                FirebaseAuth.getInstance().signOut();
                Intent toMain = new Intent(HomeActivity.this , LoginActivity.class);
                startActivity(toMain);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}