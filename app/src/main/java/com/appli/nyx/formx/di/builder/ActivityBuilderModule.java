package com.appli.nyx.formx.di.builder;

import com.appli.nyx.formx.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

	@ContributesAndroidInjector(modules = FragmentBuilderModule.class)
	abstract MainActivity mainActivity();

}
