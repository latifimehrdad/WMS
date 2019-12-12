package com.example.wms.views.activitys.user.register;

import android.content.Intent;
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
                                switch (s) {
                                    case "Successful":
                                        if(progress != null)
                                            progress.dismiss();
                                        Intent intent = new Intent(
                                                ActivitySendPhoneNumber.this,
                                                ActivityVerifyCode.class);
                                        intent.putExtra("PhoneNumber", EditPhoneNumber.getText());
                                        intent.putExtra("Password", EditPassword.getText());
                                        startActivity(intent);
                                        break;
                                    case "CancelByUser":
                                        if(progress != null)
                                            progress.dismiss();
                                        break;
                                    case "Failure":

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
        progress.show(getSupportFragmentManager(),NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ End ShowProgressDialog



    @Override
    public void onBackPressed() {//_________________________________________________________________ Start onBackPressed
        super.onBackPressed();
//        moveTaskToBack(true);
//        System.exit(0);
    }//_____________________________________________________________________________________________ End onBackPressed


}
