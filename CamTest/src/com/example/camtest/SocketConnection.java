/**
 * 
 */
package com.example.camtest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-20
 */
public class SocketConnection {

	private Socket socket;
	private DataOutputStream out;

	private byte[] tempData;

	public SocketConnection() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					socket = new Socket("172.31.11.36", 8215);
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
