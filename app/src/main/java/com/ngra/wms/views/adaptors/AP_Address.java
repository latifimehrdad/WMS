package com.ngra.wms.views.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterItemAddressBinding;
import com.ngra.wms.databinding.AdapterItemDateBinding;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.models.MD_TimeSheet;
import com.ngra.wms.utility.ApplicationUtility;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AP_Address extends RecyclerView.Adapter<AP_Address.CustomHolder> {


    private LayoutInflater layoutInflater;
    private List<MD_SpinnerItem> md_spinnerItems;
    private ItemAddressClick itemAddressClick;


    public interface ItemAddressClick {//___________________________________________________________ ItemAddressClick
        void itemAddressClick(Integer position);
    }//_____________________________________________________________________________________________ ItemAddressClick


    public AP_Address(List<MD_SpinnerItem> md_spinnerItems, ItemAddressClick itemAddressClick) {
        this.md_spinnerItems = md_spinnerItems;
        this.itemAddressClick = itemAddressClick;
    }


    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_address, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_spinnerItems.get(position), position);
    }

    @Override
    public int getItemCount() {
        return md_spinnerItems.size();
    }




    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemAddressBinding binding;

        Context context;

        @BindView(R.id.ImageViewSend)
        ImageView ImageViewSend;

        @BindView(R.id.gifLoadingSend)
        GifView gifLoadingSend;


        public CustomHolder(AdapterItemAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_SpinnerItem item, Integer Position) {

            binding.setAddress(item);
            binding.getRoot().setOnClickListener(v -> {
                ImageViewSend.setVisibility(View.GONE);
                gifLoadingSend.setVisibility(View.VISIBLE);
                itemAddressClick.itemAddressClick(Position);
            });

            binding.executePendingBindings();
        }
    }
}
