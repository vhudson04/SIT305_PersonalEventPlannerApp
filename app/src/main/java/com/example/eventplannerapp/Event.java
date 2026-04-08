package com.example.eventplannerapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "events")
public class Event implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title, location, category;
    private String date;
    private String time;

    public Event(String title, String location, String category, String date, String time)
    {
        this.title = title;
        this.location = location;
        this.category = category;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle()
    {
        return title;
    }
    public String getLocation() {
        return location;
    }
    public String getCategory() {return category;}
    public String getDate(){return date;}
    public String getTime(){return time;}
    public void setTitle(String title) {
        this.title = title;
    }
    public void setLocation(String location) {this.location = location;}
    public void setCategory(String category) {
        this.category = category;
    }
    public void setDate(String date) {this.date = date;}

    public void setTime(String time) {this.time = time;}
}
