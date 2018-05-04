package com.example.subramanyam.popularmoviespart2.database;

import android.provider.BaseColumns;

public class FavoriteContract{
    public class FavouriMovCon implements BaseColumns {
        public static final String TABLE_NAME="favorite";
        public static final String COLUMN_MOVIEID="movieid";
        public static final String COLUMN_TITLE="title";
        public static final String COLUMN_USERRATING="userrating";
        public static final String COLUMN_POSTER_PATH="posterpath";
        public static final String COLUMN_PLOT_REVIEW="overview";
}

}
