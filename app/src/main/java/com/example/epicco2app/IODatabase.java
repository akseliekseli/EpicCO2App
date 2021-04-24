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
        rootNode.getReference("USERS").child(userID).child("WEIGHTLOGS").setValue(weightLogObject);
    }

    public void getUserFoodData(String userID, final FirebaseCallback callback) {

        Task<DataSnapshot> data = null;
        rootNode.getReference("USERS").child(userID).child("FOODLOGS").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("Async", "OnDataCHange");
                ArrayList<FoodLogObject> foodList = new ArrayList<FoodLogObject>();
                System.out.println(dataSnapshot.getChildrenCount());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    FoodLogObject logObject;
                    System.out.println(snapshot.getValue().getClass());
                    /*

                    Date date = new Date((Long) snapshot.child("logTime").child("time").getValue());
                    logObject.setLogTime(date);

                    String totnum = snapshot.child("total").getValue().toString();
                    logObject.setTotal(new Long(totnum));
                    */
                    logObject = snapshot.getValue(FoodLogObject.class);
                    foodList.add(logObject);


                }
                callback.onSuccess(foodList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public interface FirebaseCallback {
        void onSuccess(ArrayList<FoodLogObject> foodList);

    }

}
