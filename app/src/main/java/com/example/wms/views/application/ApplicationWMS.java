package com.example.wms.views.application;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.example.wms.R;
import com.example.wms.daggers.retrofit.DaggerRetrofitComponent;
import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.daggers.retrofit.RetrofitModule;
import com.example.wms.daggers.utility.DaggerUtilityComponent;
import com.example.wms.daggers.utility.UtilityComponent;
import com.example.wms.daggers.utility.UtilityModule;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class ApplicationWMS extends MultiDexApplication {

    Context context;
    RetrofitComponent retrofitComponent;
    UtilityComponent utilityComponent;


    @Override
    public void onCreate() {//______________________________________________________________________ Start onCreate
        super.onCreate();
        this.context = getApplicationContext();
        ConfigurationCalligraphy();
        ConfigrationRetrofitComponent();
        ConfigrationUtilityComponent();
    }//_____________________________________________________________________________________________ End onCreate


    private void ConfigrationUtilityComponent() {//_________________________________________________ Start ConfigrationUtilityComponent
        utilityComponent = DaggerUtilityComponent
                .builder()
                .utilityModule(new UtilityModule())
                .build();
    }//_____________________________________________________________________________________________ End ConfigrationUtilityComponent


    private void ConfigrationRetrofitComponent() {//________________________________________________ Start ConfigrationRetrofitComponent
        retrofitComponent = DaggerRetrofitComponent
                .builder()
                .retrofitModule(new RetrofitModule(context))
                .build();
    }//_____________________________________________________________________________________________ End ConfigrationRetrofitComponent


    private void ConfigurationCalligraphy() {//_____________________________________________________ Start ConfigurationCalligraphy
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/iransanslight.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

    }//_____________________________________________________________________________________________ End ConfigurationCalligraphy


    public static ApplicationWMS getApplicationWMS(Context context) {//_____________________________ Start getApplicationWMS
        return (ApplicationWMS) context.getApplicationContext();
    }//_____________________________________________________________________________________________ End getApplicationWMS


    public RetrofitComponent getRetrofitComponent() {//_____________________________________________ Start getRetrofitComponent
        return retrofitComponent;
    }//_____________________________________________________________________________________________ End getRetrofitComponent


    public UtilityComponent getUtilityComponent() {//_______________________________________________ Start getUtilityComponent
        return utilityComponent;
    }//_____________________________________________________________________________________________ End getUtilityComponent
}
