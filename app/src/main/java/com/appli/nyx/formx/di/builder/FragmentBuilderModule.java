package com.appli.nyx.formx.di.builder;

import com.appli.nyx.formx.ui.fragment.account.ChangePasswordFragment;
import com.appli.nyx.formx.ui.fragment.account.ProfilEditFragment;
import com.appli.nyx.formx.ui.fragment.account.ProfilFragment;
import com.appli.nyx.formx.ui.fragment.account.ResetPasswordRequestFragment;
import com.appli.nyx.formx.ui.fragment.account.SignInFragment;
import com.appli.nyx.formx.ui.fragment.settings.SettingsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

	@ContributesAndroidInjector
	abstract SignInFragment signInFragment();

	@ContributesAndroidInjector
	abstract ProfilEditFragment signUpEndFragment();

	@ContributesAndroidInjector
	abstract ResetPasswordRequestFragment resetPasswordRequestFragment();

	@ContributesAndroidInjector
	abstract ChangePasswordFragment changePasswordFragment();

	@ContributesAndroidInjector
	abstract ProfilFragment profilFragment();

	@ContributesAndroidInjector
	abstract SettingsFragment settingsFragment();
}


