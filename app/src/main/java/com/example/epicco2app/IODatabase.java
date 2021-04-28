package com.example.epicco2app;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
/*
This class handles all writing and reading from the Firebase database. It is made using the singleton
principle.

The class has methods for reading and writing food and weight data.
All GET-requests are asynchronous and values are returned using callback interfaces.
 */
public class IODatabase {
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private User loaded_user;

    private static IODatabase io = new IODatabase();

    public IODatabase() {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("USERS");
    }

    public static synchronized IODatabase getInstance() {
        return io;
    }

    public void createUser(String userID, User user) {
        rootNode.getReference("USERS").child(userID).setValue(user);
    }

    public void addFoodToDB(String userID, FoodLogObject logObject) {
        rootNode.getReference("USERS").child(userID).child("FOODLOGS").push().setValue(logObject);
    }

    public void addWeightToDB(String userID, WeightLogObject weightLogObject){
        rootNode.getReference("USERS").child(userID).child("WEIGHTLOGS").push().setValue(weightLogObject);
    }

    public void getUserFoodData(String userID, final FirebaseCallback callback) {

        Task<DataSnapshot> data = null;
        rootNode.getReference("USERS").child(userID).child("FOODLOGS").addListenerForSingleValueEvent(new ValueEventListener() {
            /*
            This method is called once and it returns the users data as a DataSnapshot
            The data is then parsed into FoodLogObjects and returned with callback.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("Async", "OnDataChange");
                ArrayList<FoodLogObject> foodList = new ArrayList<FoodLogObject>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    FoodLogObject logObject;

                    logObject = snapshot.getValue(FoodLogObject.class);
                    foodList.add(logObject);


                }
                callback.onSuccess(foodList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("Async", "Error occurred reading the data");
            }
        });


    }
    public void getUserWeight(String userID, final WeightCallback callback){
        rootNode.getReference("USERS").child(userID).child("WEIGHTLOGS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("Async", "OnDataChange");
                ArrayList<WeightLogObject> weightList = new ArrayList<WeightLogObject>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    WeightLogObject weight;

                    weight = snapshot.getValue(WeightLogObject.class);

                    weightList.add(weight);
                }
                callback.onSuccess(weightList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v("Async", "Error occurred reading the data");
            }
        });
    }

    public interface FirebaseCallback {
        void onSuccess(ArrayList<FoodLogObject> foodList);

    }

    public interface WeightCallback{
        void onSuccess(ArrayList<WeightLogObject> weight);
    }

}
