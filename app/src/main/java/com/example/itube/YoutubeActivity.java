package com.example.itube;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.itube.data.PlaylistDbHelper;

public class YoutubeActivity extends AppCompatActivity {

    private EditText youtubeUrlField;
    private Button playButton;
    private Button addToPlaylistButton;
    private Button viewPlaylistButton;
    private PlaylistDbHelper playlistDbHelper;
    private static final String SHARED_PREFS_NAME = "user_prefs";
    private static final String KEY_LOGGED_IN_USER_ID = "logged_in_user_id";
    private int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        youtubeUrlField = findViewById(R.id.youtube_url_field);
        playButton = findViewById(R.id.play_button);
        addToPlaylistButton = findViewById(R.id.add_to_playlist_button);
        viewPlaylistButton = findViewById(R.id.view_playlist_button);
        loggedInUserId = getLoggedInUserId();
        // Implement click listeners for buttons

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = youtubeUrlField.getText().toString();

                Intent intent = new Intent(YoutubeActivity.this, VideoPlayerActivity.class);
                intent.putExtra("VIDEO_URL", url);
                startActivity(intent);
            }
        });
        playlistDbHelper = new PlaylistDbHelper(this);

        addToPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = youtubeUrlField.getText().toString();

                // Add URL to playlist for logged-in user
                long newRowId = playlistDbHelper.insertUrl(loggedInUserId, url);

                if (newRowId != -1) {
                    Toast.makeText(YoutubeActivity.this, "Added to Playlist!", LENGTH_SHORT).show();
                    youtubeUrlField.setText("");
                } else {
                    Toast.makeText(YoutubeActivity.this, "Failed to add to Playlist!", LENGTH_SHORT).show();
                }
            }
        });
        viewPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic to navigate to an activity displaying the playlist (if implemented)

                // Example: Navigate to PlaylistActivity (replace with your implementation)
                Intent intent = new Intent(YoutubeActivity.this, PlaylistActivity.class);
                startActivity(intent);
            }
        });

    }
    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_LOGGED_IN_USER_ID, -1);  // -1 as default value if not found
    }
}

