package com.example.subramanyam.popularmoviespart2;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.subramanyam.popularmoviespart2.adapters.FavoriteAdapter;
import com.example.subramanyam.popularmoviespart2.database.FavoriteContract;

import static com.example.subramanyam.popularmoviespart2.MainActivity.ID_FAVORITES_LOADER;

public class FavoriteCursorLoader implements LoaderManager.LoaderCallbacks<Cursor> {
    private Context context;
    private FavoriteAdapter favoriteAdapter;


    public FavoriteCursorLoader(Context context,FavoriteAdapter favoriteAdapter)
    {
        this.context=context;
        this.favoriteAdapter=favoriteAdapter;
    }
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id)
        {
            case ID_FAVORITES_LOADER:
                String[] projection={
                        FavoriteContract.FavouriMovCon.COLUMN_MOVIEID,
                        FavoriteContract.FavouriMovCon.COLUMN_TITLE,
                        FavoriteContract.FavouriMovCon.COLUMN_PLOT_REVIEW,
                        FavoriteContract.FavouriMovCon.COLUMN_POSTER_PATH,
                        FavoriteContract.FavouriMovCon.COLUMN_USERRATING

                };
                return new CursorLoader(context, FavoriteContract.FavouriMovCon.CONTENT_URI,projection
                ,null,null,null);
                default:
                    throw new RuntimeException("Loader failed" + id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        favoriteAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        favoriteAdapter.swapCursor(null);
    }
}
