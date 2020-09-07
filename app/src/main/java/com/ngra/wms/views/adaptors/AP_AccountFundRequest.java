package com.ngra.wms.views.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.AdapterAccountFundRequestBinding;
import com.ngra.wms.databinding.AdapterItemAddressBinding;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.models.MD_accountFundRequests;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AP_AccountFundRequest extends RecyclerView.Adapter<AP_AccountFundRequest.CustomHolder> {


    private LayoutInflater layoutInflater;
    private List<MD_accountFundRequests> list;

    //______________________________________________________________________________________________ AP_AccountFundRequest
    public AP_AccountFundRequest(List<MD_accountFundRequests> list) {
        this.list = list;
    }
    //______________________________________________________________________________________________ AP_AccountFundRequest


    //______________________________________________________________________________________________ AP_AccountFundRequest
    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_account_fund_request, parent, false));
    }
    //______________________________________________________________________________________________ AP_AccountFundRequest


    //______________________________________________________________________________________________ AP_AccountFundRequest
    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(list.get(position), position);
    }
    //______________________________________________________________________________________________ AP_AccountFundRequest


    //______________________________________________________________________________________________ AP_AccountFundRequest
    @Override
    public int getItemCount() {
        return list.size();
    }
    //______________________________________________________________________________________________ AP_AccountFundRequest


    //______________________________________________________________________________________________ CustomHolder
    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterAccountFundRequestBinding binding;
        Context context;

        public CustomHolder(AdapterAccountFundRequestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            context = view.getContext();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_accountFundRequests item, Integer Position) {

            binding.setRequest(item);
            binding.executePendingBindings();
        }
    }
    //______________________________________________________________________________________________ CustomHolder


}
