package com.example.subramanyam.popularmoviespart2;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subramanyam.popularmoviespart2.adapters.ReviewView;
import com.example.subramanyam.popularmoviespart2.adapters.TrailerView;
import com.example.subramanyam.popularmoviespart2.api.Client;
import com.example.subramanyam.popularmoviespart2.api.Service;
import com.example.subramanyam.popularmoviespart2.data.MovieItem;
import com.example.subramanyam.popularmoviespart2.data.ReviewData;
import com.example.subramanyam.popularmoviespart2.data.ReviewResponse;
import com.example.subramanyam.popularmoviespart2.data.TrailerData;
import com.example.subramanyam.popularmoviespart2.data.TrailerResponse;
import com.example.subramanyam.popularmoviespart2.database.FavMovDBHelper;
import com.example.subramanyam.popularmoviespart2.database.FavoriteContract;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsView extends AppCompatActivity {

    String movieImages, title1, releaseDate1, ratings1, review1;


    @BindView(R.id.movieImage)
    ImageView movieImage;
    @BindView(R.id.titleView)
    TextView title;
    @BindView(R.id.releaseDate)
    TextView releaseDate;
    @BindView(R.id.ratings)
    TextView ratings;
    @BindView(R.id.review)
    TextView review;
    @BindView(R.id.trailerView)
    RecyclerView trailerRecycler;
    @BindView(R.id.reviewsView)
    RecyclerView reviewRecycler;
    List<TrailerData> trailerData;
    List<ReviewData> reviewData;
    TrailerView trailerView;
    ReviewView reviewView;


    private final DetailsView activity=DetailsView.this;
    private MovieItem favorite;
    MaterialFavoriteButton favorite1;
    ImageButton imageFavorite;
    List<MovieItem> items;
    Gson gson;
    String array;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);
        ButterKnife.bind(this);
        imageFavorite=findViewById(R.id.imageButton);
        gson=new Gson();

        Bundle movieData=getIntent().getExtras();
        array= getIntent().getStringExtra("movieDetails");

        Log.i("irkjafh", String.valueOf(items));

        items= Collections.singletonList(gson.fromJson(array, MovieItem.class));
        setDetails(items);


        FavMovDBHelper favMovDBHelper = new FavMovDBHelper(this);
              if(FavoriteMovieDB(items))
              {
                 changeToFilledFavIcon();
              }else
              {
                  changeToEmptyFavIcon();
              }



              imageFavorite.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if(!FavoriteMovieDB(items))
                      {
                          changeToFilledFavIcon();
                          saveFavorite(items);
                          Snackbar.make(v,"Added to Favorite", Snackbar.LENGTH_LONG).show();
                      }else
                      {
                          changeToEmptyFavIcon();
                          deleteMovies(items);
                          Snackbar.make(v,"Deleted from Favorite", Snackbar.LENGTH_LONG).show();

                      }

                  }
              });



        trailer();
        review();



    }


    private void changeToEmptyFavIcon() {

        imageFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp);

    }



    private void changeToFilledFavIcon() {

        imageFavorite.setImageResource(R.drawable.ic_favorite_black_24dp);

    }
    public void trailer() {


        trailerData = new ArrayList<>();
        trailerView = new TrailerView(this, trailerData);

        trailerRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        trailerRecycler.setAdapter(trailerView);
        trailerView.notifyDataSetChanged();
        loadTrailer();
    }

    public void loadTrailer() {
        int id = items.get(0).getId();
        Client Client = new Client();


        Service apiKey = Client.getClient().create(Service.class);

        Call<TrailerResponse> call = apiKey.getMovieTrailer(id, BuildConfig.MOVIEDB_API_KEY);
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                List<TrailerData> itemList = response.body().getResults();
                trailerRecycler.setAdapter(new TrailerView(getApplicationContext(), itemList));

                Log.i("sffsf", itemList.toString());
                if (response.isSuccessful()) {
                    Toast.makeText(DetailsView.this, "sucessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {

                Log.i("fdsfdfs", t.toString());
            }
        });


    }

    public void review() {


        reviewData = new ArrayList<>();
        reviewView = new ReviewView(this, reviewData);

        reviewRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        reviewRecycler.setAdapter(reviewView);
        reviewView.notifyDataSetChanged();

        loadReview();
    }

    public void loadReview() {

        int id = items.get(0).getId();
        Client Client = new Client();


        Service apiKey = Client.getClient().create(Service.class);

        Call<ReviewResponse> call = apiKey.getMovieReview(id, BuildConfig.MOVIEDB_API_KEY);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {
                List<ReviewData> itemList = response.body().getResults();
                reviewRecycler.setAdapter(new ReviewView(getApplicationContext(), itemList));

                Log.i("sffsf", itemList.toString());
                if (response.isSuccessful()) {
                    Toast.makeText(DetailsView.this, "sucessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

                Log.i("fdsfdfs", t.toString());
            }
        });


    }



    public void saveFavorite(List<MovieItem> movieItem) {


        ContentValues contentValues= new ContentValues();
        contentValues.put(FavoriteContract.FavouriMovCon.COLUMN_MOVIEID,movieItem.get(0).getId());
        contentValues.put(FavoriteContract.FavouriMovCon.COLUMN_TITLE,movieItem.get(0).getTitle());
        contentValues.put(FavoriteContract.FavouriMovCon.COLUMN_USERRATING,movieItem.get(0).getVoteAverage());
        contentValues.put(FavoriteContract.FavouriMovCon.COLUMN_POSTER_PATH,movieItem.get(0).getPosterPath());
        contentValues.put(FavoriteContract.FavouriMovCon.COLUMN_PLOT_REVIEW,movieItem.get(0).getOverview());
        getContentResolver().insert(FavoriteContract.FavouriMovCon.CONTENT_URI,contentValues);
    }

    public void deleteMovies(List<MovieItem> movieItem)
    {

        String selection= FavoriteContract.FavouriMovCon.COLUMN_MOVIEID+ "=?";
        String[] selectionArgs={String.valueOf(movieItem.get(0).getId())};
        getContentResolver().delete(FavoriteContract.FavouriMovCon.CONTENT_URI,selection,selectionArgs);
    }
    public boolean FavoriteMovieDB(List<MovieItem> item)
    {
        Cursor cursor=getContentResolver().query(FavoriteContract.FavouriMovCon.CONTENT_URI,
                null,null,null,null);
        if(cursor != null)
        {
            while (cursor.moveToNext())
            {
                int movieId=cursor.getInt(cursor.getColumnIndex(FavoriteContract.FavouriMovCon.COLUMN_MOVIEID));
                if(movieId==item.get(0).getId())
                {
                    return true;
                }
            }
        }if(cursor != null){
            cursor.close();
    }
    return false;
    }

    private void setDetails(List<MovieItem> item)
    {
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185/"+item.get(0).getPosterPath()).into(movieImage);
        title.setText(item.get(0).getTitle());
        releaseDate.setText(item.get(0).getReleaseDate());
        ratings.setText( String.valueOf(item.get(0).getVoteAverage()));
        review.setText(item.get(0).getOverview());

    }
}
