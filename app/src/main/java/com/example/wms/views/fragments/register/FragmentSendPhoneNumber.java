package com.example.wms.views.fragments.register;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentSendPhoneNumberBinding;
import com.example.wms.viewmodels.register.FragmentSendPhoneNumberViewModel;

import butterknife.ButterKnife;

public class FragmentSendPhoneNumber extends Fragment {

    private Context context;
    private FragmentSendPhoneNumberViewModel fragmentSendPhoneNumberViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSendPhoneNumberBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_send_phone_number, container, false
        );
        fragmentSendPhoneNumberViewModel = new FragmentSendPhoneNumberViewModel(context);
        binding.setPhonenumber(fragmentSendPhoneNumberViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }//_____________________________________________________________________________________________ End onCreateView


    public FragmentSendPhoneNumber(Context context) {//_____________________________________________ Start FragmentSendPhoneNumber
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentSendPhoneNumber


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
    }//_____________________________________________________________________________________________ End onStart


}
