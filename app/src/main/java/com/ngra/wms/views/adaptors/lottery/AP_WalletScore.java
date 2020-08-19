package com.ngra.wms.views.adaptors.lottery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterScoreListConfigBinding;
import com.ngra.wms.databinding.AdapterWalletScoreBinding;
import com.ngra.wms.models.MD_ScoreListConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AP_WalletScore extends RecyclerView.Adapter<AP_WalletScore.CustomHolder> {


    private LayoutInflater layoutInflater;

    private List<MD_ScoreListConfig> scoreListConfigs;


    public AP_WalletScore(List<MD_ScoreListConfig> scoreListConfigs) {
        this.scoreListConfigs = scoreListConfigs;
    }

    @NonNull
    @Override
    public AP_WalletScore.CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new AP_WalletScore.CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_wallet_score, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AP_WalletScore.CustomHolder holder, int position) {
        holder.bind(scoreListConfigs.get(position));
    }

    @Override
    public int getItemCount() {
        return scoreListConfigs.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterWalletScoreBinding binding;
        Context context;

        @BindView(R.id.RecyclerViewConfig)
        RecyclerView RecyclerViewConfig;

        public CustomHolder(AdapterWalletScoreBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_ScoreListConfig item) {
            binding.setConfig(item);

            AP_WalletScoreItem ap_walletScoreItem = new AP_WalletScoreItem(item.getItems());
            RecyclerViewConfig.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            RecyclerViewConfig.setAdapter(ap_walletScoreItem);

            binding.executePendingBindings();
        }
    }


}