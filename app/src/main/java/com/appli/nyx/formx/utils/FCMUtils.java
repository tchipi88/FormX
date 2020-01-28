package com.appli.nyx.formx.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.appli.nyx.formx.R;
import com.appli.nyx.formx.preference.PrefsManager_;
import com.appli.nyx.formx.ui.MainActivity;

import androidx.core.app.NotificationCompat;

public class FCMUtils {

	/**
	 * Create and show a simple notification containing the received FCM message.
	 *
	 * @param messageBody FCM message body received.
	 */
	public static void sendNotification(Context context, String messageBody, String channelUrl) {
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		final String CHANNEL_ID = "CHANNEL_ID";
		if (Build.VERSION.SDK_INT >= 26) {  // Build.VERSION_CODES.O
			NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
			notificationManager.createNotificationChannel(mChannel);
		}

		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra("groupChannelUrl", channelUrl);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID).setSmallIcon(R.mipmap.ic_launcher).setColor(Color.parseColor("#7469C4"))  // small icon background color
				.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher)).setContentTitle(context.getResources().getString(R.string.app_name)).setAutoCancel(true).setSound(defaultSoundUri).setPriority(Notification.PRIORITY_MAX).setDefaults(Notification.DEFAULT_ALL).setContentIntent(pendingIntent);

		notificationBuilder.setContentText(messageBody);

		notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
	}

	/**
	 * Persist token to third-party servers.
	 * <p>
	 * Modify this method to associate the user's FCM InstanceID token with any server-side account
	 * maintained by your application.
	 *
	 * @param refreshedToken The new token.
	 */
	public static void sendRegistrationToServer(Context context, String refreshedToken) {
		PrefsManager_ prefsManager_ = PrefsManager_.getInstance_(context);
		prefsManager_.setFirebaseToken(refreshedToken);

	}

}
