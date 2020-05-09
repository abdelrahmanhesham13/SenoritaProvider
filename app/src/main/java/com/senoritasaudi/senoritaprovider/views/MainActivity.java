package com.senoritasaudi.senoritaprovider.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.senoritasaudi.senoritaprovider.R;
import com.senoritasaudi.senoritaprovider.databinding.ActivityMainBinding;
import com.senoritasaudi.senoritaprovider.events.OnClickListener;
import com.senoritasaudi.senoritaprovider.models.responsemodels.ClinicRequestsCountResponseModel;
import com.senoritasaudi.senoritaprovider.navutils.NavigationManager;
import com.senoritasaudi.senoritaprovider.viewmodels.MainViewModel;
import com.senoritasaudi.senoritaprovider.views.baseviews.BaseActivityWithViewModel;

public class MainActivity extends BaseActivityWithViewModel<MainViewModel, ActivityMainBinding> implements OnClickListener {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();
    }

    private void setupViews() {
        getActivityBinding().setClickHandler(this);
        toolbar = getActivityBinding().toolbar;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        getActivityBinding().clinicName.setText(getActivityViewModel().getClinic().getNameAr());
    }

    @Override
    protected void onStart() {
        super.onStart();
        getActivityViewModel().getClinicRequestsCount().observe(this, new Observer<ClinicRequestsCountResponseModel>() {
            @Override
            public void onChanged(ClinicRequestsCountResponseModel clinicRequestsCountResponseModel) {
                if (clinicRequestsCountResponseModel != null && clinicRequestsCountResponseModel.getStatus()) {
                    getActivityBinding().canceledBookingsNumber.setText(String.valueOf(clinicRequestsCountResponseModel.getCancel()));
                    getActivityBinding().finishedBookingsNumber.setText(String.valueOf(clinicRequestsCountResponseModel.getFinished()));
                    getActivityBinding().currentBookingsNumber.setText(String.valueOf(clinicRequestsCountResponseModel.getWaiting()));
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,BookingsActivity.class);
        switch (v.getId()) {
            case R.id.current_bookings_parent:
                intent.putExtra("status","1");
                startActivity(intent);
                break;
            case R.id.canceled_bookings_parent:
                intent.putExtra("status","3");
                startActivity(intent);
                break;
            case R.id.finished_bookings_parent:
                intent.putExtra("status","2");
                startActivity(intent);
                break;
            case R.id.menu:
                NavigationManager.startActivity(this,MenuActivity.class);
                break;
        }
    }
}
