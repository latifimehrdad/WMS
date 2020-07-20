package com.ngra.wms.views.fragments;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.ngra.wms.R;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.dialogs.DialogMessage;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class FragmentPrimary extends Fragment {

    private DisposableObserver<Byte> disposableObserver;
    private Activity context;
    private View view;
    private GetMessageFromObservable getMessageFromObservable;
    private VM_Primary vm_primary;
    private NavController navController;


    public interface GetMessageFromObservable {//___________________________________________________ GetMessageFromObservable

        void getMessageFromObservable(Byte action);
    }//_____________________________________________________________________________________________ GetMessageFromObservable


    public FragmentPrimary() {//____________________________________________________________________ FragmentPrimary
    }//_____________________________________________________________________________________________ FragmentPrimary


    @Override
    public void onCreate(Bundle savedInstanceState) {//_____________________________________________ onCreate
        super.onCreate(savedInstanceState);
        context = getActivity();

    }//_____________________________________________________________________________________________ onCreate


    @Override
    public void onDestroy() {//_____________________________________________________________________ onDestroy
        super.onDestroy();
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
    }//_____________________________________________________________________________________________ onDestroy


    @Override
    public void onStop() {//________________________________________________________________________ onStop
        super.onStop();
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
    }//_____________________________________________________________________________________________ onStop


    @Override
    public Activity getContext() {//________________________________________________________________ getContext
        return context;
    }//_____________________________________________________________________________________________ getContext


    @Override
    public View getView() {//_______________________________________________________________________ getView
        return view;
    }//_____________________________________________________________________________________________ getView


    public void setView(View view) {//______________________________________________________________ setView
        this.view = view;
        ButterKnife.bind(this, view);
    }//_____________________________________________________________________________________________ setView


    public void setGetMessageFromObservable(
            GetMessageFromObservable getMessageFromObservable,
            PublishSubject<Byte> publishSubject,
            VM_Primary vm_primary) {//______________________________________________________________ setGetMessageFromObservable
        this.getMessageFromObservable = getMessageFromObservable;
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
        this.vm_primary = vm_primary;
        SetObserverToObservable(publishSubject);
    }//_____________________________________________________________________________________________ setGetMessageFromObservable


    public void SetObserverToObservable(PublishSubject<Byte> publishSubject) {//____________________ SetObserverToObservable

        disposableObserver = new DisposableObserver<Byte>() {
            @Override
            public void onNext(Byte aByte) {
                actionHandler(aByte);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        publishSubject
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

    }//_____________________________________________________________________________________________ SetObserverToObservable


    private void actionHandler(Byte action) {//_____________________________________________________ actionHandler
        if (getContext() != null) {
            getContext().runOnUiThread(() -> {
                getMessageFromObservable.getMessageFromObservable(action);

                if (action.equals(StaticValues.ML_DialogClose))
                    return;

                if (vm_primary.getResponseMessage() == null)
                    return;

                if (vm_primary.getResponseMessage().equalsIgnoreCase(""))
                    return;

                if ((action.equals(StaticValues.ML_RequestCancel))
                        || (action.equals(StaticValues.ML_ResponseError))
                        || (action.equals(StaticValues.ML_ResponseFailure)))
                    ShowMessage(vm_primary.getResponseMessage(),
                            getResources().getColor(R.color.mlWhite),
                            getResources().getDrawable(R.drawable.ic_error),
                            getResources().getColor(R.color.mlCollectRight1));
                else
                    ShowMessage(vm_primary.getResponseMessage()
                            , getResources().getColor(R.color.mlWhite),
                            getResources().getDrawable(R.drawable.ic_check),
                            getResources().getColor(R.color.colorPrimaryDark));

            });
        }
    }//_____________________________________________________________________________________________ actionHandler


    public void ShowMessage(String message, int color, Drawable icon, int tintColor) {//____________ ShowMessage

        if (getFragmentManager() != null) {
            DialogMessage dialogMessage = new DialogMessage(getContext(), message, color, icon, tintColor, vm_primary.getPublishSubject());
            dialogMessage.setCancelable(false);
            dialogMessage.show(getFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
        }

    }//_____________________________________________________________________________________________ ShowMessage


    public void hideKeyboard() {//__________________________________________________________________ hideKeyboard
        if (getContext() != null) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = getContext().getCurrentFocus();
            if (view == null) {
                view = new View(getContext());
            }
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }//_____________________________________________________________________________________________ hideKeyboard


}