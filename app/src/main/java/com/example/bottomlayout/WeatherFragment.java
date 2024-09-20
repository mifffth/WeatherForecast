package com.example.bottomlayout;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomlayout.databinding.FragmentWeatherBinding;
import com.example.bottomlayout.model.ListAdapter;
import com.example.bottomlayout.model.WeatherResponse;
import com.example.bottomlayout.network.ApiClient;
import com.example.bottomlayout.network.ApiService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {

    private static final String TAG = WeatherFragment.class.getSimpleName();
    private FragmentWeatherBinding binding;
    private ListAdapter adapter;
    private List<String> cities = new ArrayList<>();
    private List<List<WeatherResponse>> weatherDataLists = new ArrayList<>();
    private RecyclerView recyclerView;
    private EditText cityEditText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();

        cities.addAll(Arrays.asList("Jakarta", "Surabaya", "Malang", "Semarang", "Palembang"));

        callWeatherApi();

        binding.btnAddCity.setOnClickListener(v -> {
            String city = cityEditText.getText().toString().trim();
            if (!city.isEmpty()) {
                addCity(city);
                cityEditText.setText("");
            }
        });

    }

    private void initViews() {
        recyclerView = binding.rvWeather;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        cityEditText = binding.etCity;

        adapter = new ListAdapter(requireContext(), weatherDataLists);
        recyclerView.setAdapter(adapter);
    }

    public void addCity(String city) {
        if (!cities.contains(city)) {
            cities.add(city);
            callWeatherApi();
        }
    }

    private void callWeatherApi() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Clear existing weather data lists
        weatherDataLists.clear();

        for (String city : cities) {
            Call<WeatherResponse> call = apiService.getWeather(
                    ApiClient.getApiKey(),
                    city,
                    5,
                    "no",
                    "no"
            );

            call.enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    handleWeatherResponse(response, city);
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                    handleWeatherFailure(t);
                }
            });
        }
    }


    private void handleWeatherResponse(Response<WeatherResponse> response, String city) {
        if (response.isSuccessful()) {
            WeatherResponse weatherResponse = response.body();
            if (weatherResponse != null) {
                List<WeatherResponse> weatherList = Collections.singletonList(weatherResponse);
                weatherDataLists.add(0, weatherList);
            }
        } else {
            Log.e(TAG, "API call unsuccessful. Code: " + response.code());
        }

        if (weatherDataLists.size() == cities.size()) {
            adapter.updateWeatherData(weatherDataLists);
        }
    }

    private void handleWeatherFailure(Throwable t) {
        t.printStackTrace();
        Log.e(TAG, "API call failed: " + t.getMessage());
    }


    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

}
