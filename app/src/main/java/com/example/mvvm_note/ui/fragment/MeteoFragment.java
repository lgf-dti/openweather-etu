package com.example.mvvm_note.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mvvm_note.R;
import com.example.mvvm_note.data.model.Weather;
import com.example.mvvm_note.databinding.FragmentMeteoBinding;
import com.example.mvvm_note.viewmodel.MeteoViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MeteoFragment extends Fragment {

    private FragmentMeteoBinding binding;
    private MeteoViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMeteoBinding.inflate(
                inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this)
                .get(MeteoViewModel.class);

        // Nouvelles données : on met l'écran à jour
        viewModel.getMeteo().observe(getViewLifecycleOwner(), meteo -> {
            binding.textVille.setText(meteo.getVille());
            binding.textTemperature.setText(getString(
                    R.string.format_temperature,
                    meteo.getMain().getTemperature()));
            binding.textDetails.setText(getString(
                    R.string.format_details,
                    meteo.getDescription(),
                    meteo.getMain().getHumidite()));
        });

        // Erreur : on affiche le message, ou on le masque (null)
        viewModel.getErreur().observe(getViewLifecycleOwner(), msg -> {
            binding.textErreur.setText(msg);
            binding.textErreur.setVisibility(
                    msg == null ? View.GONE : View.VISIBLE);
        });

        binding.buttonActualiser.setOnClickListener(
                v -> viewModel.actualiser());

        binding.buttonDetails.setOnClickListener(v -> {
            // Pas encore de données (chargement en cours ou erreur réseau) : on ne navigue pas
            Weather meteo = viewModel.getMeteo().getValue();
            if (meteo == null || meteo.getMain() == null) {
                Toast.makeText(requireContext(), R.string.meteo_pas_chargee,
                        Toast.LENGTH_SHORT).show();
                return;
            }
            String temp = getString(R.string.format_temperature,
                    meteo.getMain().getTemperature());
            NavDirections action =
                    MeteoFragmentDirections.actionMeteoFragmentToMeteoDetailsFragment(temp);
            NavHostFragment.findNavController(this).navigate(action);
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

