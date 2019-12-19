package com.appli.nyx.formx.exception;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.appli.nyx.formx.BuildConfig;
import com.appli.nyx.formx.R;

public class MyExceptionHandler implements Thread.UncaughtExceptionHandler {

	public static final String EXTRA_MY_EXCEPTION_HANDLER = "EXTRA_MY_EXCEPTION_HANDLER";
	private static final String SINGLE_LINE_SEP = "\n";
	private static final String DOUBLE_LINE_SEP = "\n\n";
	private final Context context;

	public MyExceptionHandler(Context context) {
		this.context = context;
	}

	@Override
	public void uncaughtException(final Thread t, final Throwable e) {
		if (e instanceof Exception) {
			e.printStackTrace();
			// note we can't just open in Android an dialog etc. we have to use Intents here
			// http://stackoverflow.com/questions/13416879/show-a-dialog-in-thread-setdefaultuncaughtexceptionhandler
			// Log.d(HTAExceptionHandler.class.getName(), ((RetrofitException ) ex).getStatusCode().name());

			StackTraceElement[] arr = e.getStackTrace();
			final StringBuffer report = new StringBuffer(e.toString());
			final String lineSeperator = "-------[" + context.getResources().getString(R.string.app_name) + "] Crash Report------------------------\n\n";
			report.append(DOUBLE_LINE_SEP);
			report.append("--------- Stack trace ---------\n\n");
			for (int i = 0; i < arr.length; i++) {
				report.append("    ");
				report.append(arr[i].toString());
				report.append(SINGLE_LINE_SEP);
			}
			report.append(lineSeperator);
			// If the exception was thrown in a background thread inside
			// AsyncTask, then the actual exception can be found with getCause
			report.append("--------- Cause ---------\n\n");
			Throwable cause = e.getCause();
			if (cause != null) {
				report.append(cause.toString());
				report.append(DOUBLE_LINE_SEP);
				arr = cause.getStackTrace();
				for (int i = 0; i < arr.length; i++) {
					report.append("    ");
					report.append(arr[i].toString());
					report.append(SINGLE_LINE_SEP);
				}
			}
			// Getting the Device brand,model and sdk verion details.
			report.append(lineSeperator);
			report.append("--------- Device ---------\n\n");
			report.append("Brand: ");
			report.append(Build.BRAND);
			report.append(SINGLE_LINE_SEP);
			report.append("Device: ");
			report.append(Build.DEVICE);
			report.append(SINGLE_LINE_SEP);
			report.append("Model: ");
			report.append(Build.MODEL);
			report.append(SINGLE_LINE_SEP);
			report.append("Id: ");
			report.append(Build.ID);
			report.append(SINGLE_LINE_SEP);
			report.append("Product: ");
			report.append(Build.PRODUCT);
			report.append(SINGLE_LINE_SEP);
			report.append(lineSeperator);
			report.append("--------- Firmware ---------\n\n");
			report.append("SDK: ");
			report.append(Build.VERSION.SDK);
			report.append(SINGLE_LINE_SEP);
			report.append("Release: ");
			report.append(Build.VERSION.RELEASE);
			report.append(SINGLE_LINE_SEP);
			report.append("Version: ");
			report.append(BuildConfig.VERSION_NAME);
			report.append(SINGLE_LINE_SEP);
			report.append("Incremental: ");
			report.append(Build.VERSION.INCREMENTAL);
			report.append(SINGLE_LINE_SEP);
			report.append(lineSeperator);

			Log.e("Report ::", report.toString());

			Intent email = new Intent(Intent.ACTION_SEND);
			email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "ganeo.app@gmail.com" });
			email.putExtra(Intent.EXTRA_SUBJECT, "[" + context.getResources().getString(R.string.app_name) + "] Crash Report  Version " + BuildConfig.VERSION_NAME);
			email.putExtra(Intent.EXTRA_TEXT, report.toString());
			email.setType("message/rfc822");
			context.startActivity(Intent.createChooser(email, "Choose an Email client :"));

			// make sure we die, otherwise the app will hang ...
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	}

}
