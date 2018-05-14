package com.example.subramanyam.popularmoviespart2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavMovDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorite.db";

    private static final int DATABASE_VERSION =1 ;

    public static final String LOGTAG = "FAVORITE";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public FavMovDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        Log.i(LOGTAG, "database opened");
        db = dbhandler.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG, "databaseclosed");
        dbhandler.close();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavoriteContract.FavouriMovCon.TABLE_NAME + " (" +
                FavoriteContract.FavouriMovCon._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteContract.FavouriMovCon.COLUMN_MOVIEID + " INTEGER," +
                FavoriteContract.FavouriMovCon.COLUMN_TITLE + " TEXT NOT NULL," +
                FavoriteContract.FavouriMovCon.COLUMN_USERRATING + " REAL NOT NULL," +
                FavoriteContract.FavouriMovCon.COLUMN_POSTER_PATH + " TEXT NOT NULL," +
                FavoriteContract.FavouriMovCon.COLUMN_PLOT_REVIEW + " TEXT NOT NULL" +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavouriMovCon.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }




}