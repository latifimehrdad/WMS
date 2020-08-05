package com.ngra.wms.views.adaptors.collectrequest;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterItemOrderWasteBinding;
import com.ngra.wms.models.MD_Amount;

import java.util.List;

public class AP_OrderItemsWast extends RecyclerView.Adapter<AP_OrderItemsWast.CustomHolder> {


    private LayoutInflater layoutInflater;
    private List<MD_Amount> md_amounts;

    public AP_OrderItemsWast(List<MD_Amount> md_amounts) {
        this.md_amounts = md_amounts;
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
        holder.bind(md_amounts.get(position));
    }

    @Override
    public int getItemCount() {
        return md_amounts.size();
    }


    public static class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemOrderWasteBinding binding;


        public CustomHolder(AdapterItemOrderWasteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MD_Amount item) {
            binding.setWaste(item);
            binding.executePendingBindings();
        }
    }
}
