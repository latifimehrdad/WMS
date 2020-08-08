package com.ngra.wms.views.adaptors.collectrequest;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterItemOrderWasteBinding;
import com.ngra.wms.databinding.AdapterWasteOrderUserBinding;
import com.ngra.wms.models.MD_Amount;

import java.util.List;

public class AP_OrderItemWasteUser extends RecyclerView.Adapter<AP_OrderItemWasteUser.CustomHolder> {


    private LayoutInflater layoutInflater;
    private List<String> wastes;


    public AP_OrderItemWasteUser(List<String> wastes) {
        this.wastes = wastes;
    }

    @NonNull
    @Override
    public AP_OrderItemWasteUser.CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        return new AP_OrderItemWasteUser.CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_waste_order_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AP_OrderItemWasteUser.CustomHolder holder, int position) {
        holder.bind(wastes.get(position));
    }

    @Override
    public int getItemCount() {
        return wastes.size();
    }


    public static class CustomHolder extends RecyclerView.ViewHolder {

        AdapterWasteOrderUserBinding binding;


        public CustomHolder(AdapterWasteOrderUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String item) {
            binding.setWaste(item);
            binding.executePendingBindings();
        }
    }
}