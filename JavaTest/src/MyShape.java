import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * To be implemented by all shapes. 
 * It should contain the signature of all the methods that a shape must have. 
 */
public abstract class MyShape {
	
	protected static int ID=1;
	protected final int id;
	
	protected ArrayList<MyShape> childShapes;
	
	protected String TYPE;
	
	public MyShape() {
		id=ID;
		ID++;
		childShapes=new ArrayList<MyShape>();
	}
	
	/**
	 * Draw shape on screen
	 * 
	 * Method that must be implemented by Basic as well as complex shapes
	 */

	public abstract void renderShape(Graphics2D g2d);
	
	/**
	 * 计算图形的周长
	 * @return 周长
	 */
	public abstract double calcLength();
	
	/**
	 * 计算图形的面积，如果为非闭合型图形则返回0
	 * @return 面积
	 */
	public abstract double calcArea();
	
	/**
	 * 打印图形的详细信息，包括周长，面积等等
	 */
	public abstract void printDetails(int indent);
	
	/**
	 * 获取子对象列表
	 * @return 子对象列表
	 */
	public List<MyShape> getChildShapes() {
		return childShapes;
	}
	
	/**
	 * 向对向列表中添加一个对象
	 * @param s 要添加的对象
	 */
	public void add(MyShape s) {
		childShapes.add(s);
	}
	
	/**
	 * 返回当前对象的ID
	 * @return
	 */
	public int getId() {
		return id;
	}

}
