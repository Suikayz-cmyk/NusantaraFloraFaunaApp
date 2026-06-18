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

import com.example.nusantaraflorafaunaapp.R;
import com.example.nusantaraflorafaunaapp.adapter.EndemikAdapter;
import com.example.nusantaraflorafaunaapp.viewmodel.EndemikViewModel;

public class HewanFragment extends Fragment {

    private EndemikViewModel viewModel;
    private EndemikAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Karena kita belum buat fragment_hewan.xml khusus, kita pinjam struktur RecyclerView sederhana
        RecyclerView rvEndemik = new RecyclerView(requireContext());
        rvEndemik.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rvEndemik.setPadding(0, 16, 0, 16);
        rvEndemik.setClipToPadding(false);

        rvEndemik.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new EndemikAdapter(requireContext());
        rvEndemik.setAdapter(adapter);

        // Gunakan requireActivity() agar ViewModel tidak ter-reset
        viewModel = new ViewModelProvider(requireActivity()).get(EndemikViewModel.class);

        // Nanti di Phase selanjutnya kita ubah ini agar hanya filter "Hewan"
        viewModel.getAllEndemik().observe(getViewLifecycleOwner(), endemikList -> {
            if (endemikList != null) {
                adapter.setEndemikList(endemikList);
            }
        });

        return rvEndemik;
    }
}