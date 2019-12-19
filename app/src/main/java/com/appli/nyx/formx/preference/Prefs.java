package com.appli.nyx.formx.preference;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface Prefs {

	String firebaseToken();

	String currentUserEmail();

	String currentUserName();

	boolean firstLaunch();

	boolean notifications();

	boolean isProfilComplete();
}
