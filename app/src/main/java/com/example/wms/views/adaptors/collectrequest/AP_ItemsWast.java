package com.example.wms.views.adaptors.collectrequest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wms.R;
import com.example.wms.databinding.AdapterItemWastBinding;
import com.example.wms.models.MD_ItemWast;

import java.util.List;

import butterknife.ButterKnife;

public class AP_ItemsWast extends RecyclerView.Adapter<AP_ItemsWast.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_ItemWast> md_itemWasts;
    private ItemWastClick itemWastClick;


    public interface ItemWastClick {//______________________________________________________________ ItemWastClick
        void itemWastClick(Integer position);
    }//_____________________________________________________________________________________________ ItemWastClick


    public AP_ItemsWast(List<MD_ItemWast> md_itemWasts,
                        ItemWastClick itemWastClick) {
        this.md_itemWasts = md_itemWasts;
        this.itemWastClick = itemWastClick;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_wast, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_itemWasts.get(position), position);
    }

    @Override
    public int getItemCount() {
        return md_itemWasts.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {
        AdapterItemWastBinding binding;

        public CustomHolder(AdapterItemWastBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_ItemWast item, final int position) {
            binding.setItems(item);
            binding.getRoot().setOnClickListener(v -> {
                itemWastClick.itemWastClick(position);
            });
            binding.executePendingBindings();
        }
    }

}
