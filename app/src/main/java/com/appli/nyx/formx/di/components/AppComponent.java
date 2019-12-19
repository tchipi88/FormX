package com.appli.nyx.formx.di.components;

import android.app.Application;

import com.appli.nyx.formx.MyApplication;
import com.appli.nyx.formx.di.builder.ActivityBuilderModule;
import com.appli.nyx.formx.di.module.AppModule;
import com.appli.nyx.formx.di.module.DaoModule;
import com.appli.nyx.formx.di.module.NetModule;
import com.appli.nyx.formx.di.module.RepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = { AndroidSupportInjectionModule.class, AppModule.class, ActivityBuilderModule.class, RepositoryModule.class, NetModule.class, DaoModule.class })
public interface AppComponent {

	void inject(MyApplication htaApplication);

	@Component.Builder
	interface Builder {

		@BindsInstance
		Builder application(Application application);

		AppComponent build();
	}

}