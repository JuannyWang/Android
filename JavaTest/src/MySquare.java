public class MySquare extends MyRectangle {
	
	private double height;
	
	public MySquare(MyPoint position,double height) {
		super(position,height,height);
		this.height=height;
		TYPE = "Square";
	}
	
	public MySquare(int x,int y,double height) {
		this(new MyPoint(x,y),height);
	}
	
	public MySquare() {
		this(0,0,1);
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
		result += "It has the area of : " + calcArea() + "\n";
		G51OOPInput.prompt(result);
	}
	
	
}
