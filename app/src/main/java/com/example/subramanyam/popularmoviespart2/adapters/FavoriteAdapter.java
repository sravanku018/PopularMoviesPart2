package com.example.subramanyam.popularmoviespart2.adapters;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.subramanyam.popularmoviespart2.DetailsView;
import com.example.subramanyam.popularmoviespart2.R;
import com.example.subramanyam.popularmoviespart2.data.MovieItem;
import com.example.subramanyam.popularmoviespart2.database.FavoriteContract;
import com.squareup.picasso.Picasso;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>  {
    private Cursor cursor;


    public FavoriteAdapter()
    {

    }

    public void swapCursor(Cursor cursor1)
    {

        cursor=cursor1;
        if(cursor1 != null)
        {
            this.notifyDataSetChanged();
        }

    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        ImageView favImage;
        ViewHolder(View itemView) {
            super(itemView);
            favImage=itemView.findViewById(R.id.imageView2);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(itemView.getContext(), DetailsView.class);
            cursor.moveToPosition(getAdapterPosition());
            MovieItem item=getMovieFromCursor();
            intent.putExtra("movieDetails", String.valueOf(item));
            v.getContext().startActivity(intent);

        }
        private MovieItem getMovieFromCursor()
        {
            MovieItem favMovie=new MovieItem();
            favMovie.setTitle(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavouriMovCon.COLUMN_TITLE)));
            favMovie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(FavoriteContract.FavouriMovCon.COLUMN_USERRATING)));
            favMovie.setPosterPath(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavouriMovCon.COLUMN_POSTER_PATH)));
            favMovie.setOverview(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavouriMovCon.COLUMN_PLOT_REVIEW)));
            favMovie.setId(cursor.getInt(cursor.getColumnIndex(FavoriteContract.FavouriMovCon.COLUMN_MOVIEID)));
            return favMovie;
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movielist_favorite,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cursor.moveToPosition(position);

        String posterPath=cursor.getString(cursor.getColumnIndex(FavoriteContract.FavouriMovCon.COLUMN_POSTER_PATH));
        Picasso.with(holder.favImage.getContext()).load("http://image.tmdb.org/t/p/w185/"+posterPath).into(holder.favImage);

    }

    @Override
    public int getItemCount() {
       if(cursor==null)
       {
           return 0;
       }

       return cursor.getCount();
    }


}
