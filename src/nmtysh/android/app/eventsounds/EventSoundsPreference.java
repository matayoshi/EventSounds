package nmtysh.android.app.eventsounds;

import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;

public class EventSoundsPreference extends PreferenceActivity {
	private boolean isScreenOn = false;
	private boolean isScreenOff = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);

		// イベントハンドラの登録
		int[] keys = { R.string.key_usb_connected_ringtone,
				R.string.key_usb_disconnected_ringtone,
				R.string.key_power_connected_ringtone,
				R.string.key_power_disconnected_ringtone,
				R.string.key_user_present_ringtone,
				R.string.key_screen_on_ringtone,
				R.string.key_screen_off_ringtone,
				R.string.key_boot_completed_ringtone,
				R.string.key_shutdown_ringtone };
		String key;
		RingtonePreference ringtone;
		for (int i = 0; i < keys.length; i++) {
			key = getString(keys[i]);
			ringtone = (RingtonePreference) findPreference(key);
			ringtone.setOnPreferenceChangeListener(ringtoneListener);
			ringtone.setSummary(getRingtoneTitle(getRingtone(this, key)));
		}
		keys = new int[] { R.string.key_screen_on, R.string.key_screen_off };
		CheckBoxPreference checkbox;
		for (int i = 0; i < keys.length; i++) {
			key = getString(keys[i]);
			checkbox = (CheckBoxPreference) findPreference(key);
			checkbox.setOnPreferenceChangeListener(checkboxListener);
		}
		// サービスの起動
		runService(getApplicationContext());
	}

	// 効果音の変更を捕捉
	private OnPreferenceChangeListener ringtoneListener = new OnPreferenceChangeListener() {
		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			((RingtonePreference) preference)
					.setSummary(getRingtoneTitle(newValue.toString()));
			return true;
		}
	};

	// チェックボックスの変更を捕捉
	private OnPreferenceChangeListener checkboxListener = new OnPreferenceChangeListener() {
		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			Context context = getApplicationContext();
			String key = preference.getKey();
			if (key.equals(context.getString(R.string.key_screen_on))) {
				isScreenOn = (Boolean) newValue;
			} else if (key.equals(context.getString(R.string.key_screen_off))) {
				isScreenOff = (Boolean) newValue;
			}
			runService(context, isScreenOn, isScreenOff);
			return true;
		}
	};

	// サウンドのタイトルを取得
	private String getRingtoneTitle(String url) {
		String title = getString(R.string.default_ringtone);
		if (url.length() > 0) {
			Uri uri = Uri.parse(url);
			title = RingtoneManager.getRingtone(this, uri).getTitle(this);
		}
		return title;
	}

	// サウンドのurlを取得
	static String getRingtone(Context context, String key) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString(key, "");
	}

	static String getUSBConnectedRingtone(Context context) {
		return getRingtone(context,
				context.getString(R.string.key_usb_connected_ringtone));
	}

	static String getUSBDisconnectedRingtone(Context context) {
		return getRingtone(context,
				context.getString(R.string.key_usb_disconnected_ringtone));
	}

	static String getUserPresentRingtone(Context context) {
		return getRingtone(context,
				context.getString(R.string.key_user_present_ringtone));
	}

	static String getScreenOnRingtone(Context context) {
		return getRingtone(context,
				context.getString(R.string.key_screen_on_ringtone));
	}

	static String getScreenOffRingtone(Context context) {
		return getRingtone(context,
				context.getString(R.string.key_screen_off_ringtone));
	}

	static String getPowerConnectedRingtone(Context context) {
		return getRingtone(context,
				context.getString(R.string.key_power_connected_ringtone));
	}

	static String getPowerDisconnectedRingtone(Context context) {
		return getRingtone(context,
				context.getString(R.string.key_power_disconnected_ringtone));
	}

	static String getBootCompletedRingtone(Context context) {
		return getRingtone(context,
				context.getString(R.string.key_boot_completed_ringtone));
	}

	static String getShutdownRingtone(Context context) {
		return getRingtone(context,
				context.getString(R.string.key_shutdown_ringtone));
	}

	// static boolean isAllwaysPlay(Context context) {
	// return PreferenceManager
	// .getDefaultSharedPreferences(context)
	// .getBoolean(context.getString(R.string.key_allways_play), false);
	// }

	static boolean isUSBConnected(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(context.getString(R.string.key_usb_connected),
						false);
	}

	static boolean isUSBDisconnected(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(context.getString(R.string.key_usb_disconnected),
						false);
	}

	static boolean isUserPresent(Context context) {
		return PreferenceManager
				.getDefaultSharedPreferences(context)
				.getBoolean(context.getString(R.string.key_user_present), false);
	}

	static boolean isScreenOn(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(context.getString(R.string.key_screen_on), false);
	}

	static boolean isScreenOff(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(context.getString(R.string.key_screen_off), false);
	}

	static boolean isPowerConnected(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(context.getString(R.string.key_power_connected),
						false);
	}

	static boolean isPowerDisconnected(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(context.getString(R.string.key_power_disconnected),
						false);
	}

	static boolean isShutdown(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(context.getString(R.string.key_shutdown), false);
	}

	static boolean isBootCompleted(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(context.getString(R.string.key_boot_completed),
						false);
	}

	static void runService(Context context) {
		if (isScreenOn(context) || isScreenOff(context)) {
			startService(context);
		} else {
			stopService(context);
		}
	}

	static void runService(Context context, boolean isScreenOn,
			boolean isScreenOff) {
		if (isScreenOn || isScreenOff) {
			startService(context);
		} else {
			stopService(context);
		}
	}

	static void startService(Context context) {
		context.startService(new Intent(context, ReceiverService.class));

	}

	static void stopService(Context context) {
		context.stopService(new Intent(context, ReceiverService.class));
	}
}
// EOF