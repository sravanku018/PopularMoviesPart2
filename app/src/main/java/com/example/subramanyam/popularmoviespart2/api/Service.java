package com.example.subramanyam.popularmoviespart2.api;

import com.example.subramanyam.popularmoviespart2.data.MovResponse;
import com.example.subramanyam.popularmoviespart2.data.ReviewResponse;
import com.example.subramanyam.popularmoviespart2.data.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    @GET("movie/popular")
    Call<MovResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getMovieTrailer(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResponse> getMovieReview(@Path("movie_id") int id, @Query("api_key") String apiKey);
}
