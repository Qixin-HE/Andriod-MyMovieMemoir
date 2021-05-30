package com.example.a2.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.a2.dao.CredentialsDAO;
import com.example.a2.database.CredentialsDatabase;
import com.example.a2.entity.Credentials;

import java.util.List;

public class CredentialsRepository {
    private CredentialsDAO dao;
    private LiveData<List<Credentials>> allCredentials;
    private Credentials credentials;

    public CredentialsRepository(Application application) {
        CredentialsDatabase db = CredentialsDatabase.getInstance(application);
        dao = db.credentialsDao();
    }

    public LiveData<List<Credentials>> getAllCredentials() {
        allCredentials = dao.getAll();
        return allCredentials;
    }

    public void insert(final Credentials credentials) {
        CredentialsDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(credentials);
            }
        });
    }

    /*public void deleteAll() {
        CredentialsDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll();
            }
        });
    }

     */

    public void delete(final Credentials credentials) {
        CredentialsDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(credentials);
            }
        });
    }

    public void insertAll(final Credentials... credentialss) {

        CredentialsDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertAll(credentialss);
            }
        });
    }

    public void updateCredentials(final Credentials... credentialss) {
        CredentialsDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateCredentials(credentials);
            }
        });
    }

    public Credentials findByID(final int credentialsId) {
        CredentialsDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Credentials runCredentials = dao.findByID(credentialsId);
                setCredentials(runCredentials);
            }
        });
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
