package ghost.picmatch.logic;

import java.awt.Point;
import java.util.ArrayList;

import ghost.picmatch.data.GameData;
import ghost.picmatch.data.Line;
import ghost.picmatch.util.Common;

public class GameLogic implements Common {
	
	/**
	 * 新游戏初始化
	 */
	public static void prepareNewGame() {
		MapFunction.createMap(GameData.map, GameData.grade * 5 + 8);
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 7; ++j) {
				GameData.mapvalue[i][j][0] = 5 - (int) (Math.random() * 10);
				if (Math.random() > 0.5) {
					GameData.mapvalue[i][j][1] = 0;
				} else {
					GameData.mapvalue[i][j][1] = 1;
				}
			}
		}
		GameData.time = GameData.max_time;
		GameData.lines.clear();
	}
	
	/**
	 * 判断图块连线是否成立
	 * 
	 * @param x
	 * @param y
	 */
	public static void picMatch(int x, int y) {
		int tempx = x;
		int tempy = y;
		if (GameData.map[x][y] == -1) {
			return;
		}
		if (GameData.p1 == null) {
			GameData.p1 = new Point(tempx, tempy);
			GameData.mapvalue[tempx][tempy][2] = 1;
		} else if ((tempx != GameData.p1.x) || (tempy != GameData.p1.y)) {
			GameData.p2 = new Point(tempx, tempy);
			GameData.mapvalue[tempx][tempy][2] = 1;
			if (MapFunction.canDecrease(GameData.p1, GameData.p2, GameData.map)) {
				ArrayList<Point> list = MapFunction.getList();
				// //////////////////////////添加连线
				int tempX = -30;
				int tempY = 90;
				if (list.size() == 0) {
					Point tempPoint1 = new Point(GameData.p1.y + 1,
							GameData.p1.x + 1);
					Point tempPoint2 = new Point(GameData.p2.y + 1,
							GameData.p2.x + 1);
					GameData.lines
							.add(new Line(
									tempX + tempPoint1.x * 60 + 25,
									tempY + tempPoint1.y * 60 + 25,
									(tempPoint1.x - tempPoint2.x) * 60 != 0 ? -(tempPoint1.x - tempPoint2.x) * 60
											: 10,
									(tempPoint1.y - tempPoint2.y) * 60 != 0 ? -(tempPoint1.y - tempPoint2.y) * 60
											: 10));
				} else if (list.size() == 1) {
					Point temp = list.get(0);
					Point tempPoint1 = new Point(GameData.p1.y + 1,
							GameData.p1.x + 1);
					Point tempPoint2 = new Point(temp.y, temp.x);
					GameData.lines
							.add(new Line(
									tempX + tempPoint1.x * 60 + 25,
									tempY + tempPoint1.y * 60 + 25,
									(tempPoint1.x - tempPoint2.x) * 60 != 0 ? -(tempPoint1.x - tempPoint2.x) * 60
											: 10,
									(tempPoint1.y - tempPoint2.y) * 60 != 0 ? -(tempPoint1.y - tempPoint2.y) * 60
											: 10));

					tempPoint1 = new Point(temp.y, temp.x);
					tempPoint2 = new Point(GameData.p2.y + 1, GameData.p2.x + 1);
					GameData.lines
							.add(new Line(
									tempX + tempPoint1.x * 60 + 25,
									tempY + tempPoint1.y * 60 + 25,
									(tempPoint1.x - tempPoint2.x) * 60 != 0 ? -(tempPoint1.x - tempPoint2.x) * 60
											: 10,
									(tempPoint1.y - tempPoint2.y) * 60 != 0 ? -(tempPoint1.y - tempPoint2.y) * 60
											: 10));

				} else if (list.size() == 2) {
					Point temp1 = list.get(1);
					Point temp2 = list.get(0);

					Point tempPoint1 = new Point(GameData.p1.y + 1,
							GameData.p1.x + 1);
					Point tempPoint2 = new Point(temp1.y, temp1.x);
					GameData.lines
							.add(new Line(
									tempX + tempPoint1.x * 60 + 25,
									tempY + tempPoint1.y * 60 + 25,
									(tempPoint1.x - tempPoint2.x) * 60 != 0 ? -(tempPoint1.x - tempPoint2.x) * 60
											: 10,
									(tempPoint1.y - tempPoint2.y) * 60 != 0 ? -(tempPoint1.y - tempPoint2.y) * 60
											: 10));

					tempPoint1 = new Point(temp1.y, temp1.x);
					tempPoint2 = new Point(temp2.y, temp2.x);
					GameData.lines
							.add(new Line(
									tempX + tempPoint1.x * 60 + 25,
									tempY + tempPoint1.y * 60 + 25,
									(tempPoint1.x - tempPoint2.x) * 60 != 0 ? -(tempPoint1.x - tempPoint2.x) * 60
											: 10,
									(tempPoint1.y - tempPoint2.y) * 60 != 0 ? -(tempPoint1.y - tempPoint2.y) * 60
											: 10));

					tempPoint1 = new Point(temp2.y, temp2.x);
					tempPoint2 = new Point(GameData.p2.y + 1, GameData.p2.x + 1);
					GameData.lines
							.add(new Line(
									tempX + tempPoint1.x * 60 + 25,
									tempY + tempPoint1.y * 60 + 25,
									(tempPoint1.x - tempPoint2.x) * 60 != 0 ? -(tempPoint1.x - tempPoint2.x) * 60
											: 10,
									(tempPoint1.y - tempPoint2.y) * 60 != 0 ? -(tempPoint1.y - tempPoint2.y) * 60
											: 10));
				}

				// 加分神马的....
				GameData.map[GameData.p1.x][GameData.p1.y] = -1;
				GameData.map[GameData.p2.x][GameData.p2.y] = -1;
				GameData.score++;
				if (GameData.time < GameData.max_time)
					GameData.time++;
				if (MapFunction.ckeckOver(GameData.map)) {
					new Thread(new Runnable() {
						public void run() {
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							GameData.status = WIN;
						}
					}).start();
				}
			}
			GameData.mapvalue[GameData.p1.x][GameData.p1.y][2] = 0;
			GameData.mapvalue[GameData.p2.x][GameData.p2.y][2] = 1;
			GameData.p1 = GameData.p2;
			GameData.p2 = null;
		}
	}
	
	/**
	 * 自动旋转图块
	 */
	public static void rolatePics() {
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 7; ++j) {
				if (GameData.map[i][j] != -1) {
					if (GameData.mapvalue[i][j][1] == 1) {
						GameData.mapvalue[i][j][0]++;
						if (GameData.mapvalue[i][j][0] >= 7) {
							GameData.mapvalue[i][j][1] = 0;
						}
					} else {
						GameData.mapvalue[i][j][0]--;
						if (GameData.mapvalue[i][j][0] <= -7) {
							GameData.mapvalue[i][j][1] = 1;
						}
					}
				}
			}
		}
	}

}
