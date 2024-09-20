package com.example.bottomlayout;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.bottomlayout.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        NavController navController =
                NavHostFragment.findNavController(getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_container));
        NavigationUI.setupWithNavController(binding.bottomNavigationView,
                navController);
        setContentView(binding.getRoot());
    }
}