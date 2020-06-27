package com.example.wms.viewmodels;

import io.reactivex.subjects.PublishSubject;
import retrofit2.Call;

public class VM_Primary {

    private PublishSubject<Byte> publishSubject;
    private String ResponseMessage;
    private Call PrimaryCall;

    public VM_Primary() {//_________________________________________________________________________ VM_Primary
        publishSubject = PublishSubject.create();
    }//_____________________________________________________________________________________________ VM_Primary

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
}
