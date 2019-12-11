package com.example.wms.viewmodels.user.login;

import android.content.Context;
import android.os.Handler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class ActivityBeforLoginViewModel {

    private Context context;
    public PublishSubject<String> Observables = null;
    private boolean isCancel;

    public ActivityBeforLoginViewModel(Context context) {//_________________________________________ Start ActivityBeforLoginViewModel
        this.context = context;
        Observables = PublishSubject.create();
        ObserverObservables();
    }//_____________________________________________________________________________________________ End ActivityBeforLoginViewModel



    public void SendNumber(String PhoneNumbet, String Password) {//________________________________ Start SendNumber
        isCancel = false;
        GetDataSuccess();
    }//_____________________________________________________________________________________________ End SendNumber




    private void GetDataSuccess() {//_______________________________________________________________ Start ObserverObservables

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isCancel)
                    return;
                Observables.onNext("success");
            }
        },2000);

    }//_____________________________________________________________________________________________ End ObserverObservables



    private void ObserverObservables() {//__________________________________________________________ Start ObserverObservables
        Observables
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        switch (s){
                            case "CancelByUser":
                                isCancel = true;
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }//_____________________________________________________________________________________________ End ObserverObservables



}
