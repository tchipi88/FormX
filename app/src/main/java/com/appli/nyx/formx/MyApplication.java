package com.appli.nyx.formx;

import android.app.Activity;
import android.app.Application;

import androidx.fragment.app.Fragment;

import com.appli.nyx.formx.di.components.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MyApplication extends Application implements HasActivityInjector, HasSupportFragmentInjector {

	@Inject
	DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
	@Inject
	DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

	@Override
	public DispatchingAndroidInjector<Activity> activityInjector() {
		return dispatchingAndroidInjector;
	}

	@Override
	public AndroidInjector<Fragment> supportFragmentInjector() {
		return fragmentDispatchingAndroidInjector;
	}

	public static MyApplication mInstance;

	public static synchronized MyApplication getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		initializeComponent();

		/*if (!BuildConfig.DEBUG) {
			FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
		}*/
	}

	private void initializeComponent() {
		DaggerAppComponent.builder().application(this).build().inject(this);
	}

}
