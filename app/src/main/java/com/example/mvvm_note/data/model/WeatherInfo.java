package com.example.mvvm_note.data.model;

import com.squareup.moshi.Json;

public class WeatherInfo {

    @Json(name = "description")
    private String description;

    public String getDescription() { return description; }
}

