package com.ngra.wms.views.adaptors.collectrequest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterItemOrderWasteBinding;
import com.ngra.wms.models.MR_Collect;

import java.util.List;

public class AP_OrderItemsWast extends RecyclerView.Adapter<AP_OrderItemsWast.CustomHolder> {


    private LayoutInflater layoutInflater;
    private List<MR_Collect> MRCollects;

    public AP_OrderItemsWast(List<MR_Collect> MRCollects) {
        this.MRCollects = MRCollects;
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
        holder.bind(MRCollects.get(position),position);
    }

    @Override
    public int getItemCount() {
        return MRCollects.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemOrderWasteBinding binding;


        public CustomHolder(AdapterItemOrderWasteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
        }

        public void bind(MR_Collect item, final int position) {
            binding.setWaste(item);
            binding.executePendingBindings();
        }
    }
}
