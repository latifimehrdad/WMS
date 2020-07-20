package com.example.wms.views.application;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.multidex.MultiDexApplication;

import com.example.wms.R;
import com.example.wms.daggers.imageloader.DaggerImageLoaderComponent;
import com.example.wms.daggers.imageloader.ImageLoaderComponent;
import com.example.wms.daggers.imageloader.ImageLoaderModule;
import com.example.wms.daggers.realm.DaggerRealmComponent;
import com.example.wms.daggers.realm.RealmComponent;
import com.example.wms.daggers.realm.RealmModule;
import com.example.wms.daggers.retrofit.DaggerRetrofitComponent;
import com.example.wms.daggers.retrofit.RetrofitComponent;
import com.example.wms.daggers.retrofit.RetrofitModule;
import com.example.wms.daggers.utility.DaggerUtilityComponent;
import com.example.wms.daggers.utility.UtilityComponent;
import com.example.wms.daggers.utility.UtilityModule;
import com.example.wms.utility.SSLCertificateHandler;
import com.nostra13.universalimageloader.cache.memory.BaseMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.lang.ref.Reference;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.realm.Realm;
import io.realm.RealmConfiguration;


public class ApplicationWMS extends MultiDexApplication {

    private Context context;
    private RetrofitComponent retrofitComponent;
    private UtilityComponent utilityComponent;
    private ImageLoaderComponent imageLoaderComponent;
    private RealmComponent realmComponent;


    @Override
    public void onCreate() {//______________________________________________________________________ onCreate
        super.onCreate();
        SSLCertificateHandler.nuke();
        this.context = getApplicationContext();
        ConfigurationCalligraphy();
        ConfigurationRetrofitComponent();
        ConfigurationUtilityComponent();
        ConfigurationImageLoader();
        ConfigurationRealmComponent();
        initPlaces();
    }//_____________________________________________________________________________________________ onCreate


    private void initPlaces() {

    }


    private void ConfigurationImageLoader() {//_____________________________________________________ ConfigurationImageLoader

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .displayer(new FadeInBitmapDisplayer(100))
                .build();


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new BaseMemoryCache() {
                    @Override
                    protected Reference<Bitmap> createReference(Bitmap value) {
                        return null;
                    }
                })
                .threadPoolSize(3)
                .diskCacheSize(100 * 1024 * 1024)
                .build();
        ImageLoader.getInstance().init(config);

        imageLoaderComponent = DaggerImageLoaderComponent.builder().imageLoaderModule(new ImageLoaderModule()).build();
    }//_____________________________________________________________________________________________ ConfigurationImageLoader()


    private void ConfigurationUtilityComponent() {//________________________________________________ ConfigurationUtilityComponent
        utilityComponent = DaggerUtilityComponent
                .builder()
                .utilityModule(new UtilityModule())
                .build();
    }//_____________________________________________________________________________________________ ConfigurationUtilityComponent


    private void ConfigurationRetrofitComponent() {//________________________________________________ ConfigurationRetrofitComponent
        retrofitComponent = DaggerRetrofitComponent
                .builder()
                .retrofitModule(new RetrofitModule(context))
                .build();
    }//_____________________________________________________________________________________________ ConfigurationRetrofitComponent


    private void ConfigurationCalligraphy() {//_____________________________________________________ ConfigurationCalligraphy
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/iransanslight.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

    }//_____________________________________________________________________________________________ ConfigurationCalligraphy


    private void ConfigurationRealmComponent() {//__________________________________________________ ConfigurationRealmComponent
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .name("WMS")
                .schemaVersion(1).build());
        realmComponent = DaggerRealmComponent
                .builder()
                .realmModule(new RealmModule())
                .build();
    }//_____________________________________________________________________________________________ ConfigurationRealmComponent



    public static ApplicationWMS getApplicationWMS(Context context) {//_____________________________ getApplicationWMS
        return (ApplicationWMS) context.getApplicationContext();
    }//_____________________________________________________________________________________________ getApplicationWMS


    public RetrofitComponent getRetrofitComponent() {//_____________________________________________ getRetrofitComponent
        return retrofitComponent;
    }//_____________________________________________________________________________________________ getRetrofitComponent


    public UtilityComponent getUtilityComponent() {//_______________________________________________ getUtilityComponent
        return utilityComponent;
    }//_____________________________________________________________________________________________ getUtilityComponent


    public ImageLoaderComponent getImageLoaderComponent() {//_______________________________________ getImageLoaderComponent
        return imageLoaderComponent;
    }//_____________________________________________________________________________________________ getImageLoaderComponent

    public RealmComponent getRealmComponent() {//___________________________________________________ Start getRealmComponent
        return realmComponent;
    }//_____________________________________________________________________________________________ End getRealmComponent


}
