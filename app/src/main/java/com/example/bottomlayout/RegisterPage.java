package com.example.bottomlayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class RegisterPage extends Fragment {

    public RegisterPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_page, container, false);

        // Find the UI elements in the fragment's layout
        Button logInButton = view.findViewById(R.id.logInButton);

        // Set an OnClickListener for the "Log In" button
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the second fragment by switching to the second tab
                // Assuming you have a ViewPager2, uncomment the line below if needed
                // viewPager2.setCurrentItem(1);
            }
        });

        // Set an OnClickListener for the "Create Account" button
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the ProfileFragment using the Navigation component
                Navigation.findNavController(v).navigate(R.id.action_registerPage_to_profileFragment);
            }
        });

        return view;
    }
}
