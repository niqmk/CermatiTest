package com.test.cermati.ui.main;

import android.content.Context;

import com.test.cermati.model.RepoModel;
import com.test.cermati.ui.base.BasePresenter;
import com.test.cermati.url.ObservableServices;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class MainPresenter implements BasePresenter<MainView> {
	private MainView mainView;
	private Subscription subscription = Subscriptions.empty();
	private Context context;
	private ObservableServices observableServices;

	@Inject
	public MainPresenter(ObservableServices observableServices) {
		this.observableServices = observableServices;
	}

	@Override
	public void attachView(MainView view, Context ctx) {
		mainView = view;
		context = ctx;
	}

	@Override
	public void detachView() {
		mainView = null;
		if (subscription != null) subscription.unsubscribe();
	}

	public void getRepos(String query, int page, int limit) {
		observableServices.getRepos(query, page, limit)
				.subscribe(new Observer<RepoModel>() {
					@Override
					public void onCompleted() {
					}

					@Override
					public void onError(Throwable e) {
						mainView.getUsersFailed(e.getMessage());
					}

					@Override
					public void onNext(RepoModel repoModel) {
						if (mainView != null) {
							mainView.showData(repoModel.getTotal_count(), repoModel.getItems());
						}
					}
				});
	}
}