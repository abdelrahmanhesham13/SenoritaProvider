package com.senoritasaudi.senoritaprovider.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.senoritasaudi.senoritaprovider.R;
import com.senoritasaudi.senoritaprovider.databinding.ActivityLoginBinding;
import com.senoritasaudi.senoritaprovider.events.OnClickListener;
import com.senoritasaudi.senoritaprovider.models.responsemodels.LoginResponseModel;
import com.senoritasaudi.senoritaprovider.navutils.NavigationManager;
import com.senoritasaudi.senoritaprovider.viewmodels.LoginViewModel;
import com.senoritasaudi.senoritaprovider.views.baseviews.BaseActivityWithViewModel;

public class LoginActivity extends BaseActivityWithViewModel<LoginViewModel, ActivityLoginBinding> implements OnClickListener {

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityBinding().setClickHandler(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void checkData() {
        String phone = getActivityBinding().editText.getText().toString().trim();
        String password = getActivityBinding().editText2.getText().toString().trim();
        if (phone.length() != 10) {
            getActivityBinding().editText.setError(getString(R.string.phone_not_valid));
            getActivityBinding().editText.requestFocus();
        } else if (password.isEmpty() || password.length() < 6) {
            getActivityBinding().editText2.setError(getString(R.string.password_not_valid));
            getActivityBinding().editText2.requestFocus();
        } else {
            getActivityBinding().progressParent.setVisibility(View.VISIBLE);
            login(phone, password);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_button) {
            checkData();
        }
    }

    private void login(String phone, String password) {
        getActivityViewModel().login(phone, password, getActivityViewModel().getToken()).observe(this, new Observer<LoginResponseModel>() {
            @Override
            public void onChanged(LoginResponseModel loginResponseModel) {
                getActivityBinding().progressParent.setVisibility(View.GONE);
                if (loginResponseModel != null && loginResponseModel.getStatus()) {
                    loginResponseModel.getUser().setPassword(password);
                    getActivityViewModel().saveUser(loginResponseModel.getUser());
                    getActivityViewModel().saveClinic(loginResponseModel.getClinic());
                    NavigationManager.startActivity(LoginActivity.this, MainActivity.class);
                    finish();
                } else if (loginResponseModel != null) {
                    Toast.makeText(LoginActivity.this, loginResponseModel.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
