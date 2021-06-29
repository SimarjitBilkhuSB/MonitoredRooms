package com.example.monitoredrooms.ui.feature3;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monitoredrooms.R;
import com.example.monitoredrooms.databinding.FragmentFeature1Binding;
import com.example.monitoredrooms.databinding.FragmentFeature3Binding;
import com.example.monitoredrooms.ui.feature1.Feature1ViewModel;

public class Feature3Fragment extends Fragment {

    private Feature3ViewModel mViewModel;

    public static Feature3Fragment newInstance() {
        return new Feature3Fragment();
    }

    private Feature3ViewModel feature3ViewModel;
    private FragmentFeature3Binding binding;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        feature3ViewModel =
                new ViewModelProvider(this).get(Feature3ViewModel.class);

        binding = FragmentFeature3Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFeature3;
        feature3ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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