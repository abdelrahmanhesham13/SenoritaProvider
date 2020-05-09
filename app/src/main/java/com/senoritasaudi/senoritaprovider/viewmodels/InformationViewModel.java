package com.senoritasaudi.senoritaprovider.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.senoritasaudi.senoritaprovider.models.responsemodels.InformationResponseModel;
import com.senoritasaudi.senoritaprovider.repositories.MainRepository;

public class InformationViewModel extends ViewModel {

    private String page;
    private MainRepository mainRepository;

    public InformationViewModel(String page) {
        this.page = page;
        mainRepository = MainRepository.getInstance();
    }

    public LiveData<InformationResponseModel> getInformation() {
        return mainRepository.getInformation(page);
    }
}
