package com.senoritasaudi.senoritaprovider.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.senoritasaudi.senoritaprovider.R;
import com.senoritasaudi.senoritaprovider.databinding.ActivityWebViewBinding;
import com.senoritasaudi.senoritaprovider.storeutils.Constants;
import com.senoritasaudi.senoritaprovider.views.baseviews.BaseActivityWithoutViewModel;

public class WebViewActivity extends BaseActivityWithoutViewModel<ActivityWebViewBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("QR Code");
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getActivityBinding().webView.loadUrl(Constants.NetworkConstants.BASE_URL + "generate_qrcode?id=" + getIntent().getStringExtra("id"));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_web_view;
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
