package com.ngra.wms.viewmodels;

import android.app.Activity;

import com.ngra.wms.daggers.retrofit.RetrofitApis;
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

    //______________________________________________________________________________________________ VM_UserMessage
    public VM_Ticket(Activity context) {
        setContext(context);
    }
    //______________________________________________________________________________________________ VM_UserMessage


    //______________________________________________________________________________________________ getAllTicket
    public void getAllTicket() {

        RetrofitComponent retrofitComponent =
                ApplicationWMS
                        .getApplicationWMS(getContext())
                        .getRetrofitComponent();

        String authorization = getAuthorizationTokenFromSharedPreferences();
        String aToken = get_aToken();

        setPrimaryCall(retrofitComponent
                .getRetrofitApiInterface()
                .getTicketList(RetrofitApis.app_token,
                        aToken,
                        authorization));

        if (getPrimaryCall() == null)
            return;

        getPrimaryCall().enqueue(new Callback<MR_TicketList>() {
            @Override
            public void onResponse(Call<MR_TicketList> call, Response<MR_TicketList> response) {
                setResponseMessage(checkResponse(response, false));
                if (getResponseMessage() == null) {
                    md_usersTicketLists = response.body().getResult();
                    sendActionToObservable(StaticValues.ML_GetAllTicket);
                } else
                    sendActionToObservable(StaticValues.ML_ResponseError);
            }

            @Override
            public void onFailure(Call<MR_TicketList> call, Throwable t) {
                onFailureRequest();
            }
        });

    }
    //______________________________________________________________________________________________ getAllTicket


    //______________________________________________________________________________________________ getMd_usersTicketLists
    public List<MD_UsersTicketList> getMd_usersTicketLists() {
        return md_usersTicketLists;
    }
    //______________________________________________________________________________________________ getMd_usersTicketLists


}
