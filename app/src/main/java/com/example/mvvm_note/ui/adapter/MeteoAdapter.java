package com.example.mvvm_note.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_note.R;
import com.example.mvvm_note.data.model.Weather;
import com.example.mvvm_note.data.model.WeatherResponse;
import com.example.mvvm_note.databinding.ItemMeteoBinding;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class MeteoAdapter extends RecyclerView.Adapter<MeteoAdapter.WeatherViewHolder> {

    public interface OnWeatherClickListener {
        void onWeatherClick(@NonNull Weather weather);
    }


    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("EEEE d MMMM 'à' HH'h'", Locale.FRENCH);

    private final OnWeatherClickListener listener;
    private Weather[] weathers = new Weather[0]; // tableau vide plutôt que null

    public MeteoAdapter(@NonNull OnWeatherClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setWeathers(@NonNull Weather[] newWeathers) {
        weathers = newWeathers;
        notifyDataSetChanged();
    }

    /** "dt" d'OpenWeatherMap : secondes depuis le 01/01/1970 (UTC). */
    @NonNull
    public static String formatDate(long dt) {
        return DATE_FORMAT.format(Instant.ofEpochSecond(dt).atZone(ZoneId.systemDefault()));
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new WeatherViewHolder(ItemMeteoBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        holder.bind(weathers[position]);
    }

    @Override
    public int getItemCount() {
        return weathers.length;
    }


    class WeatherViewHolder extends RecyclerView.ViewHolder {

        private final ItemMeteoBinding binding;

        WeatherViewHolder(@NonNull ItemMeteoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(@NonNull Weather weather) {
            Context context = binding.getRoot().getContext();
            WeatherResponse main = weather.getMain();

            binding.textDate.setText(formatDate(weather.getDate()));
            binding.textTemp.setText(main == null ? ""
                    : context.getString(R.string.format_temperature, main.getTemperature()));
            binding.textDescription.setText(weather.getDescription());
            binding.getRoot().setOnClickListener(v -> listener.onWeatherClick(weather));
        }
    }
}
