package com.example.eventplannerapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEventFragment extends Fragment {

    String[] categories = {"Work", "Home", "Social", "Family"};
    Spinner categorySpinner;
    EditText timeEvent, dateEvent;
    Event existingEvent;
    boolean isEditMode = false;
    public AddEventFragment() {
        // Required empty public constructor
    }
    public static AddEventFragment newInstance(String param1, String param2) {
        AddEventFragment fragment = new AddEventFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);

        EventViewModel viewModel = new ViewModelProvider(requireActivity())
                .get(EventViewModel.class);

        categorySpinner = view.findViewById(R.id.categoryEventFragSpinner);
        timeEvent = view.findViewById(R.id.timeEventFragEditText);
        dateEvent = view.findViewById(R.id.dateEventFragEditText);
        setCategorySpinner();

        //Time picker
        timeEvent.setOnClickListener(v -> {
            Calendar currentTime = Calendar.getInstance();
            int hour = currentTime.get(Calendar.HOUR_OF_DAY);
            int minute = currentTime.get(Calendar.MINUTE);

            TimePickerDialog timePicker = new TimePickerDialog(getContext(),
                    (tp, selectedHour, selectedMinute) -> {
                        timeEvent.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
            }, hour, minute, true);
            timePicker.show();
        });

        //Date picker
        dateEvent.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog datePicker = new DatePickerDialog(getContext(),
                    (dp, year, month, day) -> {
                        dateEvent.setText(day + "/" + (month + 1) + "/" + year);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            datePicker.show();
        });

        AddEventFragmentArgs args = AddEventFragmentArgs.fromBundle(getArguments());
        if (args.getEvent() != null)
        {
            existingEvent = args.getEvent();
            isEditMode = true;
        }

        if (isEditMode)
        {
            //Getting info of event getting edited
            ((EditText) view.findViewById(R.id.titleEventFragEditText))
                    .setText(existingEvent.getTitle());

            ((EditText) view.findViewById(R.id.locationEventFragEditText))
                    .setText(existingEvent.getLocation());

            dateEvent.setText(existingEvent.getDate());
            timeEvent.setText(existingEvent.getTime());
        }

        Button backButton = view.findViewById(R.id.backEventFragButton);
        backButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view); //back using button
            navController.navigateUp(); //goes back using system's back arrow
        });

        Button doneButton = view.findViewById(R.id.doneFragButton);
        doneButton.setOnClickListener(v -> {
            String title = ((EditText) view.findViewById(R.id.titleEventFragEditText)).getText().toString();
            String location = ((EditText) view.findViewById(R.id.locationEventFragEditText)).getText().toString();
            String category = categorySpinner.getSelectedItem().toString();
            String date = dateEvent.getText().toString();
            String time = timeEvent.getText().toString();

            //Error handling: Title
            if (title.isEmpty())
            {
                ((EditText) view.findViewById(R.id.titleEventFragEditText))
                        .setError("Title is required");
                return;
            }

            //Error handling: date
            if (date.isEmpty())
            {
                dateEvent.setError("Date is required");
                return;
            }

            //Error handling: past date
            if (pastDate(date))
            {
                dateEvent.setError("Date cannot be in the past");
                return;
            }

            if (isEditMode)
            {
                existingEvent.setTitle(title);
                existingEvent.setLocation(location);
                existingEvent.setCategory(category);
                existingEvent.setDate(date);
                existingEvent.setTime(time);

                viewModel.update(existingEvent);
            }
            else
            {
                Event event = new Event(title, location, category, date, time);

                viewModel.insert(event);
            }

            Toast.makeText(getContext(), "Event saved", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigateUp(); //going back to dashboard
        });



        return view;
    }

    private void setCategorySpinner()
    {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, categories
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);
    }

    private boolean pastDate(String dateString)
    {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());

            Date selectedDate = sdf.parse(dateString);
            Date today = new Date();

            //remove time from today's date
            sdf = new SimpleDateFormat("d/M/yyy", Locale.getDefault());
            today = sdf.parse(sdf.format(today));

            return selectedDate.before(today);
        }
        catch (Exception e)
        {
            return true; //label invalid date as 'invalid'
        }
    }
}