package com.example.itube.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "playlist_database.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "playlist";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_URL = "url";

    private static final String CREATE_PLAYLIST_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_USER_ID + " INTEGER REFERENCES users(id) ON DELETE CASCADE," +  // Reference users table
                    COLUMN_URL + " TEXT NOT NULL" + ")";

    public PlaylistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PLAYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database schema upgrades if needed
    }

    public long insertUrl(int userId, String url) {
        SQLiteDatabase db = getWritableDatabase();
        long newRowId = -1;  // Default value if insertion fails

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID, userId);
            values.put(COLUMN_URL, url);

            newRowId = db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            // Handle any exceptions that may occur during insertion
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.close();  // Close the database connection
            }
        }

        return newRowId;
    }


    public List<Playlist> getPlaylist(int userId) {
        List<Playlist> playlist = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") int retrievedUserId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
                @SuppressLint("Range") String url = cursor.getString(cursor.getColumnIndex(COLUMN_URL));
                playlist.add(new Playlist(id, retrievedUserId, url));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return playlist;
    }
}
