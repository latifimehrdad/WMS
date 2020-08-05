package com.ngra.wms.viewmodels.callwithus;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitComponent;
import com.ngra.wms.models.MD_UsersTicketList;
import com.ngra.wms.models.MR_TicketList;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VM_Ticket extends VM_Primary {

    private List<MD_UsersTicketList> md_usersTicketLists;

    public VM_Ticket(Activity context) {//__________________________________________________________ VM_UserMessage
        setContext(context);
    }//_____________________________________________________________________________________________ VM_UserMessage



    public void GetAllTicket() {//__________________________________________________________________ GetAllTicket

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String Authorization = GetAuthorization();


        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getTicketList(
                        Authorization));

        getPrimaryCall().enqueue(new Callback<MR_TicketList>() {
            @Override
            public void onResponse(Call<MR_TicketList> call, Response<MR_TicketList> response) {
                setResponseMessage(CheckResponse(response, false));
                if (getResponseMessage() == null) {
                    md_usersTicketLists = response.body().getResult();
                    SendMessageToObservable(StaticValues.ML_GetAllTicket);
                } else
                    SendMessageToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MR_TicketList> call, Throwable t) {
                OnFailureRequest();
            }
        });

    }//_____________________________________________________________________________________________ GetAllTicket


    public List<MD_UsersTicketList> getMd_usersTicketLists() {//____________________________________ getMd_usersTicketLists
        return md_usersTicketLists;
    }//_____________________________________________________________________________________________ getMd_usersTicketLists


}
