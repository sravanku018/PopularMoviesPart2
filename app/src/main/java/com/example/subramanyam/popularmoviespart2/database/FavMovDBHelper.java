package com.example.subramanyam.popularmoviespart2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.subramanyam.popularmoviespart2.data.MovieItem;

import java.util.ArrayList;

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

    public void addFavorite(MovieItem movieData) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(FavoriteContract.FavouriMovCon.COLUMN_MOVIEID,movieData.getId());
        contentValues.put(FavoriteContract.FavouriMovCon.COLUMN_TITLE,movieData.getTitle());
        contentValues.put(FavoriteContract.FavouriMovCon.COLUMN_USERRATING,movieData.getVoteAverage());
        contentValues.put(FavoriteContract.FavouriMovCon.COLUMN_POSTER_PATH,movieData.getPosterPath());
        contentValues.put(FavoriteContract.FavouriMovCon.COLUMN_PLOT_REVIEW,movieData.getOverview());

        db.insert(FavoriteContract.FavouriMovCon.TABLE_NAME,null, contentValues);
        db.close();


    }
    public void deleteFavorite(int id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(FavoriteContract.FavouriMovCon.TABLE_NAME,FavoriteContract.FavouriMovCon.COLUMN_MOVIEID+ "=" + id, null);

    }

    public ArrayList<MovieItem> getAllFavorite()
    {
        String[] columns= {
                FavoriteContract.FavouriMovCon.COLUMN_MOVIEID,
                FavoriteContract.FavouriMovCon.COLUMN_TITLE,
                FavoriteContract.FavouriMovCon.COLUMN_USERRATING,
                FavoriteContract.FavouriMovCon.COLUMN_POSTER_PATH,
                FavoriteContract.FavouriMovCon.COLUMN_PLOT_REVIEW
        };
        String sortOrder=FavoriteContract.FavouriMovCon._ID+ " ASC ";
             ArrayList<MovieItem> favorite1=new ArrayList<>();

             SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(FavoriteContract.FavouriMovCon.TABLE_NAME,
                columns,null,null,null,null,sortOrder);
        if(cursor.moveToFirst()){
            do {
                MovieItem movieData=new MovieItem();
                movieData.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavouriMovCon.COLUMN_MOVIEID))));
                movieData.setTitle(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavouriMovCon.COLUMN_TITLE)));
                movieData.setVoteAverage( Double.parseDouble(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavouriMovCon.COLUMN_USERRATING))));
                movieData.setPosterPath(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavouriMovCon.COLUMN_POSTER_PATH)));
                movieData.setOverview(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavouriMovCon.COLUMN_POSTER_PATH)));

                favorite1.add(movieData);


            }while (cursor.moveToNext());
            cursor.close();
            db.close();

        }
        return favorite1;

    }

}