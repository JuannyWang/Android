/**
 * 
 */
package ghost.fivechess.gui.panel;

import ghost.fivechess.bean.GameData;
import ghost.fivechess.util.Tool;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-18
 */
public class MenuPanel extends PanelObject {
	
	public MenuPanel() {
		super();
		position=new int[4];
		tempPosition=new int[4];
		position[0]=179;
		position[1]=150;
		position[2]=295;
		position[3]=440;
		for(int i=0;i<4;++i) {
			tempPosition[i]=GameData.width;
		}
	}

	/* (non-Javadoc)
	 * @see ghost.fivechess.gui.panel.PanelObject#drawFunction()
	 */
	@Override
	protected void drawFunction() {
		Tool.drawImage("logo", tempPosition[0], 18, 442, 130);
		Tool.drawImage("start", tempPosition[1], 200, 210, 50);
		Tool.drawImage("help", tempPosition[2], 290, 210, 50);
		Tool.drawImage("exit", tempPosition[3], 380, 210, 50);
		boolean tempKey=true;
		switch(this.status) {
		case PANEL_START:
			tempKey=true;
			for(int i=0;i<4;++i) {
				if(tempPosition[i]!=position[i]) {
					tempKey=false;
					break;
				}
			}
			if(tempKey) {
				this.status=PANEL_READY;
			} else {
				for(int i=0;i<4;++i) {
					if(tempPosition[i]>position[i]) {
						tempPosition[i]-=GameData.moveSpeedIn;
						if(tempPosition[i]<position[i]) {
							tempPosition[i]=position[i];
						}
					}
				}
			}
			break;
		case PANEL_READY:
			break;
		case PANEL_CLOSE:
			tempKey=true;
			for(int i=0;i<4;++i) {
				if(tempPosition[i]<GameData.width) {
					tempKey=false;
					break;
				}
			}
			if(tempKey) {
				GameData.status=nextStatus;
				if(GameData.status==EXIT)
					System.exit(0);
				this.status=PANEL_START;
			} else {
				for(int i=0;i<4;++i) {
					if(tempPosition[i]<GameData.width) {
						tempPosition[i]+=GameData.moveSpeedOut;
						if(tempPosition[i]>GameData.width) {
							tempPosition[i]=GameData.width;
						}
					}
				}
			}
			break;
		}
	}
	
	/* (non-Javadoc)
	 * @see ghost.fivechess.gui.panel.PanelObject#clickAction(int, int)
	 */
	@Override
	public void clickAction(int x, int y) {
		if(status==PANEL_READY) {
			if(y>=200&&y<=250&&x>=position[1]&&x<=(position[1]+200)) {
				nextStatus=RUN;
				this.status=PANEL_CLOSE;
			} else if(y>=290&&y<=340&&x>=position[2]&&x<=(position[2]+200)) {
				nextStatus=HELP;
				this.status=PANEL_CLOSE;
			} else if(y>=380&&y<=430&&x>=position[3]&&x<=(position[3]+200)) {
				nextStatus=EXIT;
				this.status=PANEL_CLOSE;
			}
		}
	}

}
