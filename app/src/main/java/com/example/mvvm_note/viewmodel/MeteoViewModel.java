package com.example.mvvm_note.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvm_note.data.model.Forecast;
import com.example.mvvm_note.data.model.Weather;
import com.example.mvvm_note.data.repository.MeteoRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MeteoViewModel extends ViewModel {

    private static final double ANNECY_LAT = 45.899;
    private static final double ANNECY_LON = 6.129;

    private final MeteoRepository repository;

    @Inject
    public MeteoViewModel(MeteoRepository repository) {
        this.repository = repository;
        actualiser();
    }

    public LiveData<Weather> getMeteo() {
        return repository.getMeteo();
    }

    public LiveData<Forecast> getPrevisions() {
        return repository.getPrevisions();
    }

    public LiveData<String> getErreur() {
        return repository.getErreur();
    }

    public void actualiser() {
        repository.charger(ANNECY_LAT, ANNECY_LON);
        repository.previsionsCinqJours(ANNECY_LAT, ANNECY_LON);

    }
}


