package com.example.nusantaraflorafaunaapp.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Endemik.class, Favorit.class}, version = 2, exportSchema = false)
public abstract class EndemikDatabase extends RoomDatabase {
    private static volatile EndemikDatabase INSTANCE;
    public abstract EndemikDao endemikDao();

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static EndemikDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (EndemikDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EndemikDatabase.class, "endemik_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}