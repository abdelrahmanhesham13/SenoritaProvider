package com.senoritasaudi.senoritaprovider.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.senoritasaudi.senoritaprovider.BuildConfig;
import com.senoritasaudi.senoritaprovider.R;
import com.senoritasaudi.senoritaprovider.databinding.ActivityMenuBinding;
import com.senoritasaudi.senoritaprovider.events.OnClickListener;
import com.senoritasaudi.senoritaprovider.navutils.NavigationManager;
import com.senoritasaudi.senoritaprovider.viewmodels.LoginViewModel;
import com.senoritasaudi.senoritaprovider.views.baseviews.BaseActivityWithViewModel;

public class MenuActivity extends BaseActivityWithViewModel<LoginViewModel, ActivityMenuBinding> implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("التقييمات");
        getActivityBinding().setClickHandler(this);

        if (getActivityViewModel().containsUser()) {
            if (!getActivityViewModel().getUser().getImage().isEmpty()) {
                String imageUrl = null;
                if (getActivityViewModel().getUser().getImage().contains("https")) {
                    imageUrl = getActivityViewModel().getUser().getImage();
                } else {
                    imageUrl = "https://senoritasaudi.com/admin/user_img/" + getActivityViewModel().getUser().getImage();
                }
                Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.im_placeholder)
                        .error(R.drawable.im_placeholder)
                        .into(getActivityBinding().circleImageView);
            }
            getActivityBinding().textView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("profile",true);
                    startActivity(intent);
                }
            });
            getActivityBinding().textView4.setText(getActivityViewModel().getClinic().getNameAr());
        } else {
            getActivityBinding().imageView26.setVisibility(View.GONE);
            getActivityBinding().logout.setVisibility(View.GONE);
            getActivityBinding().textView15.setVisibility(View.GONE);
            getActivityBinding().imageView27.setVisibility(View.GONE);
            getActivityBinding().textView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_menu;
    }

    @Override
    protected LoginViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notifications:
                NavigationManager.startActivity(MenuActivity.this, NotificationsActivity.class);
                break;
            case R.id.back_button:
            case R.id.home:
                finish();
                break;
            case R.id.exchange_points:
                NavigationManager.startActivity(MenuActivity.this,ReviewsActivity.class);
                break;
            case R.id.search:
                NavigationManager.startActivity(MenuActivity.this,SearchActivity.class);
                break;
            case R.id.terms_and_conditions:
                startActivity(new Intent(MenuActivity.this, InformationActivity.class)
                        .putExtra("title", getString(R.string.terms_and_conditions))
                        .putExtra("title_for_api","terms"));
                break;
            case R.id.privacy_policy:
                startActivity(new Intent(MenuActivity.this, InformationActivity.class)
                        .putExtra("title", getString(R.string.privacy_policy))
                        .putExtra("title_for_api","privacy"));
                break;
            case R.id.about_us:
                startActivity(new Intent(MenuActivity.this, InformationActivity.class)
                        .putExtra("title", getString(R.string.about_us))
                        .putExtra("title_for_api","about"));
                break;
            case R.id.contact_us:
                NavigationManager.startActivity(MenuActivity.this, ContactUsActivity.class);
                break;
            case R.id.logout:
                getActivityViewModel().removeUser();
                getActivityViewModel().removeClinic();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.add_clinic:
                NavigationManager.startActivity(MenuActivity.this,AddProviderRequestActivity.class);
                break;
            case R.id.departments:
                NavigationManager.startActivity(MenuActivity.this,DepartmentOffersActivity.class);
                break;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "استكشف برنامج سينيوريتا : https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
        }
    }
}
