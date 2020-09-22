package com.senoritasaudi.senoritaprovider.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.senoritasaudi.senoritaprovider.R;
import com.senoritasaudi.senoritaprovider.databinding.ActivityReservationBinding;
import com.senoritasaudi.senoritaprovider.events.OnClickListener;
import com.senoritasaudi.senoritaprovider.models.OfferModel;
import com.senoritasaudi.senoritaprovider.models.RequestModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.FeedbackResponse;
import com.senoritasaudi.senoritaprovider.models.responsemodels.OfferResponseModel;
import com.senoritasaudi.senoritaprovider.models.responsemodels.RequestsModelResponse;
import com.senoritasaudi.senoritaprovider.models.responsemodels.ReservationResponseModel;
import com.senoritasaudi.senoritaprovider.viewmodels.ReservationViewModel;
import com.senoritasaudi.senoritaprovider.views.baseviews.BaseActivityWithViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class ReservationActivity extends BaseActivityWithViewModel<ReservationViewModel, ActivityReservationBinding> implements OnClickListener {

    RequestModel requestModel;
    String requestId = null;
    String offerId = null;
    private static final String TAG = "ReservationActivity";
    private OfferModel offerModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityBinding().setClickHandler(this);
        setTitle(getString(R.string.reserve_now));
        requestId = getIntent().getStringExtra("request_id");
        offerId = getIntent().getStringExtra("offerId");
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (requestId != null) {
            setTitle("تغيير الموعد");
            getActivityViewModel().getRequest(requestId).observe(this, new Observer<RequestsModelResponse>() {
                @Override
                public void onChanged(RequestsModelResponse requestsModelResponse) {
                    getActivityBinding().progressParent.setVisibility(View.GONE);
                    requestModel = requestsModelResponse.getRequests().get(0);
                    Gson gson = new Gson();
                    Log.d(TAG, "onChanged: " + gson.toJson(requestModel));
                    getActivityBinding().textView.setText(requestModel.getSelectedDate());
                    getActivityBinding().textView2.setText(requestModel.getSelectedTime());

                    Glide.with(ReservationActivity.this)
                            .load(requestModel.getOfferImage())
                            .placeholder(R.drawable.im_placeholder)
                            .error(R.drawable.im_placeholder)
                            .into(getActivityBinding().imageView29);

                }
            });
            getActivityBinding().editText3.setVisibility(View.GONE);
            getActivityBinding().nextButton.setText("تغيير الموعد");
        } else {
            setTitle("احجز الان");
            getActivityViewModel().getOffer(offerId).observe(this, new Observer<OfferResponseModel>() {
                @Override
                public void onChanged(OfferResponseModel offerResponseModel) {
                    getActivityBinding().progressParent.setVisibility(View.GONE);
                    offerModel = offerResponseModel.getOffers().get(0);

                    Glide.with(ReservationActivity.this)
                            .load(offerModel.getImage())
                            .placeholder(R.drawable.im_placeholder)
                            .error(R.drawable.im_placeholder)
                            .into(getActivityBinding().imageView29);
                }
            });
            getActivityBinding().nextButton.setText("احجز الان");
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }


    private void checkDataAdd() {
        String date = getActivityBinding().textView.getText().toString();
        String time = getActivityBinding().textView2.getText().toString();
        String promoCode = getActivityBinding().editText3.getText().toString().trim();
        if (date.isEmpty()) {
            getActivityBinding().textView.setError(getString(R.string.select_date));
            getActivityBinding().textView.requestFocus();
        } else if (time.isEmpty()) {
            getActivityBinding().textView2.setError(getString(R.string.select_time));
            getActivityBinding().textView2.requestFocus();
        } else if (promoCode.isEmpty()) {
            getActivityBinding().editText3.setError(getString(R.string.user_number_error));
            getActivityBinding().editText3.requestFocus();
        } else if (!getActivityViewModel().containsUser()) {
            Toast.makeText(this, getString(R.string.please_login), Toast.LENGTH_SHORT).show();
        } else {
            getActivityBinding().progressParent.setVisibility(View.VISIBLE);
            addRequest(date,time,promoCode);
        }
    }

    private void addRequest(String date, String time, String promoCode) {
        HashMap<String , String> hashMap = new HashMap<>();
        hashMap.put("category_id",offerModel.getCategoryId());
        hashMap.put("clinic_id",offerModel.getClinicId());
        hashMap.put("offer_id",offerModel.getId());
        hashMap.put("date",date);
        hashMap.put("time",time);
        hashMap.put("description","description");
        if (!promoCode.isEmpty()) {
            hashMap.put("user_id",promoCode);
        }
        getActivityViewModel().addRequest(hashMap).observe(this, new Observer<ReservationResponseModel>() {
            @Override
            public void onChanged(ReservationResponseModel reservationResponseModel) {
                getActivityBinding().progressParent.setVisibility(View.GONE);
                if (reservationResponseModel != null && reservationResponseModel.getStatus()) {
                    Toast.makeText(ReservationActivity.this, reservationResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    showConfirmDialog(reservationResponseModel.getId());
                } else if (reservationResponseModel != null) {
                    Toast.makeText(ReservationActivity.this, reservationResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReservationActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void editRequest(String date, String time) {
        HashMap<String , String> hashMap = new HashMap<>();
        hashMap.put("date",date);
        hashMap.put("time",time);
        hashMap.put("id",requestModel.getId());
        getActivityViewModel().editRequest(hashMap).observe(this, new Observer<FeedbackResponse>() {
            @Override
            public void onChanged(FeedbackResponse feedbackResponse) {
                getActivityBinding().progressParent.setVisibility(View.GONE);
                if (feedbackResponse != null && feedbackResponse.getStatus()) {
                    Toast.makeText(ReservationActivity.this, feedbackResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else if (feedbackResponse != null) {
                    Toast.makeText(ReservationActivity.this, feedbackResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReservationActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkDataEdit() {
        String date = getActivityBinding().textView.getText().toString();
        String time = getActivityBinding().textView2.getText().toString();
        String promoCode = getActivityBinding().editText3.getText().toString().trim();
        if (date.isEmpty()) {
            getActivityBinding().textView.setError(getString(R.string.select_date));
            getActivityBinding().textView.requestFocus();
        } else if (time.isEmpty()) {
            getActivityBinding().textView2.setError(getString(R.string.select_time));
            getActivityBinding().textView2.requestFocus();
        } else if (!getActivityViewModel().containsUser()) {
            Toast.makeText(this, getString(R.string.please_login), Toast.LENGTH_SHORT).show();
        } else {
            getActivityBinding().progressParent.setVisibility(View.VISIBLE);
            editRequest(date,time);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_reservation;
    }

    @Override
    protected ReservationViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(ReservationViewModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:
                showDateDialog();
                break;
            case R.id.textView2:
                showTimeDialog();
                break;
            case R.id.next_button:
                if (requestId != null) {
                    checkDataEdit();
                } else {
                    checkDataAdd();
                }
                break;
        }
    }


    public void showConfirmDialog(String reservationId) {
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.reservation_confirm_dialog);
        d.setCancelable(false);

        Button save = d.findViewById(R.id.dismiss);
        ((TextView)d.findViewById(R.id.text)).setText(getString(R.string.confirm_reservation) + reservationId);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.show();
    }

    private void showTimeDialog() {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog mTimePickerDialog;
        mTimePickerDialog = new TimePickerDialog(ReservationActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String AM_PM ;
                if(hourOfDay < 12) {
                    AM_PM = "AM";
                } else {
                    AM_PM = "PM";
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                try {
                    getActivityBinding().textView2.setText(dateFormat.format(Objects.requireNonNull(date12Format.parse(hourOfDay + ":" + minute + " " + AM_PM))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),false);
        mTimePickerDialog.setTitle(getString(R.string.select_date));
        mTimePickerDialog.show();
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(ReservationActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                selectedMonth = selectedMonth + 1;
                getActivityBinding().textView.setText(selectedDay + "_" + selectedMonth + "_" + selectedYear);
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle(getString(R.string.select_date));
        //mDatePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        long timeInMilliseconds = Calendar.getInstance().getTimeInMillis();
        try {
            Date mDate = sdf.parse(offerModel.getValidDate());
            if (mDate != null) {
                timeInMilliseconds = mDate.getTime();
            }
            Log.d(TAG, "showDateDialog: " + timeInMilliseconds);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        mDatePicker.getDatePicker().setMaxDate(timeInMilliseconds);
        mDatePicker.show();
    }
}
