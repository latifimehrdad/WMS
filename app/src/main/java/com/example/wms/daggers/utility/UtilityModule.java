package com.example.wms.daggers.utility;

        import com.example.wms.daggers.DaggerScope;
        import com.example.wms.utility.ApplicationUtility;

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
