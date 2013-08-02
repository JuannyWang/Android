package ghost.picmatch.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class MapFunction {

	private static ArrayList<Point> temp_point;

	public static void createMap(int[][] map, int num) {
		int values[] = new int[56];
		Random rand = new Random();
		// 生成28对随机数
		for (int i = 0; i < 56;) {
			int temp = rand.nextInt(num);
			values[i++] = temp;
			values[i++] = temp;
		}

		// 生成随机地图(一维的)
		int mapValue[] = new int[56];
		int size = 56;
		for (int i = 0; i < 56; ++i) {
			int temp = rand.nextInt(size);
			mapValue[i] = values[temp];
			for (int j = temp; j < size - 1; ++j) {
				values[j] = values[j + 1];
			}
			size--;
		}

		// 将一维随机地图转换为二维
		for (int i = 0; i < 56; ++i) {
			map[i / 7][i % 7] = mapValue[i];
		}

	}

	// 判断是否能消除
	public static boolean canDecrease(Point pp1, Point pp2, int[][] map) {
		temp_point = new ArrayList<Point>();
		if ((map[pp1.x][pp1.y] != map[pp2.x][pp2.y]) || map[pp1.x][pp1.y] == -1
				|| pp1.equals(pp2)) {
			return false;
		}
		Point p1 = new Point(pp1.x + 1, pp1.y + 1);
		Point p2 = new Point(pp2.x + 1, pp2.y + 1);
		int[][] data = new int[10][9];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 9; j++) {
				data[i][j] = -1;
			}
		}
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 7; ++j) {
				data[i + 1][j + 1] = map[i][j];
			}
		}
		if (judgeNone(p1, p2, data)) {
			temp_point.clear();
			return true;
		}
		if (judgeOne(p1, p2, data)) {
			return true;
		}
		if (judgeTwo(p1, p2, data)) {
			return true;
		}
		return false;
	}

	public static boolean ckeckOver(int[][] map) {
		boolean result = true;
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 7; ++j) {
				if (map[i][j] != -1) {
					result = false;
					break;
				}

			}
			if (!result)
				break;
		}
		if (result)
			return true;
		result = true;
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 7; ++j) {
				for (int m = 0; m < 8; ++m) {
					for (int n = 0; n < 7; ++n) {
						if (canDecrease(new Point(i, j), new Point(m, n), map)) {
							return false;
						}
					}
				}
			}
		}
		return result;
	}

	public static ArrayList<Point> getList() {
		return temp_point;
	}

	// 判断竖
	private static boolean judgeVertical(Point p1, Point p2, int[][] map) {
		if (p1.x != p2.x) {
			return false;
		}
		for (int i = ((p1.y < p2.y) ? p1.y : p2.y) + 1; i < ((p1.y > p2.y) ? p1.y
				: p2.y); i++) {
			if (map[p1.x][i] != -1) {
				return false;
			}
		}
		return true;
	}

	// 判断横
	private static boolean judgeHorizontal(Point p1, Point p2, int[][] map) {
		if (p1.y != p2.y) {
			return false;
		}
		for (int i = ((p1.x < p2.x) ? p1.x : p2.x) + 1; i < ((p1.x > p2.x) ? p1.x
				: p2.x); i++) {
			if (map[i][p1.y] != -1) {
				return false;
			}
		}
		return true;
	}

	private static boolean judgeNone(Point p1, Point p2, int[][] map) {
		return judgeVertical(p1, p2, map) || judgeHorizontal(p1, p2, map);
	}

	private static boolean judgeOne(Point p1, Point p2, int[][] map) {
		Point tempPoint1 = new Point(p1.x, p2.y);
		Point tempPoint2 = new Point(p2.x, p1.y);
		if ((map[tempPoint1.x][tempPoint1.y] == -1)
				&& judgeNone(p1, tempPoint1, map)
				&& judgeNone(p2, tempPoint1, map)) {
			temp_point.add(tempPoint1);
			return true;
		} else if ((map[tempPoint2.x][tempPoint2.y] == -1)
				&& judgeNone(p1, tempPoint2, map)
				&& judgeNone(p2, tempPoint2, map)) {
			temp_point.add(tempPoint2);
			return true;
		}
		return false;
	}

	private static boolean judgeTwo(Point p1, Point p2, int[][] map) {
		Point tempPoint;
		for (int i = 0; i < 10; ++i) {
			if (i == p1.x) {
				i++;
				if (i >= 10) {
					break;
				}
			}
			tempPoint = new Point(i, p1.y);
			if (map[tempPoint.x][tempPoint.y] != -1)
				continue;
			if ((judgeHorizontal(p1, tempPoint, map) && judgeOne(p2, tempPoint,
					map))) {
				temp_point.add(tempPoint);
				return true;
			}
		}
		for (int i = 0; i < 9; ++i) {
			if (i == p1.y) {
				i++;
				if (i >= 9) {
					break;
				}
			}
			tempPoint = new Point(p1.x, i);
			if (map[tempPoint.x][tempPoint.y] != -1)
				continue;
			if ((judgeVertical(p1, tempPoint, map) && judgeOne(p2, tempPoint,
					map))) {
				temp_point.add(tempPoint);
				return true;
			}
		}
		return false;
	}

}
