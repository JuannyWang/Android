/**
 * 
 */
package com.ghost.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-21
 */
public class MyConnection {

	private DataOutputStream out;
	private DataInputStream in;

	public MyConnection(Socket socket) {
		try {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) {
		try {
			out.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String rendMessage() {
		String result = "出错啦！！！请与管理员联系";
		try {
			result = in.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
