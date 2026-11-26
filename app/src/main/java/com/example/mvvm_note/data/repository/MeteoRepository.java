package com.example.mvvm_note.data.repository;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.mvvm_note.BuildConfig;
import com.example.mvvm_note.data.api.OpenWeatherService;
import com.example.mvvm_note.data.model.Forecast;
import com.example.mvvm_note.data.model.Weather;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class MeteoRepository {


    private final OpenWeatherService service;
    private final MutableLiveData<Weather> meteo =
            new MutableLiveData<>();
    private final MutableLiveData<Forecast> previsions =
            new MutableLiveData<>();

    private final MutableLiveData<String> erreur =
            new MutableLiveData<>();

    @Inject
    public MeteoRepository(OpenWeatherService service) {
        this.service = service;
    }

    public LiveData<Weather> getMeteo() {
        return meteo;
    }

    public LiveData<Forecast> getPrevisions() {
        return previsions;
    }
    public LiveData<String> getErreur() {
        return erreur;
    }



    public void charger(double lat, double lon) {
        // 1. Préparer la requête
        Call<Weather> call = service.getCurrentWeather(
                lat, lon, BuildConfig.OWM_API_KEY, "metric", "fr");

        // 2. L'envoyer en arrière-plan. Retrofit rappellera onResponse
        //    ou onFailure, sur le thread principal, à l'arrivée.
        call.enqueue(new Callback<Weather>() {

            // Le serveur a répondu (code 200, 401, 404, 500...)
            @Override
            public void onResponse(Call<Weather> call,
                                   Response<Weather> response) {
                if (response.isSuccessful() && response.body() != null) {
                    meteo.setValue(response.body());
                    erreur.setValue(null);
                } else {
                    erreur.setValue(""+response.code());
                }
            }

            // Pas de réponse exploitable : pas de réseau, délai
            // dépassé, JSON qui ne correspond pas aux classes...
            @Override
            public void onFailure(Call<Weather> call,
                                  Throwable t) {
                erreur.setValue("Échec réseau : " + t.getMessage());
            }
        });
    }

    public void previsionsCinqJours(double lat, double lon) {
        // 1. Préparer la requête
        Call<Forecast> call = service.getForecastFiveDays(
                lat, lon, BuildConfig.OWM_API_KEY, "metric", "fr");

        // 2. L'envoyer en arrière-plan. Retrofit rappellera onResponse
        //    ou onFailure, sur le thread principal, à l'arrivée.
        call.enqueue(new Callback<Forecast>() {

            // Le serveur a répondu (code 200, 401, 404, 500...)
            @Override
            public void onResponse(Call<Forecast> call,
                                   Response<Forecast> response) {
                if (response.isSuccessful() && response.body() != null) {
                    previsions.setValue(response.body());
                    erreur.setValue(null);
                } else {
                    erreur.setValue(""+response.code());
                }
            }

            // Pas de réponse exploitable : pas de réseau, délai
            // dépassé, JSON qui ne correspond pas aux classes...
            @Override
            public void onFailure(Call<Forecast> call,
                                  Throwable t) {
                erreur.setValue("Échec réseau : " + t.getMessage());
            }
        });
    }

}


