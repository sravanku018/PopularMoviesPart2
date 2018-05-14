package com.example.subramanyam.popularmoviespart2.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    private FavMovDBHelper favMovDBHelper;

    private static final int MOVIE = 1001;
    private static final int MOVIE_WITH_ID = 1002;


    private static final UriMatcher uriMatcher = buildUriMatcher();

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = favMovDBHelper.getWritableDatabase();
        int mstch = uriMatcher.match(uri);
        int rowDeleted = 0;
        switch (mstch) {
            case MOVIE:
                rowDeleted = sqLiteDatabase.delete(FavoriteContract.FavouriMovCon.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                sqLiteDatabase.close();
                throw new UnsupportedOperationException("couldn't delete !");
        }
        if (rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        sqLiteDatabase.close();
        return rowDeleted;
    }

    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase sqLiteDatabase = favMovDBHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case MOVIE:
                long id = sqLiteDatabase.insert(FavoriteContract.FavouriMovCon.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(FavoriteContract.BASE_CONTENT_URI, id);
                } else {
                    sqLiteDatabase.close();
                    throw new UnsupportedOperationException("Failed to insert insert row" + uri);
                }
                break;
            default:
                sqLiteDatabase.close();
                throw new UnsupportedOperationException("Unknown uri" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        sqLiteDatabase.close();
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        favMovDBHelper = new FavMovDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase sqLiteDatabase = favMovDBHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        Cursor cursor;

        switch (match) {
            case MOVIE:

                cursor = sqLiteDatabase.query(FavoriteContract.FavouriMovCon.TABLE_NAME, projection, selection, selectionArgs, null
                        , null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);


        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    public static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavoriteContract.CONTENT_AUTHORITY, FavoriteContract.PATH_MOVIES, MOVIE);
        uriMatcher.addURI(FavoriteContract.CONTENT_AUTHORITY, FavoriteContract.PATH_MOVIES + "/#", MOVIE_WITH_ID);
        return uriMatcher;
    }
}
