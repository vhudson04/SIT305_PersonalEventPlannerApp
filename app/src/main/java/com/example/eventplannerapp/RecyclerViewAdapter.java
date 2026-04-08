package com.example.eventplannerapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Event> eventList;
    private EventDashboardFragment fragment;
    private int selectedPosition = -1;

    public RecyclerViewAdapter(List<Event> eventList, EventDashboardFragment fragment)
    {
        this.eventList = eventList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.titleCardTextView.setText(eventList.get(position).getTitle());
        holder.locationCardTextView.setText(eventList.get(position).getLocation());
        holder.categoryCardTextView.setText(eventList.get(position).getCategory());
        holder.dateCardTextView.setText(eventList.get(position).getDate());
        holder.timeCardTextView.setText(eventList.get(position).getTime());
        holder.itemView.setOnClickListener(v -> {
            //selectedPosition = holder.getAdapterPosition();
            int eventPosition = holder.getAdapterPosition();
            if (eventPosition == selectedPosition)
            {
                selectedPosition = -1;
                fragment.onItemClicked(null);
            }
            else
            {
                selectedPosition = eventPosition;
                fragment.onItemClicked(event);
            }
            notifyDataSetChanged();
        });

        //Selection highlighting
        if (position == selectedPosition)
        {
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void updateList(List<Event> newList)
    {
        eventList = newList;
        notifyDataSetChanged();
    }

    public void clearSelection()
    {
        selectedPosition = -1;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleCardTextView;
        TextView locationCardTextView;
        TextView categoryCardTextView;
        TextView dateCardTextView;
        TextView timeCardTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleCardTextView = itemView.findViewById(R.id.titleCardTextView);
            locationCardTextView = itemView.findViewById(R.id.locationCardTextView);
            categoryCardTextView = itemView.findViewById(R.id.categoryCardTextView);
            dateCardTextView = itemView.findViewById(R.id.dateCardTextView);
            timeCardTextView = itemView.findViewById(R.id.timeCardTextView);
        }
    }
}
