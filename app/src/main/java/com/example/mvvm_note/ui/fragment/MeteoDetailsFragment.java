package com.example.mvvm_note.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mvvm_note.databinding.FragmentMeteoDetailsBinding;
import com.example.mvvm_note.ui.adapter.MeteoAdapter;
import com.example.mvvm_note.viewmodel.MeteoViewModel;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MeteoDetailsFragment extends Fragment {

    private FragmentMeteoDetailsBinding binding;
    private MeteoViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMeteoDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Température transmise par MeteoFragment
        String temp = MeteoDetailsFragmentArgs.fromBundle(requireArguments()).getTemp();
        binding.textTemperature.setText(temp);

        viewModel = new ViewModelProvider(this).get(MeteoViewModel.class);

        MeteoAdapter adapter = new MeteoAdapter(weather ->
                Toast.makeText(requireContext(),
                        MeteoAdapter.formatDate(weather.getDate()) + " : " + weather.getDescription(),
                        Toast.LENGTH_SHORT).show());
        binding.recyclerMeteo.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerMeteo.setAdapter(adapter);

        viewModel.getMeteo().observe(getViewLifecycleOwner(), meteo ->
                binding.textVille.setText(meteo.getVille()));

        // Prévisions : le tableau Weather[] va directement dans l'adapter
        viewModel.getPrevisions().observe(getViewLifecycleOwner(), forecast ->
                adapter.setWeathers(forecast.getForecasts()));

        // Erreur : on affiche le message, ou on le masque (null)
        viewModel.getErreur().observe(getViewLifecycleOwner(), msg -> {
            binding.textErreur.setText(msg);
            binding.textErreur.setVisibility(msg == null ? View.GONE : View.VISIBLE);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
