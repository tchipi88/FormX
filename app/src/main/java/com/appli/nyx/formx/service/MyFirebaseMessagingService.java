package com.appli.nyx.formx.service;

import android.util.Log;

import com.appli.nyx.formx.MyApplication;
import com.appli.nyx.formx.utils.FCMUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

	private static final String TAG = "MyFirebaseMsgService";

	@Override
	public void onNewToken(String refreshedToken) {
		super.onNewToken(refreshedToken);
		Log.e("NEW_TOKEN", refreshedToken);
		FCMUtils.sendRegistrationToServer(MyApplication.getInstance().getBaseContext(), refreshedToken);
	}

	/**
	 * Called when message is received.
	 *
	 * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
	 */
	// [START receive_message]
	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		// [START_EXCLUDE]
		// There are two types of messages data messages and notification messages. Data messages are handled
		// here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
		// traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
		// is in the foreground. When the app is in the background an automatically generated notification is displayed.
		// When the user taps on the notification they are returned to the app. Messages containing both notification
		// and data payloads are treated as notification messages. The Firebase console always sends notification
		// messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
		// [END_EXCLUDE]

		// TODO(developer): Handle FCM messages here.
		// Not getting messages here? See why this may be: https://goo.gl/39bRNJ
		Log.d(TAG, "From: " + remoteMessage.getFrom());

		// Check if message contains a data payload.
		if (remoteMessage.getData().size() > 0) {
			Log.d(TAG, "Message data payload: " + remoteMessage.getData());
		}

		// Check if message contains a notification payload.
		if (remoteMessage.getNotification() != null) {
			Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
		}

		String channelUrl = null;
		try {
			JSONObject sendBird = new JSONObject(remoteMessage.getData().get("sendbird"));
			JSONObject channel = (JSONObject) sendBird.get("channel");
			channelUrl = (String) channel.get("channel_url");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// Also if you intend on generating your own notifications as a result of a received FCM
		// message, here is where that should be initiated. See sendNotification method below.
		FCMUtils.sendNotification(this, remoteMessage.getData().get("message"), channelUrl);
	}
}

