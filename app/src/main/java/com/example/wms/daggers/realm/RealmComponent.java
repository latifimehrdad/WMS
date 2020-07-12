package com.example.wms.daggers.realm;


import com.example.wms.daggers.DaggerScope;

import dagger.Component;
import io.realm.Realm;

@DaggerScope
@Component(modules = {RealmModule.class})
public interface RealmComponent {
    Realm getRealm();
}
