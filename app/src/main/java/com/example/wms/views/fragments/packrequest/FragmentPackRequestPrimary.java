package com.example.wms.views.fragments.packrequest;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.wms.R;
import com.example.wms.databinding.FragmentPackRequestPrimaryBinding;
import com.example.wms.models.ModelPackage;
import com.example.wms.models.ModelSpinnerItem;
import com.example.wms.models.ModelTime;
import com.example.wms.models.ModelTimes;
import com.example.wms.utility.ApplicationUtility;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.packrequest.VM_FragmentPackRequestPrimary;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.dialogs.DialogProgress;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import params.com.stepview.StatusViewScroller;

public class FragmentPackRequestPrimary extends Fragment {

    private Context context;
    private VM_FragmentPackRequestPrimary vm_fragmentPackRequestPrimary;
    private View view;
    private DialogProgress progress;
    private DisposableObserver<Byte> disposableObserver;
    private Integer timeId;
    private Integer TimePosition;
    private NavController navController;

    @BindView(R.id.FPRPSpinnerHours)
    MaterialSpinner FPRPSpinnerHours;

    @BindView(R.id.FPRPSpinnerDay)
    MaterialSpinner FPRPSpinnerDay;

    @BindView(R.id.textDate)
    TextView textDate;

    @BindView(R.id.textTime)
    TextView textTime;

    @BindView(R.id.RelativeLayoutSave)
    RelativeLayout RelativeLayoutSave;

    @BindView(R.id.LinearLayoutPackageState)
    LinearLayout LinearLayoutPackageState;

