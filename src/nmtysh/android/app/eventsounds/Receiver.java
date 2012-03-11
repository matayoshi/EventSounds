/*
BSD License

Copyright(c) 2011, N.Matayoshi All rights reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

・Redistributions of source code must retain the above copyright notice, 
  this list of conditions and the following disclaimer.
・Redistributions in binary form must reproduce the above copyright notice, 
  this list of conditions and the following disclaimer in the documentation 
  and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
ARE DISCLAIMED. 
IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package nmtysh.android.app.eventsounds;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.telephony.TelephonyManager;

public class Receiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// 端末の状態を取得
		// 通話中であれば音を鳴らさない。
		TelephonyManager tManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tManager.getCallState() != TelephonyManager.CALL_STATE_IDLE) {
			return;
		}

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
			EventSoundsPreference.runService(context);
			// 起動完了
			if (EventSoundsPreference.isBootCompleted(context)) {
				uri = EventSoundsPreference.getBootCompletedRingtone(context);
			}
		}
		if (uri != null && uri.length() > 0) {
			Ringtone ringtone = RingtoneManager.getRingtone(context,
					Uri.parse(uri));
			if (ringtone != null) {
				ringtone.setStreamType(AudioManager.STREAM_NOTIFICATION);
				ringtone.play();
			}
		}
	}
}
// EOF