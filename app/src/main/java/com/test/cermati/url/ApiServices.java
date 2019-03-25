package com.test.cermati.url;

import com.test.cermati.model.RepoModel;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiServices {
	@GET("search/repositories")
	Observable<RepoModel> getRepos(
			@Header("Authorization") String authorization,
			@Query("q") String query,
			@Query("page") int page,
			@Query("per_page") int per_page);

	class Factory {
		public static ApiServices apiServices() {
			Retrofit client = ApiBuilder.retrofit();
			return client.create(ApiServices.class);
		}
	}
}