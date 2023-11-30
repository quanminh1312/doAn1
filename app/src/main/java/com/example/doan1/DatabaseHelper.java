package com.example.doan1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "registration.db";
    static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    public static final String TABLE_READING_HISTORY = "reading_history";
    public static final String COLUMN_READ_ID = "read_id";
    public static final String COLUMN_STORY_NAME = "story_name";
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME +  "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_EMAIL + " TEXT)";

    private static final String CREATE_READING_HISTORY = "CREATE TABLE " +
            TABLE_READING_HISTORY + "(" +
            COLUMN_READ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERNAME + " TEXT, " +
            COLUMN_STORY_NAME + " TEXT)";
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_READING_HISTORY);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_READING_HISTORY);
        onCreate(db);
    }

    public boolean addUser(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Check if the username already exists
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + "=?", new String[]{username});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false; // Username already exists
        }
        cursor.close();

        // Insert the new user
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);

        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id != -1;
    }

    public boolean checkCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USERNAME};
        String selection = COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};

        try {
            Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            if (cursor.getCount() > 0) {
                Log.d("DatabaseHelper", "Login successful for user: " + username);
                return true;
            } else {
                Log.d("DatabaseHelper", "Login failed for user: " + username);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }
    // Add a reading record to the database
    public void addReadingRecord(String username, String storyName) {
        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_USERNAME, username);
            values.put(DatabaseHelper.COLUMN_STORY_NAME, storyName);
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLE_READING_HISTORY, null, values);
            db.close();
        }
        catch (Exception e){}
    }

    // Retrieve reading history
    public List<String> getMangaNamesByUsername(String username) {
        List<String> mangaNames = new ArrayList<>();
        try {
            // Truy vấn cơ sở dữ liệu để lấy tất cả các manganame theo username
            String[] columns = {COLUMN_STORY_NAME};
            String selection = COLUMN_USERNAME + "=?";
            String[] selectionArgs = {username};

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(
                    TABLE_READING_HISTORY,
                    columns,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String mangaName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STORY_NAME));
                    mangaNames.add(mangaName);
                } while (cursor.moveToNext());

                cursor.close();
            }

            db.close();
        }
        catch (Exception e){}
            return mangaNames;
    }
};

