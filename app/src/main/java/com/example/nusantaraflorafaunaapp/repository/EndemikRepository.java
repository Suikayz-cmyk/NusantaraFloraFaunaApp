package com.example.nusantaraflorafaunaapp.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.nusantaraflorafaunaapp.database.Endemik;
import com.example.nusantaraflorafaunaapp.database.EndemikDao;
import com.example.nusantaraflorafaunaapp.database.EndemikDatabase;
import com.example.nusantaraflorafaunaapp.database.Favorit;

import java.util.List;

public class EndemikRepository {

    private EndemikDao endemikDao;
    private LiveData<List<Endemik>> allEndemik;

    // Constructor repository
    public EndemikRepository(Application application) {
        EndemikDatabase database = EndemikDatabase.getInstance(application);
        endemikDao = database.endemikDao();
        // Mengambil semua data secara default
        allEndemik = endemikDao.getAllEndemik();
    }

    // Method yang akan dipanggil oleh ViewModel nanti
    public LiveData<List<Endemik>> getAllEndemik() {
        return allEndemik;
    }

    // Method opsional jika nanti kita ingin filter berdasarkan tipe
    public LiveData<List<Endemik>> getEndemikByTipe(String tipe) {
        return endemikDao.getEndemikByTipe(tipe);
    }

    public void insertFavorit(String id) {
        EndemikDatabase.databaseWriteExecutor.execute(() -> endemikDao.insertFavorit(new Favorit(id)));
    }

    public void deleteFavorit(String id) {
        EndemikDatabase.databaseWriteExecutor.execute(() -> endemikDao.deleteFavorit(id));
    }

    public LiveData<Integer> isFavorit(String id) {
        return endemikDao.isFavorit(id);
    }

    public LiveData<List<Endemik>> getModelFavorit() {
        return endemikDao.getModelFavorit();
    }
}