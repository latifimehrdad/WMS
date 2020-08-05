package com.ngra.wms.viewmodels.callwithus;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_TicketReplyDto;
import com.ngra.wms.models.MR_TicketReplyList;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Conversation extends VM_Primary {

    private List<MD_TicketReplyDto> md_ticketReplyDtos;

    public VM_Conversation(Activity activity) {//__________________________________________________ VM_Conversations
        setContext(activity);
    }//_____________________________________________________________________________________________ VM_Conversations



    public void GetAllReply(Integer TicketRef) {//__________________________________________________________________ GetAllTicket

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();


        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getReplyList(
                        TicketRef,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_TicketReplyList>() {
            @Override
            public void onResponse(Call<MR_TicketReplyList> call, Response<MR_TicketReplyList> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    md_ticketReplyDtos = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_GetAllTicketReply);
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MR_TicketReplyList> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetAllTicket




    public void SendReply(Integer ReplyId, String message) {//______________________________________ SendReply

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();


        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .SubmitClientReply(
                        ReplyId,
                        message,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_TicketReplyList>() {
            @Override
            public void onResponse(Call<MR_TicketReplyList> call, Response<MR_TicketReplyList> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    GetAllReply(ReplyId);
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MR_TicketReplyList> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ SendReply




    public void closeTicket(Integer ReplyId) {//____________________________________________________ closeTicket

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();


        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .CloseTicket(
                        ReplyId,
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_TicketReplyList>() {
            @Override
            public void onResponse(Call<MR_TicketReplyList> call, Response<MR_TicketReplyList> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    SendMessageToObservable(StaticValues.ML_CloseTicket);
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MR_TicketReplyList> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ closeTicket




    public List<MD_TicketReplyDto> getMd_ticketReplyDtos() {//______________________________________ getMd_ticketReplyDtos
        return md_ticketReplyDtos;
    }//_____________________________________________________________________________________________ getMd_ticketReplyDtos


}
