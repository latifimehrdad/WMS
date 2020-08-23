package com.ngra.wms.views.adaptors.collectrequest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterItemOrderBinding;
import com.ngra.wms.models.MD_Amount;
import com.ngra.wms.models.MD_ItemWaste;
import com.ngra.wms.models.MD_ItemWasteRequest;
import com.ngra.wms.models.MD_WasteAmountRequests2;
import com.ngra.wms.utility.StaticValues;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AP_Order extends RecyclerView.Adapter<AP_Order.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_ItemWasteRequest> md_itemWasteRequests;
    private ItemRequestCancelClick itemRequestCancelClick;

    public AP_Order(List<MD_ItemWasteRequest> md_itemWasteRequests, ItemRequestCancelClick itemRequestCancelClick) {
        this.md_itemWasteRequests = md_itemWasteRequests;
        this.itemRequestCancelClick = itemRequestCancelClick;
    }


    //______________________________________________________________________________________________ itemRequestCancel
    public interface ItemRequestCancelClick {
        void itemRequestCancel(Integer position);
    }
    //______________________________________________________________________________________________ itemRequestCancel



    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_itemWasteRequests.get(position), position);
    }

    @Override
    public int getItemCount() {
        return md_itemWasteRequests.size();
    }



    public  class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemOrderBinding binding;

        Context context;

        @BindView(R.id.RecyclerViewItemsWaste)
        RecyclerView RecyclerViewItemsWaste;

        @BindView(R.id.RecyclerViewItemsWasteUser)
        RecyclerView RecyclerViewItemsWasteUser;

        @BindView(R.id.RelativeLayoutState)
        RelativeLayout RelativeLayoutState;

        @BindView(R.id.TextViewState)
        TextView TextViewState;

        @BindView(R.id.LinearLayoutCancel)
        LinearLayout LinearLayoutCancel;



        public CustomHolder(AdapterItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            context = binding.getRoot().getContext();
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_ItemWasteRequest item, Integer Position) {
            binding.setWasteRequest(item);






            if ((item.getWasteCollectionState() == StaticValues.WasteCollectionStateNoDelivery) ||
                    (item.getWasteCollectionState() == StaticValues.WasteCollectionStateCanceled)) {
                RelativeLayoutState.setVisibility(View.GONE);
                TextViewState.setVisibility(View.VISIBLE);
            } else {
                RelativeLayoutState.setVisibility(View.VISIBLE);
                TextViewState.setVisibility(View.GONE);
            }


            if (item.getWasteCollectionState() == StaticValues.WasteCollectionStateNoDelivery) {
                TextViewState.setText(context.getResources().getString(R.string.FailureDeliver));
            } else if (item.getWasteCollectionState() == StaticValues.WasteCollectionStateCanceled) {
                TextViewState.setText(context.getResources().getString(R.string.RequestIsCanceled));
            }



            if ((item.getWasteCollectionState() == StaticValues.WasteCollectionStateNoDelivery) ||
                    (item.getWasteCollectionState() == StaticValues.WasteCollectionStateDelivered) ||
                    (item.getWasteCollectionState() == StaticValues.WasteCollectionStateCanceled))
                LinearLayoutCancel.setVisibility(View.GONE);
            else
                LinearLayoutCancel.setVisibility(View.VISIBLE);



            if (item.getWasteAmountEstimates() != null) {
                AP_OrderItemWasteUser wasteUser = new AP_OrderItemWasteUser(item.getWasteAmountEstimates());
                RecyclerViewItemsWasteUser.setLayoutManager(new LinearLayoutManager(RecyclerViewItemsWaste.getContext(), RecyclerView.VERTICAL, false));
                RecyclerViewItemsWasteUser.setAdapter(wasteUser);

            } else
                return;

            if (item.getWasteAmountRequests() != null && item.getWasteAmountRequests().size() > 0) {
                AP_OrderItemsWast ap_orderItemsWast = new AP_OrderItemsWast(item.getWasteAmountRequests());
                RecyclerViewItemsWaste.setLayoutManager(new LinearLayoutManager(RecyclerViewItemsWaste.getContext(), RecyclerView.VERTICAL, false));
                RecyclerViewItemsWaste.setAdapter(ap_orderItemsWast);
            } else {
                List<MD_WasteAmountRequests2> md_wasteAmountRequests = new ArrayList<>();
                for (String temp : item.getWasteAmountEstimates())
                    md_wasteAmountRequests.add(new MD_WasteAmountRequests2(new MD_ItemWaste(0,context.getResources().getString(R.string.WaitForAccept),null),-1,""));
                AP_OrderItemsWast ap_orderItemsWast = new AP_OrderItemsWast(md_wasteAmountRequests);
                RecyclerViewItemsWaste.setLayoutManager(new LinearLayoutManager(RecyclerViewItemsWaste.getContext(), RecyclerView.VERTICAL, false));
                RecyclerViewItemsWaste.setAdapter(ap_orderItemsWast);
            }

            LinearLayoutCancel.setOnClickListener(v -> {
                LinearLayoutCancel.setAlpha(0.3f);
                itemRequestCancelClick.itemRequestCancel(Position);
            });

            binding.executePendingBindings();
        }
    }


}
