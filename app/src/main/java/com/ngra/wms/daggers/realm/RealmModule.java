package com.ngra.wms.daggers.realm;

import com.ngra.wms.daggers.DaggerScope;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class RealmModule {

    @Provides
    @DaggerScope
    public Realm getRealm() {//_____________________________________________________________________ Start getRealm
        return Realm.getDefaultInstance();
    }//_____________________________________________________________________________________________ End getRealm
}
