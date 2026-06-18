package com.example.nusantaraflorafaunaapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nusantaraflorafaunaapp.adapter.EndemikAdapter;
import com.example.nusantaraflorafaunaapp.viewmodel.EndemikViewModel;

public class HewanFragment extends Fragment {

    private EndemikViewModel viewModel;
    private EndemikAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView rvEndemik = new RecyclerView(requireContext());
        rvEndemik.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rvEndemik.setPadding(0, 16, 0, 16);
        rvEndemik.setClipToPadding(false);

        rvEndemik.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new EndemikAdapter(requireContext());
        rvEndemik.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(EndemikViewModel.class);

        // Mengambil data KHUSUS HEWAN
        viewModel.getEndemikByTipe("Hewan").observe(getViewLifecycleOwner(), endemikList -> {
            if (endemikList != null) {
                adapter.setEndemikList(endemikList);

                // Menerapkan filter saat data pertama kali dimuat
                String currentQuery = viewModel.getSearchQuery().getValue();
                String currentRegion = viewModel.getRegionFilter().getValue();
                adapter.filter(currentQuery, currentRegion);
            }
        });

        // Observers untuk Search dan Filter
        viewModel.getSearchQuery().observe(getViewLifecycleOwner(), query -> {
            String currentRegion = viewModel.getRegionFilter().getValue();
            adapter.filter(query, currentRegion);
        });

        viewModel.getRegionFilter().observe(getViewLifecycleOwner(), region -> {
            String currentQuery = viewModel.getSearchQuery().getValue();
            adapter.filter(currentQuery, region);
        });

        return rvEndemik;
    }
}