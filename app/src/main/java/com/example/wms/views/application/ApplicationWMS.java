package com.example.wms.views.application;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import com.example.wms.R;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class ApplicationWMS extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ConfigurationCalligraphy();
    }

    private void ConfigurationCalligraphy() {
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/iransanslight.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

//        CalligraphyConfig
//                .initDefault(new Builder()
//                        .setDefaultFontPath("font/iransanslight.ttf").setFontAttrId(R.attr.fontPath).build());
    }


}
