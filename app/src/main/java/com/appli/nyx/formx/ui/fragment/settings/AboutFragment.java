package com.appli.nyx.formx.ui.fragment.settings;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appli.nyx.formx.BuildConfig;
import com.appli.nyx.formx.R;

import java.util.Calendar;
import java.util.List;

import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {

	public static String YOUTUBE_CHANNEL = "";
	public static String FACEBOOK_PAGE = "";
	public static String TWITTER_ACCOUNT = "";
	public static String WEBSITE_URL = "";
	public static String MAIL = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_about, container, false);

		((TextView) view.findViewById(R.id.version)).setText("Version " + BuildConfig.VERSION_NAME);
		final String copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
		((TextView) view.findViewById(R.id.copyright)).setText(copyrights);

		view.findViewById(R.id.contact).setOnClickListener(v -> {
			Intent intent = new Intent(Intent.ACTION_SENDTO);
			intent.setData(Uri.parse("mailto:"));
			intent.putExtra(Intent.EXTRA_EMAIL, new String[]{ MAIL });

			startActivity(intent);
		});
		view.findViewById(R.id.facebook).setOnClickListener(v -> {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addCategory(Intent.CATEGORY_BROWSABLE);

			if (isAppInstalled(getContext(), "com.facebook.katana")) {
				intent.setPackage("com.facebook.katana");
				int versionCode = 0;
				try {
					versionCode = getContext().getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
				} catch (PackageManager.NameNotFoundException e) {
					e.printStackTrace();
				}

				if (versionCode >= 3002850) {
					Uri uri = Uri.parse("fb://facewebmodal/f?href=" + "http://m.facebook.com/" + FACEBOOK_PAGE);
					intent.setData(uri);
				} else {
					Uri uri = Uri.parse("fb://page/" + FACEBOOK_PAGE);
					intent.setData(uri);
				}
			} else {
				intent.setData(Uri.parse("http://m.facebook.com/" + FACEBOOK_PAGE));
			}
			startActivity(intent);
		});
		view.findViewById(R.id.website).setOnClickListener(v -> {
			Uri uri = Uri.parse(WEBSITE_URL);
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);

			startActivity(browserIntent);
		});
		view.findViewById(R.id.twitter).setOnClickListener(v -> {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addCategory(Intent.CATEGORY_BROWSABLE);

			if (isAppInstalled(getContext(), "com.twitter.android")) {
				intent.setPackage("com.twitter.android");
				intent.setData(Uri.parse(String.format("twitter://user?screen_name=%s", TWITTER_ACCOUNT)));
			} else {
				intent.setData(Uri.parse(String.format("http://twitter.com/intent/user?screen_name=%s", TWITTER_ACCOUNT)));
			}
			startActivity(intent);
		});
		view.findViewById(R.id.youtube).setOnClickListener(v -> {

			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(String.format("http://youtube.com/channel/%s", YOUTUBE_CHANNEL)));

			if (isAppInstalled(getContext(), "com.google.android.youtube")) {
				intent.setPackage("com.google.android.youtube");
			}

			startActivity(intent);
		});
		view.findViewById(R.id.rate).setOnClickListener(v -> {

			try {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getContext().getPackageName())));
			} catch (android.content.ActivityNotFoundException e) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
			}
		});

		return view;
	}

	Boolean isAppInstalled(Context context, String appName) {
		PackageManager pm = context.getPackageManager();
		boolean installed = false;
		List<PackageInfo> packages = pm.getInstalledPackages(0);

		for (PackageInfo packageInfo : packages) {
			if (packageInfo.packageName.equals(appName)) {
				installed = true;
				break;
			}
		}

		return installed;
	}

}
