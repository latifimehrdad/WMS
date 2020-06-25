package com.example.wms.views.fragments.user.login;

import android.os.Bundle;
import android.text.InputType;
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
import com.example.wms.R;
import com.example.wms.databinding.FragmentFragmentLoginBinding;

import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.user.login.VM_Login;
import com.example.wms.viewmodels.user.login.VM_Splash;
import com.example.wms.views.fragments.FragmentPrimary;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class Login extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

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

    @BindView(R.id.EditPassword)
    EditText EditPassword;

    @BindView(R.id.ForgetPassword)
    TextView ForgetPassword;

    @BindView(R.id.txtLoading)
    TextView txtLoading;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.imgLoading)
    ImageView imgLoading;

    public Login() {//______________________________________________________________________________ Login

    }//_____________________________________________________________________________________________ Login



    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentFragmentLoginBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_fragment_login, container, false
            );
            vm_login = new VM_Login(getContext());
            binding.setLogin(vm_login);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            init();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(Login.this, vm_login.getPublishSubject());
        navController = Navigation.findNavController(getView());
    }//_____________________________________________________________________________________________ onStart


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        setAccessClick(true);
        DismissLoading();
        if (action == StaticValues.ML_GoToHome) {
            navController.navigate(R.id.action_fragmentLogin_to_fragmentHome);
            return;
        }

        if (action == StaticValues.ML_ResponseFailure) {
            ShowMessage(getResources().getString(R.string.NetworkError),
                    getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error),
                    getResources().getColor(R.color.mlBlack));
            return;
        }

        if (action == StaticValues.ML_ResponseError) {
            ShowMessage(vm_login.getResponseMessage()
                    , getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error),
                    getResources().getColor(R.color.mlBlack));
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void init() {//_________________________________________________________________________ init
        passVisible = false;
        DismissLoading();
        SetOnclick();
        SetTextWatcher();
    }//_____________________________________________________________________________________________ init



    private void SetOnclick() {//___________________________________________________________________ SetOnclick

        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        LoginClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isAccessClick())
                    return;

                if (CheckEmpty()) {
                    setAccessClick(false);
                    ShowLoading();
                    StaticFunctions.hideKeyboard(getActivity());
                    vm_login.GetLoginToken(
                            EditPhoneNumber.getText().toString(),
                            EditPassword.getText().toString()
                    );
                }
            }
        });



        SignUpClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isAccessClick())
                    return;

                Bundle bundle = new Bundle();
                bundle.putString(getContext().getString(R.string.ML_Type), getContext().getString(R.string.ML_SingUp));
                bundle.putString(getContext().getString(R.string.ML_PhoneNumber), EditPhoneNumber.getText().toString());
                navController.navigate(R.id.action_fragmentLogin_to_fragmentSendNumber, bundle);
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
                } else {
                    EditPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ImgPassVisible.setImageResource(R.drawable.ic_visibility);
                    passVisible = false;
                }
                EditPassword.setSelection(EditPassword.getText().length());
            }
        });



    }//_____________________________________________________________________________________________ SetOnclick



    private void DismissLoading() {//_______________________________________________________________ DismissLoading
        txtLoading.setText(getResources().getString(R.string.LogIn));
        LoginClick.setBackground(getResources().getDrawable(R.drawable.save_info_button));
        gifLoading.setVisibility(View.GONE);
        imgLoading.setVisibility(View.VISIBLE);
    }//_____________________________________________________________________________________________ DismissLoading



    private void ShowLoading() {//__________________________________________________________________ ShowLoading
        txtLoading.setText(getResources().getString(R.string.Cancel));
        LoginClick.setBackground(getResources().getDrawable(R.drawable.button_red));
        gifLoading.setVisibility(View.VISIBLE);
        imgLoading.setVisibility(View.INVISIBLE);
    }//_____________________________________________________________________________________________ ShowLoading



    private void SetTextWatcher() {//_______________________________________________________________ SetTextWatcher
        EditPhoneNumber.setBackgroundResource(R.drawable.edit_normal_background);
        EditPhoneNumber.addTextChangedListener(TextChangeForChangeBack(EditPhoneNumber));
        EditPassword.setBackgroundResource(R.drawable.edit_normal_background);
        EditPassword.addTextChangedListener(TextChangeForChangeBack(EditPassword));
    }//_____________________________________________________________________________________________ SetTextWatcher



    private Boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

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

    }//_____________________________________________________________________________________________ CheckEmpty



}
