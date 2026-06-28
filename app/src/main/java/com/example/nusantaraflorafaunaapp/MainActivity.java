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
        ImageButton btnLangToggle = findViewById(R.id.btnLangToggle);

        viewModel.getPageTitle().observe(this, title -> tvPageTitle.setText(title));

        viewModel.getIsViewToggleVisible().observe(this, visible -> {
            btnViewToggle.setVisibility(visible ? View.VISIBLE : View.GONE);
        });

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean isNightMode = currentNightMode == Configuration.UI_MODE_NIGHT_YES;
        btnThemeToggle.setImageResource(isNightMode ? R.drawable.ic_sun : R.drawable.ic_moon);

        btnThemeToggle.setOnClickListener(v -> {
            AppCompatDelegate.setDefaultNightMode(isNightMode ?
                    AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);
        });

        viewModel.getIsGridView().observe(this, isGrid -> {
            btnViewToggle.setImageResource(isGrid ? R.drawable.ic_list : R.drawable.ic_grid);
        });
        btnViewToggle.setOnClickListener(v -> viewModel.toggleGridView());

        btnLangToggle.setOnClickListener(v -> {
            LocaleListCompat currentLocale = AppCompatDelegate.getApplicationLocales();
            if (currentLocale.toLanguageTags().equals("en")) {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("id"));
            } else {
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"));
            }
        });

        tvPageTitle.setOnClickListener(v -> {
            String[] regions = getResources().getStringArray(R.array.region_array);

            new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                    .setTitle("Pilih Wilayah / Region")
                    .setItems(regions, (dialog, which) -> {
                        String selectedRegion = regions[which];

                        viewModel.setRegionFilter(selectedRegion);

                        String currentTitle = tvPageTitle.getText().toString();

                        if (currentTitle.contains(":")) {
                            String baseTitle = currentTitle.substring(0, currentTitle.indexOf(":")).trim();
                            viewModel.setPageTitle(baseTitle + " : " + selectedRegion);
                        }
                    })
                    .show();
        });

        findViewById(R.id.btnSearchHeader).setOnClickListener(v -> {
            tvPageTitle.setText(getString(R.string.title_search));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SearchFragment())
                    .addToBackStack(null)
                    .commit();
        });

        findViewById(R.id.btnFavHeader).setOnClickListener(v -> {
            viewModel.setPageTitle(getString(R.string.title_favorit));
            viewModel.setViewToggleVisible(true);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FavoritFragment()).commit();
        });

        findViewById(R.id.btnAkunHeader).setOnClickListener(v -> {
            viewModel.setPageTitle(getString(R.string.title_akun));
            viewModel.setViewToggleVisible(false);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AkunFragment()).commit();
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        findViewById(R.id.fragment_container).post(() -> {
            Fragment currentFrag = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

            if (currentFrag instanceof HewanFragment) {
                viewModel.setPageTitle(getString(R.string.title_hewan));
            } else if (currentFrag instanceof TumbuhanFragment) {
                viewModel.setPageTitle(getString(R.string.title_tumbuhan));
            } else if (currentFrag instanceof FavoritFragment) {
                viewModel.setPageTitle(getString(R.string.title_favorit));
            } else if (currentFrag instanceof AkunFragment) {
                viewModel.setPageTitle(getString(R.string.title_akun));
            } else if (currentFrag instanceof SearchFragment) {
                viewModel.setPageTitle(getString(R.string.title_search));
            }
        });

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.nav_hewan) {
                viewModel.setPageTitle(getString(R.string.title_hewan));
                viewModel.setViewToggleVisible(true);
                selectedFragment = new HewanFragment();
            } else if (item.getItemId() == R.id.nav_tumbuhan) {
                viewModel.setPageTitle(getString(R.string.title_tumbuhan));
                viewModel.setViewToggleVisible(true);
                selectedFragment = new TumbuhanFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment).commit();
            }
            return true;
        });

        if (savedInstanceState == null) {
            viewModel.setPageTitle(getString(R.string.title_hewan));
            viewModel.setViewToggleVisible(true);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HewanFragment()).commit();
        }
    }
}