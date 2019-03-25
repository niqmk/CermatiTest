package com.test.cermati.url;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuilder {
	public static Gson constructDefaultGson() {
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
				.create();
		return gson;
	}

	public static Retrofit retrofit() {
		return new Retrofit.Builder()
				.baseUrl("https://api.github.com/")
				.client(getTLSClient())
				.addConverterFactory(GsonConverterFactory.create(constructDefaultGson()))
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
	}

	public static OkHttpClient getTLSClient() {
		final OkHttpClient client = new OkHttpClient();
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
			try {
				SSLContext sc = SSLContext.getInstance("TLSv1.2");
				sc.init(null, null, null);

				ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
						.tlsVersions(TlsVersion.TLS_1_2)
						.build();

				List<ConnectionSpec> specs = new ArrayList<>();
				specs.add(cs);
				specs.add(ConnectionSpec.COMPATIBLE_TLS);
				specs.add(ConnectionSpec.CLEARTEXT);

				return client.newBuilder()
						.addInterceptor(interceptor)
						.connectTimeout(3000, TimeUnit.SECONDS)
						.writeTimeout(3000, TimeUnit.SECONDS)
						.readTimeout(3000, TimeUnit.SECONDS)
						.sslSocketFactory(new TLSSocketFactory(sc.getSocketFactory()))
						.connectionSpecs(specs)
						.build();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return client.newBuilder()
				.addInterceptor(interceptor)
				.connectTimeout(3000, TimeUnit.SECONDS)
				.writeTimeout(3000, TimeUnit.SECONDS)
				.readTimeout(3000, TimeUnit.SECONDS)
				.build();
	}
}