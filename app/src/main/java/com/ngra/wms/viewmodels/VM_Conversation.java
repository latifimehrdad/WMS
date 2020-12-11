package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitApis;
import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_TicketReplyDto;
import com.ngra.wms.models.MR_TicketReplyList;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Conversation extends VM_Primary {

    private List<MD_TicketReplyDto> md_ticketReplyDtos;
    private String message;
    private Integer ReplyId;


    //______________________________________________________________________________________________ VM_Conversations
    public VM_Conversation(Activity activity) {
        setContext(activity);
    }
    //______________________________________________________________________________________________ VM_Conversations


    //______________________________________________________________________________________________ getAllReply
    public void getAllReply() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();


        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getReplyList(getReplyId(), RetrofitApis.app_token,aToken,
                        authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_TicketReplyList>() {
            @Override
            public void onResponse(Call<MR_TicketReplyList> call, Response<MR_TicketReplyList> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_ticketReplyDtos = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetAllTicketReply);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MR_TicketReplyList> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getAllReply


    //______________________________________________________________________________________________ sendReply
    public void sendReply() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();


        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SubmitClientReply(
                        getReplyId(),
                        getMessage(),
                        RetrofitApis.app_token,
                        aToken,
                        Authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_TicketReplyList>() {
            @Override
            public void onResponse(Call<MR_TicketReplyList> call, Response<MR_TicketReplyList> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    getAllReply();
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MR_TicketReplyList> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ sendReply


    //______________________________________________________________________________________________ closeTicket
    public void closeTicket() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .CloseTicket(
                        getReplyId(),
                        RetrofitApis.app_token,
                        aToken,
                        authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_TicketReplyList>() {
            @Override
            public void onResponse(Call<MR_TicketReplyList> call, Response<MR_TicketReplyList> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    sendActionToObservable(StaticValues.ML_CloseTicket);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MR_TicketReplyList> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ closeTicket


    //______________________________________________________________________________________________ getMd_ticketReplyDtos
    public List<MD_TicketReplyDto> getMd_ticketReplyDtos() {
        return md_ticketReplyDtos;
    }
    //______________________________________________________________________________________________ getMd_ticketReplyDtos



    //______________________________________________________________________________________________ getMessage
    public String getMessage() {
        return message;
    }
    //______________________________________________________________________________________________ getMessage



    //______________________________________________________________________________________________ setMessage
    public void setMessage(String message) {
        this.message = message;
    }
    //______________________________________________________________________________________________ setMessage



    //______________________________________________________________________________________________ getReplyId
    public Integer getReplyId() {
        return ReplyId;
    }
    //______________________________________________________________________________________________ getReplyId



    //______________________________________________________________________________________________ setReplyId
    public void setReplyId(Integer replyId) {
        ReplyId = replyId;
    }
    //______________________________________________________________________________________________ setReplyId



}
