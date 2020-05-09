package com.senoritasaudi.senoritaprovider.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.senoritasaudi.senoritaprovider.R;
import com.senoritasaudi.senoritaprovider.databinding.ActivityIntroBinding;
import com.senoritasaudi.senoritaprovider.events.OnClickListener;
import com.senoritasaudi.senoritaprovider.navutils.NavigationManager;
import com.senoritasaudi.senoritaprovider.views.adapters.IntroPagerAdapter;
import com.senoritasaudi.senoritaprovider.views.baseviews.BaseActivityWithoutViewModel;

public class IntroActivity extends BaseActivityWithoutViewModel<ActivityIntroBinding> implements OnClickListener {

    IntroPagerAdapter mIntroPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
    }

    private void setupView() {
        mIntroPagerAdapter = new IntroPagerAdapter(getSupportFragmentManager());
        mIntroPagerAdapter.addFragment(new IntroFragment("",getString(R.string.intro_1),R.drawable.splash_offers));
        mIntroPagerAdapter.addFragment(new IntroFragment("",getString(R.string.intro_2),R.drawable.splash_steps));
        mIntroPagerAdapter.addFragment(new IntroFragment("",getString(R.string.intro_3),R.drawable.splash_scan));
        mIntroPagerAdapter.addFragment(new IntroFragment("",getString(R.string.intro_4),R.drawable.splash_notification));
        getActivityBinding().viewPager.setAdapter(mIntroPagerAdapter);
        getActivityBinding().setClickHandler(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_intro;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_button :
                if (getActivityBinding().viewPager.getCurrentItem() < mIntroPagerAdapter.getCount() - 1) {
                    moveViewPager(getActivityBinding().viewPager.getCurrentItem() + 1);
                } else {
                    NavigationManager.startActivity(IntroActivity.this, LoginActivity.class);
                    finish();
                }
                break;
            case R.id.imageView:
            case R.id.textView:
                NavigationManager.startActivity(IntroActivity.this,LoginActivity.class);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getActivityBinding().viewPager.getCurrentItem() != 0) {
            moveViewPager(getActivityBinding().viewPager.getCurrentItem() - 1);
        } else {
            super.onBackPressed();
        }
    }


    public void moveViewPager(int pageNumber) {
        getActivityBinding().viewPager.setCurrentItem(pageNumber);
    }
}
