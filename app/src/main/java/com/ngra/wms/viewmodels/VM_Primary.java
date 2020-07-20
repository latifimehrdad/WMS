package com.ngra.wms.viewmodels;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;

import com.ngra.wms.R;
import com.ngra.wms.models.ModelMessage;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.utility.StaticValues;

import org.json.JSONArray;
import org.json.JSONException;
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
                JSONObject jObjError = null;
                try {
                    jObjError = new JSONObject(response.errorBody().string());
                    return jObjError.getString("error_description");
                } catch (Exception ex) {
                    try {
                        return jObjError.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return getContext().getResources().getString(R.string.NetworkError);
                    }
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
    }//_____________________________________________________________________________________________ GetErrorMessage


    public String GetMessage(Response<ModelResponsePrimary> response) {//___________________________ GetMessage
        try {
            ArrayList<ModelMessage> modelMessages = response.body().getMessages();
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < modelMessages.size(); i++) {
                message.append(modelMessages.get(i).getMessage());
                message.append("\n");
            }
            return message.toString();
        } catch (Exception ex) {
            return getContext().getResources().getString(R.string.NetworkError);
        }
    }//_____________________________________________________________________________________________ GetMessage


    public void OnFailureRequest() {//______________________________________________________________ OnFailureRequest
        if (getPrimaryCall().isCanceled()) {
            setResponseMessage("");
            getPublishSubject().onNext(StaticValues.ML_RequestCancel);
        } else {
            setResponseMessage(getContext().getResources().getString(R.string.NetworkError));
            getPublishSubject().onNext(StaticValues.ML_ResponseFailure);
        }
    }//_____________________________________________________________________________________________ OnFailureRequest


    public void CancelRequest() {//_________________________________________________________________ CancelRequest
        if (PrimaryCall != null) {
            PrimaryCall.cancel();
            PrimaryCall = null;
        }
    }//_____________________________________________________________________________________________ CancelRequest


    public Call getPrimaryCall() {//________________________________________________________________ getPrimaryCall
        return PrimaryCall;
    }//_____________________________________________________________________________________________ getPrimaryCall

    public void setPrimaryCall(Call primaryCall) {//________________________________________________ setPrimaryCall
        CancelRequest();
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


    public void SendMessageToObservable(Byte action) {//____________________________________________ SendMessageToObservable
        Handler handler = new Handler();
        handler.postDelayed(() -> publishSubject.onNext(action),200);

    }//_____________________________________________________________________________________________ SendMessageToObservable

}
