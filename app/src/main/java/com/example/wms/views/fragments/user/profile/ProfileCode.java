package com.example.wms.views.fragments.user.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.wms.R;
import com.example.wms.databinding.FragmentProfileCodeBinding;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.user.profile.VM_ProfileCode;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.dialogs.DialogProgress;
import com.example.wms.views.fragments.FragmentPrimary;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class ProfileCode extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private NavController navController;
    private VM_ProfileCode vm_profileCode;
    private DialogProgress progress;

    @BindView(R.id.editBuildingRenovationCode)
    EditText editBuildingRenovationCode;

    @BindView(R.id.btnSendCode)
    Button btnSendCode;


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentProfileCodeBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_profile_code, container, false);
            vm_profileCode = new VM_ProfileCode(getContext());
            binding.setVmCode(vm_profileCode);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            init();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView



    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(ProfileCode.this, vm_profileCode.getPublishSubject());
        navController = Navigation.findNavController(getView());
    }//_____________________________________________________________________________________________ onStart



    private void init() {//_________________________________________________________________________ Start init
        SetTextWatcher();
        SetClick();
    }//_____________________________________________________________________________________________ End init




    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        setAccessClick(true);

        if (action == StaticValues.ML_EditProfile) {
            ShowMessage(vm_profileCode.getResponseMessage()
                    , getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_check),
                    getResources().getColor(R.color.mlWhite));
        }

        if (action == StaticValues.ML_GetRenovationCode) {
            editBuildingRenovationCode.setText(
                    vm_profileCode.getResponseMessage()
            );
        }

        if (action == StaticValues.ML_GetAccountNumberNull) {
            editBuildingRenovationCode.requestFocus();
        }

        if (action == StaticValues.ML_ResponseFailure) {
            ShowMessage(getResources().getString(R.string.NetworkError),
                    getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error),
                    getResources().getColor(R.color.mlWhite));
        }

        if (action == StaticValues.ML_ResponseError) {
            ShowMessage(vm_profileCode.getResponseMessage()
                    , getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error),
                    getResources().getColor(R.color.mlBlack));
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetClick() {//_____________________________________________________________________ SetClick


        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckEmpty()) {
                    StaticFunctions.hideKeyboard(getActivity());
                    ShowProgressDialog(null);
                    vm_profileCode.SendCode(editBuildingRenovationCode.getText().toString());
                }
            }
        });

    }//_____________________________________________________________________________________________ SetClick



    private void ShowProgressDialog(String title) {//_______________________________________________ ShowProgressDialog

        progress = ApplicationWMS
                .getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowProgress(getContext(), title);
        progress.show(getChildFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ ShowProgressDialog



    private Boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

        if (editBuildingRenovationCode.getText().length() < 1) {
            editBuildingRenovationCode.setBackgroundResource(R.drawable.edit_empty_background);
            editBuildingRenovationCode.setError(getResources().getString(R.string.EmptyAccountNumber));
            editBuildingRenovationCode.requestFocus();
            return false;
        } else
            return true;

    }//_____________________________________________________________________________________________ CheckEmpty



    private void SetTextWatcher() {//_______________________________________________________________ SetTextWatcher
        editBuildingRenovationCode.setBackgroundResource(R.drawable.edit_normal_background);
        editBuildingRenovationCode.addTextChangedListener(TextChangeForChangeBack(editBuildingRenovationCode));
    }//_____________________________________________________________________________________________ SetTextWatcher



}
