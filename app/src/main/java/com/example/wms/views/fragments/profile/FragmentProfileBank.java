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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentProfileBankBinding;
import com.example.wms.viewmodels.user.profile.FragmentProfileBankViewModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static com.example.wms.utility.StaticFunctions.SetKey;

public class FragmentProfileBank extends Fragment {

    private Context context;
    private FragmentProfileBankViewModel fragmentProfileBankViewModel;

    @BindView(R.id.LayoutBank)
    LinearLayout LayoutBank;

    @BindView(R.id.TextBank)
    TextView TextBank;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProfileBankBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_profile_bank, container, false
        );
        fragmentProfileBankViewModel = new FragmentProfileBankViewModel(context);
        binding.setBank(fragmentProfileBankViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ Start onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetItemBank();
    }//_____________________________________________________________________________________________ End onStart


    private void SetItemBank() {//__________________________________________________________________ Start SetItemBank

        TextBank.setText(getResources().getString(R.string.City_Prompt));
        ArrayList<String> items = new ArrayList<>();
        SpinnerDialog spinnerBank;
        items.add("ملی");
        items.add("ملت");
        items.add("کشاورزی");
        items.add("تجارت");
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerBank = new SpinnerDialog(
                getActivity(),
                items,
                getResources().getString(R.string.City_Search),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Ignor));// With 	Animation
        spinnerBank.setCancellable(true); // for cancellable
        spinnerBank.setShowKeyboard(false);// for open keyboard by default
        spinnerBank.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                TextBank.setText(item);
                //selectedItems.setText(item + " Position: " + position);
            }
        });

        LayoutBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerBank.showSpinerDialog();
            }
        });

    }//_____________________________________________________________________________________________ End SetItemBank


    public FragmentProfileBank(Context context) {//________________________________________________ Start FragmentProfileBank
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentProfileBank


    public FragmentProfileBank() {//_______________________________________________________________ Start FragmentProfileBank
    }//_____________________________________________________________________________________________ End FragmentProfileBank


    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKey(view));
    }//_____________________________________________________________________________________________ End BackClick


}
