package com.example.wms.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.wms.R;
import com.example.wms.databinding.DialogProgressBinding;
import com.example.wms.viewmodels.login.ActivitySendPhoneNumberViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogProgress extends DialogFragment {

    private String Title;
    private Context context;
    private ActivitySendPhoneNumberViewModel activitySendPhoneNumberViewModel;

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


    public Dialog onCreateDialog(Bundle savedInstanceState) {//_____________________________________ Start onCreateDialog
        View view = null;
        if (activitySendPhoneNumberViewModel != null) {
            DialogProgressBinding binding = DataBindingUtil
                    .inflate(LayoutInflater
                                    .from(this.context),
                            R.layout.dialog_progress,
                            null,
                            false);

            binding.setSendphonenumber(activitySendPhoneNumberViewModel);
            view = binding.getRoot();
        }

        ButterKnife.bind(this, view);
        DialogProgressTitle.setText(Title);
        DialogProgressIgnor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String str = "CancelByUser";
                if (activitySendPhoneNumberViewModel != null) {
                    activitySendPhoneNumberViewModel.Observables.onNext(str);
                }
            }
        });
        return new AlertDialog.Builder(context).setView(view).create();
    }//_____________________________________________________________________________________________ End onCreateDialog


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    @Nullable
    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ActivitySendPhoneNumberViewModel getActivitySendPhoneNumberViewModel() {
        return activitySendPhoneNumberViewModel;
    }

    public void setActivitySendPhoneNumberViewModel(ActivitySendPhoneNumberViewModel activitySendPhoneNumberViewModel) {
        this.activitySendPhoneNumberViewModel = activitySendPhoneNumberViewModel;
    }
}
