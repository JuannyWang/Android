/**
 * 
 */
package com.example.servers;

import java.io.IOException;
import java.net.UnknownHostException;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.example.bean.PackageBean;
import com.example.bean.PackageType;
import com.example.bean.ServerBackType;
import com.example.chatface02.VideoTalkInviteActivity;
import com.example.chatface02.db.DBManager;
import com.example.chatface02.entity.Messages;
import com.example.connection.ServerConnection;
import com.example.data.Common;
import com.example.utils.ChatUtils;

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
				Log.v(tag, "接收到退出指令");
				funcDisConnect(msg.getData());
				break;
			case Common.LOGIN:
				Log.v(tag, "接收到登录指令");
				funcLogin(msg.getData());
				break;
			case Common.CON:
				Log.v(tag, "接收到连接指令");
				funcConnection(msg.getData());
				break;
			case Common.BLIND:
				Log.v(tag, "接收到绑定指令");
				rMessenger = msg.replyTo;
				break;
			case Common.SEND_MSG:
				Log.v(tag, "接收到发送消息指令");
				funcMessage(msg.getData());
				break;
			case Common.VOICE_COMMUNCATION:
				Log.v(tag, "接收到发起音频指令");
				funcVoice(msg.getData());
				break;
			case Common.VIDEO_COMMUNCATION:
				Log.v(tag, "接收到发起视频指令");
				funcVideo(msg.getData());
				break;
			case Common.REGIST:
				Log.v(tag, "接收到注册指令");
				funcRegist(msg.getData());
				break;
			default:
				Log.v(tag, "接收到未定义事件码：" + msg.what);
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
	}

	/**
	 * 断开连接的事件响应
	 * 
	 * @param b
	 */
	private void funcDisConnect(Bundle b) {
		try {
			if (connection != null) {
				connection.disConnection();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		exitService();
	}

	/**
	 * 登录的事件响应
	 * 
	 * @param b
	 */
	private void funcLogin(Bundle b) {
		String account = b.getString("account");
		String password = b.getString("password");
		PackageBean bean = new PackageBean(PackageType.LOGIN, account,
				"000000000000", ChatUtils.MD5(password));
		connection.sendPackageBean(bean);
	}

	/**
	 * 注册的事件响应
	 * 
	 * @param b
	 */
	private void funcRegist(Bundle b) {
		String account = b.getString("account");
		String password = b.getString("password");
		PackageBean bean = new PackageBean(PackageType.REGIST, account,
				"000000000000", ChatUtils.MD5(password));
		connection.sendPackageBean(bean);
	}

	/**
	 * 连接的事件响应，包括后台接受线程的开启
	 * 
	 * @param b
	 */
	private void funcConnection(Bundle b) {
		new Thread(new StartListener()).start();
	}

	/**
	 * 接收到视频发起的响应
	 * 
	 * @param b
	 */
	private void funcVideo(Bundle b) {
		String tempMyId = b.getString("myID");
		String tempToId = b.getString("ID");
		PackageBean video_bean = new PackageBean(
				PackageType.VIDEO_COMMUNCATION, tempMyId, tempToId, null);
		connection.sendPackageBean(video_bean);
	}

	/**
	 * 接收到发送消息的事件响应
	 * 
	 * @param b
	 */
	private void funcMessage(Bundle b) {
		String tempMyId = b.getString("myID");
		String tempToId = b.getString("ID");
		String msg_send = b.getString("msg");
		PackageBean send_bean = new PackageBean(PackageType.MSG_SEND, tempMyId,
				tempToId, msg_send);
		Log.v(tag, send_bean.toString());
		connection.sendPackageBean(send_bean);
	}

	/**
	 * 接收到发起音频的事件响应
	 * 
	 * @param b
	 */
	private void funcVoice(Bundle b) {
		String tempMyId = b.getString("myID");
		String tempToId = b.getString("ID");
		PackageBean voice_bean = new PackageBean(
				PackageType.VOICE_COMMUNCATION, tempMyId, tempToId, null);
		connection.sendPackageBean(voice_bean);
	}

	/**
	 * 登录哦或者注册的回馈事件响应
	 * 
	 * @param obj
	 */
	private void funcBackLogin(PackageBean obj) {
		int backCommand = Integer.parseInt((String) obj.getData());
		switch (backCommand) {
		case ServerBackType.CREATE_SUCCESS:
			Log.v(tag, "激活成功");
			break;
		case ServerBackType.CREATE_FAIL:
			Log.v(tag, "激活失败");
			break;
		case ServerBackType.VALID_USER:
			Log.v(tag, "登陆成功");
			Message msg = Message.obtain(null, Common.LOGIN_SUCCESS);
			try {
				rMessenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		case ServerBackType.WRONG_MAC:
			Log.v(tag, "登陆失败:mac地址错误");
			break;
		case ServerBackType.WRONG_PASSWORD:
			Log.v(tag, "登陆失败:密码错误");
			break;
		case ServerBackType.ALREADY_LOGIN:
			Log.v(tag, "登陆失败:已经登录");
			break;
		default:
			Log.v(tag, "未知回馈消息:" + backCommand);
			break;
		}
	}

	/**
	 * 发消息的回馈时间响应
	 * 
	 * @param obj
	 */
	private void funcBackMessage(PackageBean obj) {
		Message receive_msg = Message.obtain(null, Common.SEND_MSG);
		Bundle msgBundle = new Bundle();
		String msg = obj.getData().toString();
		String tempMyId = obj.getFromUser().toString();
		msgBundle.putString("ID", tempMyId);
		msgBundle.putString("msg", msg);
		msgBundle.putInt("ISREAD", 1);
		receive_msg.setData(msgBundle);
		try {
			rMessenger.send(receive_msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		Messages msgs = new Messages(null, tempMyId, msg, 1);
		DBManager mgr = new DBManager(getApplicationContext());
		mgr.addMessage(msgs);
		mgr.closeDB();
	}

	/**
	 * 接收到视频会话请求
	 * 
	 * @param obj
	 */
	private void funcBackVoice(PackageBean obj) {
		Intent intents = new Intent(getApplicationContext(),
				VideoTalkInviteActivity.class);
		intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Message video_msg = Message.obtain(null, Common.VIDEO_COMMUNCATION);
		String videoIP = obj.getData().toString();
		Bundle video_reciverBundle = new Bundle();
		video_reciverBundle.putString("ID", obj.getFromUser());
		video_reciverBundle.putString("IP", videoIP);
		video_reciverBundle.putInt("Common", Common.VIDEO_COMMUNCATION);
		intents.putExtras(video_reciverBundle);
		startActivity(intents);
		video_msg.setData(video_reciverBundle);
		startActivity(intents);
		try {
			rMessenger.send(video_msg);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 接收到语音会话请求
	 * 
	 * @param obj
	 */
	private void funcBackVideo(PackageBean obj) {
		Intent intent = new Intent(getApplicationContext(),
				VideoTalkInviteActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		Message voice_msg = Message.obtain(null, Common.VOICE_COMMUNCATION);
		String reciverIP = obj.getData().toString();
		Bundle voice_reciverBundle = new Bundle();
		voice_reciverBundle.putString("ID", obj.getFromUser());
		voice_reciverBundle.putString("IP", reciverIP);
		voice_reciverBundle.putInt("Common", Common.VOICE_COMMUNCATION);
		voice_msg.setData(voice_reciverBundle);
		intent.putExtras(voice_reciverBundle);
		startActivity(intent);
	}

	/**
	 * 处理服务器回发指令的主逻辑体
	 * 
	 * @param obj
	 */
	private void onServerBackOrder(PackageBean obj) {
		switch (obj.getType()) {
		case 100:
			Log.v(tag, "接收到登录激活回馈消息");
			funcBackLogin(obj);
			break;
		case PackageType.MSG_SEND:
			Log.v(tag, "接收到消息转发回馈消息");
			funcBackMessage(obj);
			break;
		case PackageType.VIDEO_COMMUNCATION:
			Log.v(tag, "接收到视频IP转发回馈消息");
			funcBackVoice(obj);
			break;
		case PackageType.VOICE_COMMUNCATION:
			Log.v(tag, "接收到音频IP转发回馈消息");
			funcBackVideo(obj);
			break;
		default:
			Log.v(tag, "接收到未定义服务器回馈消息"+obj.getType());
			break;
		}
	}

	/**
	 * 调用用来停止service（备用）
	 */
	private void exitService() {
		this.stopSelf();
	}

	@Override
	/**
	 * service 停止的时候会自动断开连接
	 */
	public void onDestroy() {
		try {
			connection.disConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		flag = false;
		super.onDestroy();
	}

	/**
	 * 消息监听循环
	 * 
	 * @author 玄雨
	 * @qq 821580467
	 * @date 2013-8-2
	 */
	private class ServerBackListener implements Runnable {

		@Override
		public void run() {
			while (flag) {
				PackageBean obj = (PackageBean) connection.readPackageBean();
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
					onServerBackOrder(obj);
				}

			}
		}

	}

	/**
	 * 与服务器建立连接并且开启监听线程
	 * 
	 * @author 玄雨
	 * @qq 821580467
	 * @date 2013-8-2
	 */
	private class StartListener implements Runnable {

		@Override
		public void run() {
			try {
				Log.v(tag, "尝试与服务器建立连接");
				connection = ServerConnection.getInstance();
				Log.v(tag, "与服务器建立连接成功");
				/**
				 * 接受服务器数据事件线程
				 */
				new Thread(new ServerBackListener()).start();
			} catch (UnknownHostException e) {
				e.printStackTrace();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				run();
			} catch (IOException e) {
				e.printStackTrace();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				run();
			}

		}

	}

}
