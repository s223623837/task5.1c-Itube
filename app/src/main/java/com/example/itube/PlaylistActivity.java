package com.example.itube;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.itube.data.Playlist;
import com.example.itube.data.PlaylistAdapter;
import com.example.itube.data.PlaylistDbHelper;

import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    private ListView playlistListView;
    private PlaylistDbHelper playlistDbHelper;
    private static final String SHARED_PREFS_NAME = "user_prefs";
    private static final String KEY_LOGGED_IN_USER_ID = "logged_in_user_id";
    private int loggedInUserId;
    private PlaylistAdapter playlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        playlistListView = findViewById(R.id.playlist_list_view);

        playlistDbHelper = new PlaylistDbHelper(this);

        // Retrieve logged-in user ID from Shared Preferences (assuming MainActivity saves it)
        loggedInUserId = getLoggedInUserId();

        // Get playlist for logged-in user
        List<Playlist> playlist = playlistDbHelper.getPlaylist(loggedInUserId);
        System.out.println(playlist);
        // Create and set adapter for the playlist list view
        playlistAdapter = new PlaylistAdapter(this, playlist);
        playlistListView.setAdapter(playlistAdapter);
        // Handle click on playlist item
        playlistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked Playlist object
                Playlist selectedPlaylist = playlistAdapter.getItem(position);

                // Extract the video URL from the Playlist
                String videoUrl = selectedPlaylist.getUrl();

                // Navigate to VideoPlayerActivity with the video URL as an extra
                Intent intent = new Intent(PlaylistActivity.this, VideoPlayerActivity.class);
                intent.putExtra("VIDEO_URL", videoUrl);
                startActivity(intent);
            }
        });
    }

    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_LOGGED_IN_USER_ID, -1);  // -1 as default value if not found
    }
}
