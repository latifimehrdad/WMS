package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FrTimeSheetBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_TimeSheet;
import com.ngra.wms.views.adaptors.collectrequest.AP_TimeSheet;
import com.ngra.wms.views.adaptors.collectrequest.AP_TimeSheetItem;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class TimeSheet extends FragmentPrimary implements
        FragmentPrimary.getActionFromObservable,
        AP_TimeSheet.ItemTimeClick,
        AP_TimeSheetItem.ItemTimeItemClick {


    private VM_TimeSheet vm_timeSheet;
    private NavController navController;
    private int TimeSheetType;
    private Integer TimeSheetId;
    private Integer TimeSheetPosition;
    private Integer BoothId = 0;
    private AP_TimeSheet ap_timeSheet;
    private AP_TimeSheetItem ap_timeSheetItem;

    @BindView(R.id.RecyclerViewDates)
    RecyclerView RecyclerViewDates;

    @BindView(R.id.RecyclerViewTimes)
    RecyclerView RecyclerViewTimes;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.LinearLayoutNext)
    LinearLayout LinearLayoutNext;

    @BindView(R.id.TextViewSend)
    TextView TextViewSend;

    @BindView(R.id.ImageViewSend)
    ImageView ImageViewSend;

    @BindView(R.id.gifLoadingSend)
    GifView gifLoadingSend;


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            vm_timeSheet = new VM_TimeSheet(getContext());
            FrTimeSheetBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fr_time_sheet, container, false
            );
            binding.setTimesheer(vm_timeSheet);
            setView(binding.getRoot());
            setClicks();
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
                TimeSheet.this,
                vm_timeSheet.getPublishSubject(),
                vm_timeSheet);

        if (getView() != null)
            navController = Navigation.findNavController(getView());


    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ init
    private void init() {
        TimeSheetType = getArguments().getInt(getContext().getString(R.string.ML_Type), StaticValues.TimeSheetBooth);
        gifLoading.setVisibility(View.VISIBLE);
        TimeSheetId = -2;
        gifLoadingSend.setVisibility(View.GONE);
        if (TimeSheetType != StaticValues.TimeSheetPackage) {
            TextViewSend.setText(getContext().getResources().getString(R.string.NextStep));
            ImageViewSend.setImageDrawable(getContext().getResources().getDrawable(R.drawable.svg_arrow_left));
        } else {
            TextViewSend.setText(getContext().getResources().getString(R.string.FragmentPackRequestPrimary));
            ImageViewSend.setImageDrawable(getContext().getResources().getDrawable(R.drawable.svg_trash));
        }

        getTimeSheet();

    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        gifLoading.setVisibility(View.GONE);
        gifLoadingSend.setVisibility(View.GONE);
        ImageViewSend.setVisibility(View.VISIBLE);

        if (action.equals(StaticValues.ML_GetTimeSheet)) {
            setTimeSheetAdapter();
            return;
        }

        if (action.equals(StaticValues.ML_SendPackageRequest)) {
            getContext().onBackPressed();
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ getTimeSheet
    private void getTimeSheet() {

        if (TimeSheetType == StaticValues.TimeSheetBooth) {
            BoothId = getArguments().getInt(getContext().getResources().getString(R.string.ML_Id), 0);
            vm_timeSheet.getBoothTimes();
        } else
            vm_timeSheet.getVehicleTimes();
    }
    //______________________________________________________________________________________________ getTimeSheet


    //______________________________________________________________________________________________ setClicks
    private void setClicks() {

        LinearLayoutNext.setOnClickListener(v -> {
            if (TimeSheetId < 0) {
                showMessageDialog(getContext().getResources().getString(R.string.EmptyTimeSheet),
                        getResources().getColor(R.color.mlWhite),
                        getResources().getDrawable(R.drawable.ic_error),
                        getResources().getColor(R.color.mlCollectRight1));
                return;
            }

            if (TimeSheetType != StaticValues.TimeSheetPackage) {
                Bundle bundle = new Bundle();
                bundle.putInt(getContext().getResources().getString(R.string.ML_TimeId), TimeSheetId);
                bundle.putInt(getContext().getString(R.string.ML_Id), BoothId);
                bundle.putInt(getContext().getString(R.string.ML_Type), TimeSheetType);
                navController.navigate(R.id.action_timeSheet_to_address, bundle);
            } else {
                gifLoadingSend.setVisibility(View.VISIBLE);
                ImageViewSend.setVisibility(View.GONE);
                vm_timeSheet.sendPackageRequest(TimeSheetId);
            }

        });
    }
    //______________________________________________________________________________________________ setClicks


    //______________________________________________________________________________________________ setTimeSheetAdapter
    private void setTimeSheetAdapter() {

        ap_timeSheet = new AP_TimeSheet(vm_timeSheet.getMd_timesSheet(), TimeSheet.this, -1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setReverseLayout(true);
        RecyclerViewDates.setLayoutManager(layoutManager);
        RecyclerViewDates.setAdapter(ap_timeSheet);
        TimeSheetId = -2;
        if (vm_timeSheet.getMd_timesSheet().size() > 0)
            setItemsTime(0);

    }
    //______________________________________________________________________________________________ setTimeSheetAdapter


    //______________________________________________________________________________________________ itemTimeClick
    @Override
    public void itemTimeClick(Integer position) {

        ap_timeSheet.setSelected(position);
        ap_timeSheet.notifyDataSetChanged();
        TimeSheetId = -1;
        TimeSheetPosition = position;
        setItemsTime(position);

    }
    //______________________________________________________________________________________________ itemTimeClick


    //______________________________________________________________________________________________ setItemsTime
    private void setItemsTime(Integer Position) {

        ap_timeSheetItem = new AP_TimeSheetItem(vm_timeSheet.getMd_timesSheet().get(Position).getTimes(), TimeSheet.this);
        ap_timeSheetItem.setSelected(TimeSheetId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setReverseLayout(true);
        RecyclerViewTimes.setLayoutManager(layoutManager);
        RecyclerViewTimes.setAdapter(ap_timeSheetItem);

    }
    //______________________________________________________________________________________________ setItemsTime


    //______________________________________________________________________________________________ itemTimeItemClick
    @Override
    public void itemTimeItemClick(Integer position) {

        if (TimeSheetPosition == null) {
            showMessageDialog(
                    getContext().getResources().getString(R.string.ChooseDay),
                    getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error),
                    getResources().getColor(R.color.mlCollectRight1));
            return;
        }
        TimeSheetId = vm_timeSheet.getMd_timesSheet().get(TimeSheetPosition).getTimes().get(position).getId();
        ap_timeSheetItem.setSelected(position);
        ap_timeSheetItem.notifyDataSetChanged();

    }
    //______________________________________________________________________________________________ itemTimeItemClick


}
