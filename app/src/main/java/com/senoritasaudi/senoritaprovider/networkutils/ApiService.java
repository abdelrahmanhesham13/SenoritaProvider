package com.senoritasaudi.senoritaprovider.networkutils;

import com.senoritasaudi.senoritaprovider.models.responsemodels.ClinicRequestsCountResponseModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.FeedbackResponse;
import com.senoritasaudi.senoritaprovider.models.responsemodels.InformationResponseModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.LoginResponseModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.NotificationResponseModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.OfferResponseModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.RequestsModelResponse;
import com.senoritasaudi.senoritaprovider.models.responsemodels.ReservationResponseModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.ReviewResponseModel;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("get_offers")
    Call<OfferResponseModel> search(@QueryMap HashMap<String,String> hashMap);

    @GET("send_feedback")
    Call<FeedbackResponse> sendFeedBack(@Query("name") String name, @Query("mobile") String mobile, @Query("message") String message);

    @GET("add_clinic")
    Call<FeedbackResponse> sendRequest(@Query("name") String name, @Query("mobile") String mobile, @Query("message") String message ,
                                       @Query("user_id") String userId);

    @GET("settings")
    Call<InformationResponseModel> getInformation(@Query("type") String type);

    @GET("clinic_update_status")
    Call<FeedbackResponse> deleteRequest(@Query("id") String requestId,
                                         @Query("status") String status,
                                         @Query("clinic_id") String clinicId);

    @GET("clinic_login")
    Call<LoginResponseModel> login(@Query("password") String password,
                                   @Query("mobile") String mobile,
                                   @Query("token") String token);

    @GET("clinic_requests_count")
    Call<ClinicRequestsCountResponseModel> getClinicRequestsCount(@Query("clinic_id") String password);

    @GET("clinic_requests")
    Call<RequestsModelResponse> getRequests(@Query("clinic_id") String clinicId ,
                                            @Query("status") String status);

    @GET("get_notifications")
    Call<NotificationResponseModel> getNotifications(@Query("clinic_id") String clinicId,
                                                     @Query("user_id") String userId);

    @GET("clinic_requests")
    Call<RequestsModelResponse> getRequest(@Query("id") String requestId ,
                                            @Query("clinic_id") String clinicId);

    @GET("add_request")
    Call<ReservationResponseModel> addRequest(@QueryMap HashMap<String,String> hashMap);

    @GET("edit_request")
    Call<FeedbackResponse> editRequest(@QueryMap HashMap<String, String> hashMap);

    @GET("get_reviews")
    Call<ReviewResponseModel> getReview(@Query("id") String clinicId);

    @GET("get_offers")
    Call<OfferResponseModel> getOffer(@Query("id") String offerId , @Query("user_id") String userId);

    @GET("get_request_clinic")
    Call<RequestsModelResponse> searchBookings(@QueryMap HashMap<String, String> hashMap);
}
