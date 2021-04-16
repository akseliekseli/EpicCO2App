package com.example.epicco2app;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class APICaller {

    public static final String QUERY_FOR_FOOD_CALC = "https://ilmastodieetti.ymparisto.fi/ilmastodieetti/calculatorapi/v1/FoodCalculator";
    private static APICaller caller;
    public RequestQueue requestQueue;
    private Context ctx;



    private APICaller(Context context){
        ctx = context;
        requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
    }

    /*
    APICaller is made as a singleton class
     */
    public static synchronized APICaller getInstance(Context context){
        if (caller == null){
            caller = new APICaller(context);
        }
        return caller;

    }


    /*
    This method is used for GET calling Ilmastodietti food calculator API
     */
    public void call(ArrayList<String> params) {
        String url = QUERY_FOR_FOOD_CALC + "?query.diet="+params.get(0)+"&query.beefLevel="+params.get(1)+"&query.fishLevel="
                +params.get(2)+"&query.porkPoultryLevel="+params.get(3)+"&query.dairyLevel="+params.get(4)+"&query.cheeseLevel="
                +params.get(5)+"&query.riceLevel="+params.get(6)+"&query.eggLevel="+params.get(7);

        // Making the request and parsing it to JSON Object. Lambda expression is used for structuring the code
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> System.out.println("Response: "+ response.toString()),
                error -> System.out.println("Error"));

        // Adding the request to queue
        requestQueue.add(request);

    }

}