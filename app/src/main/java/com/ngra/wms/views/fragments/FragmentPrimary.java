package com.ngra.wms.views.fragments;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.ngra.wms.R;
import com.ngra.wms.utility.ApplicationUtility;
import com.ngra.wms.utility.StaticValues;
import com.ngra.wms.viewmodels.VM_Primary;
import com.ngra.wms.views.application.ApplicationWMS;
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
    private getActionFromObservable getActionFromObservable;
    private VM_Primary vm_primary;


    //______________________________________________________________________________________________ getActionFromObservable
    public interface getActionFromObservable {
        void getActionFromObservable(Byte action);

        void actionWhenFailureRequest();
    }
    //______________________________________________________________________________________________ getActionFromObservable


    //______________________________________________________________________________________________ FragmentPrimary
    public FragmentPrimary() {
    }
    //______________________________________________________________________________________________ FragmentPrimary


    //______________________________________________________________________________________________ onCreate
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }
    //______________________________________________________________________________________________ onCreate


    //______________________________________________________________________________________________ onDestroy
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
    }
    //______________________________________________________________________________________________ onDestroy


    //______________________________________________________________________________________________ onStop
    @Override
    public void onStop() {
        super.onStop();
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
    }
    //______________________________________________________________________________________________ onStop


    //______________________________________________________________________________________________ getContext
    @Override
    public Activity getContext() {
        return context;
    }
    //______________________________________________________________________________________________ getContext


    //______________________________________________________________________________________________ getView
    @Override
    public View getView() {
        return view;
    }
    //______________________________________________________________________________________________ getView


    //______________________________________________________________________________________________ setView
    public void setView(View view) {
        this.view = view;
        ButterKnife.bind(this, view);
    }
    //______________________________________________________________________________________________ setView


    //______________________________________________________________________________________________ setPublishSubjectFromObservable
    public void setPublishSubjectFromObservable(
            getActionFromObservable getActionFromObservable,
            PublishSubject<Byte> publishSubject,
            VM_Primary vm_primary) {
        this.getActionFromObservable = getActionFromObservable;
        if (disposableObserver != null)
            disposableObserver.dispose();
        disposableObserver = null;
        this.vm_primary = vm_primary;
        setObserverToObservable(publishSubject);
    }
    //______________________________________________________________________________________________ setPublishSubjectFromObservable


    //______________________________________________________________________________________________ setObserverToObservable
    public void setObserverToObservable(PublishSubject<Byte> publishSubject) {

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
    }
    //______________________________________________________________________________________________ setObserverToObservable


    //______________________________________________________________________________________________ actionHandler
    private void actionHandler(Byte action) {
        if (getContext() != null) {
            getContext().runOnUiThread(() -> {
                getActionFromObservable.getActionFromObservable(action);

                if (action.equals(StaticValues.ML_DialogClose))
                    return;

                if (vm_primary.getResponseMessage() == null)
                    return;

                if (vm_primary.getResponseMessage().equalsIgnoreCase(""))
                    return;

                if ((action.equals(StaticValues.ML_RequestCancel))
                        || (action.equals(StaticValues.ML_ResponseError))
                        || (action.equals(StaticValues.ML_ResponseFailure))
                        || (action.equals(StaticValues.ML_InternetAccessFailed))) {
                    showMessageDialog(vm_primary.getResponseMessage(),
                            getResources().getColor(R.color.mlWhite),
                            getResources().getDrawable(R.drawable.ic_error),
                            getResources().getColor(R.color.mlCollectRight1));
                    getActionFromObservable.actionWhenFailureRequest();
                    return;
                } else
                    showMessageDialog(vm_primary.getResponseMessage()
                            , getResources().getColor(R.color.mlWhite),
                            getResources().getDrawable(R.drawable.ic_check),
                            getResources().getColor(R.color.colorPrimaryDark));
            });
        }
    }
    //______________________________________________________________________________________________ actionHandler


    //______________________________________________________________________________________________ showMessageDialog
    public void showMessageDialog(String message, int color, Drawable icon, int tintColor) {

        if (getFragmentManager() != null) {
            DialogMessage dialogMessage = new DialogMessage(getContext(), message, color, icon, tintColor, vm_primary.getPublishSubject());
            dialogMessage.setCancelable(false);
            dialogMessage.show(getFragmentManager(), NotificationCompat.CATEGORY_PROGRESS);
        }
    }
    //______________________________________________________________________________________________ showMessageDialog


    //______________________________________________________________________________________________ hideKeyboard
    public void hideKeyboard() {
        if (getContext() != null) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = getContext().getCurrentFocus();
            if (view == null) {
                view = new View(getContext());
            }
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    //______________________________________________________________________________________________ hideKeyboard


    //______________________________________________________________________________________________ getApplicationUtility
    public ApplicationUtility getApplicationUtility() {

        return ApplicationWMS.getApplicationWMS(getContext())
                .getUtilityComponent()
                .getApplicationUtility();
    }
    //______________________________________________________________________________________________ getApplicationUtility


    //______________________________________________________________________________________________ textChangeForChangeBack
    public TextWatcher textChangeForChangeBack(EditText editText) {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setBackgroundResource(R.drawable.dw_edit_back);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

    }
    //______________________________________________________________________________________________ textChangeForChangeBack



}