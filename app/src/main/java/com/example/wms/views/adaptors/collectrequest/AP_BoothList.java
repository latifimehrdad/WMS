package com.example.wms.views.adaptors.collectrequest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wms.R;
import com.example.wms.databinding.AdapterItemBoothBinding;
import com.example.wms.databinding.AdapterItemWasteBinding;
import com.example.wms.models.MD_Booth;
import com.example.wms.models.MD_ItemWaste;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AP_BoothList extends RecyclerView.Adapter<AP_BoothList.CustomHolder> {


    private ItemBoothClick itemBoothClick;
    private LayoutInflater layoutInflater;
    private List<MD_Booth> boothList;


    public interface ItemBoothClick {//_____________________________________________________________ ItemBoothClick
        void itemBoothMap(Integer position);
    }//_____________________________________________________________________________________________ ItemBoothClick



    public AP_BoothList(ItemBoothClick itemBoothClick, List<MD_Booth> boothList) {
        this.itemBoothClick = itemBoothClick;
        this.boothList = boothList;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_booth,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(boothList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return boothList.size();
    }



    public class CustomHolder extends RecyclerView.ViewHolder {
        AdapterItemBoothBinding binding;

        @BindView(R.id.ButtonShowMap)
        Button ButtonShowMap;


        public CustomHolder(AdapterItemBoothBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_Booth item, final int position) {
            binding.setBooth(item);

            ButtonShowMap.setOnClickListener(v -> {itemBoothClick.itemBoothMap(position);});

            binding.executePendingBindings();
        }
    }
}
