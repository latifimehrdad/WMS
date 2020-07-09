package com.example.wms.views.adaptors.collectrequest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
;

import com.example.wms.R;
import com.example.wms.database.DB_ItemsWasteList;
import com.example.wms.databinding.AdapterItemWasteListBinding;
import com.example.wms.utility.StaticValues;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AP_ItemsWasteList extends RecyclerView.Adapter<AP_ItemsWasteList.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<DB_ItemsWasteList> wasteLists;
    private ItemWasteListClick itemWasteListClick;


    public AP_ItemsWasteList(List<DB_ItemsWasteList> wasteLists, ItemWasteListClick itemWasteListClick) {
        this.wasteLists = wasteLists;
        this.itemWasteListClick = itemWasteListClick;
    }


    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_waste_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(wasteLists.get(position), position);
    }

    @Override
    public int getItemCount() {
        return wasteLists.size();
    }

    public interface ItemWasteListClick {//_________________________________________________________ ItemWasteListClick

        void itemWasteClickAction(Integer position, Byte action);

        void itemWasteDeleteClick(Integer position);
    }//_____________________________________________________________________________________________ ItemWasteListClick



    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemWasteListBinding binding;

        @BindView(R.id.ImageViewDelete)
        ImageView ImageViewDelete;

        @BindView(R.id.ImageViewReduce)
        ImageView ImageViewReduce;

        @BindView(R.id.ImageViewAddition)
        ImageView ImageViewAddition;


        public CustomHolder(AdapterItemWasteListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(DB_ItemsWasteList item, final int position) {
            binding.setItem(item);

            ImageViewDelete.setOnClickListener(v -> {
                itemWasteListClick.itemWasteDeleteClick(position);
            });

            ImageViewReduce.setOnClickListener(v -> {itemWasteListClick.itemWasteClickAction(position, StaticValues.ML_ItemsOFWasteReduce);});

            ImageViewAddition.setOnClickListener(v -> {itemWasteListClick.itemWasteClickAction(position, StaticValues.ML_ItemsOFWasteAddition);});

            binding.executePendingBindings();
        }
    }
}
