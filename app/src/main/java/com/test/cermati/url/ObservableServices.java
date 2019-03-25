package com.test.cermati.url;

import com.test.cermati.AppController;
import com.test.cermati.model.RepoModel;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class ObservableServices {
    private AppController app;

    @Inject
    public ObservableServices(AppController app) {
        this.app = app;
    }

    public Observable<RepoModel> getRepos(final String query, final int page, final int limit) {
        ApiServices api = ApiServices.Factory.apiServices();
        return api.getRepos("Basic bmlxbWs6MTQ3NTk2MzEwbQ==", query, page, limit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(app.getSubscribeScheduler());
    }
}
