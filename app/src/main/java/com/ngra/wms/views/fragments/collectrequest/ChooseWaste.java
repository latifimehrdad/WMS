package com.ngra.wms.views.fragments.collectrequest;

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
import com.ngra.wms.R;
import com.ngra.wms.databinding.FrChooseWasteBinding;
import com.ngra.wms.models.MD_ChooseWaste;
import com.ngra.wms.models.MD_ItemWaste;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.collectrequest.VM_ChooseWaste;

import com.ngra.wms.views.adaptors.collectrequest.AP_ItemsWaste;
import com.ngra.wms.views.adaptors.collectrequest.AP_ItemsWasteList;
import com.ngra.wms.views.fragments.FragmentPrimary;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChooseWaste extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable,
        AP_ItemsWaste.ItemWastClick,
        AP_ItemsWasteList.ItemWasteListClicks {


    private VM_ChooseWaste vm_chooseWaste;
    private NavController navController;
    public static List<MD_ChooseWaste> wasteLists;
    private AP_ItemsWasteList ap_itemsWasteList;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.RecyclerViewItemsWaste)
    RecyclerView RecyclerViewItemsWaste;

    @BindView(R.id.RecyclerViewWasteList)
    RecyclerView RecyclerViewWasteList;

    @BindView(R.id.LinearLayoutNext)
    LinearLayout LinearLayoutNext;


    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_chooseWaste = new VM_ChooseWaste(getContext());
            FrChooseWasteBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_choose_waste, container, false
            );
            binding.setChooseWaste(vm_chooseWaste);
            setView(binding.getRoot());
            SetClicks();
            GetItemsOfWast();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {
        super.onStart();
        setGetMessageFromObservable(
                ChooseWaste.this,
                vm_chooseWaste.getPublishSubject(),
                vm_chooseWaste);
        if (getView() != null)
            navController = Navigation.findNavController(getView());

        if (ap_itemsWasteList != null) {
            ap_itemsWasteList.notifyDataSetChanged();
            if (wasteLists.size() == 0)
                LinearLayoutNext.setVisibility(View.GONE);
            else
                LinearLayoutNext.setVisibility(View.VISIBLE);
        }


    }//_____________________________________________________________________________________________ onStart


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        if (action.equals(StaticValues.ML_GetItemsOfWasteIsSuccess)) {
            gifLoading.setVisibility(View.GONE);
            SetItemsWasteAdapter();
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void GetItemsOfWast() {//_______________________________________________________________ GetItemsOfWast
        gifLoading.setVisibility(View.VISIBLE);
        LinearLayoutNext.setVisibility(View.GONE);
        RecyclerViewItemsWaste.setVisibility(View.GONE);
        RecyclerViewWasteList.setVisibility(View.GONE);
        vm_chooseWaste.GetItemsOfWast();
    }//_____________________________________________________________________________________________ GetItemsOfWast


    private void SetClicks() {//____________________________________________________________________ SetClicks

        LinearLayoutNext.setOnClickListener(v -> navController.navigate(R.id.action_chooseWaste_to_collectRequest2));

    }//_____________________________________________________________________________________________ SetClicks


    private void SetItemsWasteAdapter() {//_________________________________________________________ SetItemsWasteAdapter

        RecyclerViewItemsWaste.setVisibility(View.VISIBLE);
        RecyclerViewWasteList.setVisibility(View.VISIBLE);
        AP_ItemsWaste ap_itemsWaste = new AP_ItemsWaste(vm_chooseWaste.getMd_itemWastes(), ChooseWaste.this);
        RecyclerViewItemsWaste.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        RecyclerViewItemsWaste.setAdapter(ap_itemsWaste);
        wasteLists = new ArrayList<>();
        SetItemsWasteListAdapter();

    }//_____________________________________________________________________________________________ SetItemsWasteAdapter


    @Override
    public void itemWastClick(Integer position) {//_________________________________________________ itemWastClick

        if (getContext() != null) {
            MD_ItemWaste waste = vm_chooseWaste.getMd_itemWastes().get(position);
            if (wasteLists.size() == 0) {
                wasteLists.add(new MD_ChooseWaste(
                        waste,
                        1));
            } else {
                boolean duplicate = false;
                for (MD_ChooseWaste item : wasteLists) {
                    if (item.getWaste().getId() == waste.getId()) {
                        duplicate = true;
                        break;
                    }
                }

                if (!duplicate) {
                    wasteLists.add(new MD_ChooseWaste(
                            waste,
                            1));
                }
            }
            LinearLayoutNext.setVisibility(View.VISIBLE);
            SetItemsWasteListAdapter();
        }

    }//_____________________________________________________________________________________________ itemWastClick


    private void SetItemsWasteListAdapter() {//_____________________________________________________ SetItemsWasteListAdapter
        ap_itemsWasteList = new AP_ItemsWasteList(wasteLists, ChooseWaste.this);
        RecyclerViewWasteList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewWasteList.setAdapter(ap_itemsWasteList);
    }//_____________________________________________________________________________________________ SetItemsWasteListAdapter


    @Override
    public void itemWasteClickActionAdd(Integer position) {//_______________________________________ itemWasteClickActionAdd

        Integer count = wasteLists.get(position).getAmount();
        count = count + 1;
        wasteLists.get(position).setAmount(count);
        ap_itemsWasteList.notifyDataSetChanged();

    }//_____________________________________________________________________________________________ itemWasteClickActionAdd


    @Override
    public void itemWasteClickActionMinus(Integer position) {//_____________________________________ itemWasteClickActionMinus

        Integer count = wasteLists.get(position).getAmount();
        if (count > 1) {
            count = count - 1;
            wasteLists.get(position).setAmount(count);
            ap_itemsWasteList.notifyDataSetChanged();
        }

    }//_____________________________________________________________________________________________ itemWasteClickActionMinus


    @Override
    public void itemWasteClickActionDelete(Integer position, View view) {//_________________________ itemWasteClickActionDelete

        wasteLists.remove(wasteLists.get(position));
        ap_itemsWasteList.notifyDataSetChanged();

        if (wasteLists.size() == 0)
            LinearLayoutNext.setVisibility(View.GONE);

    }//_____________________________________________________________________________________________ itemWasteClickActionDelete


}
