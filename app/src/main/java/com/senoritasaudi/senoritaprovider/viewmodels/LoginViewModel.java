package com.senoritasaudi.senoritaprovider.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.senoritasaudi.senoritaprovider.models.ClinicModel;
import com.senoritasaudi.senoritaprovider.models.UserModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.LoginResponseModel;
import com.senoritasaudi.senoritaprovider.repositories.MainRepository;
import com.senoritasaudi.senoritaprovider.storeutils.StoreManager;
import java.io.File;

public class LoginViewModel extends AndroidViewModel {

    MainRepository mainRepository;
    StoreManager storeManager;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mainRepository = MainRepository.getInstance();
        storeManager = new StoreManager(application);
    }

    public LiveData<LoginResponseModel> login(String phone , String password , String token) {
        return mainRepository.login(token,password,phone);
    }

    public void saveUser(UserModel userModel) {
        storeManager.saveUser(userModel);
    }

    public void saveClinic(ClinicModel clinicModel) {
        storeManager.saveClinic(clinicModel);
    }

    public void removeUser() {
        storeManager.removeUser();
    }

    public void removeClinic() {
        storeManager.removeClinic();
    }

    public UserModel getUser() {
        return storeManager.getUser();
    }

    public ClinicModel getClinic() {
        return storeManager.getClinic();
    }

    public String getToken() {
        return storeManager.getToken();
    }

    public boolean containsUser() {
        return storeManager.containsUser();
    }
}
