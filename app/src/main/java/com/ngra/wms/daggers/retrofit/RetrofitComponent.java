package com.ngra.wms.daggers.retrofit;

import com.ngra.wms.daggers.DaggerScope;

import dagger.Component;

@DaggerScope
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    RetrofitApiInterface getRetrofitApiInterface();
}
