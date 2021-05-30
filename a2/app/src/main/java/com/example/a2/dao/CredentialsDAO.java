package com.example.a2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.a2.entity.Credentials;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CredentialsDAO {
    @Query("SELECT * FROM credentials")
    LiveData<List<Credentials>> getAll();

    @Query("SELECT * FROM credentials WHERE uid = :credentialsId LIMIT 1")
    Credentials findByID(int credentialsId);

    @Insert
    void insertAll(Credentials... credentials);

    @Insert
    long insert(Credentials credentials);

    @Delete
    void delete(Credentials credentials);

    @Update(onConflict = REPLACE)
    void updateCredentials(Credentials... credentials);
}
