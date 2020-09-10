package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentLearnItemsBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_LearnItem;
import com.ngra.wms.views.adaptors.AP_LearnItem;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class LearnItems extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

    private VM_LearnItem vm_learnItem;

    @BindView(R.id.RecyclerViewItems)
    RecyclerView RecyclerViewItems;


    //______________________________________________________________________________________________ LearnItems
    public LearnItems() {
    }
    //______________________________________________________________________________________________ LearnItems


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_learnItem = new VM_LearnItem(getContext());
            FragmentLearnItemsBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_learn_items, container, false);
            binding.setVmLearmItem(vm_learnItem);
            setView(binding.getRoot());
            vm_learnItem.getItems();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onSta
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                LearnItems.this,
                vm_learnItem.getPublishSubject(),
                vm_learnItem);

    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {
        if (action.equals(StaticValues.ML_GetItemLearn))
            setAdapterItems();
    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest



    //______________________________________________________________________________________________ setAdapterItems
    private void setAdapterItems() {
        AP_LearnItem ap_learnItem = new AP_LearnItem(vm_learnItem.getItemLearns());
        RecyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewItems.setAdapter(ap_learnItem);
    }
    //______________________________________________________________________________________________ setAdapterItems


}
