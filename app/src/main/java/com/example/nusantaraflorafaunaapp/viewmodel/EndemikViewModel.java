package com.example.nusantaraflorafaunaapp.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nusantaraflorafaunaapp.database.Endemik;
import com.example.nusantaraflorafaunaapp.repository.EndemikRepository;

import java.util.List;

public class EndemikViewModel extends AndroidViewModel {

    private EndemikRepository repository;
    private LiveData<List<Endemik>> allEndemik;
    private androidx.lifecycle.MutableLiveData<String> searchQuery = new androidx.lifecycle.MutableLiveData<>("");
    private androidx.lifecycle.MutableLiveData<String> regionFilter = new androidx.lifecycle.MutableLiveData<>("Semua Region");

    private final MutableLiveData<String> pageTitle = new MutableLiveData<>("List Hewan : Semua Region");
    private final MutableLiveData<Boolean> isViewToggleVisible = new MutableLiveData<>(true);
    public EndemikViewModel(@NonNull Application application) {
        super(application);
        repository = new EndemikRepository(application);
        allEndemik = repository.getAllEndemik();
    }

    // UI (Activity/Fragment) akan memanggil method ini untuk mendapatkan datanya
    public LiveData<List<Endemik>> getAllEndemik() {
        return allEndemik;
    }

    public LiveData<String> getPageTitle() { return pageTitle; }
    public void setPageTitle(String title) { pageTitle.setValue(title); }

    public LiveData<Boolean> getIsViewToggleVisible() { return isViewToggleVisible; }
    public void setViewToggleVisible(boolean visible) { isViewToggleVisible.setValue(visible); }

    public LiveData<List<Endemik>> getEndemikByTipe(String tipe) {
        return repository.getEndemikByTipe(tipe);
    }

    public void insertFavorit(String id) { repository.insertFavorit(id); }
    public void deleteFavorit(String id) { repository.deleteFavorit(id); }
    public LiveData<Integer> isFavorit(String id) { return repository.isFavorit(id); }
    public LiveData<List<Endemik>> getModelFavorit() { return repository.getModelFavorit(); }
    public void setSearchQuery(String query) { searchQuery.setValue(query); }
    public LiveData<String> getSearchQuery() { return searchQuery; }

    public void setRegionFilter(String region) { regionFilter.setValue(region); }
    public LiveData<String> getRegionFilter() { return regionFilter; }

    private final MutableLiveData<Boolean> isGridView = new MutableLiveData<>(true);

    public LiveData<Boolean> getIsGridView() {
        return isGridView;
    }

    public void toggleGridView() {
        if (isGridView.getValue() != null) {
            isGridView.setValue(!isGridView.getValue());
        }
    }
}