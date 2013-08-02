import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawGUI extends JFrame{

	private static final long serialVersionUID = 4017826819702718436L;

	public DrawGUI() {
		super("Shapes");

		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(600, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(false);
	}
}
class MyPanel extends JPanel {

	private static final long serialVersionUID = -6258619954114660388L;
	
	CompositeShape aCompositeShape;
	
	public MyPanel(MyShape compositeShape) {
		super();
		aCompositeShape = (CompositeShape)compositeShape;
		System.out.println("Drawing " + compositeShape.getChildShapes().size() + " shapes");
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		Stroke linestyle = new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND);

		g2d.setStroke(linestyle);

		aCompositeShape.renderShape(g2d);
	}


}