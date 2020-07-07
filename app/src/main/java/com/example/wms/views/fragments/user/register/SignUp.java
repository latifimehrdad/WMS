package com.example.wms.views.fragments.user.register;

import android.os.Bundle;
import android.text.InputType;
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
import com.example.wms.R;
import com.example.wms.databinding.FragmentSendNumberBinding;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.user.register.VM_SignUp;
import com.example.wms.views.fragments.FragmentPrimary;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class SignUp extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private NavController navController;
    private boolean passVisible;
    private boolean passConfirmVisible;
    private String PhoneNumber;
    private String Type;
    private VM_SignUp vm_signUp;


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


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentSendNumberBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_send_number, container, false);
            vm_signUp = new VM_SignUp(getContext());
            binding.setSignup(vm_signUp);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            init();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                SignUp.this,
                vm_signUp.getPublishSubject(),
                vm_signUp);
        navController = Navigation.findNavController(getView());
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ Start init
        SetOnclick();
        SetTextWatcher();
        passVisible = false;
        passConfirmVisible = false;
        Bundle bundle = getArguments();
        PhoneNumber = bundle.getString(getContext().getString(R.string.ML_PhoneNumber));
        Type = bundle.getString(getContext().getString(R.string.ML_Type));
        EditPhoneNumber.setText(PhoneNumber);
    }//_____________________________________________________________________________________________ End init


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable


        DismissLoading();
        if (action == StaticValues.ML_Success) {
            Bundle bundle = new Bundle();
            bundle.putString(getContext().getString(R.string.ML_PhoneNumber), EditPhoneNumber.getText().toString());
            bundle.putString(getContext().getString(R.string.ML_Password), EditPassword.getText().toString());
            navController
                    .navigate(R.id.action_signUp_to_verifyCode, bundle);
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetTextWatcher() {//_______________________________________________________________ Start SetTextWatcher
        EditPhoneNumber.setBackgroundResource(R.drawable.edit_normal_background);
        EditPhoneNumber.addTextChangedListener(TextChangeForChangeBack(EditPhoneNumber));

        EditPassword.setBackgroundResource(R.drawable.edit_normal_background);
        EditPassword.addTextChangedListener(TextChangeForChangeBack(EditPassword));

        EditPasswordConfirm.setBackgroundResource(R.drawable.edit_normal_background);
        EditPasswordConfirm.addTextChangedListener(TextChangeForChangeBack(EditPasswordConfirm));
    }//_____________________________________________________________________________________________ End SetTextWatcher


    private void SetOnclick() {//___________________________________________________________________ SetOnclick

        btnGetVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAccessClick()) {
                    if (CheckEmpty()) {
                        ShowLoading();
                        setAccessClick(false);
                        vm_signUp.setPhoneNumber(EditPhoneNumber.getText().toString());
                        vm_signUp.setPassword(EditPassword.getText().toString());
                        vm_signUp.SendNumber();
                    }
                } else
                    vm_signUp.CancelRequest();
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

    }//_____________________________________________________________________________________________ SetOnclick


    private void DismissLoading() {//_______________________________________________________________ Start DismissLoading
        txtLoading.setText(getResources().getString(R.string.GetCode));
        btnGetVerifyCode.setBackground(getResources().getDrawable(R.drawable.save_info_button));
        gifLoading.setVisibility(View.GONE);
        imgLoading.setVisibility(View.VISIBLE);
    }//_____________________________________________________________________________________________ End DismissLoading


    private void ShowLoading() {//__________________________________________________________________ Start ShowLoading
        txtLoading.setText(getResources().getString(R.string.Cancel));
        btnGetVerifyCode.setBackground(getResources().getDrawable(R.drawable.button_red));
        gifLoading.setVisibility(View.VISIBLE);
        imgLoading.setVisibility(View.INVISIBLE);
    }//_____________________________________________________________________________________________ End ShowLoading


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


}