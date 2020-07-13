package com.example.wms.views.fragments.user.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;


import com.cunoraz.gifview.library.GifView;
import com.example.wms.R;
import com.example.wms.databinding.FragmentProfileCodeBinding;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.user.profile.VM_ProfileCode;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.dialogs.DialogProgress;
import com.example.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

import static com.example.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class ProfileCode extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private VM_ProfileCode vm_profileCode;

    @BindView(R.id.editBuildingRenovationCode)
    EditText editBuildingRenovationCode;

    @BindView(R.id.RelativeLayoutSend)
    RelativeLayout RelativeLayoutSend;

    @BindView(R.id.txtLoading)
    TextView txtLoading;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.imgLoading)
    ImageView imgLoading;;


    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentProfileCodeBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_profile_code, container, false);
            vm_profileCode = new VM_ProfileCode(getContext());
            binding.setVmCode(vm_profileCode);
            setView(binding.getRoot());
            init();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                ProfileCode.this,
                vm_profileCode.getPublishSubject(),
                vm_profileCode);
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ Start init
        SetTextWatcher();
        SetClick();
    }//_____________________________________________________________________________________________ End init


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        DismissLoading();

        if (action.equals(StaticValues.ML_EditProfile)) {
            return;
        }

        if (action.equals(StaticValues.ML_GetRenovationCode)) {
            editBuildingRenovationCode.setText(
                    vm_profileCode.getResponseMessage()
            );
            return;
        }

        if (action.equals(StaticValues.ML_GetAccountNumberNull)) {
            editBuildingRenovationCode.requestFocus();
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetClick() {//_____________________________________________________________________ SetClick


        RelativeLayoutSend.setOnClickListener(v -> {

            if (CheckEmpty()) {
                hideKeyboard();
                ShowLoading();
                vm_profileCode.setBuildingRenovationCode(editBuildingRenovationCode.getText().toString());
                vm_profileCode.SendCode();
            }
        });

    }//_____________________________________________________________________________________________ SetClick



    private Boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

        if (editBuildingRenovationCode.getText().length() < 1) {
            editBuildingRenovationCode.setBackgroundResource(R.drawable.dw_edit_back_empty);
            editBuildingRenovationCode.setError(getResources().getString(R.string.EmptyAccountNumber));
            editBuildingRenovationCode.requestFocus();
            return false;
        } else
            return true;

    }//_____________________________________________________________________________________________ CheckEmpty


    private void SetTextWatcher() {//_______________________________________________________________ SetTextWatcher
        editBuildingRenovationCode.setBackgroundResource(R.drawable.dw_edit_back);
        editBuildingRenovationCode.addTextChangedListener(TextChangeForChangeBack(editBuildingRenovationCode));
    }//_____________________________________________________________________________________________ SetTextWatcher



    private void DismissLoading() {//_______________________________________________________________ Start DismissLoading
        txtLoading.setText(getResources().getString(R.string.SaveInfo));
        RelativeLayoutSend.setBackground(getResources().getDrawable(R.drawable.save_info_button));
        gifLoading.setVisibility(View.GONE);
        imgLoading.setVisibility(View.VISIBLE);
    }//_____________________________________________________________________________________________ End DismissLoading


    private void ShowLoading() {//__________________________________________________________________ Start ShowLoading
        txtLoading.setText(getResources().getString(R.string.Cancel));
        RelativeLayoutSend.setBackground(getResources().getDrawable(R.drawable.button_red));
        gifLoading.setVisibility(View.VISIBLE);
        imgLoading.setVisibility(View.INVISIBLE);
    }//_____________________________________________________________________________________________ End ShowLoading



}
