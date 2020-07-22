package com.ngra.wms.views.fragments.callwithus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentTicketBinding;

import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.callwithus.VM_Ticket;
import com.ngra.wms.views.adaptors.AP_Tickets;
import com.ngra.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class Ticket extends FragmentPrimary implements
        FragmentPrimary.GetMessageFromObservable,
        AP_Tickets.ItemTicketClick {

    private VM_Ticket vm_ticket;

    private NavController navController;

    @BindView(R.id.ImageViewNew)
    ImageView ImageViewNew;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.RecyclerViewTicket)
    RecyclerView RecyclerViewTicket;


    public Ticket() {//________________________________________________________________________ UserMessage
    }//_____________________________________________________________________________________________ UserMessage


    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView

        if (getView() == null) {
            vm_ticket = new VM_Ticket(getContext());
            FragmentTicketBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_ticket, container, false
            );
            binding.setTicket(vm_ticket);
            setView(binding.getRoot());
            SetOnClick();
            gifLoading.setVisibility(View.VISIBLE);
            vm_ticket.GetAllTicket();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                Ticket.this,
                vm_ticket.getPublishSubject(),
                vm_ticket);
        if (getView() != null)
            navController = Navigation.findNavController(getView());
    }//_____________________________________________________________________________________________ onStart


    private void SetOnClick() {//___________________________________________________________________ SetOnClick

        ImageViewNew.setOnClickListener(v -> {
            navController.navigate(R.id.action_goto_support);
        });

    }//_____________________________________________________________________________________________ SetOnClick


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

        gifLoading.setVisibility(View.GONE);
        if (action.equals(StaticValues.ML_GetAllTicket)) {
            SetAdapter();
        }

    }//_____________________________________________________________________________________________ GetMessageFromObservable


    private void SetAdapter() {//___________________________________________________________________ SetAdapter

        AP_Tickets ap_tickets = new AP_Tickets(vm_ticket.getMd_usersTicketLists(), Ticket.this);
        RecyclerViewTicket.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewTicket.setAdapter(ap_tickets);

    }//_____________________________________________________________________________________________ SetAdapter



    @Override
    public void itemChoose(Integer position) {//____________________________________________________ itemChoose

    }//_____________________________________________________________________________________________ itemChoose


}