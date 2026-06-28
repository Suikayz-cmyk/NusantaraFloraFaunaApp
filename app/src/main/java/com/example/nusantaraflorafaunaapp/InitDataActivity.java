package com.example.nusantaraflorafaunaapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.nusantaraflorafaunaapp.database.Endemik;
import com.example.nusantaraflorafaunaapp.database.EndemikDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executors;

public class InitDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Cek langsung ke database Room
        Executors.newSingleThreadExecutor().execute(() -> {
            EndemikDatabase db = EndemikDatabase.getInstance(this);
            int count = db.endemikDao().getCountSync();

            runOnUiThread(() -> {
                if (count > 0) {
                    // Data sudah ada, langsung lompat ke Home!
                    goToHome();
                } else {
                    // Database KOSONG. Tampilkan layar inisialisasi data.
                    setupUI();
                }
            });
        });
    }

    private void setupUI() {
        setContentView(R.layout.activity_init_data);
        Button btnLanjut = findViewById(R.id.btnLanjutInit);

        btnLanjut.setOnClickListener(v -> {
            btnLanjut.setEnabled(false);
            btnLanjut.setText("Menarik & Menyimpan Data...");

            // 2. Simulasi proses penarikan dari "API" (Membaca file JSON secara manual)
            Executors.newSingleThreadExecutor().execute(() -> {
                try {
                    // Buka file endemik.json dari folder assets
                    InputStream is = getAssets().open("endemik.json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();

                    // Ubah byte menjadi teks JSON
                    String jsonString = new String(buffer, "UTF-8");

                    // 3. Parsing JSON mentah menjadi List<Endemik> menggunakan Gson
                    Type listType = new TypeToken<List<Endemik>>(){}.getType();
                    List<Endemik> endemikList = new Gson().fromJson(jsonString, listType);

                    // 4. Masukkan semua data hasil parsing ke dalam Room Database
                    EndemikDatabase.getInstance(this).endemikDao().insertAll(endemikList);

                    // Proses selesai, pergi ke Home
                    runOnUiThread(this::goToHome);

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        btnLanjut.setEnabled(true);
                        btnLanjut.setText("Gagal, coba lagi");
                    });
                }
            });
        });
    }

    private void goToHome() {
        Intent intent = new Intent(InitDataActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}