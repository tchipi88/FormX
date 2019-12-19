package com.appli.nyx.formx.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SessionUtils {

	public static boolean isUserSigned() {
		return FirebaseAuth.getInstance().getCurrentUser() != null;
	}

	public static FirebaseUser getUserSigned() {
		return FirebaseAuth.getInstance().getCurrentUser();
	}

	public static String getUserUid() {
		return getUserSigned().getUid();
	}
}
