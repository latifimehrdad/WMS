package com.example.wms.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.example.wms.R;
import com.example.wms.daggers.retrofit.RetrofitApis;
import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.models.ModelMessage;
import com.example.wms.models.ModelResponcePrimery;
import com.example.wms.models.ModelSettingInfo;
import com.example.wms.models.ModelToken;
import com.example.wms.views.activitys.MainActivity;
import com.example.wms.views.application.ApplicationWMS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaticFunctions {

    public static Boolean isCancel = false;


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
        token.putBoolean(context.getString(R.string.ML_IsPackageState), profile.getPackageRequested());
        token.putInt(context.getString(R.string.ML_PackageRequest), profile.getModelPackage().getPackageRequest());
        Date d = profile.getModelPackage().getRequestDate();
        if (d != null)
            token.putString(context.getString(R.string.ML_PackageRequestDate), String.valueOf(d));
        token.apply();
        return true;
    }//_____________________________________________________________________________________________ SaveProfile


    public static boolean SaveToken(Context context, ModelToken modelToken) {//_____________________ SaveToken

        SharedPreferences.Editor token = context
                .getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0)
                .edit();

        token.putString(context.getString(R.string.ML_AccessToken), modelToken.getAccess_token());
        token.putString(context.getString(R.string.ML_TokenType), modelToken.getToken_type());
        token.putInt(context.getString(R.string.ML_ExpireSin), modelToken.getExpires_in());
        token.putString(context.getString(R.string.ML_PhoneNumber), modelToken.getPhoneNumber());
        token.putString(context.getString(R.string.ML_ClientId), modelToken.getClient_id());
        token.putString(context.getString(R.string.ML_Issued), modelToken.getIssued());
        token.putString(context.getString(R.string.ML_Expires), modelToken.getExpires());
        token.apply();
        return true;

    }//_____________________________________________________________________________________________ SaveToken


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
                editText.setBackgroundResource(R.drawable.edit_normal_background);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

    }//_____________________________________________________________________________________________ End TextChangeForChangeBack


    public static View.OnKeyListener SetBackClickAndGoHome(Boolean execute) {//_____________________ Start SetBackClickAndGoHome
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;

                if (keyCode != 4) {
                    return false;
                }
                //if (execute)
                //MainActivity.FragmentMessage.onNext("Main");
                return true;
            }
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
            String message = "";
            if (jobErrorString.contains("messages")) {
                JSONArray jsonArray = jObjError.getJSONArray("messages");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject temp = new JSONObject(jsonArray.get(i).toString());
                    message = message + temp.getString("message");
                    message = message + "\n";
                }
            } else {
                message = message + jObjError.getString("message");
            }
            return message;
        } catch (Exception ex) {
            return ex.toString();
        }
    }//_____________________________________________________________________________________________ End GetErrorMessage


    public static String GetMessage(Response<ModelResponcePrimery> response) {//____________________ Start GetMessage
        try {
            ArrayList<ModelMessage> modelMessages = response.body().getMessages();
            String message = "";
            for (int i = 0; i < modelMessages.size(); i++) {
                message = message + modelMessages.get(i).getMessage();
                message = message + "\n";
            }
            return message;
        } catch (Exception ex) {
            return "Failure";
        }
    }//_____________________________________________________________________________________________ End GetMessage


}
