package com.example.wms.views.fragments.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;

import com.example.wms.BuildConfig;
import com.example.wms.R;
import com.example.wms.databinding.FragmentUpdateBinding;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.main.VM_Update;
import com.example.wms.views.fragments.FragmentPrimary;
import com.example.wms.views.fragments.user.login.Splash;

import java.io.File;

import butterknife.BindView;

public class AppUpdate extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable,
        VM_Update.ProgressDownload {


    private VM_Update vm_update;

    @BindView(R.id.TextViewProgress)
    TextView TextViewProgress;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.ImageViewDownload)
    ImageView ImageViewDownload;

    @BindView(R.id.ButtonInstall)
    Button ButtonInstall;


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {//________________________________________________ onCreateView
        if (getView() == null) {
            vm_update = new VM_Update(getContext(), AppUpdate.this);
            FragmentUpdateBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_update, container, false);
            binding.setUpdate(vm_update);
            setView(binding.getRoot());
            TextViewProgress.setText(getContext().getResources().getString(R.string.PleaseWait));
            progressBar.setProgress(0);
            ButtonInstall.setVisibility(View.GONE);
            SetOnClick();
            init();

        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                AppUpdate.this,
                vm_update.getPublishSubject(),
                vm_update);
    }//_____________________________________________________________________________________________ onStart



    private void init() {//_________________________________________________________________________ init
//        String url = getArguments().getString(getContext().getResources().getString(R.string.ML_UpdateUrl), "");
//        String fileName = getArguments().getString(getContext().getResources().getString(R.string.ML_UpdateFile), "");

//        if (!url.equalsIgnoreCase(""))
//            if (!fileName.equalsIgnoreCase(""))
                vm_update.DownloadFile("https://upinja.com/up/Agaring.apk", "Agaring.apk");

    }//_____________________________________________________________________________________________ init

    private void SetOnClick() {//___________________________________________________________________ SetOnClick
        ButtonInstall.setOnClickListener(v -> {

            Uri uri = vm_update.getTempUri("Agaring.apk");

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //dont forget add this line
            getContext().startActivity(intent);
        });
    }//_____________________________________________________________________________________________ SetOnClick


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ getMessageFromObservable

        if (action.equals(StaticValues.ML_Success)) {
            TextViewProgress.setText(getContext().getResources().getString(R.string.DownloadingFile));
            //ImageViewDownload.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
            return;
        }

        if (action.equals(StaticValues.ML_FileDownloading)) {
            progressBar.setProgress(0);
            TextViewProgress.setText(getContext().getResources().getString(R.string.FileDownloaded));
            return;
        }

        if (action.equals(StaticValues.ML_FileDownloaded)) {
            progressBar.setProgress(0);
            ImageViewDownload.setAnimation(null);
            ButtonInstall.setVisibility(View.VISIBLE);
            ImageViewDownload.setVisibility(View.GONE);
            TextViewProgress.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            TextViewProgress.setText(getContext().getResources().getString(R.string.FileDownloaded));
            return;
        }



    }//_____________________________________________________________________________________________ getMessageFromObservable


    @Override
    public void onProgress(int progress) {//________________________________________________________ onProgress
        progressBar.setProgress(progress);
        TextViewProgress.setText(progress + " %");
    }//_____________________________________________________________________________________________ onProgress




}
