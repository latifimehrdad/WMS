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

import com.cunoraz.gifview.library.GifView;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.ngra.wms.R;
import com.ngra.wms.database.DB_ItemsWasteList;
import com.ngra.wms.databinding.FragmentCollectRequestPrimeryBinding;
import com.ngra.wms.models.MD_ItemWaste;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.models.ModelTime;
import com.ngra.wms.utility.ApplicationUtility;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.collectrequest.collectrequest.VM_CollectRequestPrimary;
import com.ngra.wms.views.adaptors.collectrequest.AP_ItemsWaste;
import com.ngra.wms.views.adaptors.collectrequest.AP_ItemsWasteList;
import com.ngra.wms.views.application.ApplicationWMS;
import com.ngra.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
    private Integer timePosition = -1;
    private Integer volumePosition = -1;


    @BindView(R.id.fcrpRecyclingCar)
    LinearLayout fcrpRecyclingCar;

    @BindView(R.id.fcrpBoothReceive)
    LinearLayout fcrpBoothReceive;

    @BindView(R.id.RecyclerViewItemsWaste)
    RecyclerView RecyclerViewItemsWaste;

    @BindView(R.id.RecyclerViewWasteList)
    RecyclerView RecyclerViewWasteList;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.MaterialSpinnerSpinnerDay)
    MaterialSpinner MaterialSpinnerSpinnerDay;

    @BindView(R.id.MaterialSpinnerSpinnerVolume)
    MaterialSpinner MaterialSpinnerSpinnerVolume;


    public CollectRequestPrimary() {//______________________________________________________________ CollectRequestPrimary
    }//_____________________________________________________________________________________________ CollectRequestPrimary

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_collectRequestPrimary = new VM_CollectRequestPrimary(getContext());
            FragmentCollectRequestPrimeryBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_collect_request_primery, container, false
            );
            binding.setVMCollectPrimary(vm_collectRequestPrimary);
            setView(binding.getRoot());
            vm_collectRequestPrimary.GetTypeTimes();
            vm_collectRequestPrimary.GetVolumeList();
            SetClicks();
            //GetItemsOfWast();
        }
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

        gifLoading.setVisibility(View.GONE);
        if (action.equals(StaticValues.ML_GetItemsOfWasteIsSuccess)) {
            SetItemsWasteAdapter();
            return;
        }

        if (action.equals(StaticValues.ML_GetTimeSheetTimes)) {
            SetMaterialSpinnersTimes();
            return;
        }

        if (action.equals(StaticValues.ML_GetVolume)) {
            SetMaterialSpinnersVolume();
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void GetItemsOfWast() {//_______________________________________________________________ GetItemsOfWast
        gifLoading.setVisibility(View.VISIBLE);
        RecyclerViewItemsWaste.setVisibility(View.GONE);
        RecyclerViewWasteList.setVisibility(View.GONE);
        vm_collectRequestPrimary.GetItemsOfWast();
    }//_____________________________________________________________________________________________ GetItemsOfWast


    private void SetClicks() {//____________________________________________________________________ SetClicks

        fcrpRecyclingCar.setOnClickListener(v -> {
            if (CheckEmpty()){

            }
        });


        fcrpBoothReceive.setOnClickListener(v -> {
            if (CheckEmpty()) {
                navController.navigate(R.id.action_collectRequestPrimary_to_boothReceivePrimary);
            }
        });


//        fcrpRecyclingCar.setOnClickListener(v -> {
//            if (wasteLists != null && wasteLists.size() > 0)
//                navController.navigate(R.id.action_collectRequest_to_recyclingCar);
//            else
//                ShowMessage(getResources().getString(R.string.EmptyList),
//                        getResources().getColor(R.color.mlWhite),
//                        getResources().getDrawable(R.drawable.ic_error),
//                        getResources().getColor(R.color.mlCollectRight1));
//        });
//
//        fcrpBoothReceive.setOnClickListener(v -> {
//            if (wasteLists != null && wasteLists.size() > 0)
//                navController.navigate(R.id.action_collectRequest_to_boothReceive);
//            else
//                ShowMessage(getResources().getString(R.string.EmptyList),
//                        getResources().getColor(R.color.mlWhite),
//                        getResources().getDrawable(R.drawable.ic_error),
//                        getResources().getColor(R.color.mlCollectRight1));
//        });

    }//_____________________________________________________________________________________________ SetClicks


    private void SetItemsWasteAdapter() {//_________________________________________________________ SetItemsWasteAdapter

        RecyclerViewItemsWaste.setVisibility(View.VISIBLE);
        RecyclerViewWasteList.setVisibility(View.VISIBLE);
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


    private void SetMaterialSpinnersTimes() {//_____________________________________________________ SetMaterialSpinnersTimes

        ApplicationUtility utility = null;
        if (getContext() != null) {
            utility = ApplicationWMS
                    .getApplicationWMS(getContext())
                    .getUtilityComponent()
                    .getApplicationUtility();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        List<String> buildingTypes = new ArrayList<>();
        buildingTypes.add(getResources().getString(R.string.ChooseDateDelivery));
        for (ModelTime item : vm_collectRequestPrimary.getModelTimes().getTimes()) {
            String builder = null;
            if (utility != null) {
                builder = utility.MiladiToJalali(item.getDate(), "FullJalaliString") +
                        " از " +
                        simpleDateFormat.format(item.getFrom()) +
                        " تا " +
                        simpleDateFormat.format(item.getTo());
            }
            buildingTypes.add(builder);
        }

        MaterialSpinnerSpinnerDay.setItems(buildingTypes);


        MaterialSpinnerSpinnerDay.setOnItemSelectedListener((view, position, id, item) -> {
            if (position == 0)
                return;
            if (timePosition == -1) {
                timePosition = position - 1;
                MaterialSpinnerSpinnerDay.getItems().remove(0);
                MaterialSpinnerSpinnerDay.setSelectedIndex(MaterialSpinnerSpinnerDay.getItems().size() - 1);
                MaterialSpinnerSpinnerDay.setSelectedIndex(position - 1);
            } else
                timePosition = position;
            MaterialSpinnerSpinnerDay.setBackgroundColor(getResources().getColor(R.color.mlEdit));
        });

    }//_____________________________________________________________________________________________ SetMaterialSpinnersTimes




    private void SetMaterialSpinnersVolume() {//____________________________________________________ SetMaterialSpinnersVolume

        ApplicationUtility utility = null;
        if (getContext() != null) {
            utility = ApplicationWMS
                    .getApplicationWMS(getContext())
                    .getUtilityComponent()
                    .getApplicationUtility();
        }

        List<String> buildingTypes = new ArrayList<>();
        buildingTypes.add(getResources().getString(R.string.ChooseVolumeDelivery));
        for (MD_SpinnerItem item : vm_collectRequestPrimary.getVolumes()) {
            buildingTypes.add(item.getTitle());
        }

        MaterialSpinnerSpinnerVolume.setItems(buildingTypes);

        MaterialSpinnerSpinnerVolume.setOnItemSelectedListener((view, position, id, item) -> {
            if (position == 0)
                return;
            if (volumePosition == -1) {
                volumePosition = position - 1;
                MaterialSpinnerSpinnerVolume.getItems().remove(0);
                MaterialSpinnerSpinnerVolume.setSelectedIndex(MaterialSpinnerSpinnerVolume.getItems().size() - 1);
                MaterialSpinnerSpinnerVolume.setSelectedIndex(position - 1);
            } else
                volumePosition = position;
            MaterialSpinnerSpinnerVolume.setBackgroundColor(getResources().getColor(R.color.mlEdit));
        });

    }//_____________________________________________________________________________________________ SetMaterialSpinnersVolume




    private boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

        boolean time = true;
        boolean volume = true;

        if (timePosition == -1) {
            MaterialSpinnerSpinnerDay.setBackgroundColor(getResources().getColor(R.color.mlEditEmpty));
            MaterialSpinnerSpinnerDay.requestFocus();
            time = false;
        }


        if (volumePosition == -1) {
            MaterialSpinnerSpinnerVolume.setBackgroundColor(getResources().getColor(R.color.mlEditEmpty));
            MaterialSpinnerSpinnerVolume.requestFocus();
            volume = false;;
        }

        return time && volume;

    }//_____________________________________________________________________________________________ CheckEmpty



}
