package com.senoritasaudi.senoritaprovider.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.senoritasaudi.senoritaprovider.R;
import com.senoritasaudi.senoritaprovider.databinding.ActivitySearchBinding;
import com.senoritasaudi.senoritaprovider.viewmodels.MainViewModel;
import com.senoritasaudi.senoritaprovider.views.baseviews.BaseActivityWithViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class SearchActivity extends BaseActivityWithViewModel<MainViewModel, ActivitySearchBinding> {

    ArrayList<String> searchWay = new ArrayList<>(Arrays.asList("بحث كامل","بحث برقم العرض","بحث برقم العميل"));
    ArrayList<String> orderStatus = new ArrayList<>(Arrays.asList("كل الطلبات","الطلبات الحالية","الطلبات المنتهية","الطلبات الملغية"));

    String selectedWayId = "0";
    String selectedStatusId = "0";
    String searchId = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.search_for_offers));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setupSpinner();

        setupSpinner2();

        getActivityBinding().fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(1);
            }
        });

        getActivityBinding().toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(2);
            }
        });


        getActivityBinding().searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String offerId = getActivityBinding().editText.getText().toString().trim();
                String fromDate = getActivityBinding().fromDate.getText().toString().trim();
                String toDate = getActivityBinding().toDate.getText().toString().trim();
                if (offerId.isEmpty() && selectedStatusId.equals("0") && selectedWayId.equals("0")
                        && (fromDate.isEmpty() || toDate.isEmpty())) {
                    Toast.makeText(SearchActivity.this, R.string.one_filter, Toast.LENGTH_SHORT).show();
                } else if (!selectedWayId.equals("all") && offerId.isEmpty()) {
                    Toast.makeText(SearchActivity.this, "ادخل رقم البحث", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(SearchActivity.this, BookingsActivity.class).
                            putExtra("type", "search").
                            putExtra("statusId", selectedStatusId).
                            putExtra("wayId", selectedWayId).
                            putExtra("fromDate",fromDate).
                            putExtra("toDate",toDate).
                            putExtra("offerId",offerId));
                }
            }
        });
    }

    private void showDateDialog(int type) {
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(SearchActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                selectedMonth = selectedMonth + 1;
                if (type == 1) {
                    getActivityBinding().fromDate.setText(selectedDay + "_" + selectedMonth + "_" + selectedYear);
                } else {
                    getActivityBinding().toDate.setText(selectedDay + "_" + selectedMonth + "_" + selectedYear);
                }
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle(getString(R.string.select_date));
        mDatePicker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        mDatePicker.show();
    }

    private void setupSpinner2() {


        searchWay.add(0,"اختر وسيلة البحث");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, searchWay) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(ContextCompat.getColor(SearchActivity.this,R.color.colorPrimary));
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getActivityBinding().clinicsSpinner.setAdapter(spinnerArrayAdapter);
        getActivityBinding().clinicsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(SearchActivity.this,R.color.colorPrimary));
                if (position != 0) {
                    switch (position) {
                        case 1:
                            selectedWayId = "all";
                            break;
                        case 2:
                            selectedWayId = "offer";
                            break;
                        case 3:
                            selectedWayId = "customer";
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupSpinner() {
        orderStatus.add(0,"حالة الطلب");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, orderStatus) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(ContextCompat.getColor(SearchActivity.this,R.color.colorPrimary));
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getActivityBinding().departmentsSpinner.setAdapter(spinnerArrayAdapter);
        getActivityBinding().departmentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(SearchActivity.this,R.color.colorPrimary));
                if (position != 0) {
                    switch (position) {
                        case 1:
                            selectedStatusId = "all";
                            break;
                        case 2:
                            selectedStatusId = "current";
                            break;
                        case 3:
                            selectedStatusId = "finish";
                            break;
                        case 4:
                            selectedStatusId = "cancel";
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_search;
    }

    @Override
    protected MainViewModel initialiseViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
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
