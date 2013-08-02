/**
 * 
 */
package test;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-7-20
 */
public class TestReceiver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MyFrame();
	}

}

class MyFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyFrame() {
		super("视频接收测试");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new MyViewPanel());
		this.pack();
		this.setVisible(true);
	}

}

class MyViewPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ServerSocket server;

	private Image image;

	private byte[] imageData;

	public MyViewPanel() {
		try {
			server = new ServerSocket(8215);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setPreferredSize(new Dimension(500, 500));
		new Thread(new Runnable() {

			@Override
			public void run() {

				Socket socket;
				DataInputStream in = null;
				try {
					socket = server.accept();
					System.out.println("接收到："
							+ socket.getInetAddress().toString());
					in = new DataInputStream(socket.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				while (true) {
					try {
						int n_len = in.readInt();
						byte[] data = new byte[n_len];
						int r_len = 0;
						while (r_len != n_len)
							r_len += in.read(data, r_len, n_len - r_len);
						System.out.println("需要的的数据包长度为:" + n_len);
						System.out.println("接收到的数据包长度为:" + r_len);
						System.out.println("开始解包");
						image = getToolkit().createImage(data, 0, r_len);
						System.out.println("解包成功,解包后的图片宽度为："
								+ image.getWidth(null) + "高度为："
								+ image.getHeight(null));
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}

				// DatagramSocket server = null;
				// try {
				// server = new DatagramSocket(5050);
				// } catch (SocketException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// while (true) {
				// byte[] recvBuf = new byte[10000];
				// DatagramPacket recvPacket = new DatagramPacket(recvBuf,
				// recvBuf.length);
				// try {
				// server.receive(recvPacket);
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// image = getToolkit().createImage(recvBuf, 0,
				// recvPacket.getLength());
				// System.out.println("解包成功,解包后的图片宽度为：" + image.getWidth(null)
				// + "高度为：" + image.getHeight(null));
				// }
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					repaint();
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (image != null) {
			g2d.drawImage(image, 0, 0, image.getWidth(null),
					image.getHeight(null), 0, 0, 500, 500, null);
		}
	}
}
