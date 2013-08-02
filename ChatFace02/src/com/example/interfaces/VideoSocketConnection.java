/**
 * 
 */
package com.example.interfaces;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-20
 */
public class VideoSocketConnection {

	private Socket socket;
	private DataOutputStream out;
	private String IP;

	private byte[] tempData;

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public VideoSocketConnection() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					socket = new Socket(IP, 8215);
					out = new DataOutputStream(socket.getOutputStream());
					System.out.println("连接成功");
					new Thread(new Runnable() {

						@Override
						public void run() {
							while (true) {
								try {
									if (out != null && tempData != null) {
										byte[] temp = tempData;
										out.writeInt(temp.length);
										out.write(temp);
										out.flush();
									}
									try {
										Thread.sleep(1);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}).start();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}).start();
	}

	public void sendDatas(final byte[] data) {
		tempData = data;
	}
}
