package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.weatherapplication.databinding.ActivityMainBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityMain extends AppCompatActivity {
    //VideoView videoView;
    BackgroundSound bgs;
    boolean musicon=true;
    String apikey="6dd144367162ab13fb56cac4972ea682";
    ActivityMainBinding binder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        getSupportActionBar().hide();

        bgs=new BackgroundSound();
        bgs.start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                //videoView=findViewById(R.id.videoView);
                playVideo(R.raw.sunny);
                getWindow().setBackgroundDrawable(getDrawable(R.drawable.blurbg));
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                binder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                    }
                });
            }
        }).start();

    }

    public void playVideo(int id)
    {
        String videoUrl="android.resource://"+getPackageName()+"/"+id;
        Uri uri= Uri.parse(videoUrl);
        binder.videoView.setVideoURI(uri);
        //MediaController mc=new MediaController(this);
        binder.videoView.start();
        getWeatherInfo();
//        videoView.setMediaController(mc);
//        mc.setAnchorView(videoView);
//        mc.hide();
    }

    private void getWeatherInfo() {
        APIInterface retrofit=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.openweathermap.org/data/2.5/").build().create(APIInterface.class);
        Call<WeatherJson> response=retrofit.getWeatherData("udupi",apikey,"metric");
        response.enqueue(new Callback<WeatherJson>() {
            @Override
            public void onResponse(Call<WeatherJson> call, Response<WeatherJson> response) {
                WeatherJson respBody=response.body();
                if(response.isSuccessful() && respBody!=null) {
                    binder.txtcelc.setText(String.valueOf(Math.round(respBody.getMain().getTemp())));
                    if(!respBody.getWeather().isEmpty()) {
                        binder.txtweather.setText(respBody.getWeather().get(0).getMain());
                        binder.txtcond.setText(respBody.getWeather().get(0).getMain());
                    }
                    binder.txtmax.setText("Max: "+String.valueOf(respBody.getMain().getTempMax())+"°C");
                    binder.txtmin.setText("Min: "+String.valueOf(respBody.getMain().getTempMin())+"°C");
                    binder.txthumidity.setText(String.valueOf(respBody.getMain().getHumidity())+"%");
                    binder.txtwind.setText(String.valueOf(respBody.getWind().getSpeed())+"m/s");

                    final Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(respBody.getSys().getSunrise());
                    final int minutes = cal.get(Calendar.MINUTE);
                    final String timeString =new SimpleDateFormat("HH:mm:ss:SSS").format(cal.getTime());
                    //System.out.println(minutes);
                    System.out.println(timeString);
                    binder.txtsunrise.setText(timeString);
                    binder.txtsunset.setText(String.valueOf(respBody.getSys().getSunset()));
                    binder.txtsea.setText(String.valueOf(respBody.getMain().getPressure())+"hPa");
                }
            }

            @Override
            public void onFailure(Call<WeatherJson> call, Throwable t) {

            }
        });
    }

    public class BackgroundSound extends Thread {
        MediaPlayer player;
        @Override
        public void run(){
            player = MediaPlayer.create(ActivityMain.this, R.raw.sunnybg);
            player.setLooping(true); // Set looping
            player.setVolume(1.0f, 1.0f);
            player.start();
            player.setLooping(true);
        }

    }

    public void onClickSound(View v){
        ImageButton imgbtn=(ImageButton) v;
        if(musicon){
            musicon=false;
            //bgs.player.stop();
            bgs.player.pause();
            imgbtn.setBackgroundResource(R.drawable.mute);
        }
        else{
            musicon=true;
            bgs.player.start();
            imgbtn.setBackgroundResource(R.drawable.volume);
        }
    }

}

