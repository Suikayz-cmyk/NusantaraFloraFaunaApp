package com.example.nusantaraflorafaunaapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nusantaraflorafaunaapp.R;
import com.example.nusantaraflorafaunaapp.adapter.EndemikAdapter;
import com.example.nusantaraflorafaunaapp.viewmodel.EndemikViewModel;

public class SearchFragment extends Fragment {

    private EndemikAdapter adapter;
    private EndemikViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ImageButton btnBack = view.findViewById(R.id.btnBackSearch);
        SearchView searchView = view.findViewById(R.id.searchViewFragment);
        Spinner spinnerRegion = view.findViewById(R.id.spinnerRegionSearch);
        RecyclerView rvSearch = view.findViewById(R.id.rvSearch);

        rvSearch.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new EndemikAdapter(requireContext());
        rvSearch.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(EndemikViewModel.class);

        viewModel.getAllEndemik().observe(getViewLifecycleOwner(), endemikList -> {
            if (endemikList != null) {
                adapter.setEndemikList(endemikList);
            }
        });

        // 1. Aksi ketik kata kunci
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.setSearchQuery(newText);
                adapter.filter(newText, viewModel.getRegionFilter().getValue());
                return true;
            }
        });

        // 2. Aksi pilih Region di dalam halaman Search
        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String region = parent.getItemAtPosition(position).toString();
                viewModel.setRegionFilter(region);
                adapter.filter(viewModel.getSearchQuery().getValue(), region);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // 3. Tombol Kembali ditekan
        btnBack.setOnClickListener(v -> {
            TextView tvTitle = requireActivity().findViewById(R.id.tvPageTitle);
            if (tvTitle != null) tvTitle.setText("List Hewan : Semua Region"); // Balikan teks header utama

            viewModel.setSearchQuery("");
            viewModel.setRegionFilter("Semua Region");

            requireActivity().getSupportFragmentManager().popBackStack();
        });

        viewModel.getIsGridView().observe(getViewLifecycleOwner(), isGrid -> {
            adapter.setGridMode(isGrid);

            if (isGrid) {
                rvSearch.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2));
            } else {
                rvSearch.setLayoutManager(new LinearLayoutManager(requireContext()));
            }
        });

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (viewModel != null) {
            viewModel.setSearchQuery("");
            viewModel.setRegionFilter("Semua Region");
        }
    }
}