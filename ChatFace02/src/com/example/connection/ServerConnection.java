/**
 * 
 */
package com.example.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.bean.PackageBean;
import com.example.data.Common;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-12
 */
public class ServerConnection {

	public static final String tag = "ServerConnection";

	/**
	 * 与服务器通信的Socket
	 */
	private Socket socket;

	private DataOutputStream out;

	private DataInputStream in;

	private static ServerConnection thisObject;

	private static boolean testFlag = false;

	private static String testIP;
	private static int testPort;

	public static void setTest(String ip, int port) {
		testFlag = true;
		testIP = ip;
		testPort = port;
	}

	public static ServerConnection getInstance() throws UnknownHostException,
			IOException {
		if (thisObject == null) {
			thisObject = new ServerConnection();
		}
		return thisObject;
	}

	private ServerConnection() throws UnknownHostException, IOException {
		reconnection();
	}

	public void reconnection() throws UnknownHostException, IOException {
		if (testFlag) {
			socket = new Socket(testIP, testPort);
		} else {
			socket = new Socket(Common.SERVER_IP, Common.SERVER_PORT);
		}
		out = new DataOutputStream(socket.getOutputStream());
		in = new DataInputStream(socket.getInputStream());
	}

	public void disConnection() throws IOException {
		out.close();
		in.close();
		socket.close();
	}

	public Object readObject() {
		return null;
	}

	public boolean sendDatas(byte[] datas) {
		try {
			out.write(datas);
			out.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public PackageBean readPackageBean() {
		
		if(thisObject==null) {
			try {
				thisObject = getInstance();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {

			int type = 0;
			String fromUser = "";
			String toUser = "";
			String message = "";

			byte length_b = in.readByte();

			byte type_b = in.readByte();
			type = type_b;
			byte[] fromUser_b = new byte[12];
			for (int i = 0; i < 12; ++i) {
				fromUser_b[i] = in.readByte();
				fromUser += (char) fromUser_b[i];
			}
			byte[] toUser_b = new byte[12];
			for (int i = 0; i < 12; ++i) {
				toUser_b[i] = in.readByte();
				toUser += (char) toUser_b[i];
			}
			int message_length = length_b - 26;
			byte[] message_b = new byte[message_length];
			for (int i = 0; i < message_length; ++i) {
				message_b[i] = in.readByte();
			}
			if(fromUser.equals("000000000000")) {
				message = message_b[0] + "";
			} else {
				message = new String(message_b,"utf-8");
			}
			PackageBean bean = new PackageBean(type, fromUser, toUser, message);
			return bean;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean sendPackageBean(PackageBean bean) {
		if(thisObject==null) {
			try {
				thisObject = getInstance();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {

			// int length = 包长占位 + type占位 + toUser占位 + fromUser占位 + Mac占位 +
			// 实际消息占位

			int length = 1 + 1 + 12 + 12 + 17;
			if (bean.getData() != null) {
				Object tempData = bean.getData();
				if (tempData instanceof String) {
					length += ((String) tempData).getBytes().length;
				}
			}
			out.write((byte) length);
			out.write((byte) bean.getType());
			String tempString = bean.getFromUser();
			for (int i = 0; i < tempString.length(); ++i) {
				out.write((byte) tempString.charAt(i));
			}
			tempString = bean.gettoUser();
			for (int i = 0; i < tempString.length(); ++i) {
				out.write((byte) tempString.charAt(i));
			}
			tempString = "DC-85-DE-47-8B-73";
			for (int i = 0; i < tempString.length(); ++i) {
				out.write((byte) tempString.charAt(i));
			}
			if (bean.getData() != null) {
				Object tempData = bean.getData();
				if (tempData instanceof String) {
					tempString = (String) tempData;
					for (int i = 0; i < tempString.getBytes().length; ++i) {
						out.write((byte) tempString.getBytes()[i]);
					}
				} else {
				}
			}
			out.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}

	public DataOutputStream getOutputStream() {
		return out;
	}

}
