package com.example.wms.views.adaptors.lottery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wms.R;
import com.example.wms.databinding.AdapterLottoryGiveScoreItemBinding;
import com.example.wms.models.MD_GiveScoreItem;
import java.util.List;

import butterknife.ButterKnife;

public class AP_GiveScore extends RecyclerView.Adapter<AP_GiveScore.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_GiveScoreItem> md_giveScoreItemList;

    public AP_GiveScore(List<MD_GiveScoreItem> md_giveScoreItemList) {
        this.md_giveScoreItemList = md_giveScoreItemList;
    }


    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_lottory_give_score_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_giveScoreItemList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return md_giveScoreItemList.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterLottoryGiveScoreItemBinding binding;

        public CustomHolder(AdapterLottoryGiveScoreItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_GiveScoreItem item, final int position) {
            binding.setScore(item);
            binding.executePendingBindings();
        }
    }

}
