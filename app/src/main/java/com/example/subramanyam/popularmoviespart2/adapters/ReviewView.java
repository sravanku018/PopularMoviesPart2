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
import com.example.subramanyam.popularmoviespart2.data.ReviewData;

import java.util.List;

public class ReviewView extends RecyclerView.Adapter<ReviewView.ViewHolder> {
    private Context context;



    private List<ReviewData> list;


    public ReviewView(Context context, List<ReviewData> list1)
    {

        this.context=context;
        this.list= list1;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewview,parent,false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.textView.setText(list.get(position).getAuthor() + "\n" + list.get(position).getContent());

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.reviewName);

        }
    }
}
