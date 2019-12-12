package com.example.wms.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.wms.R;
import com.example.wms.databinding.DialogProgressBinding;
import com.example.wms.viewmodels.main.SplashActivityViewModel;
import com.example.wms.viewmodels.user.login.ActivityBeforLoginViewModel;
import com.example.wms.viewmodels.user.register.ActivitySendPhoneNumberViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogProgress extends DialogFragment {

    private Context context;
    private String Title;
    private ActivitySendPhoneNumberViewModel activitySendPhoneNumberViewModel;
    private ActivityBeforLoginViewModel activityBeforLoginViewModel;
    private SplashActivityViewModel splashActivityViewModel;

    @BindView(R.id.DialogProgressIgnor)
    Button DialogProgressIgnor;

    @BindView(R.id.DialogProgressTitle)
    TextView DialogProgressTitle;


    public DialogProgress(
            Context context,
            String title,
            ActivitySendPhoneNumberViewModel activitySendPhoneNumberViewModel) {//__________________ Start DialogProgress
        Title = title;
        this.context = context;
        this.activitySendPhoneNumberViewModel = activitySendPhoneNumberViewModel;
    }//_____________________________________________________________________________________________ End DialogProgress


    public DialogProgress(
            Context context,
            String title,
            ActivityBeforLoginViewModel activityBeforLoginViewModel) {//____________________________ Start DialogProgress
        this.context = context;
        Title = title;
        this.activityBeforLoginViewModel = activityBeforLoginViewModel;
    }//_____________________________________________________________________________________________ End DialogProgress


    public DialogProgress(
            Context context,
            String title,
            SplashActivityViewModel splashActivityViewModel) {//____________________________ Start DialogProgress
        this.context = context;
        Title = title;
        this.splashActivityViewModel = splashActivityViewModel;
    }//_____________________________________________________________________________________________ End DialogProgress


    public Dialog onCreateDialog(Bundle savedInstanceState) {//_____________________________________ Start onCreateDialog
        View view = null;
        DialogProgressBinding binding = DataBindingUtil
                .inflate(LayoutInflater
                                .from(this.context),
                        R.layout.dialog_progress,
                        null,
                        false);

        if (activitySendPhoneNumberViewModel != null)
            binding.setSendphonenumber(activitySendPhoneNumberViewModel);
        else if (activityBeforLoginViewModel != null)
            binding.setBeforlogin(activityBeforLoginViewModel);
        else if (splashActivityViewModel != null)
            binding.setSplash(splashActivityViewModel);

        view = binding.getRoot();
        ButterKnife.bind(this, view);
        DialogProgressTitle.setText(Title);
        DialogProgressIgnor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String str = "CancelByUser";
                if (activitySendPhoneNumberViewModel != null)
                    activitySendPhoneNumberViewModel.Observables.onNext(str);
                else if (activityBeforLoginViewModel != null)
                    activityBeforLoginViewModel.Observables.onNext(str);
                else if (splashActivityViewModel != null)
                    splashActivityViewModel.Observables.onNext(str);
            }
        });
        return new AlertDialog.Builder(context).setView(view).create();
    }//_____________________________________________________________________________________________ End onCreateDialog

}
