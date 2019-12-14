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
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentProfilePersonBinding;
import com.example.wms.viewmodels.user.profile.FragmentProfilePersonViewModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static com.example.wms.utility.StaticFunctions.SetKey;

public class FragmentProfilePerson extends Fragment {

    private Context context;
    private FragmentProfilePersonViewModel fragmentProfilePersonViewModel;


    @BindView(R.id.LayoutCity)
    LinearLayout LayoutCity;

    @BindView(R.id.TextCity)
    TextView TextCity;

    @BindView(R.id.LayoutPlace)
    LinearLayout LayoutPlace;

    @BindView(R.id.TextPlace)
    TextView TextPlace;

    @BindView(R.id.LayoutUser)
    LinearLayout LayoutUser;

    @BindView(R.id.TextUser)
    TextView TextUser;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProfilePersonBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_profile_person, container, false);
        fragmentProfilePersonViewModel = new FragmentProfilePersonViewModel(context);
        binding.setPerson(fragmentProfilePersonViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ Start onCreateView


    public FragmentProfilePerson(Context context) {//______________________________________________ Start FragmentProfilePerson
        this.context = context;
    }//_____________________________________________________________________________________________ End FragmentProfilePerson


    public FragmentProfilePerson() {//_____________________________________________________________ Start FragmentProfilePerson
    }//_____________________________________________________________________________________________ End FragmentProfilePerson


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        SetItemCity();
        SetItemPlace();
        SetItemUser();
    }//_____________________________________________________________________________________________ End onStart


    private void SetItemCity() {//__________________________________________________________________ Start SetItemCity

        TextCity.setText(getResources().getString(R.string.City_Prompt));
        ArrayList<String> items = new ArrayList<>();
        SpinnerDialog spinnerCity;
        items.add("تهران");
        items.add("کرج");
        items.add("اصفهان");
        items.add("شیراز");
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerCity = new SpinnerDialog(
                getActivity(),
                items,
                getResources().getString(R.string.City_Search),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Ignor));// With 	Animation
        spinnerCity.setCancellable(true); // for cancellable
        spinnerCity.setShowKeyboard(false);// for open keyboard by default
        spinnerCity.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                TextCity.setText(item);
                //selectedItems.setText(item + " Position: " + position);
            }
        });

        LayoutCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerCity.showSpinerDialog();
            }
        });

    }//_____________________________________________________________________________________________ End SetItemCity


    private void SetItemPlace() {//_________________________________________________________________ Start SetItemPlace

        TextPlace.setText(getResources().getString(R.string.ChoosePlace));
        ArrayList<String> items = new ArrayList<>();
        SpinnerDialog spinnerPlace;
        items.add("عظیمیه");
        items.add("گوهردشت");
        items.add("شاهین ویلا");
        items.add("حصارک");
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerPlace = new SpinnerDialog(
                getActivity(),
                items,
                getResources().getString(R.string.Place_Search),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Ignor));// With 	Animation
        spinnerPlace.setCancellable(true); // for cancellable
        spinnerPlace.setShowKeyboard(false);// for open keyboard by default
        spinnerPlace.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                TextPlace.setText(item);
                //selectedItems.setText(item + " Position: " + position);
            }
        });

        LayoutPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerPlace.showSpinerDialog();
            }
        });

    }//_____________________________________________________________________________________________ End SetItemPlace


    private void SetItemUser() {//__________________________________________________________________ Start SetItemUser

        TextUser.setText(getResources().getString(R.string.ChooseUser));
        ArrayList<String> items = new ArrayList<>();
        SpinnerDialog spinnerUser;
        items.add("شهروند");
        items.add("کاربر اول");
        items.add("مدیریت");
        items.add("سرایدار");
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerUser = new SpinnerDialog(
                getActivity(),
                items,
                getResources().getString(R.string.User_Search),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Ignor));// With 	Animation
        spinnerUser.setCancellable(true); // for cancellable
        spinnerUser.setShowKeyboard(false);// for open keyboard by default
        spinnerUser.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                TextUser.setText(item);
                //selectedItems.setText(item + " Position: " + position);
            }
        });

        LayoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerUser.showSpinerDialog();
            }
        });

    }//_____________________________________________________________________________________________ End SetItemUser


    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKey(view));
    }//_____________________________________________________________________________________________ End BackClick

}
