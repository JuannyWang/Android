import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class CompositeShape extends MyShape {

	private List<MyShape> childShapes;
	private Color color;
	
	private final String TYPE="Composite";

	public CompositeShape() {
		super();
		this.childShapes = new ArrayList<MyShape>();
		int R = (int) (Math.random( )*256);
		int G = (int)(Math.random( )*256);
		int B= (int)(Math.random( )*256);
		this.color = new Color(R, G, B);		
	}
	
	@Override
	public void add(MyShape s) {
		childShapes.add(s);
	}

	public List<MyShape> getChildShapes(){
		return childShapes;
	}

	public void renderShape(Graphics2D g2d) {
		for (MyShape shape : childShapes) {
			g2d.setColor(this.color);
			shape.renderShape(g2d);
		}
	}

	@Override
	public double calcLength() {
		double result=0;
		for(MyShape shape:childShapes) {
			result+=shape.calcLength();
		}
		return result;
	}

	@Override
	public double calcArea() {
		double result=0;
		for(MyShape shape:childShapes) {
			result+=shape.calcArea();
		}
		return result;
	}

	public void printDetails(int indent) {
		String result = "";
		for (int i = 0; i < indent; ++i) {
			result += "-->";
		}
		result += "A "+TYPE+" "+getId()+":\n";
		G51OOPInput.prompt(result);
		for(int i=0;i<childShapes.size();++i) {
			result = "";
			for (int j = 0; j < indent; ++j) {
				result += "-->";
			}
			result+=(i+1)+".\n";
			G51OOPInput.prompt(result);
			childShapes.get(i).printDetails(indent+1);
		}
	}
}
