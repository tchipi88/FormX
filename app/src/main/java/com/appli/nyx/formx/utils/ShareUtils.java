package com.appli.nyx.formx.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.appli.nyx.formx.BuildConfig;
import com.appli.nyx.formx.R;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

public class ShareUtils {

	public static Intent sendFeedback(Context context) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);

		//To send an email you need to specify mailto as URI
		emailIntent.setData(Uri.parse("mailto:"));

		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ "ganeo.app@gmail.com" });
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.email_title, context.getString(R.string.app_name), BuildConfig.VERSION_NAME));

		//this is mime type of email without it no activity can be found to send email.
		emailIntent.setType("message/rfc822");
		return Intent.createChooser(emailIntent, context.getString(R.string.send_feedback));
	}

	public static Intent share() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		String shareBodyText = "Your shearing message goes here";
		intent.putExtra(Intent.EXTRA_SUBJECT, "Subject/Title");
		intent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
		return (Intent.createChooser(intent, "Choose sharing method"));
	}

	public static void openLicencesPage(Context context) {
		context.startActivity(new Intent(context, OssLicensesMenuActivity.class));
	}
}
