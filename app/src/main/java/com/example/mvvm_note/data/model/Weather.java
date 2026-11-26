package com.example.mvvm_note.data.model;

import com.squareup.moshi.Json;

import java.util.List;

public class Weather {

    @Json(name = "name")
    private String ville;

    @Json(name = "cod")
    private int codeHTTP;

    @Json(name = "dt")
    private long date;

    @Json(name = "main")
    private WeatherResponse main;             // objet JSON -> classe Java

    @Json(name = "weather")
    private List<WeatherInfo> weather; // tableau JSON -> List

    public String getVille() { return ville; }
    public long getDate() { return date; }
    public WeatherResponse getMain() { return main; }

    // "weather" est un tableau : on prend le premier élément
    public String getDescription() {
        return (weather == null || weather.isEmpty())
                ? "" : weather.get(0).getDescription();
    }
}
