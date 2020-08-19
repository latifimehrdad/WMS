package com.ngra.wms.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cunoraz.gifview.library.GifView;
import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentConversationBinding;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Conversation;

import com.ngra.wms.views.adaptors.AP_TicketReply;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class Conversation extends FragmentPrimary implements FragmentPrimary.getActionFromObservable {


    private VM_Conversation vm_conversation;
    private Integer TicketRef;
    private Integer status;

    @BindView(R.id.gifLoading)
    GifView gifLoading;

    @BindView(R.id.RecyclerViewReply)
    RecyclerView RecyclerViewReply;

    @BindView(R.id.EditTextMessage)
    EditText EditTextMessage;

    @BindView(R.id.ImageViewSend)
    ImageView ImageViewSend;

    @BindView(R.id.gifLoadingSend)
    GifView gifLoadingSend;

    @BindView(R.id.ImageViewDelete)
    ImageView ImageViewDelete;

    @BindView(R.id.gifLoadingDelete)
    GifView gifLoadingDelete;

    @BindView(R.id.LinearLayoutActions)
    LinearLayout LinearLayoutActions;

    //______________________________________________________________________________________________ Conversation
    public Conversation() {
    }
    //______________________________________________________________________________________________ Conversation


    //______________________________________________________________________________________________ onCreateView
    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        if (getView() == null) {
            vm_conversation = new VM_Conversation(getContext());
            FragmentConversationBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_conversation, container, false
            );
            binding.setConversation(vm_conversation);
            setView(binding.getRoot());
            setOnClick();
            if (getContext() != null && getArguments() != null) {
                TicketRef = getArguments().getInt(getContext().getResources().getString(R.string.ML_Id), 0);
                status = getArguments().getInt(getContext().getResources().getString(R.string.ML_Type), 0);
            }
            gifLoading.setVisibility(View.VISIBLE);
            vm_conversation.getAllReply(TicketRef);
        }
        return getView();
    }
    //______________________________________________________________________________________________ onCreateView


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        setPublishSubjectFromObservable(
                Conversation.this,
                vm_conversation.getPublishSubject(),
                vm_conversation);
        if (status.byteValue() == StaticValues.TicketStatusClosed) {
            LinearLayoutActions.setVisibility(View.GONE);
        }
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getActionFromObservable
    @Override
    public void getActionFromObservable(Byte action) {

        gifLoading.setVisibility(View.GONE);

        if (action.equals(StaticValues.ML_GetAllTicketReply)) {
            EditTextMessage.getText().clear();
            gifLoadingSend.setVisibility(View.GONE);
            ImageViewSend.setVisibility(View.VISIBLE);
            setAdapter();
            return;
        }

        if (action.equals(StaticValues.ML_CloseTicket)) {
            LinearLayoutActions.setVisibility(View.GONE);
            gifLoadingDelete.setVisibility(View.GONE);
            ImageViewDelete.setVisibility(View.VISIBLE);
        }

    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ setOnClick
    private void setOnClick() {

        ImageViewSend.setOnClickListener(v -> {
            if (EditTextMessage.getText().toString().length() > 0) {
                gifLoadingSend.setVisibility(View.VISIBLE);
                ImageViewSend.setVisibility(View.GONE);
                vm_conversation.sendReply(TicketRef, EditTextMessage.getText().toString());
            }
        });

        ImageViewDelete.setOnClickListener(v -> {
            gifLoadingDelete.setVisibility(View.VISIBLE);
            ImageViewDelete.setVisibility(View.GONE);
            vm_conversation.closeTicket(TicketRef);
        });

    }
    //______________________________________________________________________________________________ setOnClick


    //______________________________________________________________________________________________ setAdapter
    private void setAdapter() {
        AP_TicketReply ap_ticketReply = new AP_TicketReply(vm_conversation.getMd_ticketReplyDtos());
        RecyclerViewReply.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        RecyclerViewReply.setAdapter(ap_ticketReply);
        if (vm_conversation.getMd_ticketReplyDtos().size() > 0)
            RecyclerViewReply.smoothScrollToPosition(vm_conversation.getMd_ticketReplyDtos().size() - 1);
    }
    //______________________________________________________________________________________________ setAdapter


}
