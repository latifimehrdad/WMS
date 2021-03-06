package com.ngra.wms.views.adaptors.lottery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterLottoryUserScoreItemBinding;
import com.ngra.wms.models.MD_GiveScoreItem;

import java.util.List;

import butterknife.ButterKnife;

public class AP_UserScore extends RecyclerView.Adapter<AP_UserScore.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_GiveScoreItem> md_giveScoreItemList;

    public AP_UserScore(List<MD_GiveScoreItem> md_giveScoreItemList) {
        this.md_giveScoreItemList = md_giveScoreItemList;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_lottory_user_score_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_giveScoreItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return md_giveScoreItemList.size();
    }

    public static class CustomHolder extends RecyclerView.ViewHolder {

        AdapterLottoryUserScoreItemBinding binding;

        public CustomHolder(AdapterLottoryUserScoreItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_GiveScoreItem item) {
            binding.setScore(item);
            binding.executePendingBindings();
        }
    }


}
