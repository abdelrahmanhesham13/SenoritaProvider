package com.senoritasaudi.senoritaprovider.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.senoritasaudi.senoritaprovider.models.ClinicModel;
import com.senoritasaudi.senoritaprovider.models.UserModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.LoginResponseModel;
import com.senoritasaudi.senoritaprovider.repositories.MainRepository;
import com.senoritasaudi.senoritaprovider.storeutils.Constants;
import com.senoritasaudi.senoritaprovider.storeutils.StoreManager;

public class SplashViewModel extends AndroidViewModel {

    private StoreManager mStoreManager;
    private MainRepository mainRepository;

    public SplashViewModel(@NonNull Application application) {
        super(application);
        mStoreManager = new StoreManager(application);
        mainRepository = MainRepository.getInstance();
    }

    public boolean showIntro() {
        return mStoreManager.getBooleanValueForKey(Constants.SharedPreferenceConstants.SHOW_INTRO,Constants.SharedPreferenceConstants.SHOW_INTRO_DEFAULT_VALUE);
    }



    public void setShowIntro(boolean showIntro) {
        mStoreManager.setBooleanValueForKey(Constants.SharedPreferenceConstants.SHOW_INTRO,showIntro);
    }

    public LiveData<LoginResponseModel> login(String phone , String password , String token) {
        return mainRepository.login(token,password,phone);
    }

//    public LiveData<FeedbackResponse> updateToken() {
//        return mainRepository.updateToken(getUser().getId(),mStoreManager.getToken());
//    }
//
//    public LiveData<UserResponseModel> refreshUser(String id) {
//        return mainRepository.getUser(id);
//    }
//
//    public void saveUser(UserModel userModel) {
//        mStoreManager.saveUser(userModel);
//    }

    public boolean containsUser() {
        return mStoreManager.containsUser();
    }

    public UserModel getUser() {
        return mStoreManager.getUser();
    }
    public void saveUser(UserModel userModel) {
        mStoreManager.saveUser(userModel);
    }

    public void saveClinic(ClinicModel clinicModel) {
        mStoreManager.saveClinic(clinicModel);
    }

    public void removeUser() {
        mStoreManager.removeUser();
    }

    public void removeClinic() {
        mStoreManager.removeClinic();
    }


    public String getToken() {
        return mStoreManager.getToken();
    }
}
