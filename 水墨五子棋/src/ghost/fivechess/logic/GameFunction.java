/**
 * 
 */
package ghost.fivechess.logic;

import ghost.fivechess.bean.Common;

import java.util.Stack;

import android.graphics.Point;

/**
 * 游戏的逻辑类，负责棋盘的建立，AI的处理，胜负判定等等
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-19
 */
abstract public class GameFunction implements Common {

	private static int map[][];
	private static int value[][];
	public static boolean playerTurn;
	public static int status;
	private static int player;
	private static int computer;
	private static Stack<int [][]> mapStack;
	
	private static int data_a[][] = new int[5][3];// 用于储存进攻值
	private static int data_d[][] = new int[5][3];// 用于储存防守值

	/**
	 * 开始新游戏
	 * @param first true表示如果玩家先手，false表示后手
	 */
	public static void newGame(boolean first) {
				// 进攻值的初始化
				data_a[1][1] = 2;
				data_a[1][2] = 3;
				data_a[2][1] = 10;
				data_a[2][2] = 110;
				data_a[3][1] = 2500;
				data_a[3][2] = 3000;
				data_a[4][1] = 99999;
				data_a[4][2] = 99999;
				// 防守值的初始化
				data_d[1][1] = 1;
				data_d[1][2] = 2;
				data_d[2][1] = 1;
				data_d[2][2] = 100;
				data_d[3][1] = 100;
				data_d[3][2] = 500;
				data_d[4][1] = 20000;
				data_d[4][2] = 50000;
		map = new int[15][15];
		playerTurn = first;
		status = GAME_RUN;
		if (first) {
			player = 1;
			computer = -1;
		} else {
			player = -1;
			computer = 1;
			map[7][7]=computer;
		}
		mapStack=new Stack<int [][]>();
	}

	/**
	 * 获得地图信息
	 * @return
	 */
	public static int[][] getMap() {
		return map;
	}

	/**
	 * 玩家落子
	 * @param x
	 * @param y
	 * @return 落子成功返回true，否则返回false
	 */
	public static boolean doClick(int x, int y) {
		if (map[x][y] == 0) {
			push();
			map[x][y] = player;
			return true;
		} else
			return false;
	}
	
	/**
	 * 电脑落子
	 * @param x
	 * @param y
	 * @return 落子成功返回true，否则返回false
	 */
	public static boolean doComputerClick(int x, int y) {
		if (map[x][y] == 0) {
			map[x][y] = computer;
			return true;
		} else
			return false;
	}

	/**
	 * 判断哪方获胜
	 * 
	 * @return 0表示没有获胜者，1表示玩家获胜，-1表示电脑获胜,2表示和棋
	 */
	public static int checkWhoWin(int x, int y) {
		int who = map[x][y];
		int m, n, i, lianzi = 0;
		for (m = -1; m <= 1; m++) {
			for (n = -1; n <= 1; n++) {
				if (m != 0 || n != 0) {
					for (i = 1; i <= 4; i++) {
						if (x + i * m >= 0 && x + i * m < 15 && y + i * n >= 0
								&& y + i * n < 15
								&& map[x + i * m][y + i * n] == who) {
							lianzi++;
						} else {
							break;
						}
					}
					for (i = -1; i >= -4; i--) {
						if (x + i * m >= 0 && x + i * m < 15 && y + i * n >= 0
								&& y + i * n < 15
								&& map[x + i * m][y + i * n] == who) {
							lianzi++;
						} else {
							break;
						}
					}
					if (lianzi >= 4) {
						if (who == player) {
							return 1;
						} else {
							return -1;
						}
					} else {
						lianzi = 0;
					}
				}
			}
		}
		for (int a = 0; a < 15; ++a) {
			for (int b = 0; b < 15; ++b) {
				if (map[a][b] == 0) {
					return 0;
				}
			}
		}
		return 2;
	}
	
