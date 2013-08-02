package ghost.picmatch.logic;

import ghost.picmatch.util.Common;

import java.awt.Graphics2D;

public abstract class GameObject implements Common {
	abstract public void drawBackground(Graphics2D g2d);
	abstract public void drawFunc(Graphics2D g2d);
	abstract public void drawLogo(Graphics2D g2d);
	abstract public void drawButton(Graphics2D g2d);
	abstract public void doLogic();
	abstract public void clickAction(int x,int y);
}
