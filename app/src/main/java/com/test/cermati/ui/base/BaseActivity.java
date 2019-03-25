package com.test.cermati.ui.base;

import android.os.Bundle;

import com.test.cermati.AppController;
import com.test.cermati.injection.component.ActivityComponent;
import com.test.cermati.injection.component.DaggerActivityComponent;
import com.test.cermati.injection.module.ActivityModule;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
	private ActivityComponent component;

	public ActivityComponent component() {
		if (this.component == null) {
			this.component = DaggerActivityComponent.builder()
					.applicationComponent(getApp().getApplicationComponent())
					.activityModule(new ActivityModule(this))
					.build();

		}
		return this.component;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	protected AppController getApp() { return (AppController) getApplication(); }

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}
}