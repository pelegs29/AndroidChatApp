package com.example.chatapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.api.UsersAPI;
import com.example.chatapp.databinding.ActivitySignupBinding;
import com.example.chatapp.entities.User;
import com.example.chatapp.repositories.ConversationRepo;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    boolean isAllFieldsChecked = false;
    private AlertDialog.Builder alertBuilder;
    private UsersAPI usersAPI;
    Bitmap bitmap;

    public void showAlert() {
        runOnUiThread(() -> {
            alertBuilder.setMessage("Something went wrong, please try again");
            alertBuilder.show();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bitmap= null;

        usersAPI = new UsersAPI();

        alertBuilder = new AlertDialog.Builder(SignupActivity.this);
        alertBuilder.setCancelable(true);
        alertBuilder.setIcon(R.drawable.ic_warning);
        alertBuilder.setTitle("Opps...");
        alertBuilder.setPositiveButton("OK", (dialog, which) -> dialog.cancel());

        setListeners();
    }

    private void setListeners() {
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


        binding.signupBtnPickImage.setOnClickListener(v -> {
            Intent GalleryIntent = new Intent(Intent.ACTION_PICK);
            GalleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(GalleryIntent, 1);

        });


        binding.signupEtUsername.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                UsersAPI usersAPI = new UsersAPI();
                usersAPI.findUsername(binding.signupEtUsername.getText().toString(), this);
            }
        });

        binding.signupTvLogin.setOnClickListener(view -> {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        });

        binding.signupBtnSignup.setOnClickListener(view -> {
            isAllFieldsChecked = CheckAllFields();
            if (isAllFieldsChecked) {
                String fullname = binding.signupEtFullName.getText().toString();
                String username = binding.signupEtUsername.getText().toString();
                String password = binding.signupEtPassword.getText().toString();
                ConversationRepo.uploadProfilePic(username,bitmap);
                User user = new User(fullname, username, password);
                usersAPI.addUser(user, this);
            }
        });
    }

    public void goToMain() {
        runOnUiThread(() -> {
            String name = ConversationRepo.getLoggedUser().getName();
            if (name.isEmpty()) {
                name = ConversationRepo.getLoggedUser().getId();
            }
            String message = "Welcome, " + name;
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
        Intent i = new Intent(this, ContactsActivity.class);
        startActivity(i);
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
        if (username.getText().toString().contains(" ")) {
            username.setError("User name cannot contain spaces");
            return false;
        }
        return true;
    }

    public void usernameTaken() {
        binding.signupEtUsername.setError("This username is taken");
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
        }

        if (!checkUsername()) {
            return false;
        } else if (binding.signupEtUsername.getError() != null) {
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

    //the function set when the user pick an image from the gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                if(data != null){
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //set the signupBtnPickImage color tint to light green
            binding.signupBtnPickImage.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF66BB6A")));
        }
    }
}