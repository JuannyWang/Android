import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class MyEllipse extends MyShape {

	protected MyPoint position;
	private double height, width;

	public MyEllipse() {
		this(0,0,1,1);
	}

	public MyEllipse(MyPoint position, double height, double width) {
		TYPE = "Ellipse";
		this.position = position;
		this.height = height;
		this.width = width;
	}

	public MyEllipse(int x, int y, double height, double width) {
		this(new MyPoint(x, y), height, width);
	}

	public void renderShape(Graphics2D g2d) {

		Ellipse2D myEllipse = new Ellipse2D.Double(position.getX(),
				position.getY(), width, height);
		g2d.draw(myEllipse);

	}

	@Override
	public double calcLength() {
		/*
		 * pi(1.5(a+b)-sqrt(ab))
		 */
		return Math.PI * (1.5 * (height + width) - Math.sqrt(height * width));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see MyShape#calcArea()
	 */
	@Override
	public double calcArea() {
		/**
		 * S=π×A×B/4
		 */
		return Math.PI * height + width / 4;
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
