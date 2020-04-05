package com.appli.nyx.formx.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.appli.nyx.formx.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;

public class AlertDialogUtils {

	public static void showErrorDialog(Context context, String message) {
        new MaterialAlertDialogBuilder(context).setIcon(R.drawable.ic_info_black_24dp)
                .setTitle(context.getString(R.string.error)).setMessage(message).setPositiveButton("OK", (dialog, which) -> {
		}).setCancelable(false).show();
	}


    public static void showConfirmDeleteDialog(Context context, DialogInterface.OnClickListener listener) {
        new MaterialAlertDialogBuilder(context).setIcon(R.drawable.ic_delete_black_24dp)
                .setTitle(context.getString(R.string.confirm_delete)).setMessage(R.string.are_you_sure_to_delete).setPositiveButton("OK", listener)
                .setNegativeButton(R.string.cancel, null).setCancelable(false).show();
    }

    public static void showConfirmDeleteDialog(Context context, String documentPath) {
        AlertDialogUtils.showConfirmDeleteDialog(context, (dialog, which) -> {
            FirebaseFirestore.getInstance().document(documentPath).delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(context, R.string.operation_completes_successfully, Toast.LENGTH_LONG).show();
                } else {
                    AlertDialogUtils.showErrorDialog(context, task.getException().getMessage());
                }
            });
        });
    }
}
