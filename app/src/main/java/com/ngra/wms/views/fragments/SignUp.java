package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentSignupBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_SignUp;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class SignUp extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

    private NavController navController;
    private boolean passVisible;
    private boolean passConfirmVisible;
    private VM_SignUp vm_signUp;


    @BindView(R.id.btnGetVerifyCode)
    RelativeLayout btnGetVerifyCode;

    @BindView(R.id.EditPhoneNumber)
    EditText EditPhoneNumber;


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

    @BindView(R.id.EditTextReagentCode)
    EditText EditTextReagentCode;


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            FragmentSignupBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_signup, container, false);
            vm_signUp = new VM_SignUp(getContext());
            binding.setSignup(vm_signUp);
            setView(binding.getRoot());
            init();
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                SignUp.this,
                vm_signUp.getPublishSubject(),
                vm_signUp);
        if (getView() != null)
            navController = Navigation.findNavController(getView());
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ init
    private void init() {
        setOnclick();
        setTextWatcher();
        passVisible = false;
        passConfirmVisible = false;
        Bundle bundle = getArguments();
        if (bundle != null && getContext() != null) {
            String phoneNumber = bundle.getString(getContext().getString(R.string.ML_PhoneNumber));
            vm_signUp.setPhoneNumber(phoneNumber);
        }
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ getMessageFromObservable
    @Override
    public void getActionFromObservable(Byte action) {


        dismissLoading();
        if (action.equals(StaticValues.ML_Success)) {
            if (getContext() != null) {
                Bundle bundle = new Bundle();
                bundle.putString(getContext().getString(R.string.ML_PhoneNumber), vm_signUp.getPhoneNumber());
                bundle.putString(getContext().getString(R.string.ML_Type), getContext().getResources().getString(R.string.ML_SingUp));
                /*bundle.putString(getContext().getString(R.string.ML_Password), EditPassword.getText().toString());*/
                navController
                        .navigate(R.id.action_signUp_to_verifyCode, bundle);
            }
        }

    }
    //______________________________________________________________________________________________ getMessageFromObservable


    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest


    //______________________________________________________________________________________________ setTextWatcher
    private void setTextWatcher() {
        EditPhoneNumber.setBackgroundResource(R.drawable.dw_edit_back);
        EditPhoneNumber.addTextChangedListener(textChangeForChangeBack(EditPhoneNumber));
    }
    //______________________________________________________________________________________________ setTextWatcher


    //______________________________________________________________________________________________ setOnclick
    private void setOnclick() {

        btnGetVerifyCode.setOnClickListener(v -> {
            if (checkEmpty()) {
                hideKeyboard();
                showLoading();
                vm_signUp.sendNumber();
            }

        });


        ImgPassVisible.setOnClickListener(v -> {
            if (!passVisible) {
/*                EditPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);*/
                ImgPassVisible.setImageResource(R.drawable.svg_hide_password);
                passVisible = true;

            } else {
/*                EditPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);*/
                ImgPassVisible.setImageResource(R.drawable.svg_password_visible);
                passVisible = false;
            }
            /*            EditPassword.setSelection(EditPassword.getText().length());*/
        });


        ImgPassConfVisible.setOnClickListener(v -> {
            if (!passConfirmVisible) {
/*                EditPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);*/
                ImgPassConfVisible.setImageResource(R.drawable.svg_hide_password);
                passConfirmVisible = true;

            } else {
/*                EditPasswordConfirm.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD)*/
                ;
                ImgPassConfVisible.setImageResource(R.drawable.svg_password_visible);
                passConfirmVisible = false;
            }
            /*            EditPasswordConfirm.setSelection(EditPasswordConfirm.getText().length());*/
        });

    }
    //______________________________________________________________________________________________ setOnclick


    //______________________________________________________________________________________________ dismissLoading
    private void dismissLoading() {
        txtLoading.setText(getResources().getString(R.string.GetCode));
        btnGetVerifyCode.setBackground(getResources().getDrawable(R.drawable.save_info_button));
        gifLoading.setVisibility(View.GONE);
        imgLoading.setVisibility(View.VISIBLE);
    }
    //______________________________________________________________________________________________ dismissLoading


    //______________________________________________________________________________________________ showLoading
    private void showLoading() {
        txtLoading.setText(getResources().getString(R.string.Cancel));
        btnGetVerifyCode.setBackground(getResources().getDrawable(R.drawable.button_red));
        gifLoading.setVisibility(View.VISIBLE);
        imgLoading.setVisibility(View.INVISIBLE);
    }
    //______________________________________________________________________________________________ showLoading


    //______________________________________________________________________________________________ checkEmpty
    private Boolean checkEmpty() {

        boolean phone;


        if (vm_signUp.getPhoneNumber().length() != 11) {
            EditPhoneNumber.setBackgroundResource(R.drawable.dw_edit_back_empty);
            EditPhoneNumber.setError(getResources().getString(R.string.EmptyPhoneNumber));
            EditPhoneNumber.requestFocus();
            phone = false;
        } else {
            String ZeroNine = vm_signUp.getPhoneNumber().subSequence(0, 2).toString();
            if (ZeroNine.equalsIgnoreCase("09"))
                phone = true;
            else {
                EditPhoneNumber.setBackgroundResource(R.drawable.dw_edit_back_empty);
                EditPhoneNumber.setError(getResources().getString(R.string.EmptyPhoneNumber));
                EditPhoneNumber.requestFocus();
                phone = false;
            }
        }


        return phone;

    }
    //______________________________________________________________________________________________ checkEmpty


}
