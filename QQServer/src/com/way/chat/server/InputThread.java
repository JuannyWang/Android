package com.way.chat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.way.chat.common.bean.TextMessage;
import com.way.chat.common.bean.User;
import com.way.chat.common.tran.bean.TranObject;
import com.way.chat.common.tran.bean.TranObjectType;
import com.way.chat.common.util.MyDate;
import com.way.chat.dao.UserDao;
import com.way.chat.dao.impl.UserDaoFactory;

public class InputThread extends Thread {
	private Socket socket;
	private OutputThread out;
	private OutputThreadMap map;
	private ObjectInputStream ois;
	private boolean isStart = true;

	public InputThread(Socket socket, OutputThread out, OutputThreadMap map) {
		this.socket = socket;
		this.out = out;
		this.map = map;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	@Override
	public void run() {
		try {
			while (isStart) {
				// 读取消息
				readMessage();
			}
			if (ois != null)
				ois.close();
			if (socket != null)
				socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
			// isStart = false;
		}

	}

	public void readMessage() throws IOException, ClassNotFoundException {
		Object readObject = ois.readObject();// 从流中读取对象
		UserDao dao = UserDaoFactory.getInstance();// 通过dao模式管理后台
		if (readObject != null && readObject instanceof TranObject) {
			TranObject read_tranObject = (TranObject) readObject;// 转换成传输对象
			switch (read_tranObject.getType()) {
			case REGISTER:// 如果用户是注册
				User registerUser = (User) read_tranObject.getObject();
				int registerResult = dao.register(registerUser);
				System.out.println("注册:" + registerResult);
				// 给用户回复消息
				TranObject<User> register2TranObject = new TranObject<User>(
						TranObjectType.REGISTER);
				User register2user = new User();
				register2user.setId(registerResult);
				register2TranObject.setObject(register2user);
				out.setMessage(register2TranObject);
				break;
			case LOGIN:
				User loginUser = (User) read_tranObject.getObject();
				ArrayList<User> list = dao.login(loginUser);
				TranObject<ArrayList<User>> login2Object = new TranObject<ArrayList<User>>(
						TranObjectType.LOGIN);
				if (list != null) {// 如果登录成功
					TranObject<User> onObject = new TranObject<User>(
							TranObjectType.LOGIN);
					User login2User = new User();
					login2User.setId(loginUser.getId());
					onObject.setObject(login2User);
					for (OutputThread onOut : map.getAll()) {
						onOut.setMessage(onObject);// 广播一下用户上线
					}
					map.add(loginUser.getId(), out);// 先广播，再把对应用户id的写线程存入map中，以便转发消息时调用
					login2Object.setObject(list);// 把好友列表加入回复的对象中
				} else {
					login2Object.setObject(null);
				}
				out.setMessage(login2Object);// 同时把登录信息回复给用户
				
				System.out.println(MyDate.getDateCN() + " 用户：" + loginUser.getId() + " 上线了");
				break;
			case LOGOUT:// 如果是退出，更新数据库在线状态，同时群发告诉所有在线用户
				User logoutUser = (User) read_tranObject.getObject();
				int offId = logoutUser.getId();
				System.out.println(MyDate.getDateCN() + " 用户：" + offId + " 下线了");
				dao.logout(offId);
				isStart = false;// 结束自己的读循环
				out.removeThread();// 从缓存的线程中移除
				out.setMessage(null);// 先要设置一个空消息去唤醒写线程
				out.setStart(false);// 再结束写线程循环

				TranObject<User> offObject = new TranObject<User>(
						TranObjectType.LOGOUT);
				User logout2User = new User();
				logout2User.setId(logoutUser.getId());
				offObject.setObject(logout2User);
				for (OutputThread offOut : map.getAll()) {// 广播用户下线消息
					offOut.setMessage(offObject);
				}
				break;
			case MESSAGE:// 如果是转发消息（可添加群发）
				// 获取消息中要转发的对象id，然后获取缓存的该对象的写线程
				int id2 = read_tranObject.getToUser();
				OutputThread toOut = map.getById(id2);
				if (toOut != null) {// 如果用户在线
					toOut.setMessage(read_tranObject);
				} else {// 如果为空，说明用户已经下线,回复用户
					TextMessage text = new TextMessage();
					text.setMessage("发送消息失败，用户可能已经离线！");
					TranObject<TextMessage> offText = new TranObject<TextMessage>(
							TranObjectType.MESSAGE);
					offText.setObject(text);
					offText.setFromUser(0);
					out.setMessage(offText);
				}
				break;
			case FILE:
				
				break;
			default:
				break;
			}
		}
	}
}
