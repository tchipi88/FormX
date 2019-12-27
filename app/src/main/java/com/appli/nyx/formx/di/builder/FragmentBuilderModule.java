package com.appli.nyx.formx.di.builder;

import com.appli.nyx.formx.ui.fragment.account.ChangePasswordFragment;
import com.appli.nyx.formx.ui.fragment.account.ProfilEditFragment;
import com.appli.nyx.formx.ui.fragment.account.ProfilFragment;
import com.appli.nyx.formx.ui.fragment.account.ResetPasswordRequestFragment;
import com.appli.nyx.formx.ui.fragment.account.SignInFragment;
import com.appli.nyx.formx.ui.fragment.account.SignUpFragment;
import com.appli.nyx.formx.ui.fragment.business.ClusterFragment;
import com.appli.nyx.formx.ui.fragment.business.EnqueteFragment;
import com.appli.nyx.formx.ui.fragment.business.ReportsFragment;
import com.appli.nyx.formx.ui.fragment.business.StatsFragment;
import com.appli.nyx.formx.ui.fragment.business.form.FormAddDialog;
import com.appli.nyx.formx.ui.fragment.business.form.FormEditDialog;
import com.appli.nyx.formx.ui.fragment.business.form.FormFragment;
import com.appli.nyx.formx.ui.fragment.business.form.FormListFragment;
import com.appli.nyx.formx.ui.fragment.business.form.SectionAddDialog;
import com.appli.nyx.formx.ui.fragment.business.form.SectionEditDialog;
import com.appli.nyx.formx.ui.fragment.business.form.SectionFragment;
import com.appli.nyx.formx.ui.fragment.business.form.question.QuestionAddDialog;
import com.appli.nyx.formx.ui.fragment.business.form.question.dialog.BooleanQuestionDialog;
import com.appli.nyx.formx.ui.fragment.business.form.question.dialog.DateQuestionDialog;
import com.appli.nyx.formx.ui.fragment.business.form.question.dialog.NumberQuestionDialog;
import com.appli.nyx.formx.ui.fragment.business.form.question.dialog.SpinnerQuestionDialog;
import com.appli.nyx.formx.ui.fragment.business.form.question.dialog.TextQuestionDialog;
import com.appli.nyx.formx.ui.fragment.business.form.question.dialog.TimeQuestionDialog;
import com.appli.nyx.formx.ui.fragment.settings.SettingsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

	@ContributesAndroidInjector
	abstract SignInFragment signInFragment();

	@ContributesAndroidInjector
	abstract SignUpFragment signUpFragment();

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

	@ContributesAndroidInjector
	abstract ClusterFragment clusterFragment();

	@ContributesAndroidInjector
	abstract EnqueteFragment enqueteFragment();

	@ContributesAndroidInjector
	abstract FormListFragment formListFragment();

	@ContributesAndroidInjector
	abstract FormFragment formFragment();

	@ContributesAndroidInjector
	abstract SectionFragment sectionFragment();

	@ContributesAndroidInjector
	abstract ReportsFragment reportsFragment();

	@ContributesAndroidInjector
	abstract StatsFragment statsFragment();

	@ContributesAndroidInjector
	abstract FormAddDialog formAddDialog();

	@ContributesAndroidInjector
	abstract FormEditDialog formEditDialog();

	@ContributesAndroidInjector
	abstract SectionAddDialog sectionAddDialog();

	@ContributesAndroidInjector
	abstract SectionEditDialog sectionEditDialog();

	@ContributesAndroidInjector
	abstract QuestionAddDialog questionAddDialog();

	@ContributesAndroidInjector
	abstract BooleanQuestionDialog booleanDialog();

	@ContributesAndroidInjector
	abstract TimeQuestionDialog timeDialog();

	@ContributesAndroidInjector
	abstract DateQuestionDialog dateDialog();

	@ContributesAndroidInjector
	abstract SpinnerQuestionDialog spinnerDialog();

	@ContributesAndroidInjector
	abstract TextQuestionDialog textDialog();

	@ContributesAndroidInjector
	abstract NumberQuestionDialog numberDialog();


}


