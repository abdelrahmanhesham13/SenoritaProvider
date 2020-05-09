package com.senoritasaudi.senoritaprovider.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.senoritasaudi.senoritaprovider.R;
import com.senoritasaudi.senoritaprovider.databinding.ActivityReviewsBinding;
import com.senoritasaudi.senoritaprovider.models.ReviewModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.ReviewResponseModel;
import com.senoritasaudi.senoritaprovider.viewmodels.MainViewModel;
import com.senoritasaudi.senoritaprovider.views.adapters.ReviewsAdapter;
import com.senoritasaudi.senoritaprovider.views.baseviews.BaseActivityWithViewModel;

import java.util.ArrayList;

public class ReviewsActivity extends BaseActivityWithViewModel<MainViewModel, ActivityReviewsBinding> {

    ReviewsAdapter reviewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        reviewsAdapter = new ReviewsAdapter(this);
        getActivityBinding().reviews.setHasFixedSize(true);
        getActivityBinding().reviews.setLayoutManager(new LinearLayoutManager(this));
        getActivityBinding().reviews.setAdapter(reviewsAdapter);
        getActivityViewModel().getReviews().observe(ReviewsActivity.this, new Observer<ReviewResponseModel>() {
            @Override
            public void onChanged(ReviewResponseModel reviewResponseModel) {
                getActivityBinding().progressParent.setVisibility(View.GONE);
                reviewsAdapter.addReviews((ArrayList<ReviewModel>) reviewResponseModel.getRequests());
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_reviews;
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
}
