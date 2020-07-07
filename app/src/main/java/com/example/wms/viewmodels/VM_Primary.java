package com.example.wms.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.wms.R;
import com.example.wms.models.ModelMessage;
import com.example.wms.models.ModelResponsePrimary;
import com.example.wms.utility.StaticValues;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Response;

public class VM_Primary {

    private PublishSubject<Byte> publishSubject;
    private String ResponseMessage;
    private Call PrimaryCall;
    private Activity context;

    public VM_Primary() {//_________________________________________________________________________ VM_Primary
        publishSubject = PublishSubject.create();
    }//_____________________________________________________________________________________________ VM_Primary


    public String GetAuthorization() {//____________________________________________________________ GetAuthorization
        String Authorization = "Bearer ";
        SharedPreferences prefs = getContext().getSharedPreferences(getContext().getString(R.string.ML_SharePreferences), 0);
        if (prefs != null) {
            String access_token = prefs.getString(context.getString(R.string.ML_AccessToken), null);
            if (access_token != null)
                Authorization = Authorization + access_token;
        }
        return Authorization;
    }//_____________________________________________________________________________________________ GetAuthorization



    public String CheckResponse(Response response, Boolean Authorization) {//_______________________ CheckResponse
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

    }//_____________________________________________________________________________________________ CheckResponse



    public String GetErrorMessage(Response response) {//____________________________________________ GetErrorMessage
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
    }//_____________________________________________________________________________________________ GetErrorMessage


    public String GetMessage(Response<ModelResponsePrimary> response) {//___________________________ GetMessage
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
    }//_____________________________________________________________________________________________ GetMessage



    public void OnFailureRequest() {//______________________________________________________________ OnFailureRequest
        if (getPrimaryCall().isCanceled()) {
            setResponseMessage(getContext().getResources().getString(R.string.RequestCancel));
            getPublishSubject().onNext(StaticValues.ML_RequestCancel);
        }
        else {
            setResponseMessage(getContext().getResources().getString(R.string.NetworkError));
            getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
        }
    }//_____________________________________________________________________________________________ OnFailureRequest


    public void CancelRequest() {//_________________________________________________________________ CancelRequest
        if (PrimaryCall != null)
            PrimaryCall.cancel();
    }//_____________________________________________________________________________________________ CancelRequest


    public Call getPrimaryCall() {//________________________________________________________________ getPrimaryCall
        return PrimaryCall;
    }//_____________________________________________________________________________________________ getPrimaryCall

    public void setPrimaryCall(Call primaryCall) {//________________________________________________ setPrimaryCall
        PrimaryCall = null;
        PrimaryCall = primaryCall;
    }//_____________________________________________________________________________________________ setPrimaryCall

    public PublishSubject<Byte> getPublishSubject() {//_____________________________________________ getPublishSubject
        return publishSubject;
    }//_____________________________________________________________________________________________ getPublishSubject


    public String getResponseMessage() {//__________________________________________________________ getResponseMessage
        return ResponseMessage;
    }//_____________________________________________________________________________________________ getResponseMessage

    public void setResponseMessage(String responseMessage) {//______________________________________ setResponseMessage
        ResponseMessage = responseMessage;
    }//_____________________________________________________________________________________________ setResponseMessage


    public Activity getContext() {//________________________________________________________________ getContext
        return context;
    }//_____________________________________________________________________________________________ getContext


    public void setContext(Activity context) {//____________________________________________________ setContext
        this.context = context;
    }//_____________________________________________________________________________________________ setContext

}
