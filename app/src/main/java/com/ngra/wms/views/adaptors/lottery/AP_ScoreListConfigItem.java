package com.ngra.wms.views.adaptors.lottery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterScoreListConfigBinding;
import com.ngra.wms.databinding.AdapterScoreListConfigItemBinding;
import com.ngra.wms.models.MD_ScoreListConfig;
import com.ngra.wms.models.MD_ScoreListItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AP_ScoreListConfigItem extends RecyclerView.Adapter<AP_ScoreListConfigItem.CustomHolder> {

    private LayoutInflater layoutInflater;

    private List<MD_ScoreListItem> scoreListItems;

    private String Title;


    public AP_ScoreListConfigItem(List<MD_ScoreListItem> scoreListItems, String title) {
        this.scoreListItems = scoreListItems;
        Title = title;
    }


    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_score_list_config_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(scoreListItems.get(position));
    }

    @Override
    public int getItemCount() {
        return scoreListItems.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterScoreListConfigItemBinding binding;


        public CustomHolder(AdapterScoreListConfigItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_ScoreListItem item) {
            binding.setItem(item);
            binding.setTitle(Title);
            binding.executePendingBindings();
        }
    }


}
