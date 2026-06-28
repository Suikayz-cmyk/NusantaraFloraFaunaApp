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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorit(Favorit favorit);

    @Query("DELETE FROM tabel_favorit WHERE endemikId = :id")
    void deleteFavorit(String id);

    @Query("SELECT COUNT(*) FROM tabel_favorit WHERE endemikId = :id")
    LiveData<Integer> isFavorit(String id);

    @Query("SELECT * FROM tabel_endemik WHERE id IN (SELECT endemikId FROM tabel_favorit) ORDER BY nama ASC")
    LiveData<List<Endemik>> getModelFavorit();

    @Query("SELECT COUNT(id) FROM tabel_endemik")
    int getCountSync();

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    void insertAll(java.util.List<Endemik> endemikList);
}