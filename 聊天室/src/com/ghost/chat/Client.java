/**
 * 
 */
package com.ghost.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-21
 */
public class Client extends JFrame implements ActionListener {

	private MyConnection connection;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField jtf_name;
	private JTextArea jta_message;
	private JTextField jtf_send;
	private JButton jbu_login;
	private JButton jbu_send;

	public Client() {
		super("聊天室--客户端：未连接");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 10, 400, 600);
		initGUI();
		this.setVisible(true);
	}

	private void initGUI() {
		JPanel head_panel = new JPanel();
		head_panel.setBackground(Color.BLACK);
		JLabel tempLabel = new JLabel("你的名字:");
		tempLabel.setBackground(Color.BLACK);
		tempLabel.setForeground(Color.WHITE);
		head_panel.add(tempLabel);
		jtf_name = new JTextField(20);
		head_panel.add(jtf_name);
		jbu_login = new JButton("登录");
		jbu_login.addActionListener(this);
		jbu_login.setBackground(Color.BLACK);
		jbu_login.setForeground(Color.WHITE);
		head_panel.add(jbu_login);
		this.add(head_panel, BorderLayout.NORTH);

		jta_message = new JTextArea();
		jta_message.setEditable(false);
		jta_message.setBackground(Color.GRAY);
		JScrollPane tempJsp = new JScrollPane(jta_message);
		this.add(tempJsp, BorderLayout.CENTER);

		JPanel tempFoot = new JPanel();
		tempFoot.setBackground(Color.BLACK);
		JLabel temp_Label2 = new JLabel("发送消息:");
		temp_Label2.setBackground(Color.BLACK);
		temp_Label2.setForeground(Color.WHITE);
		tempFoot.add(temp_Label2);
		jtf_send = new JTextField(20);
		jtf_send.setActionCommand("发送");
		jtf_send.setEnabled(false);
		jtf_send.addActionListener(this);
		tempFoot.add(jtf_send);
		jbu_send = new JButton("发送");
		jbu_send.setBackground(Color.BLACK);
		jbu_send.setForeground(Color.WHITE);
		jbu_send.setEnabled(false);
		jbu_send.addActionListener(this);
		tempFoot.add(jbu_send);
		this.add(tempFoot, BorderLayout.SOUTH);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Client();
	}

	private void appendMessage(String message) {
		jta_message.append(message + "\n");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "登录":
			try {
				String name = jtf_name.getText();
				if (name.trim().equals("")) {
					JOptionPane.showMessageDialog(this, "请输入用户名");
					return;
				}
				Socket socket = new Socket("127.0.0.1", 8215);
				connection = new MyConnection(socket);
				jbu_login.setEnabled(false);
				jbu_send.setEnabled(true);
				jtf_send.setEnabled(true);
				jtf_name.setEnabled(false);
				this.setTitle("聊天室--客户端：" + name);
				connection.sendMessage(name + "进入聊天室");
				new Thread(new Runnable() {
					@Override
					public void run() {
						while (true) {
							String message = connection.rendMessage();
							appendMessage(message);
						}
					}
				}).start();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		case "发送":
			String message = jtf_send.getText();
			if (!message.trim().equals("")) {
				connection.sendMessage(jtf_name.getText() + ":" + message);
				jtf_send.setText("");
			}
			break;
		default:
			break;
		}
	}

}
