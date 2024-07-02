package com.example.weatherapplication;

import static com.example.weatherapplication.APIInterface.apikey;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
//import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapplication.databinding.ActivityMainBinding;
import com.example.weatherapplication.forecast_adptr.ForecastAdapter;
import com.example.weatherapplication.forecast_adptr.ForecastModel;
import com.example.weatherapplication.forecast_adptr.TimeForecastAdapter;
import com.example.weatherapplication.misc.Network;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityMain extends AppCompatActivity {
    boolean musicon=true;
    MediaPlayer player;
    String city="Udupi";
    ActivityMainBinding binder;

    ForecastAdapter adapter;
    ArrayList<ForecastModel> arrayList;
    
    TimeForecastAdapter timeadptr;
    ArrayList<ForecastModel> arrayListTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder=ActivityMainBinding.inflate(getLayoutInflater());
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binder.getRoot());
        getSupportActionBar().hide();

        binder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        if(Network.haveNetworkConnection((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE))){
            //getWeatherInfo(city);
            setData(getIntent());
            searchByCity();
            timeBasedForecast();
        }
        else{
            Toast.makeText(this,"Please connect to internet",Toast.LENGTH_LONG).show();
            //playAudioVideo(R.raw.sunny,R.raw.sunnybg);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void setData(Intent intent) {
        Bundle data=intent.getExtras();
        binder.txtloc.setText(city.substring(0,1).toUpperCase()+city.substring(1));
        binder.txtcelc.setText(data.getString("celc"));
            switch (data.getString("weather").toLowerCase()){
                case "clear sky": case "sunny": case "clear":
                    playAudioVideo(R.raw.sunny,R.raw.sunnybg);
                    break;
                case "partly clouds": case "clouds": case "overcast": case "mist": case "foggy" : case "haze":
                    playAudioVideo(R.raw.cloudy,R.raw.cloudbg);
                    break;
                case "light rain": case "drizzle": case "moderate rain": case "showers": case "heavy rain": case "rain":
                    playAudioVideo(R.raw.rainy,R.raw.rainbg);
                    break;
                case "light snow": case "moderate snow": case "heavy snow": case "blizzerd":
                    playAudioVideo(R.raw.winter,R.raw.snowbg);
            }

            binder.txtweather.setText(data.getString("desc"));
            binder.txtcond.setText(data.getString("weather"));
            Picasso.get().load(data.getString("icn")).into(binder.imgicon);

            binder.txtmax.setText("Max: " + data.getString("max"));
            binder.txtmin.setText("Min: " + data.getString("min"));
            binder.txthumidity.setText(data.getString("hum"));
            binder.txtwind.setText(data.getString("wind"));
            binder.txtsunrise.setText(timeConversion(data.getLong("srise")));
            binder.txtsunset.setText(timeConversion(data.getLong("sset")));
            binder.txtsea.setText(data.getString("sealevel"));
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(data.getLong("time"));
            binder.txtday.setText(new SimpleDateFormat("EEEE").format(cal.getTime()));
            binder.txtdate.setText(new SimpleDateFormat("dd LLLL y").format(cal.getTime()));
        }


    private void searchByCity() {
        binder.edtsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                city=query;
                getWeatherInfo(city);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }



    private void getWeatherInfo(String city) {
//        String url="https://api.openweathermap.org/data/2.5/forecast?q="+city+"&appid="+apikey;
//        StringRequest httpreq=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("TAG",response);
//                Gson gson=new Gson();
//                WeatherForecast forecast=gson.fromJson(response,WeatherForecast.class);
//                Log.d("TAG",String.valueOf(forecast.getList().get(0).getMain().getTempMin()));
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("TAG",error.networkResponse.data.toString());
//            }
//        });
//        RequestQueue requestQueue= Volley.newRequestQueue(this);
//        requestQueue.add(httpreq);

        APIInterface.CurrentWeatherInterface retrofit=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.openweathermap.org/data/2.5/").build().create(APIInterface.CurrentWeatherInterface.class);
        Call<CurrentWeather> response=retrofit.getWeatherData(city,apikey,"metric");
        response.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                CurrentWeather respBody=response.body();
                if(response.isSuccessful() && respBody!=null) {
                    binder.txtloc.setText(city.substring(0,1).toUpperCase()+city.substring(1));
                    binder.txtcelc.setText(String.valueOf(Math.round(respBody.getMain().getTemp())));
                    if(!respBody.getWeather().isEmpty()) {
                        String weather=respBody.getWeather().get(0).getMain();
                        player.stop();
                        switch (weather.toLowerCase()){
                            case "clear sky": case "sunny": case "clear":
                                playAudioVideo(R.raw.sunny,R.raw.sunnybg);
                                break;
                            case "partly clouds": case "clouds": case "overcast": case "mist": case "foggy" : case "haze":
                                playAudioVideo(R.raw.cloudy,R.raw.cloudbg);
                                break;
                            case "light rain": case "drizzle": case "moderate rain": case "showers": case "heavy rain": case "rain":
                                playAudioVideo(R.raw.rainy,R.raw.rainbg);
                                break;
                            case "light snow": case "moderate snow": case "heavy snow": case "blizzerd":
                                playAudioVideo(R.raw.winter,R.raw.snowbg);
                        }

                        binder.txtweather.setText(respBody.getWeather().get(0).getDescription());
                        binder.txtcond.setText(weather);
                    }
                    Picasso.get().load("https://openweathermap.org/img/wn/"+respBody.getWeather().get(0).getIcon()+".png").into(binder.imgicon);

                    binder.txtmax.setText("Max: "+String.valueOf(respBody.getMain().getTemp_max())+"°C");
                    binder.txtmin.setText("Min: "+String.valueOf(respBody.getMain().getTemp_min())+"°C");
                    binder.txthumidity.setText(String.valueOf(respBody.getMain().getHumidity())+"%");
                    binder.txtwind.setText(String.valueOf(respBody.getWind().getDeg())+"m/s");
                    binder.txtsunrise.setText(timeConversion(respBody.getSys().getSunrise()*1000));
                    binder.txtsunset.setText(timeConversion(respBody.getSys().getSunset()*1000));
                    binder.txtsea.setText(String.valueOf(respBody.getMain().getSea_level())+"m");
                    Calendar cal=Calendar.getInstance();
                    cal.setTimeInMillis(respBody.getDt()*1000);
                    binder.txtday.setText(new SimpleDateFormat("EEEE").format(cal.getTime()));
                    binder.txtdate.setText(new SimpleDateFormat("dd LLLL y").format(cal.getTime()));
                }
            }

            @Override
            public void onFailure(Call<CurrentWeather> call, Throwable t) {

            }
        });
    }


    String timeConversion(long millis){
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        final String timeString =new SimpleDateFormat("hh:mm:ss a").format(cal.getTime());
        return timeString;
    }


    public void onClickSound(View v){
        ImageButton imgbtn=(ImageButton) v;
        if(musicon){
            musicon=false;
            player.pause();
            imgbtn.setBackgroundResource(R.drawable.mute);
        }
        else{
            musicon=true;
            player.start();
            imgbtn.setBackgroundResource(R.drawable.volume);
        }
    }

    void playAudioVideo(int vid,int aid){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String videoUrl="android.resource://"+getPackageName()+"/"+vid;
                Uri uri= Uri.parse(videoUrl);
                binder.videoView.setVideoURI(uri);
                binder.videoView.start();

                player = MediaPlayer.create(ActivityMain.this, aid);
                player.setLooping(true);
                player.setVolume(2.0f, 2.0f);
                player.start();
                player.setLooping(true);

            }
        });
    }

    public void onClickViewMore(View v){
        BottomSheetDialog bs=new BottomSheetDialog(this);
        View z=getLayoutInflater().inflate(R.layout.forecastwindow,null,false);
        bs.setContentView(z);
        bs.show();
        bs.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        bs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bs.getWindow().getAttributes().windowAnimations = R.style.forecastDialogAnim;
        bs.getWindow().setGravity(Gravity.BOTTOM);
        bs.setCanceledOnTouchOutside(false);

        generateResult(z);
    }

    private void generateResult(View v) {
        arrayList=new ArrayList<>();
        adapter=new ForecastAdapter(arrayList,v.getContext());
        RecyclerView recyclerView=v.findViewById(R.id.forecastcycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        APIInterface.WeatherForecastInterface retrofit=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.openweathermap.org/data/2.5/").build().create(APIInterface.WeatherForecastInterface.class);
        Call<WeatherForecast> response=retrofit.getFutureWeatherData(city,apikey,"metric");
        response.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                WeatherForecast respBody=response.body();
                if(response.isSuccessful() && respBody!=null) {
                    String date,icn,temp,max,min,cond;
                    ListElement w;
                    for(int i=8;i<respBody.getList().size();i+=8){
                        w=respBody.getList().get(i);
                        try {
                            date=new SimpleDateFormat("EEEE\ndd-LLLL-y").format(new SimpleDateFormat("y-MM-dd hh:mm:ss").parse(w.getDtTxt()));
                            icn="https://openweathermap.org/img/wn/"+w.getWeather().get(0).getIcon()+"@2x.png";
                            temp=w.getMain().getTemp()+"°C";
                            max=w.getMain().getTempMax()+"°C";
                            min=w.getMain().getTempMin()+"°C";
                            cond=w.getWeather().get(0).getDescription().replace(" ","\n");
                            arrayList.add(new ForecastModel(date,icn,cond,temp,max,min));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {

            }
        });
    }
    
    private void timeBasedForecast(){
        arrayListTime=new ArrayList<>();
        timeadptr=new TimeForecastAdapter(arrayListTime,ActivityMain.this);
        RecyclerView recyclerView=this.findViewById(R.id.recyletimefor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        recyclerView.setAdapter(timeadptr);


        APIInterface.WeatherForecastInterface retrofit=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.openweathermap.org/data/2.5/").build().create(APIInterface.WeatherForecastInterface.class);
        Call<WeatherForecast> response=retrofit.getFutureWeatherData(city,apikey,"metric");
        response.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                WeatherForecast respBody=response.body();
                if(response.isSuccessful() && respBody!=null) {
                    String date,icn,cond;
                    ListElement w;
                    for(int i=0;respBody.getList().size()>=i&&i<8;i++){
                        w=respBody.getList().get(i);
                        try {
                            date=new SimpleDateFormat("hh:mm a").format(new SimpleDateFormat("y-MM-dd hh:mm:ss").parse(w.getDtTxt()));
                            icn="https://openweathermap.org/img/wn/"+w.getWeather().get(0).getIcon()+"@2x.png";
                            cond=w.getWeather().get(0).getDescription().replace(" ","\n");
                            arrayListTime.add(new ForecastModel(date,icn,cond));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                    timeadptr.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {

            }
        });
        
    }


}



