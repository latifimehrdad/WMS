package com.ngra.wms.daggers.utility;

import com.ngra.wms.daggers.DaggerScope;
import com.ngra.wms.utility.ApplicationUtility;

import dagger.Component;

@DaggerScope
@Component(modules = UtilityModule.class)
public interface UtilityComponent {

    ApplicationUtility getApplicationUtility();
}
