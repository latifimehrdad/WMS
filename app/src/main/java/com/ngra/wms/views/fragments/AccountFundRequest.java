package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FrAccountFundRequestsBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_AccountFundRequests;
import com.ngra.wms.views.adaptors.AP_AccountFundRequest;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import butterknife.BindView;

public class AccountFundRequest extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

    private VM_AccountFundRequests vm_accountFundRequests;

    @BindView(R.id.gifViewLoading)
    GifView gifViewLoading;

    @BindView(R.id.recyclerViewFundRequest)
    RecyclerView recyclerViewFundRequest;

    @BindView(R.id.textViewEmpty)
    TextView textViewEmpty;



    //______________________________________________________________________________________________ AccountFundRequest
    public AccountFundRequest() {
    }
    //______________________________________________________________________________________________ AccountFundRequest

    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_accountFundRequests = new VM_AccountFundRequests(getContext());
            FrAccountFundRequestsBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_account_fund_requests, container, false
            );
            binding.setFund(vm_accountFundRequests);
            setView(binding.getRoot());
            init();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                AccountFundRequest.this,
                vm_accountFundRequests.getPublishSubject(),
                vm_accountFundRequests);
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        gifViewLoading.setVisibility(View.GONE);
        if (action.equals(StaticValues.ML_AccountFundRequest)) {
            if (vm_accountFundRequests.getMd_accountFundRequests().size() > 0) {
                recyclerViewFundRequest.setVisibility(View.VISIBLE);
                setAdapter();
            } else
                textViewEmpty.setVisibility(View.VISIBLE);
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ init
    private void init() {
        recyclerViewFundRequest.setVisibility(View.GONE);
        gifViewLoading.setVisibility(View.VISIBLE);
        textViewEmpty.setVisibility(View.GONE);
        vm_accountFundRequests.getAccountFundRequests();
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ setAdapter
    private void setAdapter() {
        AP_AccountFundRequest ap_accountFundRequest = new AP_AccountFundRequest(vm_accountFundRequests.getMd_accountFundRequests());
        recyclerViewFundRequest.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerViewFundRequest.setAdapter(ap_accountFundRequest);
    }
    //______________________________________________________________________________________________ setAdapter


}
