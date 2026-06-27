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

    // FITUR FAVORIT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorit(Favorit favorit);

    @Query("DELETE FROM tabel_favorit WHERE endemikId = :id")
    void deleteFavorit(String id);

    // Cek apakah item ini sudah difavoritkan (kembalikan > 0 jika iya)
    @Query("SELECT COUNT(*) FROM tabel_favorit WHERE endemikId = :id")
    LiveData<Integer> isFavorit(String id);

    // Ambil data lengkap Endemik yang ID-nya ada di tabel_favorit
    @Query("SELECT * FROM tabel_endemik WHERE id IN (SELECT endemikId FROM tabel_favorit) ORDER BY nama ASC")
    LiveData<List<Endemik>> getModelFavorit();

    // Hitung jumlah baris data secara sinkron (langsung)
    @Query("SELECT COUNT(id) FROM tabel_endemik") // Pastikan 'table_endemik' sesuai dengan nama tabel di @Entity kamu
    int getCountSync();

    // Masukkan banyak data (List) sekaligus
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    void insertAll(java.util.List<Endemik> endemikList);
}