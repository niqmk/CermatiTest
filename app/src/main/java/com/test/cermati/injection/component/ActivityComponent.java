package com.test.cermati.injection.component;

import android.app.Activity;

import com.test.cermati.injection.ActivityScope;
import com.test.cermati.injection.module.ActivityModule;
import com.test.cermati.ui.main.MainActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity provideActivity();

    void inject(MainActivity mainActivity);
}