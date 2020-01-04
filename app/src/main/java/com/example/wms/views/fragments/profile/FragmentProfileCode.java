/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.fragments.profile;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.wms.viewmodels.user.profile.VM_FragmentProfileCode;
import com.example.wms.views.activitys.MainActivity;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.dialogs.DialogProgress;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.wms.utility.StaticFunctions.SetBackClickAndGoHome;
import static com.example.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class FragmentProfileCode extends Fragment {

    private Context context;
    private VM_FragmentProfileCode vm_fragmentProfileCode;
    private View view;
    private DialogProgress progress;
    private DisposableObserver<String> observer;

    @BindView(R.id.editBuildingRenovationCode)
    EditText editBuildingRenovationCode;

    @BindView(R.id.btnSendCode)
    Button btnSendCode;

    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        this.context = getContext();
        FragmentProfileCodeBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_profile_code,container,false
        );
        vm_fragmentProfileCode = new VM_FragmentProfileCode(context);
        binding.setCode(vm_fragmentProfileCode);
        view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView



    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        ObserverObservables();
        SetTextWatcher();
        SetClick();
    }//_____________________________________________________________________________________________ End onStart


    private void GetCode() {//______________________________________________________________________ Start GetCode
        ShowProgressDialog(getResources().getString(R.string.GetBuildingRenovationCode));
        vm_fragmentProfileCode.GetCode();
    }//_____________________________________________________________________________________________ End GetCode


    private void ObserverObservables() {//__________________________________________________________ Start ObserverObservables

        observer = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (progress != null)
                            progress.dismiss();
                        switch (s) {
                            case "SuccessfulNull":
                                editBuildingRenovationCode.requestFocus();
                                break;
                            case "SuccessfulBank":

                                break;
                            case "SuccessfulEdit":
                                ShowMessage(vm_fragmentProfileCode.getMessageResponcse()
                                        , getResources().getColor(R.color.mlWhite),
                                        getResources().getDrawable(R.drawable.ic_check));
                                break;
                            case "SuccessfulGetCode":
                                editBuildingRenovationCode.setText(
                                        vm_fragmentProfileCode
                                                .getMessageResponcse()
                                );
                                break;
                            case "Failure":
                                ShowMessage(getResources().getString(R.string.NetworkError),
                                        getResources().getColor(R.color.mlWhite),
                                        getResources().getDrawable(R.drawable.ic_error));
                                break;
                            default:
                                break;
                        }
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

    }//_____________________________________________________________________________________________ End ObserverObservables


    private Boolean CheckEmpty() {//________________________________________________________________ Start CheckEmpty

        if (editBuildingRenovationCode.getText().length() < 1) {
            editBuildingRenovationCode.setBackgroundResource(R.drawable.edit_empty_background);
            editBuildingRenovationCode.setError(getResources().getString(R.string.EmptyAccountNumber));
            editBuildingRenovationCode.requestFocus();
            return false;
        } else
            return true;

    }//_____________________________________________________________________________________________ End CheckEmpty


    private void SetClick() {//_____________________________________________________________________ Start SetClick


        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckEmpty()) {
                    ShowProgressDialog(null);
                    vm_fragmentProfileCode.setBuildingRenovationCode(editBuildingRenovationCode.getText().toString());
                    vm_fragmentProfileCode.SendCode();
                }
            }
        });

    }//_____________________________________________________________________________________________ End SetClick


    private void SetTextWatcher() {//_______________________________________________________________ Start SetTextWatcher
        editBuildingRenovationCode.setBackgroundResource(R.drawable.edit_normal_background);
        editBuildingRenovationCode.addTextChangedListener(TextChangeForChangeBack(editBuildingRenovationCode));
    }//_____________________________________________________________________________________________ End SetTextWatcher


    private void ShowProgressDialog(String title) {//_______________________________________________ Start ShowProgressDialog

        progress = ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowProgress(context, title);
        progress.show(getChildFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ End ShowProgressDialog


    private void ShowMessage(String message, int color, Drawable icon) {//__________________________ Start ShowMessage

        ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowMessage(context, message, color, icon, getChildFragmentManager());

    }//_____________________________________________________________________________________________ End ShowMessage



    @Override
    public void onDestroy() {//_____________________________________________________________________ Start onDestroy
        super.onDestroy();
        observer.dispose();
    }//_____________________________________________________________________________________________ End onDestroy


}
