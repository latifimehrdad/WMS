package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentFragmentLoginBinding;

import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Login;
import com.ngra.wms.views.activitys.MainActivity;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;


import static com.ngra.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class Login extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {

    private NavController navController;
    private VM_Login vm_login;
    private boolean passVisible;


    @BindView(R.id.LoginClick)
    RelativeLayout LoginClick;

    @BindView(R.id.SignUpClick)
    LinearLayout SignUpClick;

    @BindView(R.id.ImgPassVisible)
    ImageView ImgPassVisible;

    @BindView(R.id.EditPhoneNumber)
    EditText EditPhoneNumber;


    @BindView(R.id.ForgetPassword)
    TextView ForgetPassword;

    @BindView(R.id.txtLoading)
    TextView txtLoading;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.imgLoading)
    ImageView imgLoading;


    //______________________________________________________________________________________________ Login
    public Login() {
    }
    //______________________________________________________________________________________________ Login


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        if (getView() == null) {
            FragmentFragmentLoginBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_fragment_login, container, false
            );
            vm_login = new VM_Login(getContext());
            binding.setLogin(vm_login);
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
                Login.this,
                vm_login.getPublishSubject(),
                vm_login);
        if (getView() != null)
            navController = Navigation.findNavController(getView());

/*        if (MainActivity.ReferenceCode != null) {
            Bundle bundle = new Bundle();
            bundle.putString(getContext().getString(R.string.ML_Type), getContext().getString(R.string.ML_SingUp));
            bundle.putString(getContext().getString(R.string.ML_PhoneNumber), EditPhoneNumber.getText().toString());
            navController.navigate(R.id.action_login_to_signUp, bundle);
        }*/

    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        dismissLoading();

        if (action.equals(StaticValues.ML_Success)) {
            if (getContext() != null) {
                Bundle bundle = new Bundle();
                bundle.putString(getContext().getString(R.string.ML_PhoneNumber), EditPhoneNumber.getText().toString());
                bundle.putString(getContext().getString(R.string.ML_Type), getContext().getResources().getString(R.string.ML_Login));
                navController
                        .navigate(R.id.action_login_to_verifyCode, bundle);
            }
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ init
    private void init() {
        passVisible = false;
        dismissLoading();
        setOnclick();
        setTextWatcher();
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ setOnclick
    private void setOnclick() {

        ForgetPassword.setOnClickListener(v -> {

        });

        LoginClick.setOnClickListener(v -> {

            if (checkEmpty()) {
                showLoading();
                hideKeyboard();
                vm_login.getLoginCode(
                        EditPhoneNumber.getText().toString());
            }
        });


        SignUpClick.setOnClickListener(v -> {
            if (getContext() != null) {
                Bundle bundle = new Bundle();
                bundle.putString(getContext().getString(R.string.ML_Type), getContext().getString(R.string.ML_SingUp));
                bundle.putString(getContext().getString(R.string.ML_PhoneNumber), EditPhoneNumber.getText().toString());
                navController.navigate(R.id.action_login_to_signUp, bundle);
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
            /*EditPassword.setSelection(EditPassword.getText().length());*/
        });


    }
    //______________________________________________________________________________________________ setOnclick


    //______________________________________________________________________________________________ dismissLoading
    private void dismissLoading() {
        txtLoading.setText(getResources().getString(R.string.LogIn));
        LoginClick.setBackground(getResources().getDrawable(R.drawable.save_info_button));
        gifLoading.setVisibility(View.GONE);
        imgLoading.setVisibility(View.VISIBLE);
    }
    //______________________________________________________________________________________________ dismissLoading


    //______________________________________________________________________________________________ showLoading
    private void showLoading() {
        txtLoading.setText(getResources().getString(R.string.Cancel));
        LoginClick.setBackground(getResources().getDrawable(R.drawable.button_red));
        gifLoading.setVisibility(View.VISIBLE);
        imgLoading.setVisibility(View.INVISIBLE);
    }
    //______________________________________________________________________________________________ showLoading


    //______________________________________________________________________________________________ setTextWatcher
    private void setTextWatcher() {
        EditPhoneNumber.setBackgroundResource(R.drawable.dw_edit_back);
        EditPhoneNumber.addTextChangedListener(TextChangeForChangeBack(EditPhoneNumber));
    }
    //______________________________________________________________________________________________ setTextWatcher


    //______________________________________________________________________________________________ checkEmpty
    private Boolean checkEmpty() {

        boolean phone;


        if (EditPhoneNumber.getText().length() != 11) {
            EditPhoneNumber.setBackgroundResource(R.drawable.dw_edit_back_empty);
            EditPhoneNumber.setError(getResources().getString(R.string.EmptyPhoneNumber));
            EditPhoneNumber.requestFocus();
            phone = false;
        } else {
            String ZeroNine = EditPhoneNumber.getText().subSequence(0, 2).toString();
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
