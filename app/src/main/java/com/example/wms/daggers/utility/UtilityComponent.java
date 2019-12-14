package com.example.wms.daggers.utility;

import com.example.wms.daggers.DaggerScope;
import com.example.wms.utility.ApplicationUtility;

import dagger.Component;

@DaggerScope
@Component(modules = UtilityModule.class)
public interface UtilityComponent {

    ApplicationUtility getApplicationUtility();
}
