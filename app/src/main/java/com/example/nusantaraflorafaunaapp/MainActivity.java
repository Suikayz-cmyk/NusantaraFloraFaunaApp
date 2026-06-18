package com.example.nusantaraflorafaunaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import com.example.nusantaraflorafaunaapp.ui.AkunFragment;
import com.example.nusantaraflorafaunaapp.ui.FavoritFragment;
import com.example.nusantaraflorafaunaapp.ui.HewanFragment;
import com.example.nusantaraflorafaunaapp.ui.TumbuhanFragment;
import com.example.nusantaraflorafaunaapp.viewmodel.EndemikViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EndemikViewModel viewModel = new androidx.lifecycle.ViewModelProvider(this).get(EndemikViewModel.class);

        androidx.appcompat.widget.SearchView searchView = findViewById(R.id.searchView);
        android.widget.Spinner spinnerRegion = findViewById(R.id.spinnerRegion);

        // Listener untuk Search
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.setSearchQuery(newText); // Kirim kata kunci ke ViewModel
                return true;
            }
        });

        // Listener untuk Filter Region
        spinnerRegion.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                String region = parent.getItemAtPosition(position).toString();
                viewModel.setRegionFilter(region); // Kirim region ke ViewModel
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        // Setup Navigasi Bawah (Hanya Hewan & Tumbuhan)
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.nav_hewan) {
                selectedFragment = new HewanFragment();
            } else if (item.getItemId() == R.id.nav_tumbuhan) {
                selectedFragment = new TumbuhanFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment).commit();
            }
            return true;
        });

        // Setup Navigasi Tombol Atas (Favorit & Akun)
        findViewById(R.id.btnHeaderFavorit).setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FavoritFragment()).commit();
        });

        findViewById(R.id.btnHeaderAkun).setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AkunFragment()).commit();
        });

        // Tampilkan default fragment (Hewan)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HewanFragment()).commit();
        }
    }
}