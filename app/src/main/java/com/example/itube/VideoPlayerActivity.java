package com.example.itube;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class VideoPlayerActivity extends AppCompatActivity implements YouTubePlayerListener {

    private YouTubePlayerView youtubePlayerView;
    private String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        youtubePlayerView = findViewById(R.id.youtube_player_view);

        // Set up ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } else {
            // If ActionBar is null, consider using a Toolbar instead
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Get the video URL from the Intent
        Intent intent = getIntent();
        videoUrl = intent.getStringExtra("VIDEO_URL");

        getLifecycle().addObserver(youtubePlayerView);
        youtubePlayerView.addYouTubePlayerListener(this);
    }


    @Override
    public boolean onSupportNavigateUp() {
        // Handle back button press (navigate back to previous activity)
        onBackPressed();
        return true;
    }

    @Override
    public void onReady(YouTubePlayer youTubePlayer) {
        if (videoUrl != null && !videoUrl.isEmpty()) {
            youTubePlayer.cueVideo(videoUrl, 0);  // Cue video to start at 0 seconds
        } else {
            // Handle case where no video URL is provided
            Toast.makeText(this, "No video URL provided!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onError(YouTubePlayer youTubePlayer, Throwable t) {
        // Handle YouTube Player errors
        Toast.makeText(this, "Error playing video!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onApiChange(@NonNull YouTubePlayer youTubePlayer) {

    }

    @Override
    public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float v) {

    }

    @Override
    public void onError(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerError playerError) {

    }

    @Override
    public void onPlaybackQualityChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackQuality playbackQuality) {

    }

    @Override
    public void onPlaybackRateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlaybackRate playbackRate) {

    }

    @Override
    public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState playerState) {

    }

    @Override
    public void onVideoDuration(@NonNull YouTubePlayer youTubePlayer, float v) {

    }

    @Override
    public void onVideoId(@NonNull YouTubePlayer youTubePlayer, @NonNull String s) {

    }

    @Override
    public void onVideoLoadedFraction(@NonNull YouTubePlayer youTubePlayer, float v) {

    }
}
