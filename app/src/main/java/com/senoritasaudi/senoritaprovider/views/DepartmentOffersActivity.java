package com.senoritasaudi.senoritaprovider.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.senoritasaudi.senoritaprovider.R;
import com.senoritasaudi.senoritaprovider.databinding.ActivityDepartmentOffersBinding;
import com.senoritasaudi.senoritaprovider.events.OnItemClicked;
import com.senoritasaudi.senoritaprovider.models.OfferModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.OfferResponseModel;
import com.senoritasaudi.senoritaprovider.viewmodels.OffersViewModel;
import com.senoritasaudi.senoritaprovider.viewmodels.factory.ViewModelFactory;
import com.senoritasaudi.senoritaprovider.views.adapters.DepartmentOffersAdapter;
import com.senoritasaudi.senoritaprovider.views.baseviews.BaseActivityWithViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class DepartmentOffersActivity extends BaseActivityWithViewModel<OffersViewModel, ActivityDepartmentOffersBinding> implements OnItemClicked {

    int departmentId = 0;
    DepartmentOffersAdapter departmentOffersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.offers));
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupViews();
    }

    private void setupViews() {
        getActivityBinding().departmentOffersRecycler.setHasFixedSize(true);
        getActivityBinding().departmentOffersRecycler.setLayoutManager(new LinearLayoutManager(this));

        departmentOffersAdapter = new DepartmentOffersAdapter(this, this, new OnItemClicked() {
            @Override
            public void onItemClicked(int position) {

            }
        }, new DepartmentOffersAdapter.OnShareClick() {
            @Override
            public void onShareClicked() {

            }
        });
        getActivityBinding().departmentOffersRecycler.setAdapter(departmentOffersAdapter);
        if (!getIntent().hasExtra("type")) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("clinic_id", getActivityViewModel().getClinic().getId());

            getActivityViewModel().search(hashMap).observe(DepartmentOffersActivity.this, new Observer<OfferResponseModel>() {
                @Override
                public void onChanged(OfferResponseModel offerResponseModel) {
                    getActivityBinding().progressBar.setVisibility(View.GONE);
                    if (offerResponseModel != null) {
                        if (!offerResponseModel.getStatus()) {
                            Toast.makeText(DepartmentOffersActivity.this, R.string.no_offers, Toast.LENGTH_LONG).show();
                        } else {
                            departmentOffersAdapter.addOffers((ArrayList<OfferModel>) offerResponseModel.getOffers());
                        }
                    }
                }
            });
        } else {
            HashMap<String, String> hashMap = new HashMap<>();
            if (getIntent().hasExtra("fromDate")) {
                hashMap.put("from", getIntent().getStringExtra("fromDate"));
                hashMap.put("to", getIntent().getStringExtra("toDate"));
            }
            if (getIntent().hasExtra("statusId"))
                hashMap.put("request_type", getIntent().getStringExtra("statusId"));
            if (getIntent().hasExtra("wayId"))
                hashMap.put("type_id", getIntent().getStringExtra("wayId"));
            if (getIntent().hasExtra("offerId"))
                hashMap.put("id", getIntent().getStringExtra("offerId"));

            getActivityViewModel().search(hashMap).observe(DepartmentOffersActivity.this, new Observer<OfferResponseModel>() {
                @Override
                public void onChanged(OfferResponseModel offerResponseModel) {
                    getActivityBinding().progressBar.setVisibility(View.GONE);
                    if (offerResponseModel != null) {
                        if (!offerResponseModel.getStatus()) {
                            Toast.makeText(DepartmentOffersActivity.this, R.string.no_offers, Toast.LENGTH_LONG).show();
                        } else {
                            departmentOffersAdapter.addOffers((ArrayList<OfferModel>) offerResponseModel.getOffers());
                        }
                    }
                }
            });
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_department_offers;
    }

    @Override
    protected OffersViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(OffersViewModel.class);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(this, ReservationActivity.class);
        intent.putExtra("offerId", departmentOffersAdapter.getOfferIdForPosition(position));
        startActivity(intent);
    }
}
