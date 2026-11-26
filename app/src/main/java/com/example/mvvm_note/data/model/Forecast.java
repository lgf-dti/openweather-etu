package com.example.mvvm_note.data.model;

import com.squareup.moshi.Json;

public class Forecast {

    @Json(name = "list")
    private Weather[] forecasts;

    public Weather[] getForecasts() {
        return forecasts == null ? new Weather[0] : forecasts;
    }

}
