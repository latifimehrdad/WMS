package com.ngra.wms.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.ngra.wms.R;
import com.ngra.wms.models.ModelMessage;
import com.ngra.wms.models.ModelPackage;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.ModelSettingInfo;
import com.ngra.wms.models.MD_Token;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Response;

public class StaticFunctions {

    public static Boolean isCancel = false;

    public static boolean isLocationEnabled(Context context) {//____________________________________ Start isLocationEnabled
        int locationMode;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }//_____________________________________________________________________________________________ End isLocationEnabled


    public static boolean SaveProfile(
            Context context,
            ModelSettingInfo.ModelProfileSetting profile) {//_______________________________________ SaveProfile
        SharedPreferences.Editor token = context
                .getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0)
                .edit();
        token.putString(context.getString(R.string.ML_Name), profile.getName());
        token.putString(context.getString(R.string.ML_lastName), profile.getLastName());
        token.putInt(context.getString(R.string.ML_Gender), profile.getGender());
        token.putBoolean(context.getString(R.string.ML_CompleteProfile), profile.getProfileCompleted());
        token.putBoolean(context.getString(R.string.ML_AddressCompleted), profile.getAddressCompleted());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        if (profile.getModelPackage() != null) {
            token.putInt(context.getString(R.string.ML_PackageRequestStatus), profile.getModelPackage().getPackageRequest());
            Date d = profile.getModelPackage().getRequestDate();
            if (d != null)
                token.putString(context.getString(R.string.ML_PackageRequestDate), simpleDateFormat.format(d));

            Date dfrom = profile.getModelPackage().getFromDeliver();
            if (dfrom != null)
                token.putString(context.getString(R.string.ML_PackageRequestFrom), simpleDateFormat.format(dfrom));

            Date dto = profile.getModelPackage().getToDeliver();
            if (dto != null)
                token.putString(context.getString(R.string.ML_PackageRequestTo), simpleDateFormat.format(dto));

        }
        token.apply();
        return true;
    }//_____________________________________________________________________________________________ SaveProfile


    public static boolean SaveToken(Context context, MD_Token MDToken) {//_____________________ SaveToken

        SharedPreferences.Editor token = context
                .getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0)
                .edit();

        token.putString(context.getString(R.string.ML_AccessToken), MDToken.getAccess_token());
        token.putString(context.getString(R.string.ML_TokenType), MDToken.getToken_type());
        token.putInt(context.getString(R.string.ML_ExpireSin), MDToken.getExpires_in());
        token.putString(context.getString(R.string.ML_PhoneNumber), MDToken.getPhoneNumber());
        token.putString(context.getString(R.string.ML_ClientId), MDToken.getClient_id());
        token.putString(context.getString(R.string.ML_Issued), MDToken.getIssued());
        token.putString(context.getString(R.string.ML_Expires), MDToken.getExpires());
        token.putString(context.getString(R.string.ML_RefreshToken), MDToken.getRefresh_token());
        token.apply();
        return true;

    }//_____________________________________________________________________________________________ SaveToken



    public static boolean LogOut(Context context) {//_______________________________________________ LogOut
        SharedPreferences.Editor token =
                context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0).edit();

        token.putBoolean(context.getString(R.string.ML_CompleteProfile), false);
        token.putString(context.getString(R.string.ML_AccessToken), null);
        token.putString(context.getString(R.string.ML_TokenType), null);
        token.putInt(context.getString(R.string.ML_ExpireSin), 0);
        token.putString(context.getString(R.string.ML_PhoneNumber), null);
        token.putString(context.getString(R.string.ML_ClientId), null);
        token.putString(context.getString(R.string.ML_Issued), null);
        token.putString(context.getString(R.string.ML_Expires), null);
        token.putInt(context.getString(R.string.ML_PackageRequestStatus), 0);
        token.apply();
        return true;
    }//_____________________________________________________________________________________________ LogOut


    public static ModelPackage PackageRequestDate(Context context) {//______________________________ Start GetAuthorization
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs != null) {
            ModelPackage modelPackage = new ModelPackage();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            String sDate = prefs.getString(context.getString(R.string.ML_PackageRequestDate), null);
            Date date;
            if (sDate != null) {
                try {
                    date = simpleDateFormat.parse(sDate);
                    modelPackage.setRequestDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            sDate = prefs.getString(context.getString(R.string.ML_PackageRequestFrom), null);
            if (sDate != null) {
                try {
                    date = simpleDateFormat.parse(sDate);
                    modelPackage.setFromDeliver(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            sDate = prefs.getString(context.getString(R.string.ML_PackageRequestTo), null);
            if (sDate != null) {
                try {
                    date = simpleDateFormat.parse(sDate);
                    modelPackage.setToDeliver(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            return modelPackage;
        } else
        return null;
    }//_____________________________________________________________________________________________ End GetAuthorization


    public static String GetAuthorization(Context context) {//______________________________________ Start GetAuthorization
        String Authorization = "Bearer ";
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs != null) {
            String access_token = prefs.getString(context.getString(R.string.ML_AccessToken), null);
            if (access_token != null)
                Authorization = Authorization + access_token;
        }
        return Authorization;
    }//_____________________________________________________________________________________________ End GetAuthorization


    public static TextWatcher TextChangeForChangeBack(EditText editText) {//________________________ Satart TextChangeForChangeBack

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setBackgroundResource(R.drawable.dw_edit_back);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

    }//_____________________________________________________________________________________________ End TextChangeForChangeBack


    public static View.OnKeyListener SetBackClickAndGoHome(Boolean execute) {//_____________________ Start SetBackClickAndGoHome
        return (v, keyCode, event) -> {

            if (event.getAction() != KeyEvent.ACTION_DOWN)
                return true;

            return keyCode == 4;
            //if (execute)
            //MainActivity.FragmentMessage.onNext("Main");
        };
    }//_____________________________________________________________________________________________ End SetBackClickAndGoHome


    public static String CheckResponse(Response response, Boolean Authorization) {//________________ Start CheckResponse
        if (response.body() != null)
            return null;
        else {
            if (Authorization) {
                try {
                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                    return jObjError.getString("error_description");
                } catch (Exception ex) {
                    return "Failure";
                }
            } else {
                return GetErrorMessage(response);
            }
        }

    }//_____________________________________________________________________________________________ End CheckResponse


    public static String GetErrorMessage(Response response) {//_____________________________________ Start GetErrorMessage
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            String jobErrorString = jObjError.toString();
            StringBuilder message = new StringBuilder();
            if (jobErrorString.contains("messages")) {
                JSONArray jsonArray = jObjError.getJSONArray("messages");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject temp = new JSONObject(jsonArray.get(i).toString());
                    message.append(temp.getString("message"));
                    message.append("\n");
                }
            } else {
                message.append(jObjError.getString("message"));
            }
            return message.toString();
        } catch (Exception ex) {
            return ex.toString();
        }
    }//_____________________________________________________________________________________________ End GetErrorMessage


    public static String GetMessage(Response<ModelResponsePrimary> response) {//____________________ Start GetMessage
        try {
            ArrayList<ModelMessage> modelMessages = response.body().getMessages();
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < modelMessages.size(); i++) {
                message.append(modelMessages.get(i).getMessage());
                message.append("\n");
            }
            return message.toString();
        } catch (Exception ex) {
            return "Failure";
        }
    }//_____________________________________________________________________________________________ End GetMessage


}
