package com.appli.nyx.formx.utils;

import android.content.Context;

import com.appli.nyx.formx.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AlertDialogUtils {

	public static void showErrorDialog(Context context, String message) {
		new MaterialAlertDialogBuilder(context).setIcon(R.drawable.ic_info_black_24dp).setTitle(context.getString(R.string.error)).setMessage(message).setPositiveButton("OK", (dialog, which) -> {
		}).setCancelable(false).show();
	}
}
