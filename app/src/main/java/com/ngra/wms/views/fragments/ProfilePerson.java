package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentProfilePersonBinding;
import com.ngra.wms.models.ModelProfileInfo;
import com.ngra.wms.models.MD_SpinnerItem;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_ProfilePerson;
import com.ngra.wms.views.activitys.MainActivity;
import com.ngra.wms.views.application.ApplicationWMS;
import com.ngra.wms.views.dialogs.DialogProgress;
import com.ngra.wms.views.dialogs.searchspinner.MLSpinnerDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

import static com.ngra.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class ProfilePerson extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {


    private VM_ProfilePerson vm_profilePerson;
    private MLSpinnerDialog spinnerProvinces;
    private ArrayList<MD_SpinnerItem> ProvincesList;
    private String ProvinceId = "-1";
    private Boolean ClickProvince = false;
    private MLSpinnerDialog spinnerCity;
    private ArrayList<MD_SpinnerItem> CitiesList;
    private String CityId = "-1";
    private Boolean ClickCity = false;
    private MLSpinnerDialog spinnerRegion;
    private ArrayList<MD_SpinnerItem> RegionsList;
    private String RegionId = "-1";
    private Boolean ClickPlace = false;
    private DialogProgress progress;
    private int GenderCode = -1;
    private ArrayList<MD_SpinnerItem> UserType;
    private String UserTypeId = "-1";
    private boolean completeProfile;
    private boolean clickSave;
    private NavController navController;


    @BindView(R.id.LayoutCity)
    LinearLayout LayoutCity;

    @BindView(R.id.TextCity)
    TextView TextCity;

    @BindView(R.id.LayoutRegion)
    LinearLayout LayoutRegion;

    @BindView(R.id.TextRegion)
    TextView TextRegion;

    @BindView(R.id.LayoutUser)
    LinearLayout LayoutUser;

    @BindView(R.id.TextUser)
    TextView TextUser;

    @BindView(R.id.LayoutProvinces)
    LinearLayout LayoutProvinces;

    @BindView(R.id.TextProvinces)
    TextView TextProvinces;

    @BindView(R.id.RelativeLayoutSend)
    RelativeLayout RelativeLayoutSend;

    @BindView(R.id.editFirsName)
    EditText editFirsName;

    @BindView(R.id.edtiLastName)
    EditText edtiLastName;

    @BindView(R.id.layoutGender)
    RadioGroup layoutGender;

    @BindView(R.id.radioWoman)
    RadioButton radioWoman;

    @BindView(R.id.radioMan)
    RadioButton radioMan;

    @BindView(R.id.editReferenceCode)
    EditText editReferenceCode;

    @BindView(R.id.EditPhoneNumber)
    EditText EditPhoneNumber;

    @BindView(R.id.txtLoading)
    TextView txtLoading;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.imgLoading)
    ImageView imgLoading;

    @BindView(R.id.LinearLayoutEditAddress)
    LinearLayout LinearLayoutEditAddress;


    //______________________________________________________________________________________________ onCreateView
    @Nullable
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        if (getView() == null) {
            FragmentProfilePersonBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_profile_person, container, false);
            vm_profilePerson = new VM_ProfilePerson(getContext());
            binding.setVmPerson(vm_profilePerson);
            setView(binding.getRoot());
            init();
            completeProfile = MainActivity.complateprofile;
            clickSave = false;
            if (!completeProfile)
                LinearLayoutEditAddress.setVisibility(View.GONE);
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                ProfilePerson.this,
                vm_profilePerson.getPublishSubject(),
                vm_profilePerson);
        if (getView() != null)
            navController = Navigation.findNavController(getView());
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ init
    private void init() {
        TextProvinces.setText(getResources().getString(R.string.ChooseProvinces));
        TextCity.setText(getResources().getString(R.string.City_Prompt));
        TextRegion.setText(getResources().getString(R.string.ChooseRegion));
        TextUser.setText(getResources().getString(R.string.ChooseUser));
        ClickProvince = false;
        ClickCity = false;
        ClickPlace = false;
        getProfileInfo();
        setClick();
        setTextWatcher();
        setItemUser();
        GenderCode = -1;
        EditPhoneNumber.setText(vm_profilePerson.getPhoneNumber());
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ setClick
    private void setClick() {


        LinearLayoutEditAddress.setOnClickListener(v -> {
            if (getView() != null) {
                ViewGroup parent = (ViewGroup) getView().getParent();
                if (parent != null) {
                    parent.removeAllViews();
                }
            }
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getResources().getString(R.string.ML_TimeId), -1);
            navController.navigate(R.id.action_goto_address2, bundle);
        });


        LayoutProvinces.setOnClickListener(v -> {
            ClickProvince = true;
            if ((ProvincesList == null) || (ProvincesList.size() == 0))
                getProvinces();
            else
                spinnerProvinces.showSpinerDialog();
        });

        LayoutCity.setOnClickListener(v -> {

            if (ProvinceId.equalsIgnoreCase("-1")) {
                showMessageDialog(getResources().getString(R.string.PleaseChooseProvince)
                        , getResources().getColor(R.color.mlWhite),
                        getResources().getDrawable(R.drawable.ic_error),
                        getResources().getColor(R.color.mlBlack));
            } else {
                ClickCity = true;
                if ((CitiesList == null) || (CitiesList.size() == 0))
                    getCities();
                else
                    spinnerCity.showSpinerDialog();
            }

        });


        LayoutRegion.setOnClickListener(v -> {
            if (CityId.equalsIgnoreCase("-1")) {
                showMessageDialog(getResources().getString(R.string.PleaseChooseCity)
                        , getResources().getColor(R.color.mlWhite),
                        getResources().getDrawable(R.drawable.ic_error),
                        getResources().getColor(R.color.mlBlack));
            } else {
                ClickPlace = true;
                if ((RegionsList == null) || (RegionsList.size() == 0))
                    getPlaces();
                else
                    spinnerRegion.showSpinerDialog();
            }
        });


        radioMan.setOnClickListener(v -> {
            if (radioMan.isChecked()) {
                layoutGender.setBackgroundColor(getResources().getColor(R.color.mlWhite));
                GenderCode = 1;
            }
        });


        radioWoman.setOnClickListener(v -> {
            if (radioMan.isChecked()) {
                layoutGender.setBackgroundColor(getResources().getColor(R.color.mlWhite));
                GenderCode = 0;
            }
        });


        RelativeLayoutSend.setOnClickListener(v -> {

            if (checkEmpty()) {
                hideKeyboard();
                showLoading();
                clickSave = true;
                vm_profilePerson.setFirstName(editFirsName.getText().toString());
                vm_profilePerson.setLastName(edtiLastName.getText().toString());
                vm_profilePerson.setGender(GenderCode);
                vm_profilePerson.setCitizenType(Integer.valueOf(UserTypeId));
                vm_profilePerson.setCityId(CityId);
                vm_profilePerson.setRegionId(RegionId);
                vm_profilePerson.setReferenceCode(
                        editReferenceCode.getText().toString()
                );
                vm_profilePerson.editProfile();
            }
        });

    }
    //______________________________________________________________________________________________ setClick


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        if (progress != null)
            progress.dismiss();

        dismissLoading();


        if (action.equals(StaticValues.ML_GetProfileInfo)) {
            setProfileInfo();
            return;
        }

        if (action.equals(StaticValues.ML_EditProfile)) {
            MainActivity.complateprofile = true;
            //getActivity().onBackPressed();
            return;
        }

        if (action.equals(StaticValues.ML_GetRegion)) {
            RegionsList = vm_profilePerson.getRegions();
            setItemRegion();
            return;
        }

        if (action.equals(StaticValues.ML_GetCities)) {
            CitiesList = vm_profilePerson.getCities();
            setItemCity();
            return;
        }

        if (action.equals(StaticValues.ML_GetProvince)) {
            ProvincesList = vm_profilePerson.getProvinces();
            setItemProvinces();
            return;
        }

        if (clickSave)
            if (action.equals(StaticValues.ML_DialogClose))
                if (!completeProfile) {
                    MainActivity.complateprofile = true;
                    if (getContext() != null)
                        getContext().onBackPressed();
                }

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ setProfileInfo
    private void setProfileInfo() {
        ModelProfileInfo.ModelProfile profile = vm_profilePerson.getProfile();
        if (profile != null)
            if (profile.getFirstName() != null) {
                editFirsName.setText(profile.getFirstName());

                if (profile.getLastName() != null)
                    edtiLastName.setText(profile.getLastName());

                if (profile.getProvince() != null) {
                    TextProvinces.setText(profile.getProvince().getTitle());
                    ProvinceId = profile.getProvince().getId();
                }

                if (profile.getCity() != null) {
                    TextCity.setText(profile.getCity().getTitle());
                    CityId = profile.getCity().getId();
                }

                if (profile.getNeighbourhood() != null) {
                    TextRegion.setText(profile.getNeighbourhood().getTitle());
                    RegionId = profile.getNeighbourhood().getId();
                }

                if (profile.getCitizenType() != null) {
                    TextUser.setText(UserType.get(profile.getCitizenType()).getTitle());
                    UserTypeId = String.valueOf(profile.getCitizenType());
                }

                if (profile.getReferenceCode() != null) {
                    editReferenceCode.setText(String.valueOf(profile.getReferenceCode()));
                    if (editReferenceCode.getText().toString().length() > 0)
                        editReferenceCode.setEnabled(false);
                }

                if (profile.getGender() == 0)
                    radioWoman.setChecked(true);
                else
                    radioMan.setChecked(true);

                GenderCode = profile.getGender();
            }


    }
    //______________________________________________________________________________________________ setProfileInfo


    //______________________________________________________________________________________________ setItemProvinces
    private void setItemProvinces() {

        TextProvinces.setText(getResources().getString(R.string.ChooseProvinces));
        CitiesList = null;
        ClickCity = false;
        RegionsList = null;
        ClickPlace = false;
        ProvinceId = "-1";
        CityId = "-1";
        RegionId = "-1";
        TextCity.setText(getResources().getString(R.string.City_Prompt));
        TextRegion.setText(getResources().getString(R.string.ChooseRegion));
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerProvinces = new MLSpinnerDialog(
                getActivity(),
                ProvincesList,
                getResources().getString(R.string.ProvincesSearch),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Ignor));// With 	Animation

        spinnerProvinces.setCancellable(true); // for cancellable
        spinnerProvinces.setShowKeyboard(false);// for open keyboard by default
        spinnerProvinces.bindOnSpinerListener((item, position) -> {
            TextProvinces.setText(item);
            ProvinceId = ProvincesList.get(position).getId();
            CitiesList = null;
            ClickCity = false;
            RegionsList = null;
            ClickPlace = false;
            CityId = "-1";
            RegionId = "-1";
            TextCity.setText(getResources().getString(R.string.City_Prompt));
            TextRegion.setText(getResources().getString(R.string.ChooseRegion));
            LayoutProvinces.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));
            getCities();
        });

        if (ClickProvince)
            spinnerProvinces.showSpinerDialog();

    }
    //______________________________________________________________________________________________ setItemProvinces


    //______________________________________________________________________________________________ setItemCity
    private void setItemCity() {

        RegionsList = null;
        ClickPlace = false;
        CityId = "-1";
        RegionId = "-1";
        TextCity.setText(getResources().getString(R.string.City_Prompt));
        TextRegion.setText(getResources().getString(R.string.ChooseRegion));
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerCity = new MLSpinnerDialog(
                getActivity(),
                CitiesList,
                getResources().getString(R.string.City_Search),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Ignor));// With 	Animation

        spinnerCity.setCancellable(true); // for cancellable
        spinnerCity.setShowKeyboard(false);// for open keyboard by default
        spinnerCity.bindOnSpinerListener((item, position) -> {
            TextCity.setText(item);
            CityId = CitiesList.get(position).getId();
            RegionsList = null;
            ClickPlace = false;
            RegionId = "-1";
            TextRegion.setText(getResources().getString(R.string.ChooseRegion));
            LayoutCity.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));
            getPlaces();
        });

        if (ClickCity)
            spinnerCity.showSpinerDialog();


    }
    //______________________________________________________________________________________________ setItemCity


    //______________________________________________________________________________________________ setItemRegion
    private void setItemRegion() {

        TextRegion.setText(getResources().getString(R.string.ChooseRegion));
        RegionId = "-1";
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerRegion = new MLSpinnerDialog(
                getActivity(),
                RegionsList,
                getResources().getString(R.string.Region_Search),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Ignor));// With 	Animation
        spinnerRegion.setCancellable(true); // for cancellable
        spinnerRegion.setShowKeyboard(false);// for open keyboard by default
        spinnerRegion.bindOnSpinerListener((item, position) -> {
            TextRegion.setText(item);
            RegionId = RegionsList.get(position).getId();
            LayoutRegion.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));
        });

        if (ClickPlace)
            spinnerRegion.showSpinerDialog();

    }
    //______________________________________________________________________________________________ setItemRegion


    //______________________________________________________________________________________________ checkEmpty
    private Boolean checkEmpty() {

        boolean firstname;
        boolean lastname;
        boolean gender;
        boolean privence;
        boolean city;
        boolean region;
        boolean user;

        if (edtiLastName.getText().length() < 1) {
            edtiLastName.setBackgroundResource(R.drawable.dw_edit_back_empty);
            edtiLastName.setError(getResources().getString(R.string.EmptyLastName));
            edtiLastName.requestFocus();
            lastname = false;
        } else
            lastname = true;


        if (editFirsName.getText().length() < 1) {
            editFirsName.setBackgroundResource(R.drawable.dw_edit_back_empty);
            editFirsName.setError(getResources().getString(R.string.EmptyFirstName));
            editFirsName.requestFocus();
            firstname = false;
        } else
            firstname = true;


        if ((!radioMan.isChecked()) && (!radioWoman.isChecked())) {
            layoutGender.setBackground(getResources().getDrawable(R.drawable.dw_edit_back_empty));
            gender = false;
        } else
            gender = true;

        if (radioMan.isChecked())
            GenderCode = 1;
        else
            GenderCode = 0;

        if (ProvinceId.equalsIgnoreCase("-1")) {
            LayoutProvinces.setBackground(getResources().getDrawable(R.drawable.dw_edit_back_empty));
            privence = false;
        } else
            privence = true;

        if (CityId.equalsIgnoreCase("-1")) {
            LayoutCity.setBackground(getResources().getDrawable(R.drawable.dw_edit_back_empty));
            city = false;
        } else
            city = true;

        if (RegionId.equalsIgnoreCase("-1")) {
            LayoutRegion.setBackground(getResources().getDrawable(R.drawable.dw_edit_back_empty));
            region = false;
        } else
            region = true;

        if (UserTypeId.equalsIgnoreCase("-1")) {
            LayoutUser.setBackground(getResources().getDrawable(R.drawable.dw_edit_back_empty));
            user = false;
        } else
            user = true;


        return firstname && lastname && gender && privence && city && region && user;

    }
    //______________________________________________________________________________________________ checkEmpty


    //______________________________________________________________________________________________ setItemUser
    private void setItemUser() {

        TextUser.setText(getResources().getString(R.string.ChooseUser));
        MLSpinnerDialog spinnerUser;
        UserType = new ArrayList<>();
        UserType.add(new MD_SpinnerItem("0", "خانوار", "0"));
        UserType.add(new MD_SpinnerItem("1", "مدیر ساختمان", "0"));
        UserType.add(new MD_SpinnerItem("2", "سرایدار", "0"));
        UserType.add(new MD_SpinnerItem("3", "، دانش آموز", "0"));
        UserType.add(new MD_SpinnerItem("4", "سایر", "0"));
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerUser = new MLSpinnerDialog(
                getActivity(),
                UserType,
                getResources().getString(R.string.User_Search),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Ignor));// With 	Animation
        spinnerUser.setCancellable(true); // for cancellable
        spinnerUser.setShowKeyboard(false);// for open keyboard by default
        spinnerUser.bindOnSpinerListener((item, position) -> {
            TextUser.setText(item);
            UserTypeId = UserType.get(position).getId();
            LayoutUser.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));
        });

        LayoutUser.setOnClickListener(v -> spinnerUser.showSpinerDialog());

    }
    //______________________________________________________________________________________________ setItemUser


    //______________________________________________________________________________________________ dismissLoading
    private void dismissLoading() {
        txtLoading.setText(getResources().getString(R.string.SaveInfo));
        RelativeLayoutSend.setBackground(getResources().getDrawable(R.drawable.save_info_button));
        gifLoading.setVisibility(View.GONE);
        imgLoading.setVisibility(View.VISIBLE);
    }
    //______________________________________________________________________________________________ dismissLoading


    //______________________________________________________________________________________________ showLoading
    private void showLoading() {
        txtLoading.setText(getResources().getString(R.string.Cancel));
        RelativeLayoutSend.setBackground(getResources().getDrawable(R.drawable.button_red));
        gifLoading.setVisibility(View.VISIBLE);
        imgLoading.setVisibility(View.INVISIBLE);
    }
    //______________________________________________________________________________________________ showLoading


    //______________________________________________________________________________________________ getCities
    private void getCities() {
        showProgressDialog();
        vm_profilePerson.setProvinceId(ProvinceId);
        vm_profilePerson.getCitiesList();
    }
    //______________________________________________________________________________________________ getCities


    //______________________________________________________________________________________________ getProvinces
    private void getProvinces() {
        showProgressDialog();
        vm_profilePerson.getProvincesList();
    }
    //______________________________________________________________________________________________ getProvinces


    //______________________________________________________________________________________________ getPlaces
    private void getPlaces() {
        showProgressDialog();
        vm_profilePerson.setCityId(CityId);
        vm_profilePerson.getPlacesList();
    }
    //______________________________________________________________________________________________ getPlaces


    //______________________________________________________________________________________________ getProfileInfo
    private void getProfileInfo() {
        showProgressDialog();
        vm_profilePerson.getProfileInfo();
    }
    //______________________________________________________________________________________________ getProfileInfo


    //______________________________________________________________________________________________ showProgressDialog
    private void showProgressDialog() {

        if (getContext() != null) {
            progress = ApplicationWMS
                    .getApplicationWMS(getContext())
                    .getUtilityComponent()
                    .getApplicationUtility()
                    .ShowProgress(getContext(), null);
            progress.show(getChildFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
        }
    }
    //______________________________________________________________________________________________ showProgressDialog


    //______________________________________________________________________________________________ setTextWatcher
    private void setTextWatcher() {
        editFirsName.setBackgroundResource(R.drawable.dw_edit_back);
        editFirsName.addTextChangedListener(TextChangeForChangeBack(editFirsName));

        edtiLastName.setBackgroundResource(R.drawable.dw_edit_back);
        edtiLastName.addTextChangedListener(TextChangeForChangeBack(edtiLastName));

        layoutGender.setBackgroundColor(getResources().getColor(R.color.mlWhite));

        LayoutProvinces.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));

        LayoutCity.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));

        LayoutRegion.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));

        LayoutUser.setBackground(getResources().getDrawable(R.drawable.dw_edit_back));

        editReferenceCode.setBackgroundResource(R.drawable.dw_edit_back);
        editReferenceCode.addTextChangedListener(TextChangeForChangeBack(editReferenceCode));

    }
    //______________________________________________________________________________________________ setTextWatcher


}
