package com.example.a2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.a2.Converters;
import com.example.a2.dao.CredentialsDAO;
import com.example.a2.entity.Credentials;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Credentials.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CredentialsDatabase extends RoomDatabase {
    public abstract CredentialsDAO credentialsDao();
    private static CredentialsDatabase INSTANCE;
    //ExecutorService-room tutorial Task4
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized CredentialsDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    CredentialsDatabase.class, "CredentialsDatabase") .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE; }
}
