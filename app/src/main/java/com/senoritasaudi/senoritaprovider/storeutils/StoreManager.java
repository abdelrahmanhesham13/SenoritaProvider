package com.senoritasaudi.senoritaprovider.storeutils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.senoritasaudi.senoritaprovider.models.ClinicModel;
import com.senoritasaudi.senoritaprovider.models.UserModel;

public class StoreManager {

    private SharedPreferences mSharedPreferences;

    public StoreManager(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean getBooleanValueForKey(String key , boolean defaultValue) {
        return mSharedPreferences.getBoolean(key,defaultValue);
    }

    public void setBooleanValueForKey(String key , boolean value) {
        mSharedPreferences.edit().putBoolean(key,value).apply();
    }

    public void saveUser(UserModel userModel) {
        Gson gson = new Gson();
        mSharedPreferences.edit().putString("user",gson.toJson(userModel)).apply();
    }

    public void saveClinic(ClinicModel clinicModel) {
        Gson gson = new Gson();
        mSharedPreferences.edit().putString("clinic",gson.toJson(clinicModel)).apply();
    }

    public static String getAppLanguage(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("lang","ar");
    }

    public static void setAppLanguage(Context context,String lang) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("lang",lang).apply();
    }

    public void removeUser() {
        mSharedPreferences.edit().remove("user").apply();
    }

    public void removeClinic() {
        mSharedPreferences.edit().remove("clinic").apply();
    }

    public boolean containsUser() {
        return !mSharedPreferences.getString("user", "").equals("");
    }

    public void saveToken(String token) {
        mSharedPreferences.edit().putString("token",token).apply();
    }

    public String getToken() {
        return mSharedPreferences.getString("token","");
    }

    public UserModel getUser() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("user", "");
        return gson.fromJson(json, UserModel.class);
    }

    public ClinicModel getClinic() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("clinic", "");
        return gson.fromJson(json, ClinicModel.class);
    }
}
