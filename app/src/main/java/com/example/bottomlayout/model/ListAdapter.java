package com.example.bottomlayout.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomlayout.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private Context context;
    private List<List<WeatherResponse>> weatherLists; // Change to a list of lists

    public ListAdapter(Context context, List<List<WeatherResponse>> weatherLists) {
        this.context = context;
        this.weatherLists = weatherLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        List<WeatherResponse> weatherList = weatherLists.get(position);
        WeatherResponse weather = weatherList.get(0); // Assuming the list contains only one element

        holder.tvCity.setText(weather.getLocation().getCityName());
        holder.tvDegree.setText(weather.getCurrentWeatherInfo().getTemperatureCelsius() + "°C");
        holder.tvWeatherCondition.setText(weather.getCurrentWeatherInfo().getConditionInfo().getConditionText());
    }

    @Override
    public int getItemCount() {
        return weatherLists.size();
    }

    // Method to add new weather data to the existing list
    public void addWeatherData(List<WeatherResponse> newWeatherList) {
        List<WeatherResponse> newList = new ArrayList<>(newWeatherList);
        weatherLists.add(newList);
        notifyDataSetChanged();
    }

    // Method to update the entire list
    public void updateWeatherData(List<List<WeatherResponse>> weatherLists) {
        this.weatherLists = weatherLists;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCity, tvDegree, tvWeatherCondition;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCity = itemView.findViewById(R.id.tvCity);
            tvDegree = itemView.findViewById(R.id.tvDegree);
            tvWeatherCondition = itemView.findViewById(R.id.tvWeatherCondition);
        }
    }
}
