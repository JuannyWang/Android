/**
 * 
 */
package com.ghost.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ghost.beans.LoginData;
import com.ghost.beans.PackageBean;
import com.ghost.beans.PackageType;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-12
 */
public class Server {

	private ServerSocket server;

	private boolean isRun;

	private ExecutorService pool;

	public Server() {
		try {
			server = new ServerSocket(8215);
			pool = Executors.newFixedThreadPool(500);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startServer() {
		if (!isRun) {
			isRun = true;
			new Thread(new Logic()).start();
		}
	}

	public void stopServer() {
		if (isRun) {
			isRun = false;
		}
	}

	private class Logic implements Runnable {

		@Override
		public void run() {
			while (isRun) {
				try {
					Socket tempSocket = server.accept();
					System.out.println("新客户端接入:" + tempSocket.getInetAddress());
					pool.execute(new ClientLogic(tempSocket));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private class ClientLogic implements Runnable {

		private Socket client;

		private ObjectOutputStream out;
		private ObjectInputStream in;

		public ClientLogic(Socket client) {
			this.client = client;
			try {
				out = new ObjectOutputStream(client.getOutputStream());
				in = new ObjectInputStream(client.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			while (client.isConnected()) {
				try {
					PackageBean obj = (PackageBean) in.readObject();
					PackageBean back;
					switch (obj.getType()) {
					case PackageType.TEST:
						break;
					case PackageType.PROVE:
						back = new PackageBean(PackageType.RECEIVED, 0,
								obj.getFromUser(), PackageType.SCCESS);
						out.writeObject(back);
						out.flush();
						break;
					case PackageType.LOGIN:
						LoginData data = (LoginData) obj.getData();
						if (data.getPassword().equals(
								obj.getFromUser() + "" + obj.getFromUser())) {
							back = new PackageBean(PackageType.LOGIN, 0,
									obj.getFromUser(), PackageType.SCCESS);
						} else {
							back = new PackageBean(PackageType.LOGIN, 0,
									obj.getFromUser(), PackageType.FAILED);
						}
						out.writeObject(back);
						out.flush();
						break;
					default:
						break;
					}
				} catch (ClassNotFoundException | IOException e) {
					try {
						in.close();
						out.close();
						client.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
					break;
				}
			}
		}

	}

}
