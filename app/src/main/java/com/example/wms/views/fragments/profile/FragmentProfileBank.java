/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.fragments.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentProfileBankBinding;
import com.example.wms.viewmodels.user.profile.FragmentProfileBankViewModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.SetKey;

public class FragmentProfileBank extends Fragment {

    private Context context;
    private FragmentProfileBankViewModel fragmentProfileBankViewModel;

    @BindView(R.id.MaterialSpinner1)
    MaterialSpinner MaterialSpinner1;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProfileBankBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_profile_bank,container, false
        );
        fragmentProfileBankViewModel = new FragmentProfileBankViewModel(context);
        binding.setBank(fragmentProfileBankViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this,view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ Start onCreateView


    public FragmentProfileBank(Context context) {//________________________________________________ Start FragmentProfileBank
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentProfileBank


    public FragmentProfileBank() {//_______________________________________________________________ Start FragmentProfileBank
    }//_____________________________________________________________________________________________ End FragmentProfileBank


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        MaterialSpinner1.setItems("انتخاب بانک", "ملی", "ملت");
    }//_____________________________________________________________________________________________ End onStart



    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKey(view));
    }//_____________________________________________________________________________________________ End BackClick


}
