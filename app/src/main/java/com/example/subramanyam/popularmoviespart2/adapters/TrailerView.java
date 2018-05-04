package com.example.subramanyam.popularmoviespart2.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.subramanyam.popularmoviespart2.R;
import com.example.subramanyam.popularmoviespart2.data.TrailerData;

import java.util.List;

public class TrailerView extends RecyclerView.Adapter<TrailerView.ViewHolder>{
    private Context context;



    private List<TrailerData> list;


    public TrailerView(Context context, List<TrailerData> list1)
    {

        this.context=context;
        this.list= list1;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.trailerview,parent,false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.trailerName.setText(list.get(position).getName());
        holder.trailerLink.setText("https://www.youtube.com/watch?v="+list.get(position).getKey());

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView trailerName;
        TextView trailerLink;
        public ViewHolder(View itemView) {
            super(itemView);
            trailerName=itemView.findViewById(R.id.trailerName);
            trailerLink=itemView.findViewById(R.id.trailerId);
        }
    }
}
