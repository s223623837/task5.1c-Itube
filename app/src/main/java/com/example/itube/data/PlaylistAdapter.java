package com.example.itube.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.itube.R;

import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {

    private Context context;
    private List<Playlist> playlist;

    public PlaylistAdapter(Context context, List<Playlist> playlist) {
        super(context, R.layout.playlist_item_layout, playlist);  // Replace with your item layout resource ID
        this.context = context;
        this.playlist = playlist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.playlist_item_layout, parent, false);

        TextView urlTextView = itemView.findViewById(R.id.playlist_url_text_view);

        Playlist currentPlaylist = playlist.get(position);
        urlTextView.setText(currentPlaylist.getUrl());

        // You can add click listeners or other functionalities to playlist items here

        return itemView;
    }
}

