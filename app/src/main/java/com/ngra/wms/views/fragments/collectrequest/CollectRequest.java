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

import com.ngra.wms.R;
import com.ngra.wms.databinding.FrCollectRequestBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.collectrequest.VM_CollectRequest;
import com.ngra.wms.views.adaptors.collectrequest.AP_ItemsWasteList;
import com.ngra.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

import static com.ngra.wms.views.fragments.collectrequest.ChooseWaste.wasteLists;

public class CollectRequest extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable,
        AP_ItemsWasteList.ItemWasteListClicks  {


    private VM_CollectRequest vm_collectRequest;
    private NavController navController;
    private AP_ItemsWasteList ap_itemsWasteList;

    @BindView(R.id.RecyclerViewWasteList)
    RecyclerView RecyclerViewWasteList;

    @BindView(R.id.LinearLayoutBoothReceive)
    LinearLayout LinearLayoutBoothReceive;

    @BindView(R.id.LinearLayoutRecyclingCar)
    LinearLayout LinearLayoutRecyclingCar;


    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_collectRequest = new VM_CollectRequest(getContext());
            FrCollectRequestBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_collect_request, container, false
            );
            binding.setCollect(vm_collectRequest);
            setView(binding.getRoot());
            SetClicks();
            SetItemsWasteListAdapter();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {
        super.onStart();
        setGetMessageFromObservable(
                CollectRequest.this,
                vm_collectRequest.getPublishSubject(),
                vm_collectRequest);
        if (getView() != null)
            navController = Navigation.findNavController(getView());

    }//_____________________________________________________________________________________________ onStart



    private void SetClicks() {//____________________________________________________________________ SetClicks

        LinearLayoutBoothReceive.setOnClickListener(v -> navController.navigate(R.id.action_collectRequest2_to_boothReceive2));

        LinearLayoutRecyclingCar.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getString(R.string.ML_Type), StaticValues.TimeSheetVehicle);
            navController.navigate(R.id.action_collectRequest2_to_timeSheet, bundle);
        });
    }//_____________________________________________________________________________________________ SetClicks



    private void SetItemsWasteListAdapter() {//_____________________________________________________ SetItemsWasteListAdapter
        ap_itemsWasteList = new AP_ItemsWasteList(wasteLists, CollectRequest.this);
        RecyclerViewWasteList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewWasteList.setAdapter(ap_itemsWasteList);
    }//_____________________________________________________________________________________________ SetItemsWasteListAdapter



    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable


    }//_____________________________________________________________________________________________ GetMessageFromObservable



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
            getContext().onBackPressed();

    }//_____________________________________________________________________________________________ itemWasteClickActionDelete



}
