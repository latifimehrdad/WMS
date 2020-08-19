package com.ngra.wms.viewmodels;

import android.app.Activity;

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


    //______________________________________________________________________________________________ VM_Conversations
    public VM_Conversation(Activity activity) {
        setContext(activity);
    }
    //______________________________________________________________________________________________ VM_Conversations


    //______________________________________________________________________________________________ getAllReply
    public void getAllReply(Integer ticketRef) {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();


        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getReplyList(
                        ticketRef,
                        authorization));

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
    public void sendReply(Integer ReplyId, String message) {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = getAuthorizationTokenFromSharedPreferences();


        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SubmitClientReply(
                        ReplyId,
                        message,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_TicketReplyList>() {
            @Override
            public void onResponse(Call<MR_TicketReplyList> call, Response<MR_TicketReplyList> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    getAllReply(ReplyId);
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
    public void closeTicket(Integer replyId) {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();


        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .CloseTicket(
                        replyId,
                        authorization));

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


}
