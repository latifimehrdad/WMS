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
import com.ngra.wms.models.MD_ScoreListConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AP_ScoreListConfig extends RecyclerView.Adapter<AP_ScoreListConfig.CustomHolder> {


    private LayoutInflater layoutInflater;

    private List<MD_ScoreListConfig> scoreListConfigs;


    public AP_ScoreListConfig(List<MD_ScoreListConfig> scoreListConfigs) {
        this.scoreListConfigs = scoreListConfigs;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_score_list_config, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(scoreListConfigs.get(position));
    }

    @Override
    public int getItemCount() {
        return scoreListConfigs.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterScoreListConfigBinding binding;
        Context context;

        @BindView(R.id.RecyclerViewConfig)
        RecyclerView RecyclerViewConfig;

        public CustomHolder(AdapterScoreListConfigBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_ScoreListConfig item) {
            binding.setConfig(item);

            AP_ScoreListConfigItem ap_scoreListConfigItem = new AP_ScoreListConfigItem(item.getItems(), item.getTitle());
            RecyclerViewConfig.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            RecyclerViewConfig.setAdapter(ap_scoreListConfigItem);

            binding.executePendingBindings();
        }
    }


}
