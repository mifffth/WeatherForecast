package com.example.bottomlayout;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link AppCompatActivity} subclass.
 * Create an instance of this class.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    // class & variable declaration
    private GoogleMap mMap;
    private ImageView zoomInButton;
    private ImageView zoomOutButton;
    private ImageView getLocationButton;

    private static final int Request_CODE = 101;
    private Boolean ok = false;

    // create the activity layout and location permission

        // buat tampilan aktivitas sama izin lokasi
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.map_view);

            // Check if the user has granted the ACCESS_FINE_LOCATION permission.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // The user has granted the permission.
                requestLocationUpdates();
            } else {
                // The user has not granted the permission.
                // Request the permission from the user.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            }

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            // Get the zoom in and out buttons.
            zoomInButton = findViewById(R.id.zoom_in_button);
            zoomOutButton = findViewById(R.id.zoom_out_button);

            // Set listeners on the zoom in and out buttons.
            zoomInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Zoom in on the map.
                    mMap.animateCamera(CameraUpdateFactory.zoomIn());
                }
            });

            zoomOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Zoom out on the map.
                    mMap.animateCamera(CameraUpdateFactory.zoomOut());
                }
            });

            getLocationButton = findViewById(R.id.show_current_location_button);

            // Set a click listener on the location button.
            getLocationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Call the method to handle getting the location.
                    getLocation();
                }
            });

            // Request location updates
            requestLocationUpdates();
        }

        // Method buat ambil lokasi terakhir
        // Method to get and display current location and city
        private void getLocation() {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                return;
            }

            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                double latitude = lastKnownLocation.getLatitude();
                double longitude = lastKnownLocation.getLongitude();

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (!addresses.isEmpty()) {
                        String cityName = addresses.get(0).getLocality();
                        String countryName = addresses.get(0).getCountryName();

                        String title = String.format(Locale.getDefault(), "[%.6f, %.6f], %s, %s", latitude, longitude, cityName, countryName);

                        LatLng currentLocation = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(currentLocation).title(title));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    // Method request lokasi terbaru
        private void requestLocationUpdates() {
            try {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        if (ok) {
                            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

                            // Use the city name for the marker title
                            String title = String.format(Locale.getDefault(), "[%.6f, %.6f]", location.getLatitude(), location.getLongitude());

                            mMap.addMarker(new MarkerOptions().position(currentLocation).title(title));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                        }
                    }


                    @Override
                    public void onProviderEnabled(@NonNull String provider) {
                    }

                    @Override
                    public void onProviderDisabled(@NonNull String provider) {
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }
                });
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        // menanggapi izin boleh/gk
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // The user granted the ACCESS_FINE_LOCATION permission.
                requestLocationUpdates();
            } else {
                // The user denied the ACCESS_FINE_LOCATION permission.
                // Handle the case where the user denies the permission.
            }
        }

        // inisialisasi peta
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            // Add a marker in Sydney and move the camera
            LatLng yogya = new LatLng(-7.7956, 110.3695); // Change to Jogja coordinates
            mMap.addMarker(new MarkerOptions().position(yogya).title("Marker in Yogyakarta"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(yogya));

            // Set the map's long click listener
            setPoiClick();
            setMapOnLongClick();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.map_options, menu);
            return true;
        }

        // nanggepin pilihan menu
        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int mapType = GoogleMap.MAP_TYPE_NORMAL;

            if (item.getItemId() == R.id.normal_map) {
                mapType = GoogleMap.MAP_TYPE_NORMAL;
            } else if (item.getItemId() == R.id.satellite_map) {
                mapType = GoogleMap.MAP_TYPE_SATELLITE;
            } else if (item.getItemId() == R.id.terrain_map) {
                mapType = GoogleMap.MAP_TYPE_TERRAIN;
            }

            mMap.setMapType(mapType);
            return true;
        }

    private void setMapOnLongClick() {
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                // Use an AsyncTask to perform reverse geocoding in the background
                new ReverseGeocodingTask().execute(latLng);
            }
        });
    }

    private class ReverseGeocodingTask extends AsyncTask<LatLng, Void, String> {
        private LatLng latLng;

        @Override
        protected String doInBackground(LatLng... params) {
            Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
            latLng = params[0];

            try {
                List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                if (!addresses.isEmpty()) {
                    String cityName = addresses.get(0).getLocality();
                    String countryName = addresses.get(0).getCountryName();
                    return String.format(Locale.getDefault(), "[%f, %f], %s, %s", latLng.latitude, latLng.longitude, cityName, countryName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                // Add a marker with the retrieved information
                mMap.addMarker(new MarkerOptions().position(latLng).title(result));
            }
        }
    }

    //nambahin poi di lokasi
        private void setPoiClick() {
            mMap.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
                @Override
                public void onPoiClick(@NonNull PointOfInterest pointOfInterest) {
                    Marker poiMarker = mMap.addMarker(new MarkerOptions()
                            .position(pointOfInterest.latLng)
                            .title(pointOfInterest.name));
                    poiMarker.showInfoWindow();
                }
            });
        }


    }


