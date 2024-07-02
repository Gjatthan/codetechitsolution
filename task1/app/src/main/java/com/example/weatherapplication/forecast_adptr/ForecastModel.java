package com.example.weatherapplication.forecast_adptr;

public class ForecastModel {
    String date,icn,cond,temp,temp_max,temp_min;

    public ForecastModel(String date, String icn, String cond, String temp, String temp_max, String temp_min) {
        this.date = date;
        this.icn = icn;
        this.cond = cond;
        this.temp = temp;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
    }

    public ForecastModel(String date, String icn, String cond) {
        this.date = date;
        this.icn = icn;
        this.cond = cond;
    }
}
