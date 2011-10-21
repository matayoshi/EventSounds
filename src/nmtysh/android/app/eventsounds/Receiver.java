package nmtysh.android.app.eventsounds;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

public class Receiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// // MediaPlayer を使うなら必要(Ringtone は自動判断)
		// if (!EventSoundsPreference.isAllwaysPlay(context)) {
		// // マナーモードを考慮する
		// AudioManager am = (AudioManager) context
		// .getSystemService(Context.AUDIO_SERVICE);
		// if (am.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
		// return;
		// }
		// }

		String action = intent.getAction();
		String uri = null;
		if (EventSoundsPreference.isUSBConnected(context)
				&& action.equals(Intent.ACTION_UMS_CONNECTED)) {
			// USBの接続
			uri = EventSoundsPreference.getUSBConnectedRingtone(context);
		} else if (EventSoundsPreference.isUSBDisconnected(context)
				&& action.equals(Intent.ACTION_UMS_DISCONNECTED)) {
			// USBの切断
			uri = EventSoundsPreference.getUSBDisconnectedRingtone(context);
		} else if (EventSoundsPreference.isPowerConnected(context)
				&& action.equals(Intent.ACTION_POWER_CONNECTED)) {
			// 電源の接続
			uri = EventSoundsPreference.getPowerConnectedRingtone(context);
		} else if (EventSoundsPreference.isPowerDisconnected(context)
				&& action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
			// 電源の切断
			uri = EventSoundsPreference.getPowerDisconnectedRingtone(context);
		} else if (EventSoundsPreference.isUserPresent(context)
				&& action.equals(Intent.ACTION_USER_PRESENT)) {
			// ロックの解除
			uri = EventSoundsPreference.getUserPresentRingtone(context);
		} else if (EventSoundsPreference.isScreenOn(context)
				&& action.equals(Intent.ACTION_SCREEN_ON)) {
			// スクリーンのON
			uri = EventSoundsPreference.getScreenOnRingtone(context);
		} else if (EventSoundsPreference.isScreenOff(context)
				&& action.equals(Intent.ACTION_SCREEN_OFF)) {
			// スクリーンのOFF
			uri = EventSoundsPreference.getScreenOffRingtone(context);
		} else if (EventSoundsPreference.isShutdown(context)
				&& action.equals(Intent.ACTION_SHUTDOWN)) {
			// シャットダウン
			uri = EventSoundsPreference.getShutdownRingtone(context);
		} else if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
			// 起動完了
			if (EventSoundsPreference.isBootCompleted(context)) {
				uri = EventSoundsPreference.getBootCompletedRingtone(context);
			}
			EventSoundsPreference.runService(context);
		}
		if (uri != null && uri.length() > 0) {
			// MediaPlayer mp = MediaPlayer.create(context, Uri.parse(uri));
			Ringtone ringtone = RingtoneManager.getRingtone(context,
					Uri.parse(uri));
			if (ringtone != null) {
				ringtone.setStreamType(AudioManager.STREAM_NOTIFICATION);
				ringtone.play();
			}
			// if (mp != null) {
			// mp.setOnCompletionListener(listener);
			// mp.start();
			// } else {
			// Toast.makeText(context, R.string.failed_load_sound,
			// Toast.LENGTH_SHORT).show();
			// }
		}
	}
	// private OnCompletionListener listener = new OnCompletionListener() {
	// @Override
	// public void onCompletion(MediaPlayer mp) { // リソースの解放
	// mp.release();
	// }
	// };
}
// EOF