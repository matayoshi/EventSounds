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