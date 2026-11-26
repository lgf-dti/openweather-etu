package com.example.mvvm_note.data.api;

import com.example.mvvm_note.BuildConfig;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
@InstallIn(SingletonComponent.class)   // objets partagés par toute l'app
public class NetworkModule {

    // L'URL de base se termine OBLIGATOIREMENT par /
    private static final String BASE_URL =
            "https://api.openweathermap.org/data/2.5/";

    // 1. Le client HTTP (délais d'attente + logs)
    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        // logs détaillés en debug seulement : l'URL contient la clé API
        log.setLevel(BuildConfig.DEBUG
                ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(log)
                .build();
    }

    // 2. Retrofit, qui utilise ce client et Moshi
    @Provides
    @Singleton
    public static Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    // 3. Le service : Retrofit génère l'implémentation de l'interface
    @Provides
    @Singleton
    public static OpenWeatherService provideOpenWeatherService(
            Retrofit retrofit) {
        return retrofit.create(OpenWeatherService.class);
    }
}

