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

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_hewan) {
                selectedFragment = new HewanFragment();
            } else if (itemId == R.id.nav_tumbuhan) {
                selectedFragment = new TumbuhanFragment();
            } else if (itemId == R.id.nav_favorit) {
                selectedFragment = new FavoritFragment();
            } else if (itemId == R.id.nav_akun) {
                selectedFragment = new AkunFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });

        // Tampilkan fragment Hewan saat aplikasi pertama kali dibuka
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HewanFragment())
                    .commit();
        }
    }
}