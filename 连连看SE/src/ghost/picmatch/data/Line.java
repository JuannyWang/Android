package ghost.picmatch.data;

import ghost.picmatch.util.Tool;

import java.awt.Graphics2D;

/**
 * 图块连线数据结构
 * 
 * @author ghost
 * 
 */
public class Line {
	public int x, y, w, h;
	public int life = 7;

	public Line(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void drawSelf(Graphics2D g2d) {
		Tool.drawImage(ImageData.line, x, y, w, h, g2d);
		life--;
	}
}
