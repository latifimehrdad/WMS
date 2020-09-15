package com.ngra.wms.views.adaptors.lottery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterItemAddressBinding;
import com.ngra.wms.databinding.AdapterItemLotteryNotificationBinding;
import com.ngra.wms.models.MD_LotteryNotification;
import com.ngra.wms.models.MD_SpinnerItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AP_LotteryNotification extends RecyclerView.Adapter<AP_LotteryNotification.customHolder> {


    private LayoutInflater layoutInflater;

    private List<MD_LotteryNotification> md_lotteryNotifications;


    //______________________________________________________________________________________________ AP_LotteryNotification
    public AP_LotteryNotification(List<MD_LotteryNotification> md_lotteryNotifications) {
        this.md_lotteryNotifications = md_lotteryNotifications;
    }
    //______________________________________________________________________________________________ AP_LotteryNotification


    //______________________________________________________________________________________________ AP_LotteryNotification
    @NonNull
    @Override
    public customHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new customHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_lottery_notification, parent, false));
    }
    //______________________________________________________________________________________________ AP_LotteryNotification


    //______________________________________________________________________________________________ AP_LotteryNotification
    @Override
    public void onBindViewHolder(@NonNull customHolder holder, int position) {
        holder.bind(md_lotteryNotifications.get(position), position);
    }
    //______________________________________________________________________________________________ AP_LotteryNotification


    //______________________________________________________________________________________________ AP_LotteryNotification
    @Override
    public int getItemCount() {
        return md_lotteryNotifications.size();
    }
    //______________________________________________________________________________________________ AP_LotteryNotification


    //______________________________________________________________________________________________ customHolder
    public class customHolder extends RecyclerView.ViewHolder {

        AdapterItemLotteryNotificationBinding binding;

        Context context;


        public customHolder(AdapterItemLotteryNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_LotteryNotification item, Integer Position) {

            binding.setLotteryNotification(item);
            binding.executePendingBindings();
        }
    }
    //______________________________________________________________________________________________ customHolder


}
