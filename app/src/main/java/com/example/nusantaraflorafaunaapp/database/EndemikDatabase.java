package com.example.nusantaraflorafaunaapp.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Endemik.class}, version = 1, exportSchema = false)
public abstract class EndemikDatabase extends RoomDatabase {

    private static volatile EndemikDatabase INSTANCE;

    public abstract EndemikDao endemikDao();

    public static EndemikDatabase getInstance(Context context) {
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