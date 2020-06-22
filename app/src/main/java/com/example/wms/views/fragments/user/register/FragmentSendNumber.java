package com.example.wms.views.fragments.user.register;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cunoraz.gifview.library.GifView;
import com.example.wms.R;
import com.example.wms.databinding.FragmentSendNumberBinding;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.user.register.VM_FragmentSendNumber;
import com.example.wms.views.application.ApplicationWMS;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.wms.utility.StaticFunctions.TextChangeForChangeBack;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSendNumber extends Fragment {

    private Context context;
    private VM_FragmentSendNumber vm_fragmentSendNumber;
    private NavController navController;
    private View view;
    private DisposableObserver<Byte> observer;
    private boolean passVisible;
    private boolean passConfirmVisible;
    private String PhoneNumber;
    private String Type;

    @BindView(R.id.btnGetVerifyCode)
    RelativeLayout btnGetVerifyCode;

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

    @BindView(R.id.txtLoading)
    TextView txtLoading;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.imgLoading)
    ImageView imgLoading;

    public FragmentSendNumber() {//_____________________________________________________________________ Start FragmentSplash
        // Required empty public constructor
    }//_____________________________________________________________________________________________ End FragmentSplash


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ Start onCreateView
        if (view == null) {
            this.context = getActivity();
            vm_fragmentSendNumber = new VM_FragmentSendNumber(context);
            FragmentSendNumberBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_send_number, container, false
            );
            binding.setPhonenumber(vm_fragmentSendNumber);
            view = binding.getRoot();
            ButterKnife.bind(this, view);
            DismissLoading();
            init();
        }
        return view;
    }//_____________________________________________________________________________________________ End onCreateView



    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        navController = Navigation.findNavController(view);
        if(observer != null)
            observer.dispose();
        observer = null;
        ObserverObservables();
        DismissLoading();
    }//_____________________________________________________________________________________________ End onStart



    private void init() {//_________________________________________________________________________ Start init
        SetClick();
        SetTextWatcher();
        EditPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passVisible = false;
        EditPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passConfirmVisible = false;
        Bundle bundle = getArguments();
        PhoneNumber = bundle.getString(context.getString(R.string.ML_PhoneNumber));
        Type = bundle.getString(context.getString(R.string.ML_Type));
        EditPhoneNumber.setText(PhoneNumber);
    }//_____________________________________________________________________________________________ End init




    private void ObserverObservables() {//__________________________________________________________ Start ObserverObservables

        observer = new DisposableObserver<Byte>() {
            @Override
            public void onNext(Byte s) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DismissLoading();
                        HandleAction(s);
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

        vm_fragmentSendNumber
                .getObservables()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }//_____________________________________________________________________________________________ End ObserverObservables




    private void HandleAction(Byte action) {//______________________________________________________ HandleAction
        DismissLoading();
        if (action == StaticValues.ML_Success) {
            Bundle bundle = new Bundle();
            bundle.putString(context.getString(R.string.ML_PhoneNumber), EditPhoneNumber.getText().toString());
            bundle.putString(context.getString(R.string.ML_Password),EditPassword.getText().toString());
            navController
                    .navigate(R.id.action_fragmentSendNumber_to_fragmentVerifyCode, bundle);
        } else if (action == StaticValues.ML_ResponseFailure) {
            ShowMessage(getResources().getString(R.string.NetworkError),
                    getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error));
            EditPhoneNumber.requestFocus();
        } else if (action == StaticValues.ML_ResponseError) {
            ShowMessage(vm_fragmentSendNumber.getMessageResponse(),
                    getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error));
        }
    }//_____________________________________________________________________________________________ HandleAction



    private void SetClick() {//_____________________________________________________________________ Start SetClick

        btnGetVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StaticFunctions.isCancel)
                    return;
                if (CheckEmpty()) {
                    ShowLoading();
                    vm_fragmentSendNumber.SendNumber(
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
                if (!passConfirmVisible) {
                    EditPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ImgPassConfVisible.setImageResource(R.drawable.ic_visibility_off);
                    passConfirmVisible = true;

                } else {
                    EditPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ImgPassConfVisible.setImageResource(R.drawable.ic_visibility);
                    passConfirmVisible = false;
                }
                EditPasswordConfirm.setSelection(EditPasswordConfirm.getText().length());
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


    private void SetTextWatcher() {//_______________________________________________________________ Start SetTextWatcher
        EditPhoneNumber.setBackgroundResource(R.drawable.edit_normal_background);
        EditPhoneNumber.addTextChangedListener(TextChangeForChangeBack(EditPhoneNumber));

        EditPassword.setBackgroundResource(R.drawable.edit_normal_background);
        EditPassword.addTextChangedListener(TextChangeForChangeBack(EditPassword));

        EditPasswordConfirm.setBackgroundResource(R.drawable.edit_normal_background);
        EditPasswordConfirm.addTextChangedListener(TextChangeForChangeBack(EditPasswordConfirm));
    }//_____________________________________________________________________________________________ End SetTextWatcher


    private void ShowMessage(String message, int color, Drawable icon) {//__________________________ Start ShowMessage

        ApplicationWMS
                .getApplicationWMS(context)
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowMessage(context,message,color,icon,getFragmentManager());

    }//_____________________________________________________________________________________________ End ShowMessage


    private void DismissLoading() {//_______________________________________________________________ Start DismissLoading
        StaticFunctions.isCancel = true;
        txtLoading.setText(getResources().getString(R.string.GetCode));
        btnGetVerifyCode.setBackground(getResources().getDrawable(R.drawable.save_info_button));
        gifLoading.setVisibility(View.GONE);
        imgLoading.setVisibility(View.VISIBLE);
    }//_____________________________________________________________________________________________ End DismissLoading



    private void ShowLoading() {//__________________________________________________________________ Start ShowLoading
        StaticFunctions.isCancel = false;
        txtLoading.setText(getResources().getString(R.string.Cancel));
        btnGetVerifyCode.setBackground(getResources().getDrawable(R.drawable.button_red));
        gifLoading.setVisibility(View.VISIBLE);
        imgLoading.setVisibility(View.INVISIBLE);
    }//_____________________________________________________________________________________________ End ShowLoading


    @Override
    public void onDestroy() {//_____________________________________________________________________ Start onDestroy
        super.onDestroy();
        if(observer != null)
            observer.dispose();
        observer = null;
    }//_____________________________________________________________________________________________ End onDestroy



    @Override
    public void onStop() {//________________________________________________________________________ onStop
        super.onStop();
        if (observer != null)
            observer.dispose();
        observer = null;
    }//_____________________________________________________________________________________________ onStop


}
