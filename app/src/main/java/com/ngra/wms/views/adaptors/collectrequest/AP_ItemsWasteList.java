package com.ngra.wms.views.adaptors.collectrequest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterItemWasteListBinding;
import com.ngra.wms.models.MD_ChooseWaste;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AP_ItemsWasteList extends RecyclerView.Adapter<AP_ItemsWasteList.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_ChooseWaste> wasteLists;
    private ItemWasteListClicks itemWasteListClicks;


    public AP_ItemsWasteList(List<MD_ChooseWaste> wasteLists, ItemWasteListClicks itemWasteListClicks) {
        this.wasteLists = wasteLists;
        this.itemWasteListClicks = itemWasteListClicks;
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


    public interface ItemWasteListClicks {//________________________________________________________ ItemWasteListAmount

        void itemWasteClickActionAdd(Integer position);
        void itemWasteClickActionMinus(Integer position);
        void itemWasteClickActionDelete(Integer position, View view);

    }//_____________________________________________________________________________________________ ItemWasteListAmount



    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemWasteListBinding binding;

        @BindView(R.id.ImageViewMinus)
        ImageView ImageViewMinus;

        @BindView(R.id.ImageViewAdd)
        ImageView ImageViewAdd;

        @BindView(R.id.ImageViewDelete)
        ImageView ImageViewDelete;


        public CustomHolder(AdapterItemWasteListBinding binding) {
           super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_ChooseWaste item, final int position) {

            binding.setWasteList(item);

            ImageViewAdd.setOnClickListener(v -> itemWasteListClicks.itemWasteClickActionAdd(position));

            ImageViewMinus.setOnClickListener(v -> itemWasteListClicks.itemWasteClickActionMinus(position));

            ImageViewDelete.setOnClickListener(v -> itemWasteListClicks.itemWasteClickActionDelete(position, binding.getRoot()));

            binding.executePendingBindings();
        }


    }
}
