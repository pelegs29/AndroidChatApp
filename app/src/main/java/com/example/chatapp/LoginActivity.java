package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.api.UsersAPI;
import com.example.chatapp.databinding.ActivityLoginBinding;
import com.example.chatapp.entities.User;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UsersAPI usersAPI = new UsersAPI();

        binding.loginBtnLogin.setOnClickListener(view -> {
            String username = binding.loginEtUsername.getText().toString();
            String password = binding.loginEtPassword.getText().toString();
            User user = new User(username, password);
            usersAPI.login(user);
        });

        binding.loginTvSignup.setOnClickListener(view -> {
            Intent i = new Intent(this, SignupActivity.class);
            startActivity(i);
        });
    }
}