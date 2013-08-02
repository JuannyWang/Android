package com.autograph.data;

/**
 * 用于储存文字信息
 * 
 * @author MyGhost
 * 
 */
public class WordMap {

	/**
	 * 文字地图信息
	 */
	private char data[][];

	/**
	 * 裁剪后的文字地图信息
	 */
	private char scan_data[][];

	/**
	 * 格式化之后的文字地图信息
	 */
	private char format_data[][];

	/**
	 * 文字位置信息
	 */
	private int start_w, end_w, start_h, end_h, word_w, word_h;

	/**
	 * 标准地图块的大小
	 */
	public static final int unit_width = 50, unit_height = 50;

	/**
	 * 地图大小
	 */
	private final int width, height;

	public WordMap(int width, int height) {
		data = new char[width][height];
		format_data = new char[unit_width][unit_height];
		this.width = width;
		this.height = height;
	}

	/**
	 * 用于获取文字地图信息
	 * 
	 * @return 文字地图信息
	 */
	public char[][] getMap() {
		return data;
	}

	/**
	 * 内部辅助函数，用于辅助检测当前区域内是否存在点痕迹
	 * 
	 * @param start_x
	 * @param start_y
	 * @param end_x
	 * @param end_y
	 * @return
	 */
	private boolean checkContain(int start_x, int start_y, int end_x, int end_y) {
		for (int i = start_x; i < end_x; ++i) {
			for (int j = start_y; j < end_y; ++j) {
				if (scan_data[i][j] == 1)
					return true;
			}
		}
		return false;
	}

	/**
	 * 将格式化后的地图数据复制到展示面板
	 */
	public char[][] copyMap() {
		data = new char[width][height];
		int temp_x, temp_y;
		temp_x = (width - unit_width) / 2;
		temp_y = (height - unit_height) / 2;
		for (int i = 0; i < unit_width; ++i) {
			for (int j = 0; j < unit_height; ++j) {
				data[temp_x + i][temp_y + j] = format_data[i][j];
			}
		}
		return format_data;
	}

	/**
	 * 清空重置当前地图块
	 */
	public void resetMap() {
		data = new char[width][height];
		scan_data=null;
		format_data=new char[unit_width][unit_height];
	}

	/**
	 * 设置地图上的点位
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean setPoint(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return false;
		if (data[x][y] != 1) {
			data[x][y] = 1;
			return true;
		}
		return false;
	}

	/**
	 * 必须滞后于分析函数后调用
	 * 
	 * @return 检测出来的字的框架
	 */
	public char[][] getWordMap() {
		scan_data = new char[word_w][word_h];
		for (int i = 0; i < word_w; ++i) {
			for (int j = 0; j < word_h; ++j) {
				scan_data[i][j] = data[start_w + i][start_h + j];
			}
		}
		return scan_data;
	}

	/**
	 * 通过分析裁减过得文字地图进行格式化并返回格式化之后的结果
	 * 
	 * @return 格式化之后的地图信息
	 */
	public char[][] getFormateMap() {
		float scale_w, scale_h;
		scale_w = (float) word_w / (float) unit_width;
		scale_h = (float) word_h / (float) unit_height;
		for (int i = 0; i < unit_width; ++i) {
			for (int j = 0; j < unit_height; ++j) {
				if (checkContain((int) (i * scale_w), (int) (j * scale_h),
						(int) ((i + 1) * scale_w), (int) ((j + 1) * scale_h))) {
					format_data[j][i] = 1;
				}
			}
		}
		return format_data;
	}

	/**
	 * 通过逐个像素分析获得当前文字的宽度
	 * 
	 * @return 当前文字的宽度
	 */
	public int getWordWidth() {
		start_w = 0;
		for (int i = 0; i < width; ++i) {
			boolean continue_key = false;
			for (int j = 0; j < height; ++j) {
				if (data[i][j] == 1) {
					continue_key = true;
					break;
				}
			}
			if (continue_key) {
				start_w = i;
				break;
			}
		}
		end_w = 0;
		for (int i = width - 1; i >= 0; --i) {
			boolean continue_key = false;
			for (int j = 0; j < height; ++j) {
				if (data[i][j] == 1) {
					continue_key = true;
					break;
				}
			}
			if (continue_key) {
				end_w = i;
				break;
			}
		}
		word_w = end_w - start_w;
		return word_w;
	}

	/**
	 * 通过逐个像素分析获得当前文字的高度
	 * 
	 * @return 当前文字的高度
	 */
	public int getWordHeight() {
		start_h = 0;
		for (int i = 0; i < height; ++i) {
			boolean continue_key = false;
			for (int j = 0; j < width; ++j) {
				if (data[j][i] == 1) {
					continue_key = true;
					break;
				}
			}
			if (continue_key) {
				start_h = i;
				break;
			}
		}
		end_h = 0;
		for (int i = height - 1; i >= 0; --i) {
			boolean continue_key = false;
			for (int j = 0; j < width; ++j) {
				if (data[j][i] == 1) {
					continue_key = true;
					break;
				}
			}
			if (continue_key) {
				end_h = i;
				break;
			}
		}
		word_h = end_h - start_h;
		return word_h;
	}

}
