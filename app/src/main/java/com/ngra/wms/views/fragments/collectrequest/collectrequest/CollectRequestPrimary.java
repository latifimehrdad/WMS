package com.ngra.wms.views.fragments.collectrequest.collectrequest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.database.DB_ItemsWasteList;
import com.ngra.wms.databinding.FragmentCollectRequestPrimeryBinding;
import com.ngra.wms.models.MD_ItemWaste;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.collectrequest.collectrequest.VM_CollectRequestPrimary;
import com.ngra.wms.views.adaptors.collectrequest.AP_ItemsWaste;
import com.ngra.wms.views.adaptors.collectrequest.AP_ItemsWasteList;
import com.ngra.wms.views.application.ApplicationWMS;
import com.ngra.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;


public class CollectRequestPrimary extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable,
        AP_ItemsWaste.ItemWastClick,
        AP_ItemsWasteList.ItemWasteListClick {


    private VM_CollectRequestPrimary vm_collectRequestPrimary;
    private NavController navController;
    private RealmResults<DB_ItemsWasteList> wasteLists;
    private AP_ItemsWasteList ap_itemsWasteList;


    @BindView(R.id.fcrpRecyclingCar)
    LinearLayout fcrpRecyclingCar;

    @BindView(R.id.fcrpBoothReceive)
    LinearLayout fcrpBoothReceive;

    @BindView(R.id.RecyclerViewItemsWaste)
    RecyclerView RecyclerViewItemsWaste;

    @BindView(R.id.RecyclerViewWasteList)
    RecyclerView RecyclerViewWasteList;


    public CollectRequestPrimary() {//______________________________________________________________ CollectRequestPrimary
    }//_____________________________________________________________________________________________ CollectRequestPrimary

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
//        if (getView() == null) {
        vm_collectRequestPrimary = new VM_CollectRequestPrimary(getContext());
        FragmentCollectRequestPrimeryBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_collect_request_primery, container, false
        );
        binding.setVMCollectPrimary(vm_collectRequestPrimary);
        setView(binding.getRoot());
        SetClicks();
        GetItemsOfWast();
//        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {
        super.onStart();
        setGetMessageFromObservable(
                CollectRequestPrimary.this,
                vm_collectRequestPrimary.getPublishSubject(),
                vm_collectRequestPrimary);
        if (getView() != null)
            navController = Navigation.findNavController(getView());
        if (ap_itemsWasteList == null && getContext() != null) {
            Realm realm = ApplicationWMS.getApplicationWMS(getContext()).getRealmComponent().getRealm();
            wasteLists = realm.where(DB_ItemsWasteList.class).findAll();
            try {
                realm.beginTransaction();
                wasteLists.deleteAllFromRealm();
                realm.commitTransaction();
            } finally {
                SetItemsWasteListAdapter();
            }
        } else {
            SetItemsWasteListAdapter();
        }

    }//_____________________________________________________________________________________________ onStart


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (action.equals(StaticValues.ML_GetItemsOfWasteIsSuccess)) {
            SetItemsWasteAdapter();
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void GetItemsOfWast() {//_______________________________________________________________ GetItemsOfWast
        vm_collectRequestPrimary.GetItemsOfWast();
    }//_____________________________________________________________________________________________ GetItemsOfWast


    private void SetClicks() {//____________________________________________________________________ SetClicks

        fcrpRecyclingCar.setOnClickListener(v -> {
            if (wasteLists != null && wasteLists.size() > 0)
                navController.navigate(R.id.action_collectRequest_to_recyclingCar);
            else
                ShowMessage(getResources().getString(R.string.EmptyList),
                        getResources().getColor(R.color.mlWhite),
                        getResources().getDrawable(R.drawable.ic_error),
                        getResources().getColor(R.color.mlCollectRight1));
        });

        fcrpBoothReceive.setOnClickListener(v -> {
            if (wasteLists != null && wasteLists.size() > 0)
                navController.navigate(R.id.action_collectRequest_to_boothReceive);
            else
                ShowMessage(getResources().getString(R.string.EmptyList),
                        getResources().getColor(R.color.mlWhite),
                        getResources().getDrawable(R.drawable.ic_error),
                        getResources().getColor(R.color.mlCollectRight1));
        });

    }//_____________________________________________________________________________________________ SetClicks


    private void SetItemsWasteAdapter() {//_________________________________________________________ SetItemsWasteAdapter
        AP_ItemsWaste ap_itemsWaste = new AP_ItemsWaste(vm_collectRequestPrimary.getMd_itemWastes(), CollectRequestPrimary.this);
        RecyclerViewItemsWaste.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        RecyclerViewItemsWaste.setAdapter(ap_itemsWaste);
    }//_____________________________________________________________________________________________ SetItemsWasteAdapter


    private void SetItemsWasteListAdapter() {//_____________________________________________________ SetItemsWasteListAdapter
        ap_itemsWasteList = new AP_ItemsWasteList(wasteLists, CollectRequestPrimary.this);
        RecyclerViewWasteList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewWasteList.setAdapter(ap_itemsWasteList);
    }//_____________________________________________________________________________________________ SetItemsWasteListAdapter


    @Override
    public void itemWastClick(Integer position) {//_________________________________________________ itemWastClick

        if (getContext() != null) {
            MD_ItemWaste waste = vm_collectRequestPrimary.getMd_itemWastes().get(position);
            Realm realm = ApplicationWMS.getApplicationWMS(getContext()).getRealmComponent().getRealm();
            DB_ItemsWasteList duplicate = realm.where(DB_ItemsWasteList.class).equalTo("Id", waste.getId()).findFirst();
            if (duplicate != null) {
                realm.beginTransaction();
                duplicate.setAmount(duplicate.getAmount() + 1);
                realm.commitTransaction();
                ap_itemsWasteList.notifyDataSetChanged();
            } else {
                try {
                    realm.beginTransaction();
                    realm.createObject(DB_ItemsWasteList.class).insert(waste.getId(), waste.getTitle(), 1);
                    realm.commitTransaction();
                } finally {
                    ap_itemsWasteList.notifyDataSetChanged();
                }
            }
        }

    }//_____________________________________________________________________________________________ itemWastClick


    @Override
    public void itemWasteClickAction(Integer position, Byte action) {//_____________________________ itemWasteClickAction
        if (getContext() != null) {
            Integer count = wasteLists.get(position).getAmount();
            if (action.equals(StaticValues.ML_ItemsOFWasteReduce)) {
                if (count > 1)
                    count--;
            } else
                count++;
            Realm realm = ApplicationWMS.getApplicationWMS(getContext()).getRealmComponent().getRealm();
            try {
                realm.beginTransaction();
                wasteLists.get(position).setAmount(count);
                realm.commitTransaction();
            } finally {
                ap_itemsWasteList.notifyDataSetChanged();
            }
        }

    }//_____________________________________________________________________________________________ itemWasteClickAction


    @Override
    public void itemWasteDeleteClick(Integer position) {//__________________________________________ itemWasteDeleteClick
        if (getContext() != null) {
            Realm realm = ApplicationWMS.getApplicationWMS(getContext()).getRealmComponent().getRealm();
            try {
                realm.beginTransaction();
                wasteLists.get(position).deleteFromRealm();
                realm.commitTransaction();
            } finally {
                ap_itemsWasteList.notifyDataSetChanged();
            }
        }

    }//_____________________________________________________________________________________________ itemWasteDeleteClick

}
