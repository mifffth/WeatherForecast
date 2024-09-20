package com.example.bottomlayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Get references to UI elements
        EditText editTextUsername = view.findViewById(R.id.edit_text_username);
        EditText editTextPassword = view.findViewById(R.id.edit_text_password);
        Button buttonLogin = view.findViewById(R.id.login_btn);

        // Set OnClickListener for the login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve username/email and password from EditText widgets
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Check if the username and password match the expected values
                if (username.equals("kucing") && password.equals("gato123")) {
                    // If the login is successful, show a toast message
                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();

                    // TODO: Navigate to another fragment or perform other actions for a successful login
                } else {
                    // If the login fails, show a toast message
                    Toast.makeText(getActivity(), "Username or Password Incorrect", Toast.LENGTH_SHORT).show();
                }

                Log.d("Login", "Username: " + username);
                Log.d("Login", "Password: " + password);

            }
        });

        Button createAccountButton = view.findViewById(R.id.CreateAccountButton);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the first fragment by switching to the first tab (assuming the first tab is at position 0)
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_registerPage);
            }
        });

        return view;
    }
}