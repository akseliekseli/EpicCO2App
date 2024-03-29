package com.example.epicco2app;

import android.content.Context;
import android.util.Log;

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


/*
This class handles GET-requests from the Ilmastodietti API.


 */
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
    The request is asynchronous and it returns the data using callback interface.
     */
    public void call(ArrayList<String> params, final VolleyCallback callback) {
        String url = QUERY_FOR_FOOD_CALC + "?query.diet="+params.get(0)
                +"&query.lowCarbonPreference="+params.get(1)
                +"&query.beefLevel="+params.get(2)
                +"&query.fishLevel=" +params.get(3)
                +"&query.porkPoultryLevel="+params.get(4)
                +"&query.dairyLevel="+params.get(5)
                +"&query.cheeseLevel=" +params.get(6)
                +"&query.riceLevel="+params.get(7)
                +"&query.eggLevel="+params.get(8)
                +"&query.winterSaladLevel="+params.get(9)
                +"&query.restaurantSpending="+params.get(10);

        // Making the request and parsing it to JSON Object. Lambda expression is used for structuring the code
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        callback.onSuccess(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("API", "Error getting data from the API"));

        // Adding the request to queue
        requestQueue.add(request);

    }

    public interface VolleyCallback {
        void onSuccess(JSONObject response) throws JSONException;
    }

}
