/**
 * 
 */
package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-15
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MyFrame();
	}

}

class Logic {
	
	private static final int INFINITY=100;
	

	private static void copyArray(int data[][], int tempData[][]) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				tempData[i][j] = data[i][j];
			}
		}
	}

	private static int evaluate(final int data[][]) {
		int tempData[][] = new int[3][3];
		copyArray(data, tempData);
		if(checkOver(tempData)==1)
			return INFINITY;
		if(checkOver(tempData)==-1)
			return -INFINITY;
		if(checkOver(tempData)==2) {
			return 10;
		}
		int tempPlayer = 1;
		for(int i=0;i<3;++i) {
			for(int j=0;j<3;++j) {
				if(tempData[i][j]==0&&tempData[i][(j+1)>2?(2-j):(j+1)]==tempPlayer&&tempData[i][(j+2)>2?(j-1):(j+2)]==tempPlayer) {
					return INFINITY/2;
				}
			}
		}
		tempPlayer = -1;
		for(int i=0;i<3;++i) {
			for(int j=0;j<3;++j) {
				if(tempData[i][j]==0&&tempData[i][(j+1)>2?(2-j):(j+1)]==tempPlayer&&tempData[i][(j+2)>2?(j-1):(j+2)]==tempPlayer) {
					return -INFINITY/2;
				}
			}
		}
		return 0;
	}

	private static void printMap(final int data[][]) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				System.out.print(data[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static int checkOver(final int data[][]) {
		int tempData[][] = data;
		boolean over=true;
		for(int i=0;i<3;++i) {
			for(int j=0;j<3;++j) {
				if(tempData[i][j]==0) {
					over=false;
				}
			}
		}
		if(over)
			return 2;
		int tempPlayer = 1;
		if ((tempData[0][0] == tempPlayer && tempData[1][1] == tempPlayer && tempData[2][2] == tempPlayer)
				|| (tempData[0][0] == tempPlayer
						&& tempData[0][1] == tempPlayer && tempData[0][2] == tempPlayer)
				|| (tempData[1][0] == tempPlayer
						&& tempData[1][1] == tempPlayer && tempData[1][2] == tempPlayer)
				|| (tempData[2][0] == tempPlayer
						&& tempData[2][1] == tempPlayer && tempData[2][2] == tempPlayer)
				|| (tempData[0][0] == tempPlayer
						&& tempData[1][0] == tempPlayer && tempData[2][0] == tempPlayer)
				|| (tempData[0][1] == tempPlayer
						&& tempData[1][1] == tempPlayer && tempData[2][1] == tempPlayer)
				|| (tempData[0][2] == tempPlayer
						&& tempData[1][2] == tempPlayer && tempData[2][2] == tempPlayer)
				|| (tempData[2][0] == tempPlayer
						&& tempData[1][1] == tempPlayer && tempData[0][2] == tempPlayer)) {
			return tempPlayer;
		}
		tempPlayer = -1;
		if ((tempData[0][0] == tempPlayer && tempData[1][1] == tempPlayer && tempData[2][2] == tempPlayer)
				|| (tempData[0][0] == tempPlayer
						&& tempData[0][1] == tempPlayer && tempData[0][2] == tempPlayer)
				|| (tempData[1][0] == tempPlayer
						&& tempData[1][1] == tempPlayer && tempData[1][2] == tempPlayer)
				|| (tempData[2][0] == tempPlayer
						&& tempData[2][1] == tempPlayer && tempData[2][2] == tempPlayer)
				|| (tempData[0][0] == tempPlayer
						&& tempData[1][0] == tempPlayer && tempData[2][0] == tempPlayer)
				|| (tempData[0][1] == tempPlayer
						&& tempData[1][1] == tempPlayer && tempData[2][1] == tempPlayer)
				|| (tempData[0][2] == tempPlayer
						&& tempData[1][2] == tempPlayer && tempData[2][2] == tempPlayer)
				|| (tempData[2][0] == tempPlayer
						&& tempData[1][1] == tempPlayer && tempData[0][2] == tempPlayer)) {
			return tempPlayer;
		}
		return 0;
	}

	public static Point autoDo(final int data[][]) {
		int tempData[][] = new int[3][3];
		copyArray(data, tempData);
		int best = Integer.MIN_VALUE;
		int val = 0;
		Point p = null;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (tempData[i][j] == 0) {
					tempData[i][j] = -1;
					val = MaxMin(tempData, 9, 1);
					if (val >= best) {
						best = val;
						p = new Point(i, j);
					}
					copyArray(data, tempData);
				}
			}
		}
		return p;
	}

	private static int MaxMin(final int data[][], int depth, int player) {
		switch (player) {
		case 1:
			return max(data, depth);
		case -1:
			return min(data, depth);
		}
		return 0;
	}

	private static int max(final int data[][], int depth) {
		int best = Integer.MIN_VALUE;
		int val = 0;
		int tempData[][] = new int[3][3];
		copyArray(data, tempData);
		if (depth <= 0 || checkOver(tempData) != 0) {
			return evaluate(tempData) ;
		}
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (tempData[i][j] == 0) {
					tempData[i][j] = 1;
					val = min(tempData, depth - 1);
					copyArray(data, tempData);
					if (val > best) {
						best = val;
					}
				}
			}
		}
		return best;
	}

	private static int min(final int data[][], int depth) {
		int best = Integer.MAX_VALUE;
		int val = 0;
		int tempData[][] = new int[3][3];
		copyArray(data, tempData);
		if (depth <= 0 || checkOver(tempData) != 0) {
			return evaluate(tempData) * (depth + 1);
		}
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (tempData[i][j] == 0) {
					tempData[i][j] = -1;
					val = max(tempData, depth - 1);
					copyArray(data, tempData);
					if (val < best) {
						best = val;
					}
				}
			}
		}
		return best;
	}
}

class MyPanel extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int width, height;
	private int square_w, square_h;

	private int data[][];

	MyPanel() {
		super();
		data = new int[3][3];
		this.addMouseListener(this);
	}

	public void paint(Graphics g) {
		super.paint(g);
		this.width = this.getParent().getWidth();
		this.height = this.getParent().getHeight();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, width, height);
		square_w = width / 3;
		square_h = height / 3;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				g2d.setColor(Color.gray);
				g2d.fillRect(i * square_w + 5, j * square_h + 5, square_w - 10,
						square_h - 10);
				if (data[i][j] == 0) {

				} else if (data[i][j] == 1) {
					g2d.setColor(Color.blue);
					g2d.fillArc(i * square_w + 10, j * square_h + 10,
							square_w - 20, square_h - 20, 0, 360);
				} else if (data[i][j] == -1) {
					g2d.setColor(Color.cyan);
					g2d.fillArc(i * square_w + 10, j * square_h + 10,
							square_w - 20, square_h - 20, 0, 360);
				}

			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int w = x / square_w;
		int h = y / square_h;
		if (data[w][h] == 0) {
			data[w][h] = 1;
			repaint();
			if (Logic.checkOver(data) == 1) {
				System.out.println("player win!");
				return;
			}
			Point tempPoint = Logic.autoDo(data);
			data[tempPoint.x][tempPoint.y] = -1;
			repaint();
			if (Logic.checkOver(data) == -1) {
				System.out.println("computer win!");
				return;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

class MyFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MyFrame() {
		super("测试");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(200, 100);
		this.setSize(400, 400);
		this.add(new MyPanel());
		this.setResizable(true);
		this.setVisible(true);
	}

}
