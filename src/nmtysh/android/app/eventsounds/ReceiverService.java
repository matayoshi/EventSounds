package nmtysh.android.app.eventsounds;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class ReceiverService extends Service {
	// 動作中フラグ
	private boolean running = false;
	Receiver receiver = null;

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		if (!running) {
			running = true;
			receiver = new Receiver();
			IntentFilter filter = new IntentFilter();
			// これらは動的に register しないと反応しない
			filter.addAction(Intent.ACTION_USER_PRESENT);	// ロックの解除
			filter.addAction(Intent.ACTION_SCREEN_ON);		// スクリーンON
			filter.addAction(Intent.ACTION_SCREEN_OFF);		// スクリーンOFF
			getApplicationContext().registerReceiver(receiver, filter);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (running) {
			getApplicationContext().unregisterReceiver(receiver);
			receiver = null;
			running = false;
		}
	}

	// リモートからの呼び出し用(何もしない)
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
// EOF