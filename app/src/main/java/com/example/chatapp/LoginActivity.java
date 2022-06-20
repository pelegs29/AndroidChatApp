package com.example.chatapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.api.UsersAPI;
import com.example.chatapp.databinding.ActivityLoginBinding;
import com.example.chatapp.entities.User;
import com.example.chatapp.repositories.ConversationRepo;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AlertDialog.Builder alertBuilder;


    public void showAlert(int errorType) {
        runOnUiThread(() -> {
            if (errorType == -2) {
                alertBuilder.setMessage("You have entered wrong credentials, please try again");
            } else {
                alertBuilder.setMessage("Something went wrong, please try again");
            }
            alertBuilder.show();
        });
    }

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
            usersAPI.login(user, this);
        });

        binding.loginTvSignup.setOnClickListener(view -> {
            Intent i = new Intent(this, SignupActivity.class);
            startActivity(i);
        });

        binding.loginEtPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                binding.loginBtnLogin.callOnClick();
                return true;
            }
            return false;
        });
    }

    public void goToMain() {
        runOnUiThread(() -> {
            String name = ConversationRepo.getLoggedUser().getName();
            if (name.isEmpty()) {
                name = ConversationRepo.getLoggedUser().getId();
            }
            String message = "Welcome back, " + name;
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
        Intent i = new Intent(this, ContactsActivity.class);
        startActivity(i);
    }

}