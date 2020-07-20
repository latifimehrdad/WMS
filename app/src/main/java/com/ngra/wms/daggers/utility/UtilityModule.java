package com.ngra.wms.daggers.utility;

        import com.ngra.wms.daggers.DaggerScope;
        import com.ngra.wms.utility.ApplicationUtility;

        import dagger.Module;
        import dagger.Provides;

@Module
public class UtilityModule {

    @Provides
    @DaggerScope
    public ApplicationUtility getApplicationUtility() {
        return new ApplicationUtility();
    }
}
