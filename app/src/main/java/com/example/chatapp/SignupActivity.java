package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.api.UsersAPI;
import com.example.chatapp.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signupEtFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFullName();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.signupEtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkUsername();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.signupEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPassword();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.signupEtRePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkRePassword();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.signupTvLogin.setOnClickListener(view -> {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        });

        binding.signupBtnSignup.setOnClickListener(view -> {
            isAllFieldsChecked = CheckAllFields();
            if (isAllFieldsChecked) {
                Intent i = new Intent(this, ConversationActivity.class);
                startActivity(i);
            }

        });
    }

    private boolean checkPassword() {
        EditText passwordBinding = binding.signupEtPassword;
        String password = passwordBinding.getText().toString();

        if (password.length() < 8 || password.length() > 20) {
            passwordBinding.setError("Password must be 8-20 chars long");
            return false;
        }

        if (!password.matches("^.*[a-z]+.*$")) {
            passwordBinding.setError("Password must contain at least one letter");
            return false;
        }

        if (!password.matches("^.*[A-Z]+.*$")) {
            passwordBinding.setError("Password must contain at least one capital letter");
            return false;
        }

        if (!password.matches("^.*[\\d]+.*$")) {
            passwordBinding.setError("Password must contain at least one number");
            return false;
        }

        if (!password.matches("^.*[#?\\[\\]!\\\\@{}.~;/$%+^&*()-]+.*$")) {
            passwordBinding.setError("Password must contain at least one special symbol");
            return false;
        }
        return true;
    }
    private boolean checkFullName() {
        EditText fullnameBinding = binding.signupEtFullName;
        if (fullnameBinding.length() == 0) {
            fullnameBinding.setError("This field is required");
            return false;
        }
        if (!fullnameBinding.getText().toString().matches("[a-zA-Z ]+")) {
            fullnameBinding.setError("Full name must contain only letters and spaces");
            return false;
        }
        return true;
    }
    private boolean checkUsername() {
        EditText username = binding.signupEtUsername;
        if (username.length() == 0) {
            username.setError("This field is required");
            return false;
        }
        if (!username.getText().toString().contains(" ")) {
            username.setError("User name cannot contain spaces");
            return false;
        }
        return true;
    }

    private boolean isUsernameAvailable(){
        UsersAPI usersAPI = new UsersAPI();

        //TODO: add user name find.
        //String response = usersAPI.

        return false;

    }

    private boolean checkRePassword() {
        EditText rePass = binding.signupEtRePass;
        EditText pass = binding.signupEtPassword;
        if (rePass.length() == 0) {
            rePass.setError("This field is required");
            return false;
        }
        if (!rePass.getText().toString().equals(pass.getText().toString())) {
            rePass.setError("Password fields does not match");
            return false;
        }
        return true;
    }
    private boolean CheckAllFields() {
        if (!checkFullName()) {
            return false;
        } else {

        }

        if (!checkUsername()) {
            return false;
        }

        if (!checkPassword()) {
            return false;
        }

        if (!checkRePassword()) {
            return false;
        }

        return true;
    }
}