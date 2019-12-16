/*
Create By Mehrdad Latifi in
1398/09/09 - 12:08 PM
 */
package com.example.wms.views.fragments.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.wms.R;
import com.example.wms.databinding.FragmentProfilePersonBinding;
import com.example.wms.models.ModelProvince;
import com.example.wms.viewmodels.user.profile.FragmentProfilePersonViewModel;
import com.example.wms.views.activitys.MainActivity;
import com.example.wms.views.activitys.user.login.ActivityBeforLogin;
import com.example.wms.views.dialogs.DialogMessage;
import com.example.wms.views.dialogs.DialogProgress;
import com.example.wms.views.dialogs.searchspinner.MLSpinnerDialog;
import com.example.wms.views.dialogs.searchspinner.OnSpinnerItemClick;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.wms.utility.StaticFunctions.SetKey;
import static com.example.wms.utility.StaticFunctions.TextChangeForChangeBack;

public class FragmentProfilePerson extends Fragment {

    private Context context;
    private FragmentProfilePersonViewModel fragmentProfilePersonViewModel;
    private DialogProgress progress;
    private MLSpinnerDialog spinnerProvinces;
    private ArrayList<ModelProvince> ProvincesList;
    private String ProvinceId = "-1";
    private Boolean ClickProvince = false;
    private MLSpinnerDialog spinnerCity;
    private ArrayList<ModelProvince> CitysList;
    private String CityId = "-1";
    private Boolean ClickCity = false;
    private MLSpinnerDialog spinnerPlace;
    private ArrayList<ModelProvince> PlacesList;
    private String PlaceId = "-1";
    private Boolean ClickPlace = false;

    private String UserTypeId = "-1";

