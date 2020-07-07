/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.fragments.user.profile;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentProfileCodeBinding;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.user.profile.VM_FragmentProfileCode;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.dialogs.DialogProgress;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class FragmentProfileCode extends Fragment {

    private Context context;
    private VM_FragmentProfileCode vm_fragmentProfileCode;
    private View view;
    private DialogProgress progress;
    private DisposableObserver<Byte> observer;

    @BindView(R.id.editBuildingRenovationCode)
    EditText editBuildingRenovationCode;

    @BindView(R.id.btnSendCode)
    Button btnSendCode;

    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (view == null) {
            this.context = getActivity();
            FragmentProfileCodeBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_profile_code, container, false
            );
            vm_fragmentProfileCode = new VM_FragmentProfileCode(context);
            binding.setCode(vm_fragmentProfileCode);
            view = binding.getRoot();
            ButterKnife.bind(this, view);
            SetTextWatcher();
            SetClick();
        }
        return view;
    }//_____________________________________________________________________________________________ onCreateView



    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        if(observer != null)
            observer.dispose();
        observer = null;
        ObserverObservables();
    }//_____________________________________________________________________________________________ onStart


    private void GetCode() {//______________________________________________________________________ GetCode
        ShowProgressDialog(getResources().getString(R.string.GetBuildingRenovationCode));
        vm_fragmentProfileCode.GetCode();
    }//_____________________________________________________________________________________________ GetCode


    private void ObserverObservables() {//__________________________________________________________ ObserverObservables

        observer = new DisposableObserver<Byte>() {
            @Override
            public void onNext(Byte s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (progress != null)
                            progress.dismiss();
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


        vm_fragmentProfileCode
                .getObservables()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }//_____________________________________________________________________________________________ ObserverObservables



    private void HandleAction(Byte action) {//______________________________________________________ HandleAction
        if (action == StaticValues.ML_EditProfile) {
            ShowMessage(vm_fragmentProfileCode.getMessageResponse()
                    , getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_check));
        } else if (action == StaticValues.ML_GetRenovationCode) {
            editBuildingRenovationCode.setText(
                    vm_fragmentProfileCode.getMessageResponse()
            );
        } else if (action == StaticValues.ML_GetAccountNumberNull) {
            editBuildingRenovationCode.requestFocus();
        }
        else if (action == StaticValues.ML_ResponseFailure) {
            ShowMessage(getResources().getString(R.string.NetworkError),
                    getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error));
        } else if (action == StaticValues.ML_ResponseError) {
            ShowMessage(vm_fragmentProfileCode.getMessageResponse()
                    , getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error));
        }
    }//_____________________________________________________________________________________________ HandleAction



    private Boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

        if (editBuildingRenovationCode.getText().length() < 1) {
            editBuildingRenovationCode.setBackgroundResource(R.drawable.edit_empty_background);
            editBuildingRenovationCode.setError(getResources().getString(R.string.EmptyAccountNumber));
            editBuildingRenovationCode.requestFocus();
            return false;
        } else
            return true;

    }//_____________________________________________________________________________________________ CheckEmpty


    private void SetClick() {//_____________________________________________________________________ SetClick


        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckEmpty()) {
                    //StaticFunctions.hideKeyboard(getActivity());
                    ShowProgressDialog(null);
                    vm_fragmentProfileCode.setBuildingRenovationCode(editBuildingRenovationCode.getText().toString());
                    vm_fragmentProfileCode.SendCode();
                }
            }
        });

    }//_____________________________________________________________________________________________ SetClick


    private void SetTextWatcher() {//_______________________________________________________________ SetTextWatcher
        editBuildingRenovationCode.setBackgroundResource(R.drawable.edit_normal_background);
        editBuildingRenovationCode.addTextChangedListener(TextChangeForChangeBack(editBuildingRenovationCode));
    }//_____________________________________________________________________________________________ SetTextWatcher


    private void ShowProgressDialog(String title) {//_______________________________________________ ShowProgressDialog

        progress = ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowProgress(context, title);
        progress.show(getChildFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ ShowProgressDialog


    private void ShowMessage(String message, int color, Drawable icon) {//__________________________ ShowMessage

        ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowMessage(context, message, color, icon, getChildFragmentManager(),0);

    }//_____________________________________________________________________________________________ ShowMessage



    @Override
    public void onDestroy() {//_____________________________________________________________________ onDestroy
        super.onDestroy();
        if(observer != null)
            observer.dispose();
        observer = null;
    }//_____________________________________________________________________________________________ onDestroy



    @Override
    public void onStop() {//________________________________________________________________________ onStop
        super.onStop();
        if (observer != null)
            observer.dispose();
        observer = null;
    }//_____________________________________________________________________________________________ onStop


}
