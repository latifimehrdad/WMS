package com.example.wms.views.activitys.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.ActivityBeforLoginBinding;
import com.example.wms.viewmodels.login.ActivityBeforLoginViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ActivityBeforLogin extends AppCompatActivity {

    private Context context;
    private ActivityBeforLoginViewModel activityBeforLoginViewModel;

    @BindView(R.id.LoginClick)
    LinearLayout LoginClick;

    @BindView(R.id.SignUpClick)
    LinearLayout SignUpClick;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBeforLoginBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_befor_login
        );
        activityBeforLoginViewModel = new ActivityBeforLoginViewModel(context);
        binding.setBeforlogin(activityBeforLoginViewModel);
        ButterKnife.bind(this);
        SetClick();
    }//_____________________________________________________________________________________________ End onCreate


    private void SetClick() {//_____________________________________________________________________ Start SetClick

        LoginClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        SignUpClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityBeforLogin.this, ActivitySendPhoneNumber.class);
                startActivity(intent);
            }
        });


    }//_____________________________________________________________________________________________ End SetClick


    public static View.OnKeyListener SetKey(View view) {//_________________________________________________ Start SetKey
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;

                if (keyCode != 4) {
                    return false;
                }
                return true;
            }
        };
    }//_____________________________________________________________________________________________ End SetKey


    public void attachBaseContext(Context newBase) {//______________________________________________ Start attachBaseContext
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }//_____________________________________________________________________________________________ End attachBaseContext


    @Override
    public void onBackPressed() {//_________________________________________________________________ Start onBackPressed
        super.onBackPressed();
        moveTaskToBack(true);
        System.exit(0);
    }//_____________________________________________________________________________________________ End onBackPressed


}
