package com.example.doan1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "registration.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTableQuery = "CREATE TABLE "+ TABLE_NAME +" ("+COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_USERNAME+" TEXT, "+COLUMN_PASSWORD+" TEXT, "+COLUMN_EMAIL+" TEXT)";
        db.execSQL(createTableQuery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(String username, String password,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        //check if the username already exists
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE COLUMN_USERNAME=?", new String[]{username});
        if(cursor.moveToFirst()){
            cursor.close();
            return false;
        }
        cursor.close();

        //Insert the new user
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);

        long id = db.insert(TABLE_NAME,null,values);
        db.close();
        return id != -1;
    }
    public boolean checkCredentials(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"username"};
        String selection = "username=? AND password=?";
        String[] selectionArgs = {username,password};
        Cursor cursor = db.query("users",columns,selection,selectionArgs,null,null,null);
        boolean loginSuccessful = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return loginSuccessful;
    }
}
