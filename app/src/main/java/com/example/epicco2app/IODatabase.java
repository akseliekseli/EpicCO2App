package com.example.epicco2app;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IODatabase {
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private User loaded_user;

    private static IODatabase io = new IODatabase();

    public IODatabase() {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
    }

    public static IODatabase getInstance() {
        return io;
    }

    public void createUser(String userID, User user) {
        rootNode.getReference("users").child(userID).setValue(user);
    }

    public void addFoodToDB(String userID, FoodLogObject logObject) {
        rootNode.getReference("users").child(userID).child("Logs").setValue(logObject);
    }



}
