package com.ngra.wms.viewmodels;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;

import com.ngra.wms.R;
import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.ModelMessage;
import com.ngra.wms.models.ModelResponsePrimary;
import com.ngra.wms.models.ModelToken;
import com.ngra.wms.utility.StaticFunctions;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Primary {

    private PublishSubject<Byte> publishSubject;
    private String ResponseMessage;
    private Call PrimaryCall;
    private Activity context;
    private int responseCode;

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


    public String GetRefreshToken() {//_____________________________________________________________ GetRefreshToken
        String Authorization = "";
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.ML_SharePreferences), 0);
        if (prefs != null) {
            String access_token = prefs.getString(context.getString(R.string.ML_RefreshToken), null);
            if (access_token != null)
                Authorization = Authorization + access_token;
        }
        return Authorization;
    }//_____________________________________________________________________________________________ GetRefreshToken



    public String CheckResponse(Response response, Boolean Authorization) {//_______________________ CheckResponse
        if (response.body() != null)
            return null;
        else {
            if (Authorization)
                return CheckAuthorizationResponse(response);
            else
                return CheckNormalResponse(response);
        }
    }//_____________________________________________________________________________________________ CheckResponse


    private String CheckAuthorizationResponse(Response response) {//________________________________ CheckResponseAuthorization

        JSONObject jObjError = null;
        try {
            setResponseCode(response.code());
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

    }//_____________________________________________________________________________________________ CheckResponseAuthorization


    public String CheckNormalResponse(Response response) {//________________________________________ CheckNormalResponse
        try {
            setResponseCode(response.code());
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
    }//_____________________________________________________________________________________________ CheckNormalResponse


    public String GetMessage(ModelResponsePrimary responsePrimary) {//______________________________ GetMessage
        try {
            ArrayList<ModelMessage> modelMessages = responsePrimary.getMessages();
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


    public void ReactionToIncorrectResponse(Byte action) {//________________________________________ ReactionToIncorrectResponse

        switch (getResponseCode()) {
            case 401:
                RefreshToken();
                break;
            default:
                publishSubject.onNext(action);
                break;
        }

    }//_____________________________________________________________________________________________ ReactionToIncorrectResponse




    private void RefreshToken() {//_________________________________________________________________ RefreshToken


        setResponseMessage(getContext().getResources().getString(R.string.PleaseTryAgain));
        publishSubject.onNext(StaticValues.ML_ResponseError);

/*        RetrofitComponent retrofitComponent = ApplicationWMS
                .getApplicationWMS(getContext())
                .getRetrofitComponent();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getToken(
                        RetrofitApis.client_id_value,
                        RetrofitApis.client_secret_value,
                        RetrofitApis.grant_type_value));

        getPrimaryCall().enqueue(new Callback<ModelToken>() {
            @Override
            public void onResponse(Call<ModelToken> call, Response<ModelToken> response) {
                setResponseMessage(CheckResponse(response, true));
                if (getResponseMessage() == null) {
                    if (StaticFunctions.SaveToken(getContext(), response.body()))
                        setResponseMessage(getContext().getResources().getString(R.string.PleaseTryAgain));
                        publishSubject.onNext(StaticValues.ML_ResponseError);
                } else
                    publishSubject.onNext(StaticValues.ML_ResponseFailure);
            }

            @Override
            public void onFailure(Call<ModelToken> call, Throwable t) {
                OnFailureRequest();
            }
        });*/

    }//_____________________________________________________________________________________________ RefreshToken



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
        if (action.equals(StaticValues.ML_ResponseError))
            handler.postDelayed(() -> ReactionToIncorrectResponse(action), 200);
        else
            handler.postDelayed(() -> publishSubject.onNext(action), 200);

    }//_____________________________________________________________________________________________ SendMessageToObservable


    public int getResponseCode() {//________________________________________________________________ getResponseCode
        return responseCode;
    }//_____________________________________________________________________________________________ getResponseCode


    public void setResponseCode(int responseCode) {//_______________________________________________ setResponseCode
        this.responseCode = responseCode;
    }//_____________________________________________________________________________________________ setResponseCode


}
