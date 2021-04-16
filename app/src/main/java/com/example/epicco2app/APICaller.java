package com.example.epicco2app;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class APICaller {

    private static APICaller caller = new APICaller();
    private RequestQueue requestQueue;



    APICaller(){
    }


    public static APICaller getInstance(){
        return caller;

    }

    public void call(Context ctx) {
        RequestQueue queue = Volley.newRequestQueue(ctx);
        String url = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator?query.diet=omnivore&query.beefLevel=10";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> System.out.println("Response: "+ response.toString()),
                error -> System.out.println("Error"));


        queue.add(request);

    }

}
