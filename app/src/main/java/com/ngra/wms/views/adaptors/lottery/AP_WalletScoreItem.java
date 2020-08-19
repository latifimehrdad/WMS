package com.ngra.wms.views.adaptors.lottery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterWalletListConfigBinding;
import com.ngra.wms.models.MD_ScoreListItem;

import java.util.List;

import butterknife.ButterKnife;

public class AP_WalletScoreItem extends RecyclerView.Adapter<AP_WalletScoreItem.CustomHolder> {

    private LayoutInflater layoutInflater;

    private List<MD_ScoreListItem> scoreListItems;



    public AP_WalletScoreItem(List<MD_ScoreListItem> scoreListItems) {
        this.scoreListItems = scoreListItems;
    }


    @NonNull
    @Override
    public AP_WalletScoreItem.CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new AP_WalletScoreItem.CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_wallet_list_config, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AP_WalletScoreItem.CustomHolder holder, int position) {
        holder.bind(scoreListItems.get(position));
    }

    @Override
    public int getItemCount() {
        return scoreListItems.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterWalletListConfigBinding binding;


        public CustomHolder(AdapterWalletListConfigBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_ScoreListItem item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }


}