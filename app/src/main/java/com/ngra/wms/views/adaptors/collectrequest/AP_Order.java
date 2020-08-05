package com.ngra.wms.views.adaptors.collectrequest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterItemOrderBinding;
import com.ngra.wms.models.MD_ItemWasteRequest;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AP_Order extends RecyclerView.Adapter<AP_Order.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_ItemWasteRequest> md_itemWasteRequests;

    public AP_Order(List<MD_ItemWasteRequest> md_itemWasteRequests) {
        this.md_itemWasteRequests = md_itemWasteRequests;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_itemWasteRequests.get(position));
    }

    @Override
    public int getItemCount() {
        return md_itemWasteRequests.size();
    }



    public static class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemOrderBinding binding;

        @BindView(R.id.RecyclerViewItemsWaste)
        RecyclerView RecyclerViewItemsWaste;


        public CustomHolder(AdapterItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_ItemWasteRequest item) {
            binding.setWasteRequest(item);
            if (item.getAmounts() != null) {
                AP_OrderItemsWast ap_orderItemsWast = new AP_OrderItemsWast(item.getAmounts());
                RecyclerViewItemsWaste.setLayoutManager(new LinearLayoutManager(RecyclerViewItemsWaste.getContext(), RecyclerView.VERTICAL, false));
                RecyclerViewItemsWaste.setAdapter(ap_orderItemsWast);
            }
            binding.executePendingBindings();
        }
    }


}
