/**
 * 
 */
package com.ghost.services;

import java.io.IOException;
import java.net.UnknownHostException;

import com.ghost.beans.LoginData;
import com.ghost.beans.PackageBean;
import com.ghost.beans.PackageType;
import com.ghost.connection.ServerConnection;
import com.ghost.datas.Common;
import com.ghost.utils.Utils;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-11
 */
public class ChatService extends Service {

	public static final String tag = "ChatService";

	/**
	 * 消息循环线程标示符
	 */
	private boolean flag;

	/**
	 * 服务器连接对象
	 */
	private ServerConnection connection;

	/**
	 * Service 的事件响应
	 */
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Common.EXIT:
				try {
					if (connection != null) {
						connection.disConnection();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case Common.LOGIN:
				Bundle b = msg.getData();
				int account = b.getInt("account");
				String password = b.getString("password");
				LoginData data = new LoginData(password);
				PackageBean bean = new PackageBean(PackageType.LOGIN, account
						+ "", "000000000000", data);
				connection.sendPackageBean(bean);
				break;
			case Common.CON:
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							connection = ServerConnection.getInstance();
							Log.v(tag, "与服务器建立连接成功");
							/**
							 * 接受服务器数据事件线程
							 */
							new Thread(new Runnable() {

								@Override
								public void run() {
									while (flag) {
										PackageBean obj = (PackageBean) connection
												.readPackageBean();

										/**
										 * 如果读取到错误的消息则重新与服务器连接
										 */
										if (obj == null) {
											try {
												try {
													Thread.sleep(10000);
												} catch (InterruptedException e) {
													e.printStackTrace();
												}
												Log.v(tag, "与服务器断开连接尝试重新连接...");
												connection.reconnection();
												Log.v(tag, "与服务器重新建立连接成功");
											} catch (UnknownHostException e) {
												e.printStackTrace();
											} catch (IOException e) {
												e.printStackTrace();
											}
											continue;
										} else {
											/**
											 * 主逻辑循环体
											 */
											switch (obj.getType()) {
											case PackageType.LOGIN:
												if ((Integer) obj.getData() == PackageType.SCCESS) {
													Log.v(tag, "登陆成功");
													Message msg = Message.obtain(null,
															Common.LOGIN_SUCCESS);
													try {
														rMessenger.send(msg);
													} catch (RemoteException e) {
														e.printStackTrace();
													}
												} else if ((Integer) obj.getData() == PackageType.FAILED) {
													Log.v(tag, "登陆失败");
												} else {
													Log.v(tag, "登陆发生未知错误");
												}
												break;
											default:
												break;
											}
										}

									}
								}

							}).start();
						} catch (UnknownHostException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

				}).start();
				break;
			case Common.BLIND:
				rMessenger = msg.replyTo;
				Log.v(tag, "绑定消息对象成功");
				break;
			case PackageType.TEST:
				connection.sendPackageBean(new PackageBean(PackageType.PROVE,
						"111111111111", "000000000000",
						"12121212121212121212121212121212"));
				break;
			default:
				Utils.alertToast(getApplicationContext(), tag, "接收到未定义事件码："
						+ msg.what);
				break;
			}
		}
	};

	/**
	 * 自己的消息对象
	 */
	private Messenger mMessenger;

	/**
	 * 交互对象的消息对象
	 */
	private Messenger rMessenger;

	@Override
	public IBinder onBind(Intent intent) {
		return mMessenger.getBinder();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mMessenger = new Messenger(mHandler);
		flag = true;
		/**
		 * 本地事件线程
		 */
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (flag) {
					/**
					 * 本地事件，例如定时发送MAC地址 或者 其他本地需求
					 */
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();
	}

	/**
	 * 调用用来停止service（备用）
	 */
	private void exitService() {
		this.stopSelf();
	}

	@Override
	public void onDestroy() {
		try {
			connection.disConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		flag = false;
		super.onDestroy();
	}

}
