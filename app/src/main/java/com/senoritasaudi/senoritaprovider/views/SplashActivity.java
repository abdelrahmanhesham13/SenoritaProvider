package com.senoritasaudi.senoritaprovider.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.senoritasaudi.senoritaprovider.R;
import com.senoritasaudi.senoritaprovider.databinding.ActivitySplashBinding;
import com.senoritasaudi.senoritaprovider.models.responsemodels.LoginResponseModel;
import com.senoritasaudi.senoritaprovider.navutils.NavigationManager;
import com.senoritasaudi.senoritaprovider.viewmodels.SplashViewModel;
import com.senoritasaudi.senoritaprovider.views.baseviews.BaseActivityWithViewModel;

public class SplashActivity extends BaseActivityWithViewModel<SplashViewModel, ActivitySplashBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivityViewModel().showIntro()) {
            NavigationManager.startActivity(this,IntroActivity.class);
            getActivityViewModel().setShowIntro(false);
            finish();
        } else if (getActivityViewModel().containsUser()) {
            refreshUser();
        } else {
            NavigationManager.startActivity(this, LoginActivity.class);
            finish();
        }
    }

    private void refreshUser() {
        getActivityViewModel().login(getActivityViewModel().getUser().getMobile(),getActivityViewModel().getUser().getPassword(),getActivityViewModel().getToken()).observe(this, new Observer<LoginResponseModel>() {
            @Override
            public void onChanged(LoginResponseModel loginResponseModel) {
                if (loginResponseModel != null && loginResponseModel.getStatus()) {
                    loginResponseModel.getUser().setPassword(getActivityViewModel().getUser().getPassword());
                    getActivityViewModel().saveUser(loginResponseModel.getUser());
                    getActivityViewModel().saveClinic(loginResponseModel.getClinic());
                    NavigationManager.startActivity(SplashActivity.this, MainActivity.class);
                    finish();
                } else if (loginResponseModel != null) {
                    getActivityViewModel().removeClinic();
                    getActivityViewModel().removeUser();
                    NavigationManager.startActivity(SplashActivity.this, LoginActivity.class);
                    Toast.makeText(SplashActivity.this, loginResponseModel.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    getActivityViewModel().removeClinic();
                    getActivityViewModel().removeUser();
                    NavigationManager.startActivity(SplashActivity.this, LoginActivity.class);
                    Toast.makeText(SplashActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    protected SplashViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(SplashViewModel.class);
    }
}
