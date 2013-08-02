/**
 * 
 */
package com.ghost.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-21
 */
public class Server extends JFrame implements ActionListener {

	private ServerSocket server;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField jtf_port;
	private JTextField jtf_send;
	private JTextArea jta_message;
	private JButton jbu_start;
	private JButton jbu_send;
	private List<DataOutputStream> outList;

	public Server() {
		super("聊天室--服务器:未开启");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 600, 500);
		outList = new ArrayList<>();
		initGUI();
		this.setVisible(true);
	}

	private void initGUI() {
		JPanel tempHead = new JPanel();
		tempHead.setBackground(Color.BLACK);
		JLabel jl_port_info = new JLabel("端口号:");
		jl_port_info.setBackground(Color.BLACK);
		jl_port_info.setForeground(Color.WHITE);
		tempHead.add(jl_port_info);
		jtf_port = new JTextField(8);
		tempHead.add(jtf_port);
		jbu_start = new JButton("开启");
		jbu_start.setBackground(Color.BLACK);
		jbu_start.setForeground(Color.WHITE);
		jbu_start.addActionListener(this);
		tempHead.add(jbu_start);
		JLabel jl_message_info = new JLabel("下发消息:");
		jl_message_info.setBackground(Color.BLACK);
		jl_message_info.setForeground(Color.WHITE);
		tempHead.add(jl_message_info);
		jtf_send = new JTextField(20);
		jtf_send.setActionCommand("下发");
		jtf_send.setEnabled(false);
		jtf_send.addActionListener(this);
		tempHead.add(jtf_send);
		jbu_send = new JButton("下发");
		jbu_send.setEnabled(false);
		jbu_send.setBackground(Color.BLACK);
		jbu_send.setForeground(Color.WHITE);
		jbu_send.addActionListener(this);
		tempHead.add(jbu_send);

		jta_message = new JTextArea();
		jta_message.setEditable(false);
		jta_message.setBackground(Color.GRAY);
		JScrollPane tempJsp = new JScrollPane(jta_message);
		this.add(tempJsp, BorderLayout.CENTER);

		this.add(tempHead, BorderLayout.NORTH);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Server();
	}

	private void sendAll(String message) {
		for (int i = 0; i < outList.size(); ++i) {
			DataOutputStream tempOut = outList.get(i);
			try {
				tempOut.writeUTF(message);
			} catch (IOException e) {
				outList.remove(i);
			}
		}
	}

	private void appendMessage(String message) {
		jta_message.append(message + "\n");
	}

	private class ServerService implements Runnable {

		private DataInputStream in;

		public ServerService(DataInputStream in) {
			this.in = in;
		}

		@Override
		public void run() {
			while (true) {
				try {
					String message = in.readUTF();
					appendMessage(message);
					sendAll(message);
				} catch (IOException e) {
					return;
				}
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "开启":
			String port = jtf_port.getText();
			try {
				server = new ServerSocket(Integer.parseInt(port));
				this.setTitle("聊天室--服务器:" + port);
				new Thread(new Runnable() {

					@Override
					public void run() {
						while (true) {
							try {
								Socket socket = server.accept();
								outList.add(new DataOutputStream(socket
										.getOutputStream()));
								new Thread(new ServerService(
										new DataInputStream(socket
												.getInputStream()))).start();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}

				}).start();
				jtf_port.setEnabled(false);
				jbu_start.setEnabled(false);
				jtf_send.setEnabled(true);
				jbu_send.setEnabled(true);
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		case "下发":
			String message = jtf_send.getText();
			if (!message.trim().equals("")) {
				jtf_send.setText("");
				sendAll("[下发消息]:" + message);
			}
			break;
		}
	}

}
