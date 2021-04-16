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

    public static final String QUERY_FOR_FOOD_CALC = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator?";
    private static APICaller caller;
    public RequestQueue requestQueue;
    private static Context ctx;



    private APICaller(Context context){
        ctx = context;
        requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
    }


    public static synchronized APICaller getInstance(Context context){
        if (caller == null){
            caller = new APICaller(context);
        }
        return caller;

    }



    public void call() {
        String url = QUERY_FOR_FOOD_CALC +"query.diet=omnivore&query.beefLevel=10";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> System.out.println("Response: "+ response.toString()),
                error -> System.out.println("Error"));


        requestQueue.add(request);

    }

}
