package com.project.note.android.zero.mynote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/8/13.
 */
public class NotedbHelper extends SQLiteOpenHelper{
    public static final String TABLE_NAME="my_note";
    public static final String ID="_id";
    public static final String CONTENT="words";
    public static final String TIME="time";
    public static final String IMAGE_PATH="image";
    public static final String VIDEO_PATH="video";


    public NotedbHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         String sql="CREATE TABLE "+TABLE_NAME +"("
                +ID+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                +CONTENT+" TEXT NOT NULL,"
                +IMAGE_PATH+ " TEXT,"
                +VIDEO_PATH+" TEXT,"+TIME +" TEXT NOT NULL);";
        db.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
