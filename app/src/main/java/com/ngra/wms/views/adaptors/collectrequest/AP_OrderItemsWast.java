package com.ngra.wms.views.adaptors.collectrequest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterItemOrderBinding;
import com.ngra.wms.databinding.AdapterItemOrderWasteBinding;
import com.ngra.wms.models.MD_RequestCollect;

import java.util.List;

public class AP_OrderItemsWast extends RecyclerView.Adapter<AP_OrderItemsWast.CustomHolder> {


    private LayoutInflater layoutInflater;
    private List<MD_RequestCollect> md_requestCollects;

    public AP_OrderItemsWast(List<MD_RequestCollect> md_requestCollects) {
        this.md_requestCollects = md_requestCollects;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_order_waste, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_requestCollects.get(position),position);
    }

    @Override
    public int getItemCount() {
        return md_requestCollects.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemOrderWasteBinding binding;


        public CustomHolder(AdapterItemOrderWasteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
        }

        public void bind(MD_RequestCollect item, final int position) {
            binding.setWaste(item);
            binding.executePendingBindings();
        }
    }
}
