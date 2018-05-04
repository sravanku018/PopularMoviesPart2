package com.example.subramanyam.popularmoviespart2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.subramanyam.popularmoviespart2.adapters.MovieView;
import com.example.subramanyam.popularmoviespart2.api.Client;
import com.example.subramanyam.popularmoviespart2.api.Service;
import com.example.subramanyam.popularmoviespart2.data.MovResponse;
import com.example.subramanyam.popularmoviespart2.data.MovieItem;
import com.example.subramanyam.popularmoviespart2.database.FavMovDBHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private RecyclerView recyclerView;
    private MovieView movieView;
    private List<MovieItem> resultsItems;
    private AppCompatActivity activity=MainActivity.this;
    private FavMovDBHelper favMovDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isInternetOn();
    }

    public Activity getActivity()
    {
        Context context = this;
        while (context instanceof ContextWrapper){
            if (context instanceof Activity){
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
    public void viewData() {

        recyclerView = findViewById(R.id.movieImages);
        resultsItems = new ArrayList<>();
        movieView = new MovieView(this, resultsItems);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(movieView);
        movieView.notifyDataSetChanged();
        favMovDBHelper=new FavMovDBHelper(activity);
        checkSortOrder();
    }

    public void viewData2()
    {
        recyclerView = findViewById(R.id.movieImages);
        resultsItems = new ArrayList<>();
        movieView = new MovieView(this, resultsItems);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(movieView);
        movieView.notifyDataSetChanged();
        favMovDBHelper=new FavMovDBHelper(activity);
        getAllFavorites();

    }


    public void loadData() {
        Client Client = new Client();


        Service apiKey = Client.getClient().create(Service.class);

        Call<MovResponse> call = apiKey.getPopularMovies(BuildConfig.MOVIEDB_API_KEY);
        call.enqueue(new Callback<MovResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovResponse> call, @NonNull Response<MovResponse> response) {
                List<MovieItem> itemList = response.body().getResults();
                recyclerView.setAdapter(new MovieView(getApplicationContext(), itemList));

                Log.i("sffsf", itemList.toString());
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "sucessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovResponse> call, Throwable t) {

                Log.i("fdsfdfs", t.toString());
            }
        });


    }

    public void loadData2() {
        Client Client = new Client();


        Service apiKey = Client.getClient().create(Service.class);

        Call<MovResponse> call = apiKey.getTopRatedMovies(BuildConfig.MOVIEDB_API_KEY);
        call.enqueue(new Callback<MovResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovResponse> call, @NonNull Response<MovResponse> response) {
                List<MovieItem> itemList = response.body().getResults();
                recyclerView.setAdapter(new MovieView(getApplicationContext(), itemList));

                Log.i("sffsf", itemList.toString());
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "sucessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovResponse> call, Throwable t) {

                Log.i("fdsfdfs", t.toString());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        checkSortOrder();

    }

    public void checkSortOrder() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = sharedPreferences.getString(this.getString(R.string.pref_sort_order_key),
                this.getString(R.string.pref_most_popular));
        if (sortOrder.equals(this.getString(R.string.pref_most_popular))) {
            loadData();
        }else if(sortOrder.equals(this.getString(R.string.favorite)))
        {
            viewData2();
        }

        else {
            loadData2();
        }
    }

    public final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec != null) {
            if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

                // if connected with internet
                viewData();
                onResume();


                return true;

            } else if (
                    connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {

                Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return false;
    }

    @Override
    public void onResume(){
        super.onResume();
        if (resultsItems.isEmpty()){

            checkSortOrder();

        }else{

            checkSortOrder();

        }
    }
    @SuppressLint("StaticFieldLeak")
    public void getAllFavorites()
    {
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                resultsItems.clear();
                resultsItems.addAll(favMovDBHelper.getAllFavorite());
                return  null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                movieView.notifyDataSetChanged();
            }
        }.execute();

    }

}
