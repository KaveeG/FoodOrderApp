package com.example.food.Domain;

import androidx.annotation.NonNull;

public class Location {
    private int Id;
    private String Loc;

    public int getId() {
        return Id;
    }

    @NonNull
    @Override
    public String toString() {
        return Loc;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLoc() {
        return Loc;
    }

    public void setLoc(String loc) {
        Loc = loc;
    }

    public Location() {
    }
}
