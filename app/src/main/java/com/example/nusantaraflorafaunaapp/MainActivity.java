package com.example.nusantaraflorafaunaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nusantaraflorafaunaapp.ui.AkunFragment;
import com.example.nusantaraflorafaunaapp.ui.FavoritFragment;
import com.example.nusantaraflorafaunaapp.ui.HewanFragment;
import com.example.nusantaraflorafaunaapp.ui.SearchFragment;
import com.example.nusantaraflorafaunaapp.ui.TumbuhanFragment;
import com.example.nusantaraflorafaunaapp.viewmodel.EndemikViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private EndemikViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(EndemikViewModel.class);

        TextView tvPageTitle = findViewById(R.id.tvPageTitle);
        ImageButton btnThemeToggle = findViewById(R.id.btnThemeToggle);
        ImageButton btnViewToggle = findViewById(R.id.btnViewToggle);

        // --- OBSERVER UNTUK HEADER ---
        // 1. Update teks judul otomatis
        viewModel.getPageTitle().observe(this, title -> tvPageTitle.setText(title));

        // 2. Sembunyikan/Tampilkan tombol Grid
        viewModel.getIsViewToggleVisible().observe(this, visible -> {
            btnViewToggle.setVisibility(visible ? View.VISIBLE : View.GONE);
        });

        // --- THEME TOGGLE (HEADER) ---
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean isNightMode = currentNightMode == Configuration.UI_MODE_NIGHT_YES;
        btnThemeToggle.setImageResource(isNightMode ? R.drawable.ic_sun : R.drawable.ic_moon);

        btnThemeToggle.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(isNightMode ?
                    AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);
        });

        // --- VIEW TOGGLE (GRID/LIST) ---
        viewModel.getIsGridView().observe(this, isGrid -> {
            btnViewToggle.setImageResource(isGrid ? R.drawable.ic_list : R.drawable.ic_grid);
        });
        btnViewToggle.setOnClickListener(v -> viewModel.toggleGridView());

        // --- NAVIGASI KLIK ---
        findViewById(R.id.btnSearchHeader).setOnClickListener(v -> {
            tvPageTitle.setText("Pencarian Spesies");

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SearchFragment())
                    .addToBackStack(null)
                    .commit();
        });

        findViewById(R.id.btnFavHeader).setOnClickListener(v -> {
            viewModel.setPageTitle("Favorit Tersimpan");
            viewModel.setViewToggleVisible(true); // Tampilkan toggle grid
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FavoritFragment()).commit();
        });

        findViewById(R.id.btnAkunHeader).setOnClickListener(v -> {
            viewModel.setPageTitle("Informasi Akun");
            viewModel.setViewToggleVisible(false); // Sembunyikan toggle grid
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AkunFragment()).commit();
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.nav_hewan) {
                viewModel.setPageTitle("List Hewan : Semua Region");
                viewModel.setViewToggleVisible(true);
                selectedFragment = new HewanFragment();
            } else if (item.getItemId() == R.id.nav_tumbuhan) {
                viewModel.setPageTitle("List Tumbuhan : Semua Region");
                viewModel.setViewToggleVisible(true);
                selectedFragment = new TumbuhanFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment).commit();
            }
            return true;
        });

        // Tampilkan default HANYA saat aplikasi pertama kali dibuka (bukan saat ganti tema)
        if (savedInstanceState == null) {
            viewModel.setPageTitle("List Hewan : Semua Region");
            viewModel.setViewToggleVisible(true);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HewanFragment()).commit();
        }
    }
}