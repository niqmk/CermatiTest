package com.test.cermati.injection.module;

import android.app.Activity;

import com.test.cermati.injection.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    final Activity mActivity;

    public ActivityModule(Activity activity) {
    	mActivity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return this.mActivity;
    }
}
