package com.example.itube;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.itube.data.UserDbHelper;

public class SignupActivity extends AppCompatActivity {

    private EditText fullNameField;
    private EditText usernameField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private Button createAccountButton;
    private UserDbHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullNameField = findViewById(R.id.full_name_field);
        usernameField = findViewById(R.id.username_field);
        passwordField = findViewById(R.id.password_field);
        confirmPasswordField = findViewById(R.id.confirm_password_field);
        createAccountButton = findViewById(R.id.create_account_button);

        // Implement click listener for Create Account button
        databaseHelper = new UserDbHelper(this);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameField.getText().toString();
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                String confirmPassword = confirmPasswordField.getText().toString();

                // Input validation (optional)
                if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || !password.equals(confirmPassword)) {
                    // Show error message for missing or mismatched fields
                    return;
                }

                // Insert user into database
                long newRowId = databaseHelper.insertUser(fullName, username, password);

                if (newRowId != -1) {
                    // Signup successful (navigate to login activity or show success message)
                    Toast.makeText(SignupActivity.this, "Sign Up successful!", Toast.LENGTH_SHORT).show();
                    // Option 1: Clear input fields
                    fullNameField.setText("");
                    usernameField.setText("");
                    passwordField.setText("");
                    confirmPasswordField.setText("");

                    // Option 2: Navigate to Login Activity
                    Intent loginIntent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(loginIntent);
                } else {
                    // Signup failed (show error message, e.g., username might already exist)
                    Toast.makeText(SignupActivity.this, "Sign Up failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
