package com.example.nusantaraflorafaunaapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tabel_favorit")
public class Favorit {

    @PrimaryKey
    @NonNull
    private String endemikId;

    public Favorit(@NonNull String endemikId) {
        this.endemikId = endemikId;
    }

    @NonNull
    public String getEndemikId() { return endemikId; }
    public void setEndemikId(@NonNull String endemikId) { this.endemikId = endemikId; }
}