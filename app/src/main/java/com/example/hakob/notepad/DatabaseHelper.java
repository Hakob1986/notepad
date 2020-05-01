package com.example.hakob.notepad;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.graphics.Canvas;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "NoteUsers1"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    public  static final String TABLE = "user"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMID = "_id";
    public static final String COLUMTITLE = "title";
    public static final String COLUMDATE = "date";
    public static final String COLUMTEXT = "text";

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE user (" + COLUMID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMTITLE
                + " TEXT, " + COLUMDATE + " TEXT, " + COLUMTEXT + " TEXT);");
        // добавление начальных данных



    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

}