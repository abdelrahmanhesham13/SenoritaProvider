package com.senoritasaudi.senoritaprovider.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.senoritasaudi.senoritaprovider.R;
import com.senoritasaudi.senoritaprovider.databinding.ActivityBookingsBinding;
import com.senoritasaudi.senoritaprovider.events.OnItemClicked;
import com.senoritasaudi.senoritaprovider.models.OfferModel;
import com.senoritasaudi.senoritaprovider.models.RequestModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.FeedbackResponse;
import com.senoritasaudi.senoritaprovider.models.responsemodels.OfferResponseModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.RequestsModelResponse;
import com.senoritasaudi.senoritaprovider.viewmodels.MainViewModel;
import com.senoritasaudi.senoritaprovider.views.adapters.BookingsAdapter;
import com.senoritasaudi.senoritaprovider.views.baseviews.BaseActivityWithViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class BookingsActivity extends BaseActivityWithViewModel<MainViewModel, ActivityBookingsBinding> implements OnItemClicked {

    private BookingsAdapter reservationsAdapter;
    private String status;

    Observer<RequestsModelResponse> addRequests = new Observer<RequestsModelResponse>() {
        @Override
        public void onChanged(RequestsModelResponse requestsModelResponse) {
            getActivityBinding().progressBar.setVisibility(View.GONE);
            if (requestsModelResponse != null) {
                reservationsAdapter.removeAll();
                reservationsAdapter.addRequests((ArrayList<RequestModel>) requestsModelResponse.getRequests());
                if (requestsModelResponse.getCount() == 0) {
                    Toast.makeText(BookingsActivity.this, R.string.no_reservations, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("الحجوزات");
        status = getIntent().getStringExtra("status");
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupViews();
    }

    private void setupViews() {
        getActivityBinding().reservationsRecycler.setLayoutManager(new LinearLayoutManager(this));
        reservationsAdapter = new BookingsAdapter(this, this, new BookingsAdapter.OnDeleteClicked() {
            @Override
            public void onDeleteClicked(RequestModel requestModel) {
                getActivityBinding().progressBar.setVisibility(View.VISIBLE);
                getActivityViewModel().deleteRequest(requestModel.getId(), "4", requestModel.getClinicId()).observe(BookingsActivity.this, new Observer<FeedbackResponse>() {
                    @Override
                    public void onChanged(FeedbackResponse feedbackResponse) {
                        getActivityViewModel().getRequests(status).observe(BookingsActivity.this, addRequests);
                        if (feedbackResponse != null && feedbackResponse.getStatus()) {
                            Toast.makeText(BookingsActivity.this, feedbackResponse.getMessageAr(), Toast.LENGTH_LONG).show();
                        } else if (feedbackResponse != null) {
                            Toast.makeText(BookingsActivity.this, feedbackResponse.getMessageAr(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(BookingsActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        },status);
        getActivityBinding().reservationsRecycler.setAdapter(reservationsAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!getIntent().hasExtra("type")) {
            getActivityViewModel().getRequests(status).observe(this, addRequests);
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

            getActivityViewModel().search(hashMap).observe(BookingsActivity.this, addRequests);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_bookings;
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
    protected MainViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(BookingsActivity.this,ReservationActivity.class);
        intent.putExtra("request_id",reservationsAdapter.getRequestId(position));
        startActivity(intent);
    }
}
