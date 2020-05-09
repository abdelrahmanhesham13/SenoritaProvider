package com.senoritasaudi.senoritaprovider.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.senoritasaudi.senoritaprovider.models.ClinicModel;
import com.senoritasaudi.senoritaprovider.models.UserModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.OfferResponseModel;
import com.senoritasaudi.senoritaprovider.repositories.MainRepository;
import com.senoritasaudi.senoritaprovider.storeutils.StoreManager;

import java.util.HashMap;

public class OffersViewModel extends AndroidViewModel {

    private StoreManager storeManager;
    private MainRepository mainRepository;

    public OffersViewModel(@NonNull Application application) {
        super(application);
        mainRepository = MainRepository.getInstance();
        storeManager = new StoreManager(application);

    }

    public LiveData<OfferResponseModel> search(HashMap<String,String> hashMap) {
        if (storeManager.containsUser()) {
            hashMap.put("user_id",storeManager.getUser().getId());
        }
        return mainRepository.search(hashMap);
    }

    public UserModel getUser() {
        return storeManager.getUser();
    }
    public ClinicModel getClinic() {
        return storeManager.getClinic();
    }

    public void saveUser(UserModel userModel) {
        storeManager.saveUser(userModel);
    }

    public boolean containsUser() {
        return storeManager.containsUser();
    }

}
