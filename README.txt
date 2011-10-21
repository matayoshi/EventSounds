Android のスクリーンON/OFFした時などに効果音を鳴らすアプリ。

対応するイベントは、
・スクリーンのON(android.intent.action.SCREEN_ON)
・スクリーンのOFF(android.intent.action.SCREEN_OFF)
・ロックの解除(android.intent.action.ACTION_USER_PRESENT)
・電源の接続(android.intent.action.ACTION_POWER_CONNECTED)
・電源の切断(android.intent.action.ACTION_POWER_DISCONNECTED)
・USBの接続(android.intent.action.UMS_CONNECTED)
・USBの切断(android.intent.action.UMS_DISCONNECTED)
・シャットダウン(android.intent.action.ACTION_SHUTDOWN)
・システムの起動完了(android.intent.action.BOOT_COMPLETED)
です。

それぞれのイベントについて効果音のON/OFFと音を設定出来ます。
再生時の音量には効果音の音量設定が適応されます。

※注意※システムの起動完了時に外部メディアに保存した効果音を指定していると、
再生のタイミングによっては再生されません。


ライセンスは LICENSE.txt / LICENSE.ja.txt をご覧ください。
