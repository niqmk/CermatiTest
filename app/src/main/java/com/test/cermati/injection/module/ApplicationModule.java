package com.test.cermati.injection.module;

import com.test.cermati.AppController;
import com.test.cermati.injection.ApplicationScope;
import com.test.cermati.url.ObservableServices;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final AppController app;

    public ApplicationModule(AppController app){ this.app = app;}

    @Provides
    @ApplicationScope
    public AppController provideApplication() {
        return app;
    }

    @Provides
    @ApplicationScope
    public ObservableServices provideObservableService(){
        return new ObservableServices(this.app);
    }
}
