package com.senoritasaudi.senoritaprovider.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.senoritasaudi.senoritaprovider.R;
import com.senoritasaudi.senoritaprovider.databinding.ListDepartmentOfferItemBinding;
import com.senoritasaudi.senoritaprovider.events.OnItemClicked;
import com.senoritasaudi.senoritaprovider.models.OfferModel;
import com.senoritasaudi.senoritaprovider.storeutils.StoreManager;

import java.util.ArrayList;

public class DepartmentOffersAdapter extends RecyclerView.Adapter<DepartmentOffersAdapter.DepartmentOfferViewHolder> {

    private ArrayList<OfferModel> offerModels;
    private Context mContext;
    OnItemClicked mOnItemClicked;
    OnItemClicked onLikeClicked;
    StoreManager storeManager;
    OnShareClick onShareClick;

    public DepartmentOffersAdapter(Context context , OnItemClicked onItemClicked , OnItemClicked onLikeClicked , OnShareClick onShareClick) {
        this.mContext = context;
        this.mOnItemClicked = onItemClicked;
        this.onLikeClicked = onLikeClicked;
        offerModels = new ArrayList<>();
        this.onShareClick = onShareClick;
        storeManager = new StoreManager(context);
    }

    public interface OnShareClick {
        void onShareClicked();
    }

    @NonNull
    @Override
    public DepartmentOfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListDepartmentOfferItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.list_department_offer_item,parent,false);
        return new DepartmentOfferViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentOfferViewHolder holder, int position) {
//        Picasso.get()
//                .load(offerModels.get(position).getImage())
//                .error(R.drawable.im_placeholder)
//                .placeholder(R.drawable.im_placeholder)
//                .into(holder.listDepartmentOfferItemBinding.imageView36);
//        Picasso.get()
//                .load(offerModels.get(position).getClinicImage())
//                .error(R.drawable.im_placeholder)
//                .placeholder(R.drawable.im_placeholder)
//                .into(holder.listDepartmentOfferItemBinding.clinicImage);
        Glide.with(mContext)
                .load(offerModels.get(position).getImage())
                .placeholder(R.drawable.im_placeholder)
                .error(R.drawable.im_placeholder)
                .into(holder.listDepartmentOfferItemBinding.imageView36);
        Glide.with(mContext)
                .load(offerModels.get(position).getClinicImage())
                .placeholder(R.drawable.im_placeholder)
                .error(R.drawable.im_placeholder)
                .into(holder.listDepartmentOfferItemBinding.clinicImage);
        if (StoreManager.getAppLanguage(mContext).equals("ar")) {
            holder.listDepartmentOfferItemBinding.textView15.setText(offerModels.get(position).getClinicNameAr());
        } else {
            holder.listDepartmentOfferItemBinding.textView15.setText(offerModels.get(position).getClinicName());
        }
        holder.listDepartmentOfferItemBinding.textView16.setText(offerModels.get(position).getId());
        holder.listDepartmentOfferItemBinding.textView21.setText(offerModels.get(position).getPlaceName());
    }

    public void addOffers(ArrayList<OfferModel> offerModels) {
        this.offerModels.addAll(offerModels);
        notifyDataSetChanged();
    }

    public void deleteOffer(int position) {
        this.offerModels.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return offerModels.size();
    }

    public String getOfferIdForPosition(int position) {
        return offerModels.get(position).getId();
    }

    class DepartmentOfferViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ListDepartmentOfferItemBinding listDepartmentOfferItemBinding;

        public DepartmentOfferViewHolder(ListDepartmentOfferItemBinding listDepartmentOfferItemBinding) {
            super(listDepartmentOfferItemBinding.getRoot());
            this.listDepartmentOfferItemBinding = listDepartmentOfferItemBinding;
            listDepartmentOfferItemBinding.getRoot().setOnClickListener(this);
            listDepartmentOfferItemBinding.reserveButton.setOnClickListener(this);
            listDepartmentOfferItemBinding.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "\"احجزي هذا العرض من خلال ابليكشن سنيوريتا  ("+ offerModels.get(getAdapterPosition()).getId() + " ) http://senoritasaudi.com/landing");
                    sendIntent.setType("text/plain");
                    mContext.startActivity(sendIntent);
                    onShareClick.onShareClicked();
                }
            });
        }

        @Override
        public void onClick(View v) {
            mOnItemClicked.onItemClicked(getAdapterPosition());
        }
    }
}
