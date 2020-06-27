package com.example.wms.views.fragments.user.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.wms.R;
import com.example.wms.databinding.FragmentProfilePersonBinding;
import com.example.wms.models.ModelProfileInfo;
import com.example.wms.models.ModelSpinnerItem;
import com.example.wms.utility.StaticFunctions;
import com.example.wms.utility.StaticValues;
import com.example.wms.viewmodels.user.profile.VM_ProfilePerson;
import com.example.wms.views.activitys.MainActivity;
import com.example.wms.views.application.ApplicationWMS;
import com.example.wms.views.dialogs.DialogProgress;
import com.example.wms.views.dialogs.searchspinner.MLSpinnerDialog;
import com.example.wms.views.dialogs.searchspinner.OnSpinnerItemClick;
import com.example.wms.views.fragments.FragmentPrimary;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class ProfilePerson extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {


    private NavController navController;
    private VM_ProfilePerson vm_profilePerson;
    private MLSpinnerDialog spinnerProvinces;
    private ArrayList<ModelSpinnerItem> ProvincesList;
    private String ProvinceId = "-1";
    private Boolean ClickProvince = false;
    private MLSpinnerDialog spinnerCity;
    private ArrayList<ModelSpinnerItem> CitiesList;
    private String CityId = "-1";
    private Boolean ClickCity = false;
    private MLSpinnerDialog spinnerRegion;
    private ArrayList<ModelSpinnerItem> RegionsList;
    private String RegionId = "-1";
    private Boolean ClickPlace = false;
    private DialogProgress progress;
    private int GenderCode = -1;
    private ArrayList<ModelSpinnerItem> UserType;
    private String UserTypeId = "-1";


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

    @BindView(R.id.btnEditProfile)
    Button btnEditProfile;

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


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentProfilePersonBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_profile_person, container, false);
            vm_profilePerson = new VM_ProfilePerson(getContext());
            binding.setVmPerson(vm_profilePerson);
            setView(binding.getRoot());
            ButterKnife.bind(this, getView());
            init();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(ProfilePerson.this, vm_profilePerson.getPublishSubject());
        navController = Navigation.findNavController(getView());
    }//_____________________________________________________________________________________________ onStart



    private void init() {//_________________________________________________________________________ Start init
        TextProvinces.setText(getResources().getString(R.string.ChooseProvinces));
        TextCity.setText(getResources().getString(R.string.City_Prompt));
        TextRegion.setText(getResources().getString(R.string.ChooseRegion));
        TextUser.setText(getResources().getString(R.string.ChooseUser));
        ClickProvince = false;
        ClickCity = false;
        ClickPlace = false;
        GetProfileInfo();
        SetClick();
        SetTextWatcher();
        SetItemUser();
        GenderCode = -1;
        EditPhoneNumber.setText(vm_profilePerson.GetPhoneNumber());
    }//_____________________________________________________________________________________________ End init


    private void SetClick() {//_____________________________________________________________________ SetClick

        LayoutProvinces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickProvince = true;
                if ((ProvincesList == null) || (ProvincesList.size() == 0))
                    GetProvinces();
                else
                    spinnerProvinces.showSpinerDialog();
            }
        });

        LayoutCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ProvinceId.equalsIgnoreCase("-1")) {
                    ShowMessage(getResources().getString(R.string.PleaseChooseProvince)
                            , getResources().getColor(R.color.mlWhite),
                            getResources().getDrawable(R.drawable.ic_error),
                            getResources().getColor(R.color.mlBlack));
                } else {
                    ClickCity = true;
                    if ((CitiesList == null) || (CitiesList.size() == 0))
                        GetCitys();
                    else
                        spinnerCity.showSpinerDialog();
                }

            }
        });


        LayoutRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CityId.equalsIgnoreCase("-1")) {
                    ShowMessage(getResources().getString(R.string.PleaseChooseCity)
                            , getResources().getColor(R.color.mlWhite),
                            getResources().getDrawable(R.drawable.ic_error),
                            getResources().getColor(R.color.mlBlack));
                } else {
                    ClickPlace = true;
                    if ((RegionsList == null) || (RegionsList.size() == 0))
                        GetPlaces();
                    else
                        spinnerRegion.showSpinerDialog();
                }
            }
        });


        radioMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioMan.isChecked()) {
                    layoutGender.setBackgroundColor(getResources().getColor(R.color.mlWhite));
                    GenderCode = 1;
                }
            }
        });


        radioWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioMan.isChecked()) {
                    layoutGender.setBackgroundColor(getResources().getColor(R.color.mlWhite));
                    GenderCode = 0;
                }
            }
        });


        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAccessClick())
                    return;
                if (CheckEmpty()) {
                    setAccessClick(false);
                    StaticFunctions.hideKeyboard(getActivity());
                    ShowProgressDialog();
                    vm_profilePerson.setFirstName(editFirsName.getText().toString());
                    vm_profilePerson.setLastName(edtiLastName.getText().toString());
                    vm_profilePerson.setGender(GenderCode);
                    vm_profilePerson.setCitizenType(Integer.valueOf(UserTypeId));
                    vm_profilePerson.setCityId(CityId);
                    vm_profilePerson.setRegionId(RegionId);
                    vm_profilePerson.setReferenceCode(
                            editReferenceCode.getText().toString()
                    );
                    vm_profilePerson.EditProfile();
                }
            }
        });

    }//_____________________________________________________________________________________________ SetClick


    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        setAccessClick(true);

        if (action == StaticValues.ML_GetProfileInfo) {
            SetProfileInfo();
            return;
        }

        if (action == StaticValues.ML_EditProfile) {
            ShowMessage(vm_profilePerson.getResponseMessage()
                    , getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_check),
                    getResources().getColor(R.color.mlBlack));
            MainActivity.complateprofile = true;
            getActivity().onBackPressed();
            return;
        }

        if (action == StaticValues.ML_GetRegion) {
            RegionsList = vm_profilePerson.getRegions();
            SetItemRegion();
            return;
        }

        if (action == StaticValues.ML_GetCities) {
            CitiesList = vm_profilePerson.getCities();
            SetItemCity();
            return;
        }

        if (action == StaticValues.ML_GetProvince) {
            ProvincesList = vm_profilePerson.getProvinces();
            SetItemProvinces();
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
            ShowMessage(vm_profilePerson.getResponseMessage()
                    , getResources().getColor(R.color.mlWhite),
                    getResources().getDrawable(R.drawable.ic_error),
                    getResources().getColor(R.color.mlBlack));
            return;
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable



    private void SetProfileInfo() {//_______________________________________________________________ SetProfileInfo
        ModelProfileInfo.ModelProfile profile = vm_profilePerson.getProfile();
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

            if (profile.getReferenceCode() != null)
                editReferenceCode.setText(String.valueOf(profile.getReferenceCode()));

            if(profile.getGender() == 0)
                radioWoman.setChecked(true);
            else
                radioMan.setChecked(true);

            GenderCode = profile.getGender();
        }


    }//_____________________________________________________________________________________________ SetProfileInfo


    private void SetItemProvinces() {//_____________________________________________________________ SetItemProvinces

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
        spinnerProvinces.bindOnSpinerListener(new OnSpinnerItemClick() {
            @Override
            public void onClick(String item, int position) {
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
                LayoutProvinces.setBackgroundColor(getResources().getColor(R.color.mlEdit));
                GetCitys();
            }
        });

        if (ClickProvince)
            spinnerProvinces.showSpinerDialog();

    }//_____________________________________________________________________________________________ SetItemProvinces


    private void SetItemCity() {//__________________________________________________________________ SetItemCity

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
        spinnerCity.bindOnSpinerListener(new OnSpinnerItemClick() {
            @Override
            public void onClick(String item, int position) {
                TextCity.setText(item);
                CityId = CitiesList.get(position).getId();
                RegionsList = null;
                ClickPlace = false;
                RegionId = "-1";
                TextRegion.setText(getResources().getString(R.string.ChooseRegion));
                LayoutCity.setBackgroundColor(getResources().getColor(R.color.mlEdit));
                GetPlaces();
            }
        });

        if (ClickCity)
            spinnerCity.showSpinerDialog();


    }//_____________________________________________________________________________________________ SetItemCity



    private void SetItemRegion() {//________________________________________________________________ SetItemRegion

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
        spinnerRegion.bindOnSpinerListener(new OnSpinnerItemClick() {
            @Override
            public void onClick(String item, int position) {
                TextRegion.setText(item);
                RegionId = RegionsList.get(position).getId();
                LayoutRegion.setBackgroundColor(getResources().getColor(R.color.mlEdit));
            }
        });

        if (ClickPlace)
            spinnerRegion.showSpinerDialog();

    }//_____________________________________________________________________________________________ SetItemRegion



    private Boolean CheckEmpty() {//________________________________________________________________ CheckEmpty

        boolean firstname = false;
        boolean lastname = false;
        boolean gender = false;
        boolean privence = false;
        boolean city = false;
        boolean region = false;
        boolean user = false;

        if (edtiLastName.getText().length() < 1) {
            edtiLastName.setBackgroundResource(R.drawable.edit_empty_background);
            edtiLastName.setError(getResources().getString(R.string.EmptyLastName));
            edtiLastName.requestFocus();
            lastname = false;
        } else
            lastname = true;


        if (editFirsName.getText().length() < 1) {
            editFirsName.setBackgroundResource(R.drawable.edit_empty_background);
            editFirsName.setError(getResources().getString(R.string.EmptyFirstName));
            editFirsName.requestFocus();
            firstname = false;
        } else
            firstname = true;


        if ((!radioMan.isChecked()) && (!radioWoman.isChecked())) {
            layoutGender.setBackground(getResources().getDrawable(R.drawable.edit_empty_background));
            gender = false;
        } else
            gender = true;

        if(radioMan.isChecked())
            GenderCode = 1;
        else
            GenderCode = 0;

        if (ProvinceId.equalsIgnoreCase("-1")) {
            LayoutProvinces.setBackgroundColor(getResources().getColor(R.color.mlEditEmpty));
            privence = false;
        } else
            privence = true;

        if (CityId.equalsIgnoreCase("-1")) {
            LayoutCity.setBackgroundColor(getResources().getColor(R.color.mlEditEmpty));
            city = false;
        } else
            city = true;

        if (RegionId.equalsIgnoreCase("-1")) {
            LayoutRegion.setBackgroundColor(getResources().getColor(R.color.mlEditEmpty));
            region = false;
        } else
            region = true;

        if (UserTypeId.equalsIgnoreCase("-1")) {
            LayoutUser.setBackgroundColor(getResources().getColor(R.color.mlEditEmpty));
            user = false;
        } else
            user = true;


        if (firstname && lastname && gender && privence && city && region && user)
            return true;
        else
            return false;

    }//_____________________________________________________________________________________________ CheckEmpty



    private void SetItemUser() {//__________________________________________________________________ SetItemUser

        TextUser.setText(getResources().getString(R.string.ChooseUser));
        MLSpinnerDialog spinnerUser;
        UserType = new ArrayList<>();
        UserType.add(new ModelSpinnerItem("0", "خانوار"));
        UserType.add(new ModelSpinnerItem("1", "مدیر ساختمان"));
        UserType.add(new ModelSpinnerItem("2", "سرایدار"));
        UserType.add(new ModelSpinnerItem("3", "، دانش آموز"));
        UserType.add(new ModelSpinnerItem("4", "سایر"));
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerUser = new MLSpinnerDialog(
                getActivity(),
                UserType,
                getResources().getString(R.string.User_Search),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Ignor));// With 	Animation
        spinnerUser.setCancellable(true); // for cancellable
        spinnerUser.setShowKeyboard(false);// for open keyboard by default
        spinnerUser.bindOnSpinerListener(new OnSpinnerItemClick() {
            @Override
            public void onClick(String item, int position) {
                TextUser.setText(item);
                UserTypeId = UserType.get(position).getId();
                LayoutUser.setBackgroundColor(getResources().getColor(R.color.mlEdit));
            }
        });

        LayoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerUser.showSpinerDialog();
            }
        });

    }//_____________________________________________________________________________________________ SetItemUser



    private void GetCitys() {//_____________________________________________________________________ GetCitys
        ShowProgressDialog();
        vm_profilePerson.setProvinceId(ProvinceId);
        vm_profilePerson.GetCitiesList();
    }//_____________________________________________________________________________________________ GetCitys


    private void GetProvinces() {//_________________________________________________________________ GetProvinces
        ShowProgressDialog();
        vm_profilePerson.GetProvincesList();
    }//_____________________________________________________________________________________________ GetProvinces


    private void GetPlaces() {//____________________________________________________________________ GetPlaces
        ShowProgressDialog();
        vm_profilePerson.setCityId(CityId);
        vm_profilePerson.GetPlacesList();
    }//_____________________________________________________________________________________________ GetPlaces


    private void GetProfileInfo() {//_______________________________________________________________ GetProfileInfo
        ShowProgressDialog();
        vm_profilePerson.GetProfileInfo();
    }//_____________________________________________________________________________________________ GetProfileInfo


    private void ShowProgressDialog() {//___________________________________________________________ ShowProgressDialog

        progress = ApplicationWMS
                .getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility()
                .ShowProgress(getContext(),null);
        progress.show(getChildFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ ShowProgressDialog


    private void SetTextWatcher() {//_______________________________________________________________ SetTextWatcher
        editFirsName.setBackgroundResource(R.drawable.edit_normal_background);
        editFirsName.addTextChangedListener(TextChangeForChangeBack(editFirsName));

        edtiLastName.setBackgroundResource(R.drawable.edit_normal_background);
        edtiLastName.addTextChangedListener(TextChangeForChangeBack(edtiLastName));

        layoutGender.setBackgroundColor(getResources().getColor(R.color.mlWhite));

        LayoutProvinces.setBackgroundColor(getResources().getColor(R.color.mlEdit));

        LayoutCity.setBackgroundColor(getResources().getColor(R.color.mlEdit));

        LayoutRegion.setBackgroundColor(getResources().getColor(R.color.mlEdit));

        LayoutUser.setBackgroundColor(getResources().getColor(R.color.mlEdit));

        editReferenceCode.setBackgroundResource(R.drawable.edit_normal_background);
        editReferenceCode.addTextChangedListener(TextChangeForChangeBack(editReferenceCode));

    }//_____________________________________________________________________________________________ SetTextWatcher

}
