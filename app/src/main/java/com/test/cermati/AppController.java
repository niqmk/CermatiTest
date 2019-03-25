package com.test.cermati;

import android.app.Application;
import android.content.Context;

import com.test.cermati.injection.component.ApplicationComponent;
import com.test.cermati.injection.component.DaggerApplicationComponent;
import com.test.cermati.injection.module.ApplicationModule;

import androidx.multidex.MultiDex;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class AppController extends Application {
    public Scheduler scheduler;
    private ApplicationComponent applicationComponent;

    public AppController() {
    }

    public static AppController get(Context context) {
        return (AppController) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public Scheduler getSubscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }
        return scheduler;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
