package com.example.weatherapplication.forecast_adptr;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolderClass>{

    ArrayList<ForecastModel> arrayList;
    Context mcontext;

    public ForecastAdapter(ArrayList<ForecastModel> arrayList, Context mcontext) {
        this.arrayList = arrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ForecastAdapter.ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.forecastview,parent,false);
        ForecastAdapter.ViewHolderClass viewhold=new ForecastAdapter.ViewHolderClass(view);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        holder.txtdate.setText(arrayList.get(position).date);
        holder.txttemp.setText(String.valueOf(arrayList.get(position).temp));
        holder.txtmax.setText(String.valueOf(arrayList.get(position).temp_max));
        holder.txtmin.setText(String.valueOf(arrayList.get(position).temp_min));
        holder.txtcond.setText(arrayList.get(position).cond);
        Picasso.get().load(arrayList.get(position).icn).resize(100,100).into(holder.imgicn);
        //Picasso.with(mcontext).load(arrayList.get(position).icn).into(holder.imgicn);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView txtdate,txttemp,txtmax,txtmin,txtcond;
        ImageView imgicn;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            txtdate= itemView.findViewById(R.id.txtdate1);
            txttemp=itemView.findViewById(R.id.txttemp1);
            txtmax=itemView.findViewById(R.id.txtmax1);
            txtmin=itemView.findViewById(R.id.txtmin1);
            imgicn=itemView.findViewById(R.id.icon1);
            txtcond=itemView.findViewById(R.id.txtcond1);
        }
    }
}


