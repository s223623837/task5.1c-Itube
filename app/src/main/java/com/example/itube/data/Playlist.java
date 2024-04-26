package com.example.itube.data;

public class Playlist {

    private int id;
    private int userId;
    private String url;

    public Playlist(int id, int userId, String url) {
        this.id = id;
        this.userId = userId;
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    // Getters and setters (optional)
}
