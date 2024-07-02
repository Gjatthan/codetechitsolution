package com.example.weatherapplication;

import static com.example.weatherapplication.APIInterface.apikey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.example.weatherapplication.misc.Network;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreen extends AppCompatActivity {
    ViewAnimator va;
    FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        getSupportActionBar().hide();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        
        va=findViewById(R.id.va);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if(Network.haveNetworkConnection(cm)) {
                    getCityByLoc();
//                    startActivity(new Intent(SplashScreen.this, ActivityMain.class));
//                    finish();
                }
                else
                    Toast.makeText(SplashScreen.this,"Please connect to internet",Toast.LENGTH_LONG).show();
            }
        },6000);
    }

    @SuppressLint("MissingPermission")
    private void getCityByLoc() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>(){
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            Intent main_activity=new Intent(SplashScreen.this,ActivityMain.class);
                            APIInterface.CurrentWeatherInterface retrofit=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://api.openweathermap.org/data/2.5/").build().create(APIInterface.CurrentWeatherInterface.class);
                            Call<CurrentWeather> response=retrofit.getWeatherDataLatLon(String.valueOf(location.getLatitude()),String.valueOf(location.getLatitude()),apikey,"metric");
                            response.enqueue(new Callback<CurrentWeather>() {
                                @Override
                                public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                                    CurrentWeather respBody=response.body();
                                    if(response.isSuccessful() && respBody!=null) {
                                        main_activity.putExtra("celc",String.valueOf(Math.round(respBody.getMain().getTemp())));
                                        if(!respBody.getWeather().isEmpty()) {
                                            String weather=respBody.getWeather().get(0).getMain();
                                            main_activity.putExtra("weather",weather);
                                            main_activity.putExtra("desc",respBody.getWeather().get(0).getDescription());
                                        }
                                        main_activity.putExtra("icn","https://openweathermap.org/img/wn/"+respBody.getWeather().get(0).getIcon()+".png");
                                        main_activity.putExtra("max",String.valueOf(respBody.getMain().getTemp_max())+"°C");
                                        main_activity.putExtra("min",String.valueOf(respBody.getMain().getTemp_min())+"°C");
                                        main_activity.putExtra("hum",String.valueOf(respBody.getMain().getHumidity())+"%");
                                        main_activity.putExtra("wind",String.valueOf(respBody.getWind().getDeg())+"m/s");
                                        main_activity.putExtra("srise",respBody.getSys().getSunrise()*1000);
                                        main_activity.putExtra("sset",respBody.getSys().getSunset()*1000);
                                        main_activity.putExtra("sealevel",String.valueOf(respBody.getMain().getSea_level())+"m");
                                        main_activity.putExtra("time",respBody.getDt()*1000);
                                        startActivity(main_activity);
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<CurrentWeather> call, Throwable t) {

                                }
                            });
                            Log.d("TAG",location.getLatitude()+"\n"+location.getLongitude());
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }
    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Geocoder geocoder = new Geocoder(SplashScreen.this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String cityName = addresses.get(0).getAddressLine(0);
            String stateName = addresses.get(0).getAddressLine(1);
            String countryName = addresses.get(0).getAddressLine(2);
            Log.d("TAG",cityName);
        }
    };

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION}, 44);
    }


    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCityByLoc();
            }
        }
    }


}