package com.example.epicco2app;

import com.google.firebase.database.FirebaseDatabase;

/*
The user's information is stored in this class.
 */
public class User {
    private String name, phone, email;



    public User (){
    }

    public String getName(){
        return name;
    }

    public String getPhone(){
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
