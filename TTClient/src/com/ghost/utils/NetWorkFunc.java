/**
 * 
 */
package com.ghost.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-8
 */
public class NetWorkFunc {
	
	public static ServerSocket server;
	public static Socket socket;
	public static BufferedOutputStream serverOut;
	public static BufferedInputStream serverIn;
	private static String tag;

	/**
	 * 连接服务器（GHOST）
	 * 根据公用字段中配置信息连接服务器
	 * @return
	 */
	public static Thread connectServer() {
		Thread mythread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Log.v(tag, "尝试连接服务器");
					socket = new Socket(Constants.SERVER_IP, Constants.SERVER_PORT);
					Log.v(tag, "连接服务器成功");
					serverOut = new BufferedOutputStream(new DataOutputStream(
							socket.getOutputStream()));
					serverIn = new BufferedInputStream(new DataInputStream(
							socket.getInputStream()));
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		mythread.start();
		return mythread;
	}
	
	/**
	 * 向服务器发数据(GHOST)
	 * @param message
	 */
	public static void sendMessageToServer(String message) {
		try {
			serverOut.write(message.getBytes());
			serverOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendLocalMesage(String phoneNum, String ip) {
		try {
			serverOut.write(("1;" + phoneNum + ";" + ip).getBytes());
			serverOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendLocalMesage(String phoneNum) {
		try {
			serverOut.write(("2;" + phoneNum).getBytes());
			serverOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendMesage(String str) {
		try {
			serverOut.write(str.getBytes());
			serverOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void startServer() {
		try {
			server = new ServerSocket(3060);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void accept() {
		try {
			socket = server.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取一次消息(GHOST)
	 * @return
	 */
	public static String readLine() {
		try {
			byte[] pack = new byte[100];
			serverIn.read(pack);
			return new String(pack).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "消息读取失败";
	}

}
