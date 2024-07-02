package com.example.weatherapplication.forecast_adptr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TimeForecastAdapter extends RecyclerView.Adapter<TimeForecastAdapter.ViewHolderClass>{

    ArrayList<ForecastModel> arrayList;
    Context mcontext;

    public TimeForecastAdapter(ArrayList<ForecastModel> arrayList, Context mcontext) {
        this.arrayList = arrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public TimeForecastAdapter.ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.time_forecast,parent,false);
        TimeForecastAdapter.ViewHolderClass viewhold=new TimeForecastAdapter.ViewHolderClass(view);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        holder.txttime.setText(arrayList.get(position).date);
        holder.txtcond.setText(String.valueOf(arrayList.get(position).cond));
        Picasso.get().load(arrayList.get(position).icn).resize(100,100).into(holder.imgicn);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView txttime,txtcond;
        ImageView imgicn;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            txttime= itemView.findViewById(R.id.txtftime);
            txtcond=itemView.findViewById(R.id.txtfcond);
            imgicn=itemView.findViewById(R.id.icnfweather);
        }
    }
}
