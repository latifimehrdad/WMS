package com.example.wms.views.activitys.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.ActivitySendPhoneNumberBinding;
import com.example.wms.viewmodels.login.ActivitySendPhoneNumberViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivitySendPhoneNumber extends AppCompatActivity {

    private ActivitySendPhoneNumberViewModel activitySendPhoneNumberViewModel;

    @BindView(R.id.btnGetVerifyCode)
    Button btnGetVerifyCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySendPhoneNumberBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_send_phone_number
        );
        activitySendPhoneNumberViewModel = new ActivitySendPhoneNumberViewModel(this);
        binding.setPhonenumber(activitySendPhoneNumberViewModel);
        ButterKnife.bind(this);
        SetClick();
    }//_____________________________________________________________________________________________ End onCreateView



    private void SetClick() {//_____________________________________________________________________ Start SetClick

        btnGetVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySendPhoneNumber.this, ActivityVerifyCode.class);
                startActivity(intent);
            }
        });

    }//_____________________________________________________________________________________________ End SetClick

}
