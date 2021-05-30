package com.example.a2.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a2.entity.Credentials;
import com.example.a2.repository.CredentialsRepository;

import java.util.List;

public class CredentialsViewModel extends ViewModel {
    private CredentialsRepository cRepository;

    private MutableLiveData<List<Credentials>> allCredentials;

    public CredentialsViewModel() {
        allCredentials = new MutableLiveData<>();
    }

    public void setCredentials(List<Credentials> credentialss) {
        allCredentials.setValue(credentialss);
    }

    public LiveData<List<Credentials>> getAllCredentials() {
        return cRepository.getAllCredentials();
    }

    public void initalizeVars(Application application) {
        cRepository = new CredentialsRepository(application);
    }

    public void insert(Credentials credentials) {
        cRepository.insert(credentials);
    }

    public void insertAll(Credentials... credentialss) {
        cRepository.insertAll(credentialss);
    }
/*
    public void deleteAll() {
        cRepository.deleteAll();
    }

 */

    public void insertAll(Credentials credentials) {
        cRepository.delete(credentials);
    }

    public void update(Credentials... credentialss) {
        cRepository.updateCredentials(credentialss);
    }

    public Credentials insertAll(int id) {
        return cRepository.findByID(id);
    }

    public Credentials findByID(int credentialsId) {
        return cRepository.findByID(credentialsId);
    }
}
