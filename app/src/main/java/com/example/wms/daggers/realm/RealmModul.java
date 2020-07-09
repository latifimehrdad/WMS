package com.example.wms.daggers.realm;

import com.example.wms.daggers.DaggerScope;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class RealmModul {

    @Provides
    @DaggerScope
    public Realm getRealm() {//_____________________________________________________________________ Start getRealm
        return Realm.getDefaultInstance();
    }//_____________________________________________________________________________________________ End getRealm
}
