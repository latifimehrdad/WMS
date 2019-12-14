package com.example.wms.views.activitys.user.register;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;

import com.example.wms.R;
import com.example.wms.databinding.ActivitySendPhoneNumberBinding;
import com.example.wms.viewmodels.user.register.ActivitySendPhoneNumberViewModel;
import com.example.wms.views.activitys.SplashActivity;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.dialogs.DialogMessage;
import com.example.wms.views.dialogs.DialogProgress;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class ActivitySendPhoneNumber extends AppCompatActivity {

    private ActivitySendPhoneNumberViewModel activitySendPhoneNumberViewModel;
    private boolean passVisible;
    private boolean passconfirmVisible;
    private DialogProgress progress;
    private String PhoneNumber;
    private String Password;
    private String Type;

    @BindView(R.id.btnGetVerifyCode)
    Button btnGetVerifyCode;

    @BindView(R.id.EditPhoneNumber)
    EditText EditPhoneNumber;

    @BindView(R.id.EditPassword)
    EditText EditPassword;

    @BindView(R.id.EditPasswordConfirm)
    EditText EditPasswordConfirm;

    @BindView(R.id.ImgPassVisible)
    ImageView ImgPassVisible;

    @BindView(R.id.ImgPassConfVisible)
    ImageView ImgPassConfVisible;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {//________________________________ Start onCreate
        super.onCreate(savedInstanceState);
        ActivitySendPhoneNumberBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_send_phone_number
        );
        activitySendPhoneNumberViewModel = new ActivitySendPhoneNumberViewModel(this);
        binding.setPhonenumber(activitySendPhoneNumberViewModel);
        ButterKnife.bind(this);
        SetClick();
        SetTextWatcher();
        ObserverObservables();
        init();
    }//_____________________________________________________________________________________________ End onCreate


    private void init() {//_________________________________________________________________________ Start init
        EditPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passVisible = false;

        EditPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passconfirmVisible = false;

        Bundle extra = getIntent().getExtras();
        PhoneNumber = extra.getString("PhoneNumber");
        Password = extra.getString("Password");
        Type = extra.getString("type");

    }//_____________________________________________________________________________________________ End init


    private void ObserverObservables() {//__________________________________________________________ Start ObserverObservables
        activitySendPhoneNumberViewModel
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
                                    case "Successful":
                                        Intent intent = new Intent(
                                                ActivitySendPhoneNumber.this,
                                                ActivityVerifyCode.class);
                                        intent.putExtra("PhoneNumber", EditPhoneNumber.getText().toString());
                                        intent.putExtra("Password", EditPassword.getText().toString());
                                        startActivity(intent);
                                        finish();
                                        break;
                                    case "Failure":
                                        ShowMessage(getResources().getString(R.string.NetworkError),
                                                getResources().getColor(R.color.mlWhite),
                                                getResources().getDrawable(R.drawable.ic_error));
                                        EditPhoneNumber.requestFocus();
                                        break;
                                    case "Error":
                                        ShowMessage(activitySendPhoneNumberViewModel.getMessageResponse(),
                                                getResources().getColor(R.color.mlWhite),
                                                getResources().getDrawable(R.drawable.ic_error));
                                        break;
                                    default:
                                        ShowMessage(s,getResources().getColor(R.color.mlWhite),
                                                getResources().getDrawable(R.drawable.ic_error));
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


    private void SetTextWatcher() {//_______________________________________________________________ Start SetTextWatcher
        EditPhoneNumber.setBackgroundResource(R.drawable.edit_normal_background);
        EditPhoneNumber.addTextChangedListener(TextChangeForChangeBack(EditPhoneNumber));

        EditPassword.setBackgroundResource(R.drawable.edit_normal_background);
        EditPassword.addTextChangedListener(TextChangeForChangeBack(EditPassword));

        EditPasswordConfirm.setBackgroundResource(R.drawable.edit_normal_background);
        EditPasswordConfirm.addTextChangedListener(TextChangeForChangeBack(EditPasswordConfirm));

    }//_____________________________________________________________________________________________ End SetTextWatcher


    private void SetClick() {//_____________________________________________________________________ Start SetClick

        btnGetVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckEmpty()) {
                    ShowProgressDialog();
                    activitySendPhoneNumberViewModel.SendNumber(
                            EditPhoneNumber.getText().toString(),
                            EditPassword.getText().toString()
                    );
                }

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


        ImgPassConfVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!passconfirmVisible) {
                    EditPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ImgPassConfVisible.setImageResource(R.drawable.ic_visibility_off);
                    passconfirmVisible = true;
                    EditPasswordConfirm.setSelection(EditPasswordConfirm.getText().length());

                } else {
                    EditPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ImgPassConfVisible.setImageResource(R.drawable.ic_visibility);
                    passconfirmVisible = false;
                    EditPasswordConfirm.setSelection(EditPasswordConfirm.getText().length());
                }
            }
        });


    }//_____________________________________________________________________________________________ End SetClick


    private Boolean CheckEmpty() {//________________________________________________________________ Start CheckEmpty

        boolean phone = false;
        boolean pass = false;
        boolean passconf = false;

        if (!EditPasswordConfirm.getText().toString().equalsIgnoreCase(EditPassword.getText().toString())) {
            EditPassword.setText("");
            EditPasswordConfirm.setText("");
            EditPasswordConfirm.setBackgroundResource(R.drawable.edit_empty_background);
            EditPasswordConfirm.setError(getResources().getString(R.string.EmptyPasswordConfirm));
            EditPasswordConfirm.requestFocus();
            EditPassword.setBackgroundResource(R.drawable.edit_empty_background);
            EditPassword.setError(getResources().getString(R.string.EmptyPasswordConfirm));
            EditPassword.requestFocus();

            passconf = false;
        } else
            passconf = true;


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


        if (phone && pass && passconf)
            return true;
        else
            return false;

    }//_____________________________________________________________________________________________ End CheckEmpty


    private void ShowProgressDialog() {//___________________________________________________________ Start ShowProgressDialog
        progress = new DialogProgress(ActivitySendPhoneNumber.this,
                null, activitySendPhoneNumberViewModel);
        progress.setCancelable(false);
        progress.show(getSupportFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ End ShowProgressDialog



    private void ShowMessage(String message, int color, Drawable icon) {//__________________________ Start ShowMessage

        DialogMessage dialogMessage = new DialogMessage(ActivitySendPhoneNumber.this,message,color,icon);
        dialogMessage.setCancelable(false);
        dialogMessage.show(getSupportFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);

    }//_____________________________________________________________________________________________ End ShowMessage


    @Override
    public void onBackPressed() {//_________________________________________________________________ Start onBackPressed
        super.onBackPressed();
//        moveTaskToBack(true);
//        System.exit(0);
    }//_____________________________________________________________________________________________ End onBackPressed


}
