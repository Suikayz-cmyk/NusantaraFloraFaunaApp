package com.example.nusantaraflorafaunaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat; // <-- IMPORT INI UNTUK BAHASA
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

        // --- INISIALISASI TOMBOL BAHASA ---
        ImageButton btnLangToggle = findViewById(R.id.btnLangToggle);

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

        // --- LANGUAGE TOGGLE (GANTI BAHASA) ---
        btnLangToggle.setOnClickListener(v -> {
            LocaleListCompat currentLocale = AppCompatDelegate.getApplicationLocales();
            // Cek jika bahasa saat ini Inggris, ubah ke Indonesia (id), jika tidak ubah ke Inggris (en)
            if (currentLocale.toLanguageTags().equals("en")) {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("id"));
            } else {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"));
            }
        });

        // --- KLIK JUDUL UNTUK FILTER REGION ---
        tvPageTitle.setOnClickListener(v -> {
            // Ambil daftar wilayah dari strings.xml (akan otomatis menyesuaikan bahasa)
            String[] regions = getResources().getStringArray(R.array.region_array);

            // Munculkan Popup Alert Dialog
            new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                    .setTitle("Pilih Wilayah / Region")
                    .setItems(regions, (dialog, which) -> {
                        String selectedRegion = regions[which];

                        // 1. Kasih instruksi ke ViewModel untuk memfilter daftar (Fragment otomatis update!)
                        viewModel.setRegionFilter(selectedRegion);

                        // 2. Ganti teks judul agar sesuai dengan region yang dipilih
                        String currentTitle = tvPageTitle.getText().toString();

                        // Trik: Potong teks judul lama sebelum tanda ":" lalu sambung dengan region baru
                        if (currentTitle.contains(":")) {
                            String baseTitle = currentTitle.substring(0, currentTitle.indexOf(":")).trim();
                            viewModel.setPageTitle(baseTitle + " : " + selectedRegion);
                        }
                    })
                    .show();
        });
        // --------------------------------------

        // --- NAVIGASI KLIK ---
        findViewById(R.id.btnSearchHeader).setOnClickListener(v -> {
            // Gunakan getString agar bisa menyesuaikan bahasa
            tvPageTitle.setText(getString(R.string.title_search));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SearchFragment())
                    .addToBackStack(null)
                    .commit();
        });

        findViewById(R.id.btnFavHeader).setOnClickListener(v -> {
            viewModel.setPageTitle(getString(R.string.title_favorit)); // Sesuaikan bahasa
            viewModel.setViewToggleVisible(true);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FavoritFragment()).commit();
        });

        findViewById(R.id.btnAkunHeader).setOnClickListener(v -> {
            viewModel.setPageTitle(getString(R.string.title_akun)); // Sesuaikan bahasa
            viewModel.setViewToggleVisible(false);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AkunFragment()).commit();
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        int selectedItemId = bottomNav.getSelectedItemId();
        if (selectedItemId == R.id.nav_hewan) {
            viewModel.setPageTitle(getString(R.string.title_hewan));
        } else if (selectedItemId == R.id.nav_tumbuhan) {
            viewModel.setPageTitle(getString(R.string.title_tumbuhan));
        }
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.nav_hewan) {
                viewModel.setPageTitle(getString(R.string.title_hewan)); // Sesuaikan bahasa
                viewModel.setViewToggleVisible(true);
                selectedFragment = new HewanFragment();
            } else if (item.getItemId() == R.id.nav_tumbuhan) {
                viewModel.setPageTitle(getString(R.string.title_tumbuhan)); // Sesuaikan bahasa
                viewModel.setViewToggleVisible(true);
                selectedFragment = new TumbuhanFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment).commit();
            }
            return true;
        });

        // Tampilkan default HANYA saat aplikasi pertama kali dibuka
        if (savedInstanceState == null) {
            viewModel.setPageTitle(getString(R.string.title_hewan)); // Sesuaikan bahasa
            viewModel.setViewToggleVisible(true);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HewanFragment()).commit();
        }
    }
}