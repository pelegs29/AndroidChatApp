package com.example.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.chatapp.databinding.ActivitySignupBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SettingsPageActivity extends AppCompatActivity {

    private SettingsPageActivity binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        Button btnSend = findViewById(R.id.setting_btnsend);
        btnSend.setOnClickListener(v -> {
            finish();
        });

    }

}