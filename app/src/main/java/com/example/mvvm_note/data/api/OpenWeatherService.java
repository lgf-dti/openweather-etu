package com.example.mvvm_note.data.api;

import com.example.mvvm_note.data.model.Forecast;
import com.example.mvvm_note.data.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {

    // GET https://api.openweathermap.org/data/2.5/weather
    //       ?lat=...&lon=...&appid=...&units=metric&lang=fr
    @GET("weather")
    Call<Weather> getCurrentWeather(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String apiKey,
            @Query("units") String units,
            @Query("lang") String lang);

    @GET("forecast")
    Call<Forecast> getForecastFiveDays(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String apiKey,
            @Query("units") String units,
            @Query("lang") String lang);
}