	/**
	 * 返回电脑走棋，可以用来辅助玩家走棋
	 * @param who 1代表帮玩家走棋，-1代表电脑走棋
	 * @return 走棋位置
	 */
	public static Point getComputerDone(int who) {
		boolean tempKey = true;
		for(int i=0;i<15;++i) {
			for(int j=0;j<15;++j) {
				if(map[i][j] != 0) {
					tempKey = false;
				}
			}
		}
		if(tempKey) {
			return new Point(7,7);
		}
		int player1,player2;
		if(who==1) {
			player1=player;
			player2=computer;
		} else {
			player2=player;
			player1=computer;
		}
		Point p=null;
		value=new int[15][15];
		int continues1 = 0, continues2 = 0, free = 0;
		for (int x = 0; x < 15; ++x) {
			for (int y = 0; y < 15; ++y) {
				if (map[x][y] == 0) {
					for (int m = -1; m <= 1; ++m) {
						for (int n = -1; n <= 1; ++n) {
							if (m != 0 || n != 0) {
								for (int i = 1; i <= 4; ++i) {
									if (x + i * m < 15
											&& y + i * n < 15
											&& x + i * m >= 0
											&& y + i * n >= 0
											&& map[x + i * m][y + i * n] == player1) {
										continues1++;
									} else {
										if (x + i * m < 15
												&& y + i * n < 15
												&& x + i * m >= 0
												&& y + i * n >= 0
												&& map[x + i * m][y + i
														* n] == 0) {
											free++;
										}
										i = 5;
										break;
									}
								}
								for (int i = -1; i >= -4; --i) {
									if (x + i * m < 15
											&& y + i * n < 15
											&& x + i * m >= 0
											&& y + i * n >= 0
											&& map[x + i * m][y + i * n] == player1) {
										continues1++;
									} else {
										if (x + i * m < 15
												&& y + i * n < 15
												&& x + i * m >= 0
												&& y + i * n >= 0
												&& map[x + i * m][y + i
														* n] == 0) {
											free++;
										}
										i = -5;
										break;
									}
								}
								if (continues1 == 1) {
									if (free == 1) {
										value[x][y] += data_a[1][1];
									} else if (free >= 2) {
										value[x][y] += data_a[1][2];
									}
								} else if (continues1 == 2) {
									if (free == 1) {
										value[x][y] += data_a[2][1];
									} else if (free >= 2) {
										value[x][y] += data_a[2][2];
									}
								} else if (continues1 == 3) {
									if (free == 1) {
										value[x][y] += data_a[3][1];
									} else if (free >= 2) {
										value[x][y] += data_a[3][2];
									}
								} else if (continues1 >= 4) {
									if (free >= 0) {
										value[x][y] += data_a[4][1];
									} else if (free >= 2) {
										value[x][y] += data_a[4][2];
									}
								}
								free = 0;
								// ///////////////////////进攻////////////////////////////////////
								for (int i = 1; i <= 4; ++i) {
									if (x + i * m < 15
											&& y + i * n < 15
											&& x + i * m >= 0
											&& y + i * n >= 0
											&& map[x + i * m][y + i * n] == player2) {
										continues2++;
									} else {
										if (x + i * m < 15
												&& y + i * n < 15
												&& x + i * m >= 0
												&& y + i * n >= 0
												&& map[x + i * m][y + i
														* n] == 0) {
											free++;
										}
										i = 5;
										break;
									}
								}
								for (int i = -1; i >= -4; --i) {
									if (x + i * m < 15
											&& y + i * n < 15
											&& x + i * m >= 0
											&& y + i * n >= 0
											&& map[x + i * m][y + i * n] == player2) {
										continues2++;
									} else {
										if (x + i * m < 15
												&& y + i * n < 15
												&& x + i * m >= 0
												&& y + i * n >= 0
												&& map[x + i * m][y + i
														* n] == 0) {
											free++;
										}
										i = -5;
										break;
									}
								}
								if (continues2 == 1) {
									if (free == 1) {
										value[x][y] += data_d[1][1];
									} else if (free >= 2) {
										value[x][y] += data_d[1][2];
									}
								} else if (continues2 == 2) {
									if (free == 1) {
										value[x][y] += data_d[2][1];
									} else if (free >= 2) {
										value[x][y] += data_d[2][2];
									}
								} else if (continues2 == 3) {
									if (free == 1) {
										value[x][y] += data_d[3][1];
									} else if (free >= 2) {
										value[x][y] += data_d[3][2];
									}
								} else if (continues2 >= 4) {
									if (free >= 0) {
										value[x][y] += data_d[4][1];
									} else if (free >= 2) {
										value[x][y] += data_d[4][2];
									}
								}
								continues1 = 0;
								continues2 = 0;
								free = 0;
								// ////////////////////////防守///////////////////////
							}
						}
					}
					// /////////222222222///////////////////////////////////////////////////////////////////
				}
			}
		}
		int max = 0;
		for (int i = 0; i < 15; ++i) {
			for (int j = 0; j < 15; ++j) {
				if (max < value[i][j]) {
					max = value[i][j];
					p=new Point(i,j);
				}
			}
		}
		return p;
	}
	
	/**
	 * 将当前棋盘压栈
	 */
	private static void push() {
		int tempMap[][]=new int[15][15];
		for(int i=0;i<15;++i) {
			for(int j=0;j<15;++j) {
				tempMap[i][j]=map[i][j];
			}
		}
		mapStack.push(tempMap);
	}
	
	/**
	 * 弹栈操作
	 */
	public static void pop() {
		if(!mapStack.isEmpty()) {
			map=mapStack.pop();
		}
	}
}

