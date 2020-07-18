package com.example.wms.views.fragments.learn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wms.R;
import com.example.wms.databinding.FragmentLearnItemsBinding;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.learn.VM_LearnItem;
import com.example.wms.views.adaptors.AP_LearnItem;
import com.example.wms.views.fragments.FragmentPrimary;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LearnItems extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private VM_LearnItem vm_learnItem;

    @BindView(R.id.RecyclerViewItems)
    RecyclerView RecyclerViewItems;


    public LearnItems() {//_________________________________________________________________________ LearnItems

    }//_____________________________________________________________________________________________ LearnItems



    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_learnItem = new VM_LearnItem(getContext());
            FragmentLearnItemsBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_learn_items, container, false);
            binding.setVmLearmItem(vm_learnItem);
            setView(binding.getRoot());
            vm_learnItem.GetItems();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView



    @Override
    public void onStart() {//_______________________________________________________ Start
        super.onStart();
        setGetMessageFromObservable(
                LearnItems.this,
                vm_learnItem.getPublishSubject(),
                vm_learnItem);

    }//_____________________________________________________________________________________________ onCreateView



    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable
        if (action.equals(StaticValues.ML_GetItemLearn))
            SetAdapterItems();
    }//_____________________________________________________________________________________________ GetMessageFromObservable



    private void SetAdapterItems() {//______________________________________________________________ SetAdapterItems
        AP_LearnItem ap_learnItem = new AP_LearnItem(vm_learnItem.getItemLearns());
        RecyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false));
        RecyclerViewItems.setAdapter(ap_learnItem);
    }//_____________________________________________________________________________________________ SetAdapterItems


}
