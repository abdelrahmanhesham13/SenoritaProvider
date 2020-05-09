package com.senoritasaudi.senoritaprovider.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.senoritasaudi.senoritaprovider.models.responsemodels.ClinicRequestsCountResponseModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.FeedbackResponse;
import com.senoritasaudi.senoritaprovider.models.responsemodels.InformationResponseModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.LoginResponseModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.NotificationResponseModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.OfferResponseModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.RequestsModelResponse;
import com.senoritasaudi.senoritaprovider.models.responsemodels.ReservationResponseModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.ReviewResponseModel;
import com.senoritasaudi.senoritaprovider.networkutils.ApiService;
import com.senoritasaudi.senoritaprovider.networkutils.RetrofitService;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    private static MainRepository mainRepository;
    private ApiService apiService;

    public static MainRepository getInstance() {
        if (mainRepository == null) {
            mainRepository = new MainRepository();
        }
        return mainRepository;
    }

    public LiveData<OfferResponseModel> getOffer(String offerId, String userId) {
        final MutableLiveData<OfferResponseModel> offerResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getOffer(offerId, userId).enqueue(new Callback<OfferResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<OfferResponseModel> call, @NotNull Response<OfferResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus())
                        offerResponseModelMutableLiveData.setValue(response.body());
                    else
                        offerResponseModelMutableLiveData.setValue(null);
                } else {
                    offerResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<OfferResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                offerResponseModelMutableLiveData.setValue(null);
            }
        });

        return offerResponseModelMutableLiveData;
    }

    public LiveData<ReservationResponseModel> addRequest(HashMap<String, String> hashMap) {
        final MutableLiveData<ReservationResponseModel> reservationResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.addRequest(hashMap).enqueue(new Callback<ReservationResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ReservationResponseModel> call, @NotNull Response<ReservationResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reservationResponseModelMutableLiveData.setValue(response.body());
                } else {
                    reservationResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ReservationResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                reservationResponseModelMutableLiveData.setValue(null);
            }
        });
        return reservationResponseModelMutableLiveData;
    }

    public LiveData<OfferResponseModel> search(HashMap<String,String> hashMap) {
        final MutableLiveData<OfferResponseModel> offerResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.search(hashMap).enqueue(new Callback<OfferResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<OfferResponseModel> call, @NotNull Response<OfferResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    offerResponseModelMutableLiveData.setValue(response.body());
                } else {
                    offerResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<OfferResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                offerResponseModelMutableLiveData.setValue(null);
            }
        });
        return offerResponseModelMutableLiveData;
    }

    public LiveData<RequestsModelResponse> searchBookings(HashMap<String,String> hashMap) {
        final MutableLiveData<RequestsModelResponse> offerResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.searchBookings(hashMap).enqueue(new Callback<RequestsModelResponse>() {
            @Override
            public void onResponse(@NotNull Call<RequestsModelResponse> call, @NotNull Response<RequestsModelResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    offerResponseModelMutableLiveData.setValue(response.body());
                } else {
                    offerResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<RequestsModelResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                offerResponseModelMutableLiveData.setValue(null);
            }
        });
        return offerResponseModelMutableLiveData;
    }

    public LiveData<ReviewResponseModel> getReviews(String clinicId) {
        final MutableLiveData<ReviewResponseModel> reviewResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getReview(clinicId).enqueue(new Callback<ReviewResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ReviewResponseModel> call, @NotNull Response<ReviewResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus())
                        reviewResponseModelMutableLiveData.setValue(response.body());
                    else
                        reviewResponseModelMutableLiveData.setValue(null);
                } else {
                    reviewResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ReviewResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                reviewResponseModelMutableLiveData.setValue(null);
            }
        });
        return reviewResponseModelMutableLiveData;
    }

    public LiveData<FeedbackResponse> sendFeedback(String name, String message, String mobile) {
        final MutableLiveData<FeedbackResponse> feedbackResponseMutableLiveData = new MutableLiveData<>();
        apiService.sendFeedBack(name, mobile, message).enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(@NotNull Call<FeedbackResponse> call, @NotNull Response<FeedbackResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    feedbackResponseMutableLiveData.setValue(response.body());
                } else {
                    feedbackResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<FeedbackResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                feedbackResponseMutableLiveData.setValue(null);
            }
        });
        return feedbackResponseMutableLiveData;
    }

    public LiveData<FeedbackResponse> sendRequest(String name, String message, String mobile, String userId) {
        final MutableLiveData<FeedbackResponse> feedbackResponseMutableLiveData = new MutableLiveData<>();
        apiService.sendRequest(name, mobile, message, userId).enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(@NotNull Call<FeedbackResponse> call, @NotNull Response<FeedbackResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    feedbackResponseMutableLiveData.setValue(response.body());
                } else {
                    feedbackResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<FeedbackResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                feedbackResponseMutableLiveData.setValue(null);
            }
        });
        return feedbackResponseMutableLiveData;
    }

    public LiveData<FeedbackResponse> editRequest(HashMap<String, String> hashMap) {
        final MutableLiveData<FeedbackResponse> reservationResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.editRequest(hashMap).enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(@NotNull Call<FeedbackResponse> call, @NotNull Response<FeedbackResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    reservationResponseModelMutableLiveData.setValue(response.body());
                } else {
                    reservationResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<FeedbackResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                reservationResponseModelMutableLiveData.setValue(null);
            }
        });
        return reservationResponseModelMutableLiveData;
    }

    public LiveData<FeedbackResponse> deleteRequest(String clinicId, String status , String requestId) {
        final MutableLiveData<FeedbackResponse> feedbackResponseMutableLiveData = new MutableLiveData<>();
        apiService.deleteRequest(requestId, status,clinicId).enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(@NotNull Call<FeedbackResponse> call, @NotNull Response<FeedbackResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    feedbackResponseMutableLiveData.setValue(response.body());
                } else {
                    feedbackResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<FeedbackResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                feedbackResponseMutableLiveData.setValue(null);
            }
        });
        return feedbackResponseMutableLiveData;
    }

    private MainRepository() {
        apiService = RetrofitService.getService();
    }

    public LiveData<ClinicRequestsCountResponseModel> getClinicRequestsCount(String clinicId) {
        final MutableLiveData<ClinicRequestsCountResponseModel> clinicRequestsCountResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getClinicRequestsCount(clinicId).enqueue(new Callback<ClinicRequestsCountResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ClinicRequestsCountResponseModel> call, @NotNull Response<ClinicRequestsCountResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    clinicRequestsCountResponseModelMutableLiveData.setValue(response.body());
                } else {
                    clinicRequestsCountResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ClinicRequestsCountResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                clinicRequestsCountResponseModelMutableLiveData.setValue(null);
            }
        });
        return clinicRequestsCountResponseModelMutableLiveData;
    }

    public LiveData<LoginResponseModel> login(String token, String password, String mobile) {
        final MutableLiveData<LoginResponseModel> loginResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.login(password, mobile).enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponseModel> call, @NotNull Response<LoginResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loginResponseModelMutableLiveData.setValue(response.body());
                } else {
                    loginResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                loginResponseModelMutableLiveData.setValue(null);
            }
        });
        return loginResponseModelMutableLiveData;
    }

    public LiveData<RequestsModelResponse> getRequests(String clinicId , String status) {
        final MutableLiveData<RequestsModelResponse> requestsModelResponseMutableLiveData = new MutableLiveData<>();
        apiService.getRequests(clinicId,status).enqueue(new Callback<RequestsModelResponse>() {
            @Override
            public void onResponse(@NotNull Call<RequestsModelResponse> call, @NotNull Response<RequestsModelResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus())
                        requestsModelResponseMutableLiveData.setValue(response.body());
                    else
                        requestsModelResponseMutableLiveData.setValue(null);
                } else {
                    requestsModelResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<RequestsModelResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                requestsModelResponseMutableLiveData.setValue(null);
            }
        });
        return requestsModelResponseMutableLiveData;
    }

    public LiveData<RequestsModelResponse> getRequest(String requestId , String clinicId) {
        final MutableLiveData<RequestsModelResponse> requestsModelResponseMutableLiveData = new MutableLiveData<>();
        apiService.getRequest(requestId,clinicId).enqueue(new Callback<RequestsModelResponse>() {
            @Override
            public void onResponse(@NotNull Call<RequestsModelResponse> call, @NotNull Response<RequestsModelResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus())
                        requestsModelResponseMutableLiveData.setValue(response.body());
                    else
                        requestsModelResponseMutableLiveData.setValue(null);
                } else {
                    requestsModelResponseMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<RequestsModelResponse> call, @NotNull Throwable t) {
                t.printStackTrace();
                requestsModelResponseMutableLiveData.setValue(null);
            }
        });
        return requestsModelResponseMutableLiveData;
    }

    public LiveData<NotificationResponseModel> getNotifications(String userId) {
        final MutableLiveData<NotificationResponseModel> notificationResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getNotifications(userId).enqueue(new Callback<NotificationResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<NotificationResponseModel> call, @NotNull Response<NotificationResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus())
                        notificationResponseModelMutableLiveData.setValue(response.body());
                    else
                        notificationResponseModelMutableLiveData.setValue(null);
                } else {
                    notificationResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<NotificationResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                notificationResponseModelMutableLiveData.setValue(null);
            }
        });
        return notificationResponseModelMutableLiveData;
    }

    public LiveData<InformationResponseModel> getInformation(String type) {
        final MutableLiveData<InformationResponseModel> informationResponseModelMutableLiveData = new MutableLiveData<>();
        apiService.getInformation(type).enqueue(new Callback<InformationResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<InformationResponseModel> call, @NotNull Response<InformationResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    informationResponseModelMutableLiveData.setValue(response.body());
                } else {
                    informationResponseModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<InformationResponseModel> call, @NotNull Throwable t) {
                t.printStackTrace();
                informationResponseModelMutableLiveData.setValue(null);
            }
        });
        return informationResponseModelMutableLiveData;
    }

}
