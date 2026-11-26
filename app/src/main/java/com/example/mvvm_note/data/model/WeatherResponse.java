package com.example.mvvm_note.data.model;

import com.squareup.moshi.Json;

public class WeatherResponse {

    @Json(name = "temp")
    private double temperature;

    @Json(name = "temp_min")
    private double temperatureMin;

    @Json(name = "temp_max")
    private double temperatureMax;

    @Json(name = "feels_like")
    private double ressentie;

    @Json(name = "humidity")
    private int humidite;



    public double getTemperature() { return temperature; }
    public double getRessentie() { return ressentie; }
    public int getHumidite() { return humidite; }
}

