package com.senoritasaudi.senoritaprovider.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.senoritasaudi.senoritaprovider.R;
import com.senoritasaudi.senoritaprovider.databinding.ListReservationItemBinding;
import com.senoritasaudi.senoritaprovider.events.OnItemClicked;
import com.senoritasaudi.senoritaprovider.models.RequestModel;
import com.senoritasaudi.senoritaprovider.views.WebViewActivity;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ReservationViewHolder>  {

    private ArrayList<RequestModel> requestModels;
    private Context mContext;
    private OnItemClicked mOnItemClicked;
    private OnDeleteClicked onDeleteClicked;
    private String status;

    public BookingsAdapter(Context context , OnItemClicked onItemClicked , OnDeleteClicked onDeleteClicked , String status) {
        this.mContext = context;
        this.mOnItemClicked = onItemClicked;
        requestModels = new ArrayList<>();
        this.onDeleteClicked = onDeleteClicked;
        this.status = status;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListReservationItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_reservation_item,parent,false);
        return new ReservationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        holder.listReservationItemBinding.clinicName.setText(requestModels.get(position).getCustomerName() + " #" + requestModels.get(position).getCustomerId());
        try {
            holder.listReservationItemBinding.rating.setRating(Float.parseFloat(requestModels.get(position).getRate()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (requestModels.get(position).getStatus().equals("4")) {
            holder.listReservationItemBinding.imageView34.setVisibility(View.GONE);
        } else {
            holder.listReservationItemBinding.imageView34.setVisibility(View.VISIBLE);
        }

        holder.listReservationItemBinding.reservationNumber.setText(String.format("%s : %s", mContext.getString(R.string.reservation_number), requestModels.get(position).getId()));
        holder.listReservationItemBinding.price.setText("سعر العرض : " + requestModels.get(position).getOffer().getPrice());
        holder.listReservationItemBinding.date.setText(requestModels.get(position).getSelectedDate() + "\n" + requestModels.get(position).getSelectedTime());
        holder.listReservationItemBinding.textView21.setText(mContext.getString(R.string.offer_number) + " : " + requestModels.get(position).getOfferId());
        Glide.with(mContext)
                .load(requestModels.get(position).getClinicImage())
                .placeholder(R.drawable.im_placeholder)
                .error(R.drawable.im_placeholder)
                .into(holder.listReservationItemBinding.imageView30);
        Glide.with(mContext)
                .load(requestModels.get(position).getCustomerImage())
                .placeholder(R.drawable.im_placeholder)
                .error(R.drawable.im_placeholder)
                .into(holder.listReservationItemBinding.imageView32);
        switch (requestModels.get(position).getStatus()){
            case "0":
                holder.listReservationItemBinding.confirmationStatus.setText(R.string.waiting_confirmation);
                holder.listReservationItemBinding.confirmationStatus.setTextColor(Color.parseColor("#C793C8"));
                holder.listReservationItemBinding.circle.setImageResource(R.drawable.bg_circle_waiting);
                break;
            case "1":
                holder.listReservationItemBinding.confirmationStatus.setText(R.string.confirmed);
                holder.listReservationItemBinding.confirmationStatus.setTextColor(Color.parseColor("#2392A0"));
                holder.listReservationItemBinding.circle.setImageResource(R.drawable.bg_circle_confirmed);
                break;
            case "2":
                holder.listReservationItemBinding.confirmationStatus.setText(R.string.visit_completed);
                holder.listReservationItemBinding.confirmationStatus.setTextColor(Color.parseColor("#8A2332"));
                holder.listReservationItemBinding.circle.setImageResource(R.drawable.bg_circle_visit_completed);
                break;
            case "3":
                holder.listReservationItemBinding.confirmationStatus.setText(R.string.visit_not_completed);
                holder.listReservationItemBinding.confirmationStatus.setTextColor(Color.parseColor("#C87938"));
                holder.listReservationItemBinding.circle.setImageResource(R.drawable.bg_circle_visit_not_completed);
                break;
            case "4":
                holder.listReservationItemBinding.confirmationStatus.setText(R.string.canceled);
                holder.listReservationItemBinding.confirmationStatus.setTextColor(Color.parseColor("#A0171E"));
                holder.listReservationItemBinding.circle.setImageResource(R.drawable.bg_circle_canceled);
                break;
        }
    }

    public String getRequestId(int position) {
        return requestModels.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }

    public void addRequests(ArrayList<RequestModel> requestModels) {
        this.requestModels.addAll(requestModels);
        notifyDataSetChanged();
    }

    public void removeAll() {
        this.requestModels.clear();
    }

    public interface OnDeleteClicked {
        void onDeleteClicked(RequestModel requestModel);
    }

    class ReservationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ListReservationItemBinding listReservationItemBinding;

        public ReservationViewHolder(ListReservationItemBinding listReservationItemBinding) {
            super(listReservationItemBinding.getRoot());
            listReservationItemBinding.getRoot().setOnClickListener(this);
            this.listReservationItemBinding = listReservationItemBinding;
            if (!status.equals("1")) {
                listReservationItemBinding.buttonParent1.setVisibility(View.GONE);
                listReservationItemBinding.buttonParent2.setVisibility(View.GONE);
            } else {
                listReservationItemBinding.callCustomer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialPhoneNumber(requestModels.get(getAdapterPosition()).getCustomerMobile());
                    }
                });
                listReservationItemBinding.deleteBooking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onDeleteClicked.onDeleteClicked(requestModels.get(getAdapterPosition()));
                    }
                });

                listReservationItemBinding.changeDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClicked.onItemClicked(getAdapterPosition());
                    }
                });
                listReservationItemBinding.qrCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, WebViewActivity.class).putExtra("id",requestModels.get(getAdapterPosition()).getId()));
                    }
                });
            }
        }

        @Override
        public void onClick(View v) {

        }
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
        }
    }

}