    @BindView(R.id.FPRPStatusViewScroller)
    StatusViewScroller FPRPStatusViewScroller;


    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (view == null) {
            this.context = getActivity();
            FragmentPackRequestPrimaryBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_pack_request_primary, container, false
            );
            vm_fragmentPackRequestPrimary = new VM_FragmentPackRequestPrimary(context);
            binding.setRequestprimery(vm_fragmentPackRequestPrimary);
            view = binding.getRoot();
            ButterKnife.bind(this, view);
            SetOnClick();
        }
        return view;
    }//_____________________________________________________________________________________________ onCreateView



    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        navController = Navigation.findNavController(view);
        if (disposableObserver != null)
            disposableObserver.dispose();
        ObserverObservables();

        Integer state = vm_fragmentPackRequestPrimary.GetPackageState();
        boolean isPackage = vm_fragmentPackRequestPrimary.IsPackageState();

        if (isPackage) {
            FPRPStatusViewScroller.getStatusView().setCurrentCount(state + 1);
            if (state != 0) {
                RelativeLayoutSave.setVisibility(View.GONE);
                LinearLayoutPackageState.setVisibility(View.VISIBLE);
                FPRPSpinnerDay.setVisibility(View.GONE);
                SetPackageDate(StaticFunctions.PackageRequestDate(context));
            }
        } else {
            RelativeLayoutSave.setVisibility(View.VISIBLE);
            LinearLayoutPackageState.setVisibility(View.GONE);
            FPRPSpinnerDay.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    vm_fragmentPackRequestPrimary.GetTypeTimes();
                }
            },50);
        }



    }//_____________________________________________________________________________________________ End onStart



    private void ObserverObservables() {//__________________________________________________________ ObserverObservables
        disposableObserver = new DisposableObserver<Byte>() {
            @Override
            public void onNext(Byte s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HandleAction(s);
                    }
                });

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        vm_fragmentPackRequestPrimary
                .getObservables()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

    }//_____________________________________________________________________________________________ ObserverObservables


    private void HandleAction(Byte action) {//______________________________________________________ HandleAction
        if (progress != null)
        progress.dismiss();
        StaticFunctions.isCancel = true;
        if (action == StaticValues.ML_SendPackageRequest) {
            RelativeLayoutSave.setVisibility(View.GONE);
            LinearLayoutPackageState.setVisibility(View.VISIBLE);
            FPRPSpinnerDay.setVisibility(View.GONE);
            FPRPStatusViewScroller.getStatusView().setCurrentCount(2);
            ModelPackage modelPackage = new ModelPackage();
            modelPackage.setRequestDate(vm_fragmentPackRequestPrimary.getModelTimes().getTimes().get(TimePosition).getDate());
            modelPackage.setFromDeliver(vm_fragmentPackRequestPrimary.getModelTimes().getTimes().get(TimePosition).getFrom());
            modelPackage.setToDeliver(vm_fragmentPackRequestPrimary.getModelTimes().getTimes().get(TimePosition).getTo());
            SetPackageDate(modelPackage);
            ShowMessage(vm_fragmentPackRequestPrimary.getMessageResponse()
                    , getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_check));

        } else if (action == StaticValues.ML_GetTimeSheetTimes) {
            SetMaterialSpinnersTimes();
        }
            else if (action == StaticValues.ML_ResponseFailure) {
            ShowMessage(getResources().getString(R.string.NetworkError),
                    getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error));
        } else if (action == StaticValues.ML_ResponseError) {
            ShowMessage(vm_fragmentPackRequestPrimary.getMessageResponse()
                    , getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error));
        }
    }//_____________________________________________________________________________________________ HandleAction



    private void SetPackageDate(ModelPackage modelPackage) {//______________________________________ SetPackageDate
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        ApplicationUtility utility = ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility();
        textDate.setText(utility.MiladiToJalali(
                modelPackage.getFromDeliver(),"FullJalaliString"));
        StringBuilder builder = new StringBuilder();
        builder.append(simpleDateFormat.format(modelPackage.getFromDeliver()));
        builder.append(" تا ");
        builder.append(simpleDateFormat.format(modelPackage.getToDeliver()));
        textTime.setText(builder.toString());
    }//_____________________________________________________________________________________________ SetPackageDate


    private void SetOnClick() {//___________________________________________________________________ SetOnClick

        FPRPSpinnerDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vm_fragmentPackRequestPrimary.getModelTimes() == null) {
                    ShowProgressDialog();
                    vm_fragmentPackRequestPrimary.GetTypeTimes();
                } else
                    SetMaterialSpinnersTimes();
            }
        });

        FPRPSpinnerDay.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                TimePosition = position - 1;
                timeId = vm_fragmentPackRequestPrimary.getModelTimes().getTimes().get(TimePosition).getId();
                FPRPSpinnerDay.setBackgroundColor(context.getResources().getColor(R.color.mlEdit));
            }
        });

        RelativeLayoutSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StaticFunctions.isCancel) {
                    if (CheckEmpty()) {ShowProgressDialog();
                        vm_fragmentPackRequestPrimary.SendPackageRequest(timeId);
                    }
                }
            }
        });


    }//_____________________________________________________________________________________________ SetOnClick



    private Boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

        if (FPRPSpinnerDay.getSelectedIndex() == 0) {
            FPRPSpinnerDay.setBackgroundColor(context.getResources().getColor(R.color.mlEditEmpty));
            FPRPSpinnerDay.requestFocus();
            return false;
        } else
            return true;
    }//_____________________________________________________________________________________________ CheckEmpty



    private void ShowMessage(String message, int color, Drawable icon) {//__________________________ ShowMessage

        ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowMessage(context, message, color, icon, getChildFragmentManager());

    }//_____________________________________________________________________________________________ ShowMessage


    private void ShowProgressDialog() {//___________________________________________________________ ShowProgressDialog

        StaticFunctions.isCancel = false;
        progress = ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowProgress(context, null);
        progress.show(getChildFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ ShowProgressDialog



    private void SetMaterialSpinnersTimes() {//_____________________________________________________ SetMaterialSpinnersTimes
        if (progress != null)
            progress.dismiss();
        ApplicationUtility utility = ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);

        List<String> buildingTypes = new ArrayList<>();
        buildingTypes.add("انتخاب تاریخ دریافت");
        for (ModelTime item : vm_fragmentPackRequestPrimary.getModelTimes().getTimes()) {
            StringBuilder builder = new StringBuilder();
            builder.append(utility.MiladiToJalali(item.getDate(),"FullJalaliString"));
            builder.append(" از ساعت ");
            builder.append(simpleDateFormat.format(item.getFrom()));
            builder.append(" تا ");
            builder.append(simpleDateFormat.format(item.getTo()));
            buildingTypes.add(builder.toString());
        }

        FPRPSpinnerDay.setItems(buildingTypes);

//        FPRPSpinnerHours.setItems("ساعت تحویل", "8 - 10", "11 - 13");
//        FPRPSpinnerDay.setItems("روز تحویل","شنبه 99/03/31 ساعت 9-10","شنبه 99/03/31 ساعت 10-14","یکشنبه 99/04/01 ساعت 10-12");
//
//        FPRPSpinnerDay.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
//                if(position != 0)
//                    textDate.setText("1398/01/01");
//                else
//                    textDate.setText("");
//            }
//        });


//        FPRPSpinnerHours.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
//                if(position == 1)
//                    textTime.setText("8 - 10");
//                else if (position == 2)
//                    textTime.setText("11 - 13");
//                else
//                    textTime.setText("");
//            }
//        });
    }//_____________________________________________________________________________________________ SetMaterialSpinnersTimes



    @Override
    public void onDestroy() {//_____________________________________________________________________ onDestroy
        super.onDestroy();
        if(disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
    }//_____________________________________________________________________________________________ onDestroy



    @Override
    public void onStop() {//________________________________________________________________________ onStop
        super.onStop();
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
    }//_____________________________________________________________________________________________ onStop



}
