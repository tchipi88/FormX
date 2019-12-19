package com.appli.nyx.formx.di.module;

import android.app.Application;

import com.appli.nyx.formx.BuildConfig;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class NetModule {

	@Provides
	@Singleton
	public Cache provideHttpCache(Application application) {
		long cacheSize = 10 * 1024 * 1024L;
		Cache cache = new Cache(application.getCacheDir(), cacheSize);
		return cache;
	}

	@Provides
	@Singleton
	public Gson provideGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		return gsonBuilder.create();
	}

	public Interceptor provideHttpLoggingInterceptor() {
		HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
		if (BuildConfig.DEBUG) {
			httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		} else {
			httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
		}
		return httpLoggingInterceptor;
	}

}