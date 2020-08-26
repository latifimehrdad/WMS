package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FrCollectRequestBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_CollectRequest;
import com.ngra.wms.views.adaptors.collectrequest.AP_ItemsWasteList;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

import static com.ngra.wms.views.fragments.ChooseWaste.wasteLists;

public class CollectRequest extends FragmentPrimary implements
        FragmentPrimary.getActionFromObservable,
        AP_ItemsWasteList.ItemWasteListClicks {


    private VM_CollectRequest vm_collectRequest;
    private NavController navController;
    private AP_ItemsWasteList ap_itemsWasteList;

    @BindView(R.id.RecyclerViewWasteList)
    RecyclerView RecyclerViewWasteList;

    @BindView(R.id.LinearLayoutBoothReceive)
    LinearLayout LinearLayoutBoothReceive;

    @BindView(R.id.LinearLayoutRecyclingCar)
    LinearLayout LinearLayoutRecyclingCar;

    @BindView(R.id.TextViewScoreBooth)
    TextView TextViewScoreBooth;

    @BindView(R.id.TextViewScoreCar)
    TextView TextViewScoreCar;


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_collectRequest = new VM_CollectRequest(getContext());
            FrCollectRequestBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_collect_request, container, false
            );
            binding.setCollect(vm_collectRequest);
            setView(binding.getRoot());
            setClicks();
            setItemsWasteListAdapter();
            vm_collectRequest.getScoreList();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                CollectRequest.this,
                vm_collectRequest.getPublishSubject(),
                vm_collectRequest);
        if (getView() != null)
            navController = Navigation.findNavController(getView());

    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ setClicks
    private void setClicks() {

        LinearLayoutBoothReceive.setOnClickListener(v -> navController.navigate(R.id.action_collectRequest2_to_map));

        LinearLayoutRecyclingCar.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getString(R.string.ML_Type), StaticValues.TimeSheetVehicle);
            navController.navigate(R.id.action_collectRequest2_to_timeSheet, bundle);
        });


        TextViewScoreBooth.setOnClickListener(v -> {
            String score = "";
            if (vm_collectRequest.getScoreBooth() != null)
                for (String s : vm_collectRequest.getScoreBooth())
                    score = score + s + System.getProperty("line.separator");

            showMessageDialog(score
                    , getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_check),
                    getResources().getColor(R.color.colorPrimaryDark));

        });


        TextViewScoreCar.setOnClickListener(v -> {
            String score = "";
            if (vm_collectRequest.getScoreVehicle() != null)
                for (String s : vm_collectRequest.getScoreVehicle())
                    score = score + s + System.getProperty("line.separator");

            showMessageDialog(score
                    , getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_check),
                    getResources().getColor(R.color.colorPrimaryDark));

        });

    }
    //______________________________________________________________________________________________ setClicks


    //______________________________________________________________________________________________ setItemsWasteListAdapter
    private void setItemsWasteListAdapter() {
        ap_itemsWasteList = new AP_ItemsWasteList(wasteLists, CollectRequest.this);
        RecyclerViewWasteList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewWasteList.setAdapter(ap_itemsWasteList);
    }
    //______________________________________________________________________________________________ setItemsWasteListAdapter


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {


    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ itemWasteClickActionAdd
    @Override
    public void itemWasteClickActionAdd(Integer position) {

        Integer count = wasteLists.get(position).getAmount();
        count = count + 1;
        wasteLists.get(position).setAmount(count);
        ap_itemsWasteList.notifyDataSetChanged();

    }
    //______________________________________________________________________________________________ itemWasteClickActionAdd


    //______________________________________________________________________________________________ itemWasteClickActionMinus
    @Override
    public void itemWasteClickActionMinus(Integer position) {

        Integer count = wasteLists.get(position).getAmount();
        if (count > 1) {
            count = count - 1;
            wasteLists.get(position).setAmount(count);
            ap_itemsWasteList.notifyDataSetChanged();
        }

    }
    //______________________________________________________________________________________________ itemWasteClickActionMinus


    //______________________________________________________________________________________________ itemWasteClickActionDelete
    @Override
    public void itemWasteClickActionDelete(Integer position, View view) {

        wasteLists.remove(wasteLists.get(position));
        ap_itemsWasteList.notifyDataSetChanged();

        if (wasteLists.size() == 0)
            getContext().onBackPressed();

    }
    //______________________________________________________________________________________________ itemWasteClickActionDelete


}
