import java.awt.Graphics2D;


public class MyLine extends MyShape{
	
	private MyPoint p1,p2;
	
	public MyLine() {
		this(0,0,1,1);
	}
	
	public MyLine(MyPoint p1,MyPoint p2) {
		TYPE = "Line";
		this.p1=p1;
		this.p2=p2;
	}
	
	public MyLine(int x1,int y1,int x2,int y2) {
		this(new MyPoint(x1,y1),new MyPoint(x2,y2));
	}

	public MyPoint getP1() {
		return p1;
	}
	
	public MyPoint getP2() {
		return p2;
	}

	public void renderShape(Graphics2D g2d) {
		g2d.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	@Override
	public double calcLength() {
		return Math.sqrt((p1.getX()-p2.getX())*(p1.getX()-p2.getX())+(p1.getY()-p2.getY())*(p1.getY()-p2.getY()));
	}

	@Override
	public double calcArea() {
		return 0;
	}

	@Override
	public void printDetails(int indent) {
		String result = "";
		for (int i = 0; i < indent; ++i) {
			result += "-->";
		}
		result += "The shape is an " + TYPE + " " + getId() + "\n";
		for (int i = 0; i < indent; ++i) {
			result += "-->";
		}
		result += "It has the length of : " + calcLength() + "\n";
		G51OOPInput.prompt(result);
	}
}