    private int GenderCode = -1;


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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = getContext();
        FragmentProfilePersonBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_profile_person, container, false);
        fragmentProfilePersonViewModel = new FragmentProfilePersonViewModel(context);
        binding.setPerson(fragmentProfilePersonViewModel);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        BackClick(view);
        return view;
    }//_____________________________________________________________________________________________ Start onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ Start onStart
        super.onStart();
        ObserverObservables();
        TextProvinces.setText(getResources().getString(R.string.ChooseProvinces));
        TextCity.setText(getResources().getString(R.string.City_Prompt));
        TextPlace.setText(getResources().getString(R.string.ChoosePlace));
        TextUser.setText(getResources().getString(R.string.ChooseUser));
        ClickProvince = false;
        ClickCity = false;
        ClickPlace = false;
        GetProvinces();
        SetClick();
        SetTextWatcher();
        SetItemUser();
        GenderCode = -1;
        CheckProfile();
    }//_____________________________________________________________________________________________ End onStart


    private void SetClick() {//_____________________________________________________________________ Start SetClick

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
                            getResources().getDrawable(R.drawable.ic_error));
                } else {
                    ClickCity = true;
                    if ((CitysList == null) || (CitysList.size() == 0))
                        GetCitys();
                    else
                        spinnerCity.showSpinerDialog();
                }

            }
        });


        LayoutPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CityId.equalsIgnoreCase("-1")) {
                    ShowMessage(getResources().getString(R.string.PleaseChooseCity)
                            , getResources().getColor(R.color.mlWhite),
                            getResources().getDrawable(R.drawable.ic_error));
                } else {
                    ClickPlace = true;
                    if ((PlacesList == null) || (PlacesList.size() == 0))
                        GetPlaces();
                    else
                        spinnerPlace.showSpinerDialog();
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
                if (CheckEmpty()) {
                    ShowProgressDialog();
                    fragmentProfilePersonViewModel.setFirstName(editFirsName.getText().toString());
                    fragmentProfilePersonViewModel.setLastName(edtiLastName.getText().toString());
                    fragmentProfilePersonViewModel.setGender(GenderCode);
                    fragmentProfilePersonViewModel.setCitizenType(Integer.valueOf(UserTypeId));
                    fragmentProfilePersonViewModel.setCityId(CityId);
                    fragmentProfilePersonViewModel.setPlaceId(PlaceId);
                    fragmentProfilePersonViewModel.setReferenceCode(
                            Integer.valueOf(editReferenceCode.getText().toString())
                    );
                    fragmentProfilePersonViewModel.EditProfile();
                }
            }
        });

    }//_____________________________________________________________________________________________ End SetClick


    private Boolean CheckEmpty() {//________________________________________________________________ Start CheckEmpty

        boolean firstname = false;
        boolean lastname = false;
        boolean gender = false;
        boolean privence = false;
        boolean city = false;
        boolean place = false;
        boolean user = false;
        boolean reference = true;


        if (editReferenceCode.getText().length() < 1) {
            editReferenceCode.setBackgroundResource(R.drawable.edit_empty_background);
            editReferenceCode.setError(getResources().getString(R.string.EmptyReferenceCode));
            editReferenceCode.requestFocus();
            reference = false;
        } else
            reference = true;


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

        if (PlaceId.equalsIgnoreCase("-1")) {
            LayoutPlace.setBackgroundColor(getResources().getColor(R.color.mlEditEmpty));
            place = false;
        } else
            place = true;

        if (UserTypeId.equalsIgnoreCase("-1")) {
            LayoutUser.setBackgroundColor(getResources().getColor(R.color.mlEditEmpty));
            user = false;
        } else
            user = true;


        if (firstname && lastname && gender && privence && city && place && user && reference)
            return true;
        else
            return false;

    }//_____________________________________________________________________________________________ End CheckEmpty


    private void SetTextWatcher() {//_______________________________________________________________ Start SetTextWatcher
        editFirsName.setBackgroundResource(R.drawable.edit_normal_background);
        editFirsName.addTextChangedListener(TextChangeForChangeBack(editFirsName));

        edtiLastName.setBackgroundResource(R.drawable.edit_normal_background);
        edtiLastName.addTextChangedListener(TextChangeForChangeBack(edtiLastName));

        layoutGender.setBackgroundColor(getResources().getColor(R.color.mlWhite));

        LayoutProvinces.setBackgroundColor(getResources().getColor(R.color.mlEdit));

        LayoutCity.setBackgroundColor(getResources().getColor(R.color.mlEdit));

        LayoutPlace.setBackgroundColor(getResources().getColor(R.color.mlEdit));

        LayoutUser.setBackgroundColor(getResources().getColor(R.color.mlEdit));

        editReferenceCode.setBackgroundResource(R.drawable.edit_normal_background);
        editReferenceCode.addTextChangedListener(TextChangeForChangeBack(editReferenceCode));

    }//_____________________________________________________________________________________________ End SetTextWatcher


    private void CheckProfile() {//_________________________________________________________________ Start CheckProfile

        SharedPreferences prefs = context.getSharedPreferences("wmstoken", 0);
        if (prefs == null) {

        } else {
            String PhoneNumber = prefs.getString("phonenumber", null);
            if (PhoneNumber != null)
                EditPhoneNumber.setText(PhoneNumber);
        }

    }//_____________________________________________________________________________________________ End CheckProfile


    private void BackClick(View view) {//____________________________________________________________________ Start BackClick
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(SetKey(view));
    }//_____________________________________________________________________________________________ End BackClick


    private void GetProvinces() {//_________________________________________________________________ Start GetProvinces
        ShowProgressDialog();
        fragmentProfilePersonViewModel.GetProvincesList();
    }//_____________________________________________________________________________________________ End GetProvinces


    private void GetCitys() {//_____________________________________________________________________ Start GetCitys
        ShowProgressDialog();
        fragmentProfilePersonViewModel.setProvinceId(ProvinceId);
        fragmentProfilePersonViewModel.GetCitysList();
    }//_____________________________________________________________________________________________ End GetCitys


    private void GetPlaces() {//____________________________________________________________________ Start GetPlaces
        ShowProgressDialog();
        fragmentProfilePersonViewModel.setCityId(CityId);
        fragmentProfilePersonViewModel.GetPlasesList();
    }//_____________________________________________________________________________________________ End GetPlaces


    private void ObserverObservables() {//__________________________________________________________ Start ObserverObservables
        fragmentProfilePersonViewModel
                .Observables
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (progress != null)
                                    progress.dismiss();
                                switch (s) {
                                    case "SuccessfulEdit":
                                        ShowMessage(fragmentProfilePersonViewModel.getMessageResponcse()
                                                , getResources().getColor(R.color.mlWhite),
                                                getResources().getDrawable(R.drawable.ic_check));
                                        break;
                                    case "SuccessfulProvince":
                                        ProvincesList = fragmentProfilePersonViewModel.getProvinces();
                                        SetItemProvinces();
                                        break;
                                    case "SuccessfulCity":
                                        CitysList = fragmentProfilePersonViewModel.getCitys();
                                        SetItemCity();
                                        break;
                                    case "SuccessfulPlace":
                                        PlacesList = fragmentProfilePersonViewModel.getPlases();
                                        SetItemPlace();
                                        break;
                                    case "Error":
                                        ShowMessage(fragmentProfilePersonViewModel.getMessageResponcse()
                                                , getResources().getColor(R.color.mlWhite),
                                                getResources().getDrawable(R.drawable.ic_error));
                                        break;
                                    case "Failure":
                                        ShowMessage(getResources().getString(R.string.NetworkError),
                                                getResources().getColor(R.color.mlWhite),
                                                getResources().getDrawable(R.drawable.ic_error));
                                        break;
                                    default:

                                        break;
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }//_____________________________________________________________________________________________ End ObserverObservables


    private void ShowProgressDialog() {//___________________________________________________________ Start ShowProgressDialog
        progress = new DialogProgress(context
                , null, fragmentProfilePersonViewModel);

        progress.setCancelable(false);
        progress.show(getChildFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
    }//_____________________________________________________________________________________________ End ShowProgressDialog


    private void ShowMessage(String message, int color, Drawable icon) {//__________________________ Start ShowMessage

        DialogMessage dialogMessage = new DialogMessage(context, message, color, icon);
        dialogMessage.setCancelable(false);
        dialogMessage.show(getChildFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);

    }//_____________________________________________________________________________________________ End ShowMessage


    private void SetItemProvinces() {//_____________________________________________________________ Start SetItemProvinces

        TextProvinces.setText(getResources().getString(R.string.ChooseProvinces));
        CitysList = null;
        ClickCity = false;
        PlacesList = null;
        ClickPlace = false;
        ProvinceId = "-1";
        CityId = "-1";
        PlaceId = "-1";
        TextCity.setText(getResources().getString(R.string.City_Prompt));
        TextPlace.setText(getResources().getString(R.string.ChoosePlace));
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
                CitysList = null;
                ClickCity = false;
                PlacesList = null;
                ClickPlace = false;
                CityId = "-1";
                PlaceId = "-1";
                TextCity.setText(getResources().getString(R.string.City_Prompt));
                TextPlace.setText(getResources().getString(R.string.ChoosePlace));
                LayoutProvinces.setBackgroundColor(getResources().getColor(R.color.mlEdit));
                GetCitys();
            }
        });

        if (ClickProvince)
            spinnerProvinces.showSpinerDialog();

    }//_____________________________________________________________________________________________ End SetItemProvinces


    private void SetItemCity() {//__________________________________________________________________ Start SetItemCity

        PlacesList = null;
        ClickPlace = false;
        CityId = "-1";
        PlaceId = "-1";
        TextCity.setText(getResources().getString(R.string.City_Prompt));
        TextPlace.setText(getResources().getString(R.string.ChoosePlace));
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerCity = new MLSpinnerDialog(
                getActivity(),
                CitysList,
                getResources().getString(R.string.City_Search),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Ignor));// With 	Animation

        spinnerCity.setCancellable(true); // for cancellable
        spinnerCity.setShowKeyboard(false);// for open keyboard by default
        spinnerCity.bindOnSpinerListener(new OnSpinnerItemClick() {
            @Override
            public void onClick(String item, int position) {
                TextCity.setText(item);
                CityId = CitysList.get(position).getId();
                PlacesList = null;
                ClickPlace = false;
                PlaceId = "-1";
                TextPlace.setText(getResources().getString(R.string.ChoosePlace));
                LayoutCity.setBackgroundColor(getResources().getColor(R.color.mlEdit));
                GetPlaces();
            }
        });

        if (ClickCity)
            spinnerCity.showSpinerDialog();


    }//_____________________________________________________________________________________________ End SetItemCity


    private void SetItemPlace() {//_________________________________________________________________ Start SetItemPlace

        TextPlace.setText(getResources().getString(R.string.ChoosePlace));
        PlaceId = "-1";
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerPlace = new MLSpinnerDialog(
                getActivity(),
                PlacesList,
                getResources().getString(R.string.Place_Search),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Ignor));// With 	Animation
        spinnerPlace.setCancellable(true); // for cancellable
        spinnerPlace.setShowKeyboard(false);// for open keyboard by default
        spinnerPlace.bindOnSpinerListener(new OnSpinnerItemClick() {
            @Override
            public void onClick(String item, int position) {
                TextPlace.setText(item);
                PlaceId = PlacesList.get(position).getId();
                LayoutPlace.setBackgroundColor(getResources().getColor(R.color.mlEdit));
            }
        });

        if (ClickPlace)
            spinnerPlace.showSpinerDialog();

    }//_____________________________________________________________________________________________ End SetItemPlace


    private void SetItemUser() {//__________________________________________________________________ Start SetItemUser

        TextUser.setText(getResources().getString(R.string.ChooseUser));
        ArrayList<ModelProvince> items = new ArrayList<>();
        MLSpinnerDialog spinnerUser;
        items.add(new ModelProvince("0", "خانوار"));
        items.add(new ModelProvince("1", "مدیر ساختمان"));
        items.add(new ModelProvince("2", "سرایدار"));
        items.add(new ModelProvince("3", "، دانش آموز"));
        items.add(new ModelProvince("4", "سایر"));
        //spinnerDialog = new SpinnerDialog(getActivity(),items,"Select or Search City","Close Button Text");// With No Animation
        spinnerUser = new MLSpinnerDialog(
                getActivity(),
                items,
                getResources().getString(R.string.User_Search),
                R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.Ignor));// With 	Animation
        spinnerUser.setCancellable(true); // for cancellable
        spinnerUser.setShowKeyboard(false);// for open keyboard by default
        spinnerUser.bindOnSpinerListener(new OnSpinnerItemClick() {
            @Override
            public void onClick(String item, int position) {
                TextUser.setText(item);
                UserTypeId = items.get(position).getId();
                LayoutUser.setBackgroundColor(getResources().getColor(R.color.mlEdit));
            }
        });

        LayoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerUser.showSpinerDialog();
            }
        });

    }//_____________________________________________________________________________________________ End SetItemUser


}
