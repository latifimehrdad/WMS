package com.ngra.wms.views.adaptors.collectrequest;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterItemOrderWasteBinding;
import com.ngra.wms.models.MD_Amount;
import com.ngra.wms.models.MD_WasteAmountRequests2;

import java.util.List;

public class AP_OrderItemsWast extends RecyclerView.Adapter<AP_OrderItemsWast.CustomHolder> {


    private LayoutInflater layoutInflater;
    private List<MD_WasteAmountRequests2> md_wasteAmountRequests;

    public AP_OrderItemsWast(List<MD_WasteAmountRequests2> md_wasteAmountRequests) {
        this.md_wasteAmountRequests = md_wasteAmountRequests;
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
        holder.bind(md_wasteAmountRequests.get(position));
    }

    @Override
    public int getItemCount() {
        return md_wasteAmountRequests.size();
    }


    public static class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemOrderWasteBinding binding;


        public CustomHolder(AdapterItemOrderWasteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MD_WasteAmountRequests2 item) {
            binding.setWaste(item);
            binding.executePendingBindings();
        }
    }
}
