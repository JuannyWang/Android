public class MyCircle extends MyEllipse {

	private double radius;
	
	public MyCircle() {
		this(0,0,1);
	}

	public MyCircle(MyPoint position, double radius) {
		super(position, radius * 2, radius * 2);
		TYPE = "Circle";
		this.radius = radius;
	}

	public MyCircle(int x, int y, double radius) {
		super(new MyPoint(x, y), radius * 2, radius * 2);
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
		result += "It has the radius of : " + radius + "\n";
		for (int i = 0; i < indent; ++i) {
			result += "-->";
		}
		result += "It has the area of : " + calcArea() + "\n";
		G51OOPInput.prompt(result);
	}

}
