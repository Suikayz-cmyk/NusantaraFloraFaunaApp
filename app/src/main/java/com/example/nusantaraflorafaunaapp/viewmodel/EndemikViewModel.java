package com.example.nusantaraflorafaunaapp.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.nusantaraflorafaunaapp.database.Endemik;
import com.example.nusantaraflorafaunaapp.repository.EndemikRepository;

import java.util.List;

public class EndemikViewModel extends AndroidViewModel {

    private EndemikRepository repository;
    private LiveData<List<Endemik>> allEndemik;

    public EndemikViewModel(@NonNull Application application) {
        super(application);
        repository = new EndemikRepository(application);
        allEndemik = repository.getAllEndemik();
    }

    // UI (Activity/Fragment) akan memanggil method ini untuk mendapatkan datanya
    public LiveData<List<Endemik>> getAllEndemik() {
        return allEndemik;
    }

    public LiveData<List<Endemik>> getEndemikByTipe(String tipe) {
        return repository.getEndemikByTipe(tipe);
    }
}