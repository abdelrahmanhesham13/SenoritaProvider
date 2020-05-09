package com.senoritasaudi.senoritaprovider.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.senoritasaudi.senoritaprovider.models.ClinicModel;
import com.senoritasaudi.senoritaprovider.models.UserModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.ClinicRequestsCountResponseModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.FeedbackResponse;
import com.senoritasaudi.senoritaprovider.models.responsemodels.RequestsModelResponse;
import com.senoritasaudi.senoritaprovider.models.responsemodels.ReviewResponseModel;
import com.senoritasaudi.senoritaprovider.repositories.MainRepository;
import com.senoritasaudi.senoritaprovider.storeutils.StoreManager;

import java.util.HashMap;

public class MainViewModel extends AndroidViewModel {

    private MainRepository mainRepository;
    StoreManager storeManager;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mainRepository = MainRepository.getInstance();
        storeManager = new StoreManager(application);
    }

    public LiveData<ReviewResponseModel> getReviews() {
        return mainRepository.getReviews(getClinic().getId());
    }

    public void saveUser(UserModel userModel) {
        storeManager.saveUser(userModel);
    }

    public UserModel getUser() {
        return storeManager.getUser();
    }

    public LiveData<ClinicRequestsCountResponseModel> getClinicRequestsCount() {
        return mainRepository.getClinicRequestsCount(getClinic().getId());
    }

    public LiveData<RequestsModelResponse> search(HashMap<String,String> hashMap) {
        return mainRepository.searchBookings(hashMap);
    }

    public LiveData<FeedbackResponse> deleteRequest(String requestId, String status, String clinicId) {
        return mainRepository.deleteRequest(requestId,status,clinicId);
    }

    public void saveClinic(ClinicModel clinicModel) {
        storeManager.saveClinic(clinicModel);
    }

    public ClinicModel getClinic() {
        return storeManager.getClinic();
    }

    public boolean containsUser() {
        return storeManager.containsUser();
    }

    public LiveData<RequestsModelResponse> getRequests(String status) {
        return mainRepository.getRequests(getClinic().getId(),status);
    }

}
