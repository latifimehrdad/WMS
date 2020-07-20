package com.ngra.wms.views.fragments.callwithus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.ngra.wms.R;
import com.ngra.wms.databinding.FragmentUserMessageBinding;

import com.ngra.wms.viewmodels.callwithus.VM_UserMessage;
import com.ngra.wms.views.fragments.FragmentPrimary;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class UserMessage extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {

    private VM_UserMessage vm_userMessage;

    private NavController navController;

    @BindView(R.id.ButtonNew)
    Button ButtonNew;

    public UserMessage() {//________________________________________________________________________ UserMessage
    }//_____________________________________________________________________________________________ UserMessage


    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView

        if (getView() == null) {
            vm_userMessage = new VM_UserMessage(getContext());
            FragmentUserMessageBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_user_message,container, false
            );
            binding.setUserMessage(vm_userMessage);
            setView(binding.getRoot());
            SetOnClick();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView



    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                UserMessage.this,
                vm_userMessage.getPublishSubject(),
                vm_userMessage);
        if (getView() != null)
            navController = Navigation.findNavController(getView());
    }//_____________________________________________________________________________________________ onStart



    private void SetOnClick() {//___________________________________________________________________ SetOnClick

        ButtonNew.setOnClickListener(v -> {
            navController.navigate(R.id.action_goto_support);
        });

    }//_____________________________________________________________________________________________ SetOnClick


    @Override
    public void getMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable

}
