package com.example.to_do;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseController extends SQLiteOpenHelper {
    static String name = "todo.db";
    static int version = 1;
    String create = "CREATE TABLE IF NOT EXISTS data(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,task VARCHAR(250) NOT NULL,date VARCHAR(30),isCompleted INTEGER NOT NULL,priority VARCHAR(20) NOT NULL)";

    public DatabaseController(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor ReadItems() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM data";
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }

    public long WriteItems(String task, String date, int isCompleted, String priority) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("task", task);
        values.put("date", date);
        values.put("isCompleted", isCompleted);
        values.put("priority", priority);
        long result = database.insert("data", null, values);
        return result;
    }

    public void UpdateItems(int id, String task, String date, int isCompleted, String priority) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("task", task);
        values.put("date", date);
        values.put("isCompleted", isCompleted);
        values.put("priority", priority);
        database.update("data", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public void DeleteItem(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("data", "id = ?", new String[]{String.valueOf(id)});
    }
}
