import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class MyRectangle extends MyShape {

	protected MyPoint position;
	private double height, width;

	public MyRectangle(MyPoint position, double width, double height) {
		super();
		TYPE = "Rectangle";
		this.position = position;
		this.width = width;
		this.height = height;
	}

	public MyRectangle(int x, int y, double width, double height) {
		this(new MyPoint(x, y), width, height);
	}

	public MyRectangle() {
		this(0, 0, 1, 1);
	}

	public void renderShape(Graphics2D g2d) {
		Rectangle2D rect = new Rectangle2D.Double(position.getX(),
				position.getY(), width, height);
		g2d.draw(rect);
	}

	@Override
	public double calcLength() {
		return (height + width) * 2;
	}

	@Override
	public double calcArea() {
		return height * width;
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
		result += "It has the height of : " + height + "\n";
		for (int i = 0; i < indent; ++i) {
			result += "-->";
		}
		result += "It has the width of : " + width + "\n";
		for (int i = 0; i < indent; ++i) {
			result += "-->";
		}
		result += "It has the area of : " + calcArea() + "\n";
		G51OOPInput.prompt(result);
	}
}
