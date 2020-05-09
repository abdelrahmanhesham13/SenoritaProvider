package com.senoritasaudi.senoritaprovider.views;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.senoritasaudi.senoritaprovider.R;
import com.senoritasaudi.senoritaprovider.databinding.ActivityInformationBinding;
import com.senoritasaudi.senoritaprovider.models.responsemodels.InformationResponseModel;
import com.senoritasaudi.senoritaprovider.viewmodels.InformationViewModel;
import com.senoritasaudi.senoritaprovider.viewmodels.factory.ViewModelFactory;
import com.senoritasaudi.senoritaprovider.views.baseviews.BaseActivityWithViewModel;

public class InformationActivity extends BaseActivityWithViewModel<InformationViewModel, ActivityInformationBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra("title"));
        getActivityBinding().textView4.setText(getIntent().getStringExtra("title"));
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getActivityViewModel().getInformation().observe(this, new Observer<InformationResponseModel>() {
            @Override
            public void onChanged(InformationResponseModel informationResponseModel) {
                getActivityBinding().progressBar.setVisibility(View.GONE);
                getActivityBinding().registerNow.setText(informationResponseModel.getData());
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_information;
    }

    @Override
    protected InformationViewModel initialiseViewModel() {
        ViewModelFactory viewModelFactory = new ViewModelFactory(getApplication(),"");
        viewModelFactory.setPage(getIntent().getStringExtra("title_for_api"));
        return new ViewModelProvider(this,viewModelFactory).get(InformationViewModel.class);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
