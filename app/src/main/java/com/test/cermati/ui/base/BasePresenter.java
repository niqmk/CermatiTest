package com.test.cermati.ui.base;

import android.content.Context;

public interface BasePresenter<V extends BaseView> {
	void attachView(V view, Context context);

	void detachView();
}