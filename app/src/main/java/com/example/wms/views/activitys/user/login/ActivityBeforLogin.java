package com.example.wms.views.activitys.user.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.ActivityBeforLoginBinding;
import com.example.wms.viewmodels.user.login.ActivityBeforLoginViewModel;
import com.example.wms.views.activitys.MainActivity;
import com.example.wms.views.activitys.SplashActivity;
import com.example.wms.views.activitys.user.register.ActivitySendPhoneNumber;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.dialogs.DialogMessage;
import com.example.wms.views.dialogs.DialogProgress;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class ActivityBeforLogin extends AppCompatActivity {


    private ActivityBeforLoginViewModel activityBeforLoginViewModel;
    private boolean passVisible;
    private DialogProgress progress;

    @BindView(R.id.LoginClick)
    LinearLayout LoginClick;

    @BindView(R.id.SignUpClick)
    LinearLayout SignUpClick;

    @BindView(R.id.ImgPassVisible)
    ImageView ImgPassVisible;

    @BindView(R.id.EditPhoneNumber)
    EditText EditPhoneNumber;

    @BindView(R.id.EditPassword)
    EditText EditPassword;

    @BindView(R.id.ForgetPassword)
    TextView ForgetPassword;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBeforLoginBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_befor_login
        );
        activityBeforLoginViewModel = new ActivityBeforLoginViewModel(this);
        binding.setBeforlogin(activityBeforLoginViewModel);
        ButterKnife.bind(this);
        SetTextWatcher();
        SetClick();
        init();
        ObserverObservables();
    }//_____________________________________________________________________________________________ End onCreate


    private void init() {//_________________________________________________________________________ Start init
        EditPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passVisible = false;
    }//_____________________________________________________________________________________________ End init


    private void SetClick() {//_____________________________________________________________________ Start SetClick

        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityBeforLogin.this, ActivitySendPhoneNumber.class);
                intent.putExtra("type", "forget");
                intent.putExtra("PhoneNumber", EditPhoneNumber.getText().toString());
                intent.putExtra("Password", EditPassword.getText().toString());
                startActivity(intent);
            }
        });

        LoginClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckEmpty()) {
                    ShowProgressDialog();
                    activityBeforLoginViewModel.GetLoginToken(
                            EditPhoneNumber.getText().toString(),
                            EditPassword.getText().toString()
                    );
                }
            }
        });

        SignUpClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityBeforLogin.this, ActivitySendPhoneNumber.class);
                intent.putExtra("type", "singup");
                intent.putExtra("PhoneNumber", EditPhoneNumber.getText().toString());
                intent.putExtra("Password", EditPassword.getText().toString());
                startActivity(intent);
            }
        });


        ImgPassVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!passVisible) {
                    EditPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ImgPassVisible.setImageResource(R.drawable.ic_visibility_off);
                    passVisible = true;
                    EditPassword.setSelection(EditPassword.getText().length());

                } else {
                    EditPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ImgPassVisible.setImageResource(R.drawable.ic_visibility);
                    passVisible = false;
                    EditPassword.setSelection(EditPassword.getText().length());
                }
            }
        });


    }//_____________________________________________________________________________________________ End SetClick


    private void ObserverObservables() {//__________________________________________________________ Start ObserverObservables
        activityBeforLoginViewModel
                .Observables
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (progress != null)
                                    progress.dismiss();
                                switch (s) {
                                    case "SuccessfulToken":
                                        activityBeforLoginViewModel.GetLoginInformation();
                                        break;
                                    case "Successful":
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                MainActivity.FragmentMessage.onNext("CheckProfile");
                                            }
                                        }, 100);
                                        finish();
                                        break;
                                    case "Error":
                                        ShowMessage(activityBeforLoginViewModel.getMessageResponse()
                                        ,getResources().getColor(R.color.mlWhite),
                                                getResources().getDrawable(R.drawable.ic_error));
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
                });

    }//_____________________________________________________________________________________________ End ObserverObservables


    private Boolean CheckEmpty() {//________________________________________________________________ Start CheckEmpty

        boolean phone = false;
        boolean pass = false;


        if (EditPassword.getText().length() < 6) {
            EditPassword.setBackgroundResource(R.drawable.edit_empty_background);
            EditPassword.setError(getResources().getString(R.string.EmptyPassword));
            EditPassword.requestFocus();
            pass = false;
        } else
            pass = true;


        if (EditPhoneNumber.getText().length() != 11) {
            EditPhoneNumber.setBackgroundResource(R.drawable.edit_empty_background);
            EditPhoneNumber.setError(getResources().getString(R.string.EmptyPhoneNumber));
            EditPhoneNumber.requestFocus();
            phone = false;
        } else {
            String ZeroNine = EditPhoneNumber.getText().subSequence(0, 2).toString();
            if (ZeroNine.equalsIgnoreCase("09"))
                phone = true;
            else {
                EditPhoneNumber.setBackgroundResource(R.drawable.edit_empty_background);
                EditPhoneNumber.setError(getResources().getString(R.string.EmptyPhoneNumber));
                EditPhoneNumber.requestFocus();
                phone = false;
            }
        }


        if (phone && pass)
            return true;
        else
            return false;

    }//_____________________________________________________________________________________________ End CheckEmpty


    private void ShowProgressDialog() {//___________________________________________________________ Start ShowProgressDialog
        progress = new DialogProgress(ActivityBeforLogin.this
                , null, activityBeforLoginViewModel);

        progress.setCancelable(false);
        progress.show(getSupportFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ End ShowProgressDialog


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


    private void SetTextWatcher() {//_______________________________________________________________ Start SetTextWatcher
        EditPhoneNumber.setBackgroundResource(R.drawable.edit_normal_background);
        EditPhoneNumber.addTextChangedListener(TextChangeForChangeBack(EditPhoneNumber));
        EditPassword.setBackgroundResource(R.drawable.edit_normal_background);
        EditPassword.addTextChangedListener(TextChangeForChangeBack(EditPassword));
    }//_____________________________________________________________________________________________ End SetTextWatcher


    public void attachBaseContext(Context newBase) {//______________________________________________ Start attachBaseContext
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }//_____________________________________________________________________________________________ End attachBaseContext


    private void ShowMessage(String message, int color, Drawable icon) {//__________________________ Start ShowMessage

        DialogMessage dialogMessage = new DialogMessage(ActivityBeforLogin.this,message,color,icon);
        dialogMessage.setCancelable(false);
        dialogMessage.show(getSupportFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);

    }//_____________________________________________________________________________________________ End ShowMessage



    @Override
    public void onBackPressed() {//_________________________________________________________________ Start onBackPressed
        super.onBackPressed();
        moveTaskToBack(true);
        System.exit(0);
    }//_____________________________________________________________________________________________ End onBackPressed


}
