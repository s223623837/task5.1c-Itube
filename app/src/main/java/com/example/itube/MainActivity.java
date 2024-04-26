package com.example.itube;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itube.data.UserDbHelper;


public class MainActivity extends AppCompatActivity {

        private EditText usernameField;
        private EditText passwordField;
        private Button loginButton;
        private Button signupButton;
    private static final String SHARED_PREFS_NAME = "user_prefs";
    private static final String KEY_LOGGED_IN_USER_ID = "logged_in_user_id";

    private UserDbHelper databaseHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            usernameField = findViewById(R.id.username_field);
            passwordField = findViewById(R.id.password_field);
            loginButton = findViewById(R.id.login_button);
            signupButton = findViewById(R.id.signup_button);

            databaseHelper = new UserDbHelper(this);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = usernameField.getText().toString();
                    String password = passwordField.getText().toString();

                    if (databaseHelper.validateLogin(username, password)) {
                        // Login successful (navigate to main activity or show success message)
                       Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
//                        // Save logged-in user ID to Shared Preferences
                        int loggedInUserId = databaseHelper.getUserId(username);  // Assuming getUserId retrieves user ID
                        saveLoggedInUserId(loggedInUserId);
//                        // Navigate to YoutubeActivity
                        Intent intent = new Intent(MainActivity.this, YoutubeActivity.class);
                        startActivity(intent);

                    } else {
                        // Login failed (show error message)
                        Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            signupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Redirect to Sign Up Activity (replace with your implementation)
                    Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                    startActivity(intent);
                }
            });
        }

    private void saveLoggedInUserId(int userId) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_LOGGED_IN_USER_ID, userId);
        editor.apply();  // or editor.commit() (commit is synchronous, apply is asynchronous)
    }
    }
