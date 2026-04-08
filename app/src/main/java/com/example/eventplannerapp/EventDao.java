package com.example.eventplannerapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDao {
    @Insert
    void insert(Event event);

    @Query("Select * FROM events ORDER BY date DESC")
    LiveData<List<Event>> getAllEvents();

    @Update
    void update(Event event);

    @Delete
    void delete(Event event);
}
