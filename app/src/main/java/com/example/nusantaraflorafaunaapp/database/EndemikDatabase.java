package com.example.nusantaraflorafaunaapp.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Endemik.class, Favorit.class}, version = 2, exportSchema = false)
public abstract class EndemikDatabase extends RoomDatabase {

    private static volatile EndemikDatabase INSTANCE;
    public abstract EndemikDao endemikDao();

    // ExecutorService digunakan agar proses simpan data berjalan di background thread
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static EndemikDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (EndemikDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EndemikDatabase.class, "endemik_database")
                            .fallbackToDestructiveMigration()
                            // Tambahkan Callback di sini
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    // Panggil method prepopulate saat DB pertama kali dibuat
                                    databaseWriteExecutor.execute(() -> {
                                        EndemikDao dao = INSTANCE.endemikDao();
                                        prepopulateDatabase(context, dao);
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Method untuk membaca JSON dan menyimpannya ke Room
    private static void prepopulateDatabase(Context context, EndemikDao dao) {
        try {
            // 1. Baca file endemik.json dari folder assets
            InputStream is = context.getAssets().open("endemik.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String jsonStr = new String(buffer, "UTF-8");

            // 2. Parse JSON ke bentuk List<Endemik> menggunakan Gson
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Endemik>>(){}.getType();
            List<Endemik> endemikList = gson.fromJson(jsonStr, listType);

            // 3. Masukkan data ke Room Database
            if (endemikList != null && !endemikList.isEmpty()) {
                dao.insertAllEndemik(endemikList);
                Log.d("RoomDB", "Berhasil memasukkan " + endemikList.size() + " data ke database!");
            }

        } catch (Exception e) {
            Log.e("RoomDB", "Error saat prepopulate database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}