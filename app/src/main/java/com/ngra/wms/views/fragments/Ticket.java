package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentTicketBinding;

import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Ticket;
import com.ngra.wms.views.adaptors.AP_Tickets;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class Ticket extends FragmentPrimary implements
        FragmentPrimary.getActionFromObservable,
        AP_Tickets.ItemTicketClick {

    private VM_Ticket vm_ticket;
    private NavController navController;
    private boolean gotoNew;

    @BindView(R.id.ImageViewNew)
    ImageView ImageViewNew;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.RecyclerViewTicket)
    RecyclerView RecyclerViewTicket;

    @BindView(R.id.TextViewNoRequest)
    TextView TextViewNoRequest;

    //______________________________________________________________________________________________ Ticket
    public Ticket() {
    }
    //______________________________________________________________________________________________ Ticket


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        if (getView() == null) {
            gotoNew = false;
            vm_ticket = new VM_Ticket(getContext());
            FragmentTicketBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_ticket, container, false
            );
            binding.setTicket(vm_ticket);
            setView(binding.getRoot());
            setOnClick();
            gifLoading.setVisibility(View.VISIBLE);
            vm_ticket.getAllTicket();
            TextViewNoRequest.setVisibility(View.GONE);
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                Ticket.this,
                vm_ticket.getPublishSubject(),
                vm_ticket);
        if (getView() != null)
            navController = Navigation.findNavController(getView());
        if (gotoNew) {
            gotoNew = false;
            RecyclerViewTicket.setAdapter(null);
            gifLoading.setVisibility(View.VISIBLE);
            vm_ticket.getAllTicket();
        }
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ setOnClick
    private void setOnClick() {

        ImageViewNew.setOnClickListener(v -> {
            gotoNew = true;
            navController.navigate(R.id.action_ticket_to_callSupport);
        });


        RecyclerViewTicket.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (ImageViewNew.getVisibility() == View.VISIBLE) {
                        ImageViewNew.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom));
                        ImageViewNew.setVisibility(View.GONE);
                    }
                    // Scrolling up
                } else {
                    if (ImageViewNew.getVisibility() == View.GONE) {
                        ImageViewNew.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottom));
                        ImageViewNew.setVisibility(View.VISIBLE);
                    }
                    // Scrolling down
                }
            }

        });


    }
    //______________________________________________________________________________________________ setOnClick


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        gifLoading.setVisibility(View.GONE);
        if (action.equals(StaticValues.ML_GetAllTicket)) {
            setAdapter();
        }
    }
    //______________________________________________________________________________________________ getActionFromObservable



    //______________________________________________________________________________________________ actionWhenFailureRequest
    @Override
    public void actionWhenFailureRequest() {
    }
    //______________________________________________________________________________________________ actionWhenFailureRequest



    //______________________________________________________________________________________________ setAdapter
    private void setAdapter() {

        AP_Tickets ap_tickets = new AP_Tickets(vm_ticket.getMd_usersTicketLists(), Ticket.this);
        RecyclerViewTicket.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewTicket.setAdapter(ap_tickets);

        if (vm_ticket.getMd_usersTicketLists().size() > 0)
            TextViewNoRequest.setVisibility(View.GONE);
        else
            TextViewNoRequest.setVisibility(View.VISIBLE);

    }
    //______________________________________________________________________________________________ setAdapter


    //______________________________________________________________________________________________ itemChoose
    @Override
    public void itemChoose(Integer position) {
        if (getContext() != null) {
            gotoNew = true;
            Bundle bundle = new Bundle();
            bundle.putInt(getContext().getResources().getString(R.string.ML_Id), vm_ticket.getMd_usersTicketLists().get(position).getId());
            bundle.putInt(getContext().getResources().getString(R.string.ML_Type), vm_ticket.getMd_usersTicketLists().get(position).getStatus());
            navController.navigate(R.id.action_ticket_to_conversation, bundle);
        }
    }
    //______________________________________________________________________________________________ itemChoose


}
