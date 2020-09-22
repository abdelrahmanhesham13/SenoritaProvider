package com.senoritasaudi.senoritaprovider.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.senoritasaudi.senoritaprovider.models.responsemodels.NotificationResponseModel;
import com.senoritasaudi.senoritaprovider.repositories.MainRepository;
import com.senoritasaudi.senoritaprovider.storeutils.StoreManager;

public class NotificationsViewModel extends AndroidViewModel {

    MutableLiveData<NotificationResponseModel> notificationResponseModelLiveData;
    StoreManager storeManager;

    public NotificationsViewModel(@NonNull Application application) {
        super(application);
        MainRepository mainRepository = MainRepository.getInstance();
        storeManager = new StoreManager(application);
        if (storeManager.containsUser()) {
            notificationResponseModelLiveData = (MutableLiveData<NotificationResponseModel>) mainRepository.getNotifications(storeManager.getClinic().getId(),storeManager.getUser().getId());
        } else {
            notificationResponseModelLiveData = new MutableLiveData<>();
            notificationResponseModelLiveData.setValue(null);
        }
    }


    public LiveData<NotificationResponseModel> getNotificationResponseModelLiveData() {
        return notificationResponseModelLiveData;
    }

    public boolean containsUser() {
        return storeManager.containsUser();
    }

}
