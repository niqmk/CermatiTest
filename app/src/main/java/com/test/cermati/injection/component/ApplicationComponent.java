package com.test.cermati.injection.component;

import com.test.cermati.AppController;
import com.test.cermati.injection.ApplicationScope;
import com.test.cermati.injection.module.ApplicationModule;
import com.test.cermati.url.ObservableServices;

import dagger.Component;

@ApplicationScope
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
	void inject(AppController app);

	AppController provideApplication();

	ObservableServices provideObservableService();
}