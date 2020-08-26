package com.ngra.wms.views.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterSuggestionBinding;
import com.ngra.wms.models.MD_AdapterSuggestion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AP_Suggestion extends RecyclerView.Adapter<AP_Suggestion.CustomHolder> {

    private List<MD_AdapterSuggestion> items;
    private LayoutInflater layoutInflater;
    private Context context;
    private ItemAddressClick itemAddressClick;

    public AP_Suggestion(List<MD_AdapterSuggestion> items, Context context, ItemAddressClick itemAddressClick) {
        this.items = items;
        this.context = context;
        this.itemAddressClick = itemAddressClick;
    }

    //______________________________________________________________________________________________ ItemAddressClick
    public interface ItemAddressClick {
        void itemAddressClick(Integer position);
        void itemAddressMapClick(Integer position);
    }
    //______________________________________________________________________________________________ ItemAddressClick



    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        return new AP_Suggestion.CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_suggestion, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder {
        AdapterSuggestionBinding binding;

        @BindView(R.id.LinearLayoutShowOnMap)
        LinearLayout LinearLayoutShowOnMap;

        @BindView(R.id.GifViewAddress)
        GifView GifViewAddress;

        public CustomHolder(AdapterSuggestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_AdapterSuggestion item, final int position) {

            GifViewAddress.setVisibility(View.GONE);

            if (item.isLoadMore())
                LinearLayoutShowOnMap.setVisibility(View.GONE);
            else
                LinearLayoutShowOnMap.setVisibility(View.VISIBLE);

            LinearLayoutShowOnMap.setOnClickListener(v -> itemAddressClick.itemAddressMapClick(position));

            binding.setSuggestion(item);
            binding.getRoot().setOnClickListener(v -> {
                if (item.isLoadMore()) {
                    GifViewAddress.setVisibility(View.VISIBLE);
                    itemAddressClick.itemAddressClick(position);
                }
            });
            binding.executePendingBindings();
        }
    }
}
