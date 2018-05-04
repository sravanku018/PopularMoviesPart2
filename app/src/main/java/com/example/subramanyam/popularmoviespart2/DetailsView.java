package com.example.subramanyam.popularmoviespart2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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


    private FavMovDBHelper favMovDBHelper;
    private final DetailsView activity=DetailsView.this;
    private MovieItem favorite;
    MaterialFavoriteButton favorite1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);
        ButterKnife.bind(this);

        movieImages = getIntent().getExtras().getString("movieImage");
        title1 = getIntent().getExtras().getString("movieName");
        releaseDate1 = getIntent().getExtras().getString("releaseDate");
        ratings1 = getIntent().getExtras().getString("ratings");
        review1 = getIntent().getExtras().getString("review");

        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185/"+movieImages).into(movieImage);
        title.setText(title1);
        releaseDate.setText(releaseDate1);
        ratings.setText(ratings1);
        review.setText(review1);


                favorite1 = findViewById(R.id.favButton);


        final SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        favorite1.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if(favorite)
                        {



                            SharedPreferences.Editor editor=  getSharedPreferences("com.example.subramanyam.popularmoviespart2.DetailsView",MODE_PRIVATE).edit();
                            editor.putBoolean("Added Favorite",true);
                            editor.apply();
                            saveFavorite();
                          Snackbar.make(buttonView,"Added to Favorite", Snackbar.LENGTH_LONG).show();
                        }else {
                            int movieid1=getIntent().getExtras().getInt("movieId");
                            favMovDBHelper=new FavMovDBHelper(DetailsView.this);
                            favMovDBHelper.deleteFavorite(movieid1);
                            SharedPreferences.Editor editor= getSharedPreferences("com.example.subramanyam.popularmoviespart2.DetailsView",MODE_PRIVATE).edit();

                            editor.putBoolean("Removed Favorite",false);
                            editor.apply();

                         Snackbar.make(buttonView,"Removed from Favorites",Snackbar.LENGTH_LONG).show();
                        }
                    }

                });
        trailer();
        review();



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
        int id = getIntent().getExtras().getInt("movieId");
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

        int id = getIntent().getExtras().getInt("movieId");
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



    public void saveFavorite() {
        favMovDBHelper=new FavMovDBHelper(activity);
        favorite=new MovieItem();
        int movieid=getIntent().getExtras().getInt("movieId");
        String poster=getIntent().getExtras().getString("movieImage");
        String rating=getIntent().getExtras().getString("ratings");


        favorite.setId(movieid);

        favorite.setPosterPath(poster);
        favorite.setTitle(title.getText().toString().trim());
        favorite.setVoteAverage(Double.parseDouble(rating));
        favorite.setOverview(review.getText().toString().trim());

        favMovDBHelper.addFavorite(favorite);
    }
}
