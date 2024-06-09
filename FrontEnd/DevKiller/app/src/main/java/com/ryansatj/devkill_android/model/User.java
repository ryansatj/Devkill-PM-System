package com.ryansatj.devkill_android.model;

public class User {
    public int id;
    public int _id;
    public String name;
    public String email;
    public String username;

    public User(int id, int _id, String name, String email, String username){
        this.id = id;
        this._id = _id;
        this.name = name;
        this.email = email;
        this.username = username;
    }
}
