package com.example.memorytrainer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "coursework";

    public static final String RESULTS_TABLE_NAME = "results";
    public static final String RESULTS_KEY_ID = "id";
    public static final String RESULTS_KEY_TIME_RESULT = "time_result";
    public static final String RESULTS_KEY_TYPE_OF_GAME = "type_game";
    public static final String RESULTS_KEY_SIZE_OF_GAME = "size_game";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + RESULTS_TABLE_NAME + "("
                + RESULTS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RESULTS_KEY_TIME_RESULT + " TEXT NOT NULL, "
                + RESULTS_KEY_TYPE_OF_GAME + " INTEGER NOT NULL, "
                + RESULTS_KEY_SIZE_OF_GAME + " INTEGER NOT NULL" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }
}
