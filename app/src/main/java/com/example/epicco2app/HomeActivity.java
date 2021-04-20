package com.example.epicco2app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONObject;

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
    }
}