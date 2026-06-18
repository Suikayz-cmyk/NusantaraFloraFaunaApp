package com.example.nusantaraflorafaunaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.nusantaraflorafaunaapp.adapter.EndemikAdapter;
import com.example.nusantaraflorafaunaapp.viewmodel.EndemikViewModel;

public class MainActivity extends AppCompatActivity {

    private EndemikViewModel viewModel;
    private EndemikAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Setup RecyclerView & Adapter
        RecyclerView rvEndemik = findViewById(R.id.rvEndemik);
        rvEndemik.setLayoutManager(new LinearLayoutManager(this));

        adapter = new EndemikAdapter(this);
        rvEndemik.setAdapter(adapter);

        // 2. Inisialisasi ViewModel
        viewModel = new ViewModelProvider(this).get(EndemikViewModel.class);

        // 3. Observe Data (Mengamati perubahan data dari Room)
        viewModel.getAllEndemik().observe(this, endemikList -> {
            if (endemikList != null) {
                // Jika ada data, masukkan ke adapter untuk ditampilkan
                adapter.setEndemikList(endemikList);
            }
        });
    }
}