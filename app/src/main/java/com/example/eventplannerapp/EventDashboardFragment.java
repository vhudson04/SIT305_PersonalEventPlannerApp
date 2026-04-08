package com.example.eventplannerapp;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class EventDashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Event> eventList;
    private Button addButton, editButton, deleteButton;
    private Event selectedEvent;

    private AppDatabase db;
    EventViewModel viewModel;
    NavController navController;

    public EventDashboardFragment() {
        // Required empty public constructor
    }

    public static EventDashboardFragment newInstance(String param1, String param2) {
        EventDashboardFragment fragment = new EventDashboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_dashboard, container, false);
        addButton = view.findViewById(R.id.addButton);
        editButton = view.findViewById(R.id.editButton);
        deleteButton = view.findViewById(R.id.deleteButton);
        recyclerView = view.findViewById(R.id.recyclerView);

        eventList = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(eventList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);

        viewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        viewModel.getAllEvents().observe(getViewLifecycleOwner(), events -> {
            recyclerViewAdapter.updateList(events);
        });

        //navigating to addEvent through Navigation Component
        addButton.setOnClickListener(v -> {
            com.example.eventplannerapp.EventDashboardFragmentDirections.ActionDashboardToAdd action =
                    EventDashboardFragmentDirections.actionDashboardToAdd(null);
            Navigation.findNavController(view).navigate(action);
        });


        editButton.setOnClickListener(v -> {
            if (selectedEvent != null)
            {
                EventDashboardFragmentDirections.ActionDashboardToAdd action =
                        EventDashboardFragmentDirections.actionDashboardToAdd(selectedEvent);

                Navigation.findNavController(v).navigate(action);
            }
            else
            {
                Toast.makeText(getContext(), "Please select an event", Toast.LENGTH_SHORT).show();
            }
        });

        deleteButton.setOnClickListener(v -> {
            if (selectedEvent != null)
            {
                EventViewModel viewModel = new ViewModelProvider(requireActivity())
                        .get(EventViewModel.class);
                viewModel.delete(selectedEvent);
                Toast.makeText(getContext(), "Event deleted", Toast.LENGTH_SHORT).show();
                selectedEvent = null; //selection reset
                recyclerViewAdapter.clearSelection();
            }
            else
            {
                Toast.makeText(getContext(), "Please select an event", Toast.LENGTH_SHORT).show();
            }

        });

        return view;
    }


    public void onItemClicked(Event event)
    {
        selectedEvent = event;
    }

}