package com.example.nusantaraflorafaunaapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.nusantaraflorafaunaapp.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class AkunFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_akun, container, false);

        SwitchMaterial switchDarkMode = view.findViewById(R.id.switchDarkMode);

        // Cek apakah mode malam sedang aktif saat ini
        int currentNightMode = AppCompatDelegate.getDefaultNightMode();
        switchDarkMode.setChecked(currentNightMode == AppCompatDelegate.MODE_NIGHT_YES);

        // Aksi ketika switch ditekan
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        return view;
    }
}