package com.example.eventplannerapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executors;

public class EventViewModel extends AndroidViewModel {
    private EventDao eventDao;
    private LiveData<List<Event>> allEvents;
    public EventViewModel(@NonNull Application application) {
        super(application);

        AppDatabase db = AppDatabase.getInstance(application);
        eventDao = db.eventDao();

        allEvents = eventDao.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents()
    {
        return allEvents;
    }

    public void insert(Event event)
    {
        Executors.newSingleThreadExecutor().execute(() -> {
            eventDao.insert(event);
        });
    }

    public void delete(Event event)
    {
        Executors.newSingleThreadExecutor().execute(() -> {
            eventDao.delete(event);
        });
    }

    public void update(Event event)
    {
        Executors.newSingleThreadExecutor().execute(() -> {
            eventDao.update(event);
        });
    }
}
