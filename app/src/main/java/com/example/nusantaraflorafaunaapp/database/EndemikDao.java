package com.example.nusantaraflorafaunaapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EndemikDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllEndemik(List<Endemik> endemikList);

    @Query("SELECT * FROM tabel_endemik ORDER BY nama ASC")
    LiveData<List<Endemik>> getAllEndemik();

    @Query("SELECT * FROM tabel_endemik WHERE tipe = :tipe")
    LiveData<List<Endemik>> getEndemikByTipe(String tipe);
}