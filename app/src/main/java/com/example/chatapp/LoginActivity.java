package com.example.chatapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.api.UsersAPI;
import com.example.chatapp.databinding.ActivityLoginBinding;
import com.example.chatapp.entities.User;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UsersAPI usersAPI = new UsersAPI();

        alertBuilder = new AlertDialog.Builder(LoginActivity.this);
        alertBuilder.setCancelable(true);
        alertBuilder.setIcon(R.drawable.ic_warning);
        alertBuilder.setTitle("Opps...");
        alertBuilder.setPositiveButton("OK", (dialog, which) -> dialog.cancel());


        binding.loginBtnLogin.setOnClickListener(view -> {
            String username = binding.loginEtUsername.getText().toString();
            String password = binding.loginEtPassword.getText().toString();
            if (username.isEmpty() || password.isEmpty()) {
                alertBuilder.setMessage("Please enter valid username and password");
                alertBuilder.show();
                return;
            }
            User user = new User(username, password);
            usersAPI.login(user, this, alertBuilder);
        });

        binding.loginTvSignup.setOnClickListener(view -> {
            Intent i = new Intent(this, SignupActivity.class);
            startActivity(i);
        });
    }


}