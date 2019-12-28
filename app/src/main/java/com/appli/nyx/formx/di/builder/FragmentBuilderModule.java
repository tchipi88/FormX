package com.appli.nyx.formx.di.builder;

import com.appli.nyx.formx.ui.fragment.account.ChangePasswordFragment;
import com.appli.nyx.formx.ui.fragment.account.ProfilEditFragment;
import com.appli.nyx.formx.ui.fragment.account.ProfilFragment;
import com.appli.nyx.formx.ui.fragment.account.ResetPasswordRequestFragment;
import com.appli.nyx.formx.ui.fragment.account.SignInFragment;
import com.appli.nyx.formx.ui.fragment.account.SignUpFragment;
import com.appli.nyx.formx.ui.fragment.business.ClusterFragment;
import com.appli.nyx.formx.ui.fragment.business.enquete.EnqueteFragment;
import com.appli.nyx.formx.ui.fragment.business.ReportsFragment;
import com.appli.nyx.formx.ui.fragment.business.enquete.EnqueteListFragment;
import com.appli.nyx.formx.ui.fragment.business.form.FormAddDialog;
import com.appli.nyx.formx.ui.fragment.business.form.FormEditDialog;
import com.appli.nyx.formx.ui.fragment.business.form.FormFragment;
import com.appli.nyx.formx.ui.fragment.business.form.FormListFragment;
import com.appli.nyx.formx.ui.fragment.business.form.FormViewFragment;
import com.appli.nyx.formx.ui.fragment.business.form.SectionAddDialog;
import com.appli.nyx.formx.ui.fragment.business.form.SectionEditDialog;
import com.appli.nyx.formx.ui.fragment.business.form.SectionFragment;
import com.appli.nyx.formx.ui.fragment.business.form.question.QuestionAddDialog;
import com.appli.nyx.formx.ui.fragment.business.form.question.BooleanQuestionFragment;
import com.appli.nyx.formx.ui.fragment.business.form.question.DateQuestionFragment;
import com.appli.nyx.formx.ui.fragment.business.form.question.NumberQuestionFragment;
import com.appli.nyx.formx.ui.fragment.business.form.question.SpinnerQuestionFragment;
import com.appli.nyx.formx.ui.fragment.business.form.question.TextQuestionFragment;
import com.appli.nyx.formx.ui.fragment.business.form.question.TimeQuestionFragment;
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
	abstract FormListFragment formListFragment();

	@ContributesAndroidInjector
	abstract FormFragment formFragment();

	@ContributesAndroidInjector
	abstract SectionFragment sectionFragment();

	@ContributesAndroidInjector
	abstract ReportsFragment reportsFragment();

	@ContributesAndroidInjector
	abstract FormViewFragment formViewFragment();

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
	abstract BooleanQuestionFragment booleanDialog();

	@ContributesAndroidInjector
	abstract TimeQuestionFragment timeDialog();

	@ContributesAndroidInjector
	abstract DateQuestionFragment dateDialog();

	@ContributesAndroidInjector
	abstract SpinnerQuestionFragment spinnerDialog();

	@ContributesAndroidInjector
	abstract TextQuestionFragment textDialog();

	@ContributesAndroidInjector
	abstract NumberQuestionFragment numberDialog();

	@ContributesAndroidInjector
	abstract EnqueteListFragment enqueteListFragment();

	@ContributesAndroidInjector
	abstract EnqueteFragment enqueteFragment();

}


