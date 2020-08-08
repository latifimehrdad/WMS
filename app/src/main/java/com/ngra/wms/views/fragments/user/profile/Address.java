package com.ngra.wms.views.fragments.user.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FrAddressBinding;
import com.ngra.wms.models.MD_Booth;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.models.MD_Time;
import com.ngra.wms.models.MD_WasteAmountRequests;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.user.profile.VM_Address;
import com.ngra.wms.views.adaptors.AP_Address;
import com.ngra.wms.views.fragments.FragmentPrimary;
import com.ngra.wms.views.fragments.collectrequest.ChooseWaste;


import org.jetbrains.annotations.NotNull;


import butterknife.BindView;

public class Address extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable,
        AP_Address.ItemAddressClick {


    private VM_Address vm_address;
    private Integer TimeSheetId;
    private Integer BoothId;
    private NavController navController;
    private int TimeSheetType;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.RecyclerViewAddress)
    RecyclerView RecyclerViewAddress;

    @BindView(R.id.ImageViewNew)
    ImageView ImageViewNew;


    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            vm_address = new VM_Address(getContext());
            FrAddressBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_address, container, false
            );
            binding.setAddress(vm_address);
            setView(binding.getRoot());
            gifLoading.setVisibility(View.VISIBLE);
            TimeSheetId = getArguments().getInt(getContext().getString(R.string.ML_TimeId), 0);
            BoothId = getArguments().getInt(getContext().getString(R.string.ML_Id), 0);
            TimeSheetType = getArguments().getInt(getContext().getString(R.string.ML_Type), StaticValues.TimeSheetBooth);
            SetOnclick();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {
        super.onStart();
        setGetMessageFromObservable(
                Address.this,
                vm_address.getPublishSubject(),
                vm_address);

        if (getView() != null)
            navController = Navigation.findNavController(getView());

        GetAddress();

    }//_____________________________________________________________________________________________ onStart




    private void SetOnclick(){
        ImageViewNew.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(getResources().getString(R.string.ML_Id), 0);
            navController.navigate(R.id.action_address_to_packageRequestAddress, bundle);
        });
    }



    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        gifLoading.setVisibility(View.GONE);
        if (action.equals(StaticValues.ML_GetAddress)) {
            SetItemsAddress();
            return;
        }

        if (action.equals(StaticValues.ML_CollectRequestDone)) {
            if (BoothId != 0) {
                getContext().onBackPressed();
                getContext().onBackPressed();
                getContext().onBackPressed();
                getContext().onBackPressed();
                getContext().onBackPressed();
            } else {
                getContext().onBackPressed();
                getContext().onBackPressed();
                getContext().onBackPressed();
                getContext().onBackPressed();
            }
        }


    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void GetAddress() {//___________________________________________________________________ GetAddress

        vm_address.GetAddress();

    }//_____________________________________________________________________________________________ GetAddress


    private void SetItemsAddress() {//______________________________________________________________ SetItemsAddress
        AP_Address ap_address = new AP_Address(vm_address.getAddress(), Address.this);
        RecyclerViewAddress.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewAddress.setAdapter(ap_address);
    }//_____________________________________________________________________________________________ SetItemsAddress


    @Override
    public void itemAddressClick(Integer position) {//______________________________________________ SetItemsAddress

        if (TimeSheetId != -1) {
            MD_WasteAmountRequests md_wasteAmountRequests;
            if (BoothId != 0) {
                md_wasteAmountRequests = new MD_WasteAmountRequests(
                        0,
                        new MD_Booth(BoothId),
                        new MD_Time(TimeSheetId),
                        ChooseWaste.wasteLists,
                        new MD_SpinnerItem(vm_address.getAddress().get(position).getId()));

            } else {
                md_wasteAmountRequests = new MD_WasteAmountRequests(
                        1,
                        null,
                        new MD_Time(TimeSheetId),
                        ChooseWaste.wasteLists,
                        new MD_SpinnerItem(vm_address.getAddress().get(position).getId()));
            }

            vm_address.SendCollectRequest(md_wasteAmountRequests);
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt(getResources().getString(R.string.ML_Id), Integer.valueOf(vm_address.getAddress().get(position).getId()));
            navController.navigate(R.id.action_address_to_packageRequestAddress, bundle);
        }


    }//_____________________________________________________________________________________________ SetItemsAddress



}
