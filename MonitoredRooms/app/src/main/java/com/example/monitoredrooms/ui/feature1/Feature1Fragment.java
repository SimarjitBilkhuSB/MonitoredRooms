package com.example.monitoredrooms.ui.feature1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.monitoredrooms.databinding.FragmentFeature1Binding; //must change this when when updating feature name

import org.jetbrains.annotations.NotNull;

public class Feature1Fragment extends Fragment {

    private Feature1ViewModel feature1ViewModel;
    private FragmentFeature1Binding binding;

    //turn comment into code to edit onCreatesOptionsMenu to add a menu
    //in the fragment if necessary
    /** @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); //the fragment has it's own menu
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        //inflate menu here
        super.onCreateOptionsMenu(menu, inflater);
    } */

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        feature1ViewModel =
                new ViewModelProvider(this).get(Feature1ViewModel.class);

        binding = FragmentFeature1Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFeature1;
        feature1ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}