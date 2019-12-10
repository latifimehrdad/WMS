package com.example.wms.views.activitys.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.ActivitySendPhoneNumberBinding;
import com.example.wms.viewmodels.login.ActivitySendPhoneNumberViewModel;

import butterknife.ButterKnife;

public class ActivitySendPhoneNumber extends AppCompatActivity {

    private ActivitySendPhoneNumberViewModel activitySendPhoneNumberViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySendPhoneNumberBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_send_phone_number
        );
        activitySendPhoneNumberViewModel = new ActivitySendPhoneNumberViewModel(this);
        binding.setPhonenumber(activitySendPhoneNumberViewModel);
        ButterKnife.bind(this);
    }//_____________________________________________________________________________________________ End onCreateView

}
