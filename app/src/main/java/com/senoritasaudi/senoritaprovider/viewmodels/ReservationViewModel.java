package com.senoritasaudi.senoritaprovider.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.senoritasaudi.senoritaprovider.models.ClinicModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.FeedbackResponse;
import com.senoritasaudi.senoritaprovider.models.responsemodels.OfferResponseModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.RequestsModelResponse;
import com.senoritasaudi.senoritaprovider.models.responsemodels.ReservationResponseModel;
import com.senoritasaudi.senoritaprovider.repositories.MainRepository;
import com.senoritasaudi.senoritaprovider.storeutils.StoreManager;

import java.util.HashMap;

public class ReservationViewModel extends AndroidViewModel {

    private MainRepository mainRepository;
    private StoreManager storeManager;

    public ReservationViewModel(@NonNull Application application) {
        super(application);
        mainRepository = MainRepository.getInstance();
        storeManager = new StoreManager(application);
    }

    public LiveData<RequestsModelResponse> getRequest(String requestId) {
        return mainRepository.getRequest(requestId,getClinic().getId());
    }

    public LiveData<FeedbackResponse> editRequest(HashMap<String , String> hashMap) {
        return mainRepository.editRequest(hashMap);
    }

    public ClinicModel getClinic() {
        return storeManager.getClinic();
    }

    public boolean containsUser() {
        return storeManager.containsUser();
    }

    public LiveData<ReservationResponseModel> addRequest(HashMap<String , String> hashMap) {
        return mainRepository.addRequest(hashMap);
    }

    public LiveData<OfferResponseModel> getOffer(String offerId) {
        if (storeManager.containsUser()) {
            return mainRepository.getOffer(offerId, storeManager.getUser().getId());
        } else {
            return mainRepository.getOffer(offerId, "0");
        }
    }
}
