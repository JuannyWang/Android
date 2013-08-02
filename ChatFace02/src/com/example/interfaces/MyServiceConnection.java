/**
 * 
 */
package com.example.interfaces;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.example.data.Common;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-14
 */
public class MyServiceConnection implements ServiceConnection {

	/**
	 * 自己的消息对象
	 */
	private Messenger mMessenger;

	/**
	 * 交互目标的消息对象
	 */
	private Messenger rMessenger;

	public MyServiceConnection(Messenger mMessenger, Messenger rMessenger) {
		this.mMessenger = mMessenger;
		this.rMessenger = rMessenger;
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		rMessenger = new Messenger(service);
		Message msg = Message.obtain(null, Common.BLIND);
		msg.replyTo = mMessenger;
		try {
			rMessenger.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		Message msg = Message.obtain(null, Common.BLIND);
		msg.replyTo = null;
		try {
			rMessenger.send(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		mMessenger = null;
	}

}
