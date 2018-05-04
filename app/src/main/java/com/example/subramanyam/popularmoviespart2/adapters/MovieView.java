package com.example.subramanyam.popularmoviespart2.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.subramanyam.popularmoviespart2.DetailsView;
import com.example.subramanyam.popularmoviespart2.R;
import com.example.subramanyam.popularmoviespart2.data.MovieItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieView extends RecyclerView.Adapter<MovieView.ViewHolder> {

    private Context context;



    private List<MovieItem> list;


    public MovieView(Context context, List<MovieItem> list1)
    {

        this.context=context;
        this.list= list1;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movieimages,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+list.get(position).getPosterPath()).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailsView.class);
                intent.putExtra("movieImage",list.get(position).getPosterPath());
                intent.putExtra("movieName",list.get(position).getTitle());
                intent.putExtra("releaseDate",list.get(position).getReleaseDate());
                intent.putExtra("ratings",Double.toString(list.get(position).getVoteAverage()));
                intent.putExtra("review",list.get(position).getOverview());
                intent.putExtra("movieId",list.get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
