package com.appli.nyx.formx.preference;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EBean
public class PrefsManager {

	@Pref
	protected Prefs_ prefs_;

	public String getFirebaseToken() {
		return prefs_.firebaseToken().get();
	}

	public void setFirebaseToken(String token) {
		prefs_.edit().firebaseToken().put(token).apply();
	}

	public String getCurrentUserEmail() {
		return prefs_.currentUserEmail().get();
	}

	public void setCurrentUserEmail(String email) {
		prefs_.edit().currentUserEmail().put(email).apply();
	}

	public String getCurrentUserName() {
		return prefs_.currentUserName().get();
	}

	public void setCurrentUserName(String name) {
		prefs_.edit().currentUserName().put(name).apply();
	}

	public boolean isFirstLaunch() {
		return prefs_.firstLaunch().getOr(true);
	}

	public void setFirstLaunch(boolean firstLaunch) {
		prefs_.edit().firstLaunch().put(firstLaunch).apply();
	}

	public void clearSessionPrefs() {
		setCurrentUserName(null);
		setCurrentUserEmail(null);
	}

	public boolean isNotifications() {
		return prefs_.notifications().getOr(true);
	}

	public void setNotifications(boolean isLogged) {
		prefs_.edit().notifications().put(isLogged).apply();
	}

	//TODO
	public boolean isProfilComplete() {
		return prefs_.isProfilComplete().getOr(false);
	}

	public void setProfilComplete(boolean value) {
		prefs_.edit().isProfilComplete().put(value).apply();
	}
}
