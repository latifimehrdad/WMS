package com.ngra.wms.views.adaptors.lottery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterBestScoreBinding;
import com.ngra.wms.models.MD_BestScore;

import java.util.List;

import butterknife.ButterKnife;

public class AP_BestScore extends RecyclerView.Adapter<AP_BestScore.CustomHolder> {

    private LayoutInflater layoutInflater;

    private List<MD_BestScore> md_bestScores;

    public AP_BestScore(List<MD_BestScore> md_bestScores) {
        this.md_bestScores = md_bestScores;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_best_score, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_bestScores.get(position));
    }

    @Override
    public int getItemCount() {
        return md_bestScores.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterBestScoreBinding binding;

        public CustomHolder(AdapterBestScoreBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_BestScore item) {
            binding.setScore(item);
            binding.executePendingBindings();
        }
    }


}
