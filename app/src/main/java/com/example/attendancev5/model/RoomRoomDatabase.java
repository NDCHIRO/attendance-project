package com.example.attendancev5.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Room.class}, version = 1, exportSchema = false)
public abstract class RoomRoomDatabase extends RoomDatabase {
    public abstract RoomDAO roomDao();
    private static RoomRoomDatabase INSTANCE;

    static RoomRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = androidx.room.Room.databaseBuilder(context.getApplicationContext(),
                            RoomRoomDatabase.class, "word_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
