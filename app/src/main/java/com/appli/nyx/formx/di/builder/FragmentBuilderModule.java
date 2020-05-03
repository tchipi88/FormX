package com.appli.nyx.formx.di.builder;

import com.appli.nyx.formx.ui.fragment.account.ChangePasswordFragment;
import com.appli.nyx.formx.ui.fragment.account.ProfilEditFragment;
import com.appli.nyx.formx.ui.fragment.account.ProfilFragment;
import com.appli.nyx.formx.ui.fragment.account.ResetPasswordRequestFragment;
import com.appli.nyx.formx.ui.fragment.account.SignInFragment;
import com.appli.nyx.formx.ui.fragment.account.SignUpFragment;
import com.appli.nyx.formx.ui.fragment.business.SelectUserFragment;
import com.appli.nyx.formx.ui.fragment.business.cluster.ClusterAddDialog;
import com.appli.nyx.formx.ui.fragment.business.cluster.ClusterEditDialog;
import com.appli.nyx.formx.ui.fragment.business.cluster.ClusterFragment;
import com.appli.nyx.formx.ui.fragment.business.cluster.ClusterListFragment;
import com.appli.nyx.formx.ui.fragment.business.cluster.ClusterStatsFragment;
import com.appli.nyx.formx.ui.fragment.business.cluster.SelectEnqueteFragment;
import com.appli.nyx.formx.ui.fragment.business.enquete.EnqueteAddDialog;
import com.appli.nyx.formx.ui.fragment.business.enquete.EnqueteCreatedFragment;
import com.appli.nyx.formx.ui.fragment.business.enquete.EnqueteEditDialog;
import com.appli.nyx.formx.ui.fragment.business.enquete.EnqueteFragment;
import com.appli.nyx.formx.ui.fragment.business.enquete.EnqueteJoinedFragment;
import com.appli.nyx.formx.ui.fragment.business.enquete.EnqueteMenuFragment;
import com.appli.nyx.formx.ui.fragment.business.enquete.EnqueteReplyFragment;
import com.appli.nyx.formx.ui.fragment.business.enquete.EnqueteReplyIntroFragment;
import com.appli.nyx.formx.ui.fragment.business.enquete.EnqueteSharedFragment;
import com.appli.nyx.formx.ui.fragment.business.enquete.EnqueteVisibilityDialog;
import com.appli.nyx.formx.ui.fragment.business.enquete.JoinEnqueteFragment;
import com.appli.nyx.formx.ui.fragment.business.enquete.MyEnqueteFragment;
import com.appli.nyx.formx.ui.fragment.business.form.FormAddDialog;
import com.appli.nyx.formx.ui.fragment.business.form.FormEditDialog;
import com.appli.nyx.formx.ui.fragment.business.form.FormFragment;
import com.appli.nyx.formx.ui.fragment.business.form.FormListFragment;
import com.appli.nyx.formx.ui.fragment.business.form.FormViewFragment;
import com.appli.nyx.formx.ui.fragment.business.form.ImportSectionFragment;
import com.appli.nyx.formx.ui.fragment.business.form.SectionAddDialog;
import com.appli.nyx.formx.ui.fragment.business.form.SectionEditDialog;
import com.appli.nyx.formx.ui.fragment.business.form.SectionFragment;
import com.appli.nyx.formx.ui.fragment.business.form.SelectFormFragment;
import com.appli.nyx.formx.ui.fragment.business.form.question.BooleanQuestionFragment;
import com.appli.nyx.formx.ui.fragment.business.form.question.ChooseQuestionTypeDialog;
import com.appli.nyx.formx.ui.fragment.business.form.question.DateQuestionFragment;
import com.appli.nyx.formx.ui.fragment.business.form.question.NumberQuestionFragment;
import com.appli.nyx.formx.ui.fragment.business.form.question.SpinnerQuestionFragment;
import com.appli.nyx.formx.ui.fragment.business.form.question.TextQuestionFragment;
import com.appli.nyx.formx.ui.fragment.business.form.question.TimeQuestionFragment;
import com.appli.nyx.formx.ui.fragment.business.reports.ReportAddDialog;
import com.appli.nyx.formx.ui.fragment.business.reports.ReportEditDialog;
import com.appli.nyx.formx.ui.fragment.business.reports.ReportsFragment;
import com.appli.nyx.formx.ui.fragment.business.reports.ReportsListFragment;
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
    abstract ChooseQuestionTypeDialog questionAddDialog();

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
    abstract MyEnqueteFragment enqueteListFragment();

    @ContributesAndroidInjector
    abstract EnqueteFragment enqueteFragment();

    @ContributesAndroidInjector
    abstract ReportsListFragment reportsListFragment();

    @ContributesAndroidInjector
    abstract ClusterListFragment clusterListFragment();

    @ContributesAndroidInjector
    abstract EnqueteMenuFragment enqueteMenuFragment();

    @ContributesAndroidInjector
    abstract ImportSectionFragment importSectionDialog();

    @ContributesAndroidInjector
    abstract SelectFormFragment selectFormDialog();

    @ContributesAndroidInjector
    abstract EnqueteAddDialog enqueteAddDialog();


    @ContributesAndroidInjector
    abstract EnqueteEditDialog enqueteEditDialog();

    @ContributesAndroidInjector
    abstract EnqueteSharedFragment enqueteSharedFragment();

    @ContributesAndroidInjector
    abstract JoinEnqueteFragment joinEnqueteFragment();

    @ContributesAndroidInjector
    abstract SelectUserFragment selectUserFragment();

    @ContributesAndroidInjector
    abstract ClusterAddDialog clusterAddDialog();

    @ContributesAndroidInjector
    abstract ClusterEditDialog clusterEditDialog();

    @ContributesAndroidInjector
    abstract SelectEnqueteFragment clusterEnqueteFragment();

    @ContributesAndroidInjector
    abstract ClusterStatsFragment clusterStatsFragment();


    @ContributesAndroidInjector
    abstract EnqueteVisibilityDialog enqueteVisibilityDialog();

    @ContributesAndroidInjector
    abstract EnqueteJoinedFragment enqueteJoinedFragment();

    @ContributesAndroidInjector
    abstract EnqueteCreatedFragment myEnqueteFragment();

    @ContributesAndroidInjector
    abstract ReportAddDialog reportAddDialog();

    @ContributesAndroidInjector
    abstract ReportEditDialog reportEditDialog();

    @ContributesAndroidInjector
    abstract EnqueteReplyFragment enqueteReplyFragment();

    @ContributesAndroidInjector
    abstract EnqueteReplyIntroFragment enqueteReplyIntroFragment();


}


