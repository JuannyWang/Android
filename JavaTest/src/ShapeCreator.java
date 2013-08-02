
public class ShapeCreator {

	// Part 2 will require you to remove this list of shapes and change to use a
	// composite shape to contain other shapes.
	private CompositeShape childShapes;
	private DrawGUI frame;

	/*
	 * ShapeCreator constructor intialise childShapes list using an empty
	 * ArrayList intialise the GUI frame used to draw shapes
	 */
	public ShapeCreator() {
		super();
		frame = new DrawGUI();
		// For part 2 you should change this to a composite shape.
		childShapes = new CompositeShape();
	}

	public void draw() {
		MyPanel panel = new MyPanel(childShapes);
		frame.add(panel);
		frame.setVisible(true);

	}// end draw

	public static void main(String[] args) {
		ShapeCreator aShapeCreator = new ShapeCreator();
		aShapeCreator.processMainMenu();
	}// main end

	private void printMainMenu() {
		G51OOPInput
				.prompt("Shape Creator\n-------------\n1) Add a Shape\n2) Print Shapes Details\n3) Draw Shapes\n4) Quit\n\nEnter Choice:");
	}

	/**
	 * 打印主菜单
	 */
	private void processMainMenu() {
		printMainMenu();
		int choice = G51OOPInput.readInt();
		/**
		 * 菜单选项分级操作
		 */
		if (choice == 1) {
			processOptionsMenu();
		} else if (choice == 2) {
			printDetails();
			processMainMenu();
		} else if (choice == 3) {
			draw();
			processMainMenu();
		} else if (choice == 4) {
			exit();
		} else {
			G51OOPInput.prompt("Invalid Menu Option\n");
			processMainMenu();
		}
	}

	private void printOptionsMenu() {
		G51OOPInput
				.prompt("Add a Shape \n  ------------\n A) Line\n B) Rectangle\n C) Square\n D) Ellipse\n E) Circle\n F) Composite Shape\n M) Main Menu\n Q) Quit\n\n Enter Choice:");
	}

	/**
	 * 打印分级菜单
	 */
	private void processOptionsMenu() {
		printOptionsMenu();
		char choice = G51OOPInput.getChar();
		/**
		 * 菜单选项分级操作
		 */
		if (choice == 'A' || choice == 'a') {
			G51OOPInput.prompt("please input some information\n");
			G51OOPInput.prompt("please input the start point's X position:");
			int x1 = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the start point's Y position:");
			int y1 = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the end point's X position:");
			int x2 = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the end point's Y position:");
			int y2 = G51OOPInput.readInt();
			MyLine line = new MyLine(x1, y1, x2, y2);
			childShapes.add(line);
			G51OOPInput.prompt("add succeed!\n");
			processOptionsMenu();
		} if (choice == 'B' || choice == 'b') {
			G51OOPInput.prompt("please input some information\n");
			G51OOPInput.prompt("please input the start point's X position:");
			int x = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the start point's Y position:");
			int y = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the width:");
			double width = G51OOPInput.readDouble();
			G51OOPInput.prompt("please input the height:");
			double height = G51OOPInput.readDouble();
			MyRectangle rectangle = new MyRectangle(x, y, width, height);
			childShapes.add(rectangle);
			G51OOPInput.prompt("add succeed!\n");
			processOptionsMenu();
		} if (choice == 'C' || choice == 'c') {
			G51OOPInput.prompt("please input some information\n");
			G51OOPInput.prompt("please input the start point's X position:");
			int x = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the start point's Y position:");
			int y = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the height:");
			double height = G51OOPInput.readDouble();
			MySquare square = new MySquare(x, y, height);
			childShapes.add(square);
			G51OOPInput.prompt("add succeed!\n");
			processOptionsMenu();
		} else if (choice == 'D' || choice == 'd') {
			G51OOPInput.prompt("please input some information\n");
			G51OOPInput.prompt("please input the start point's X position:");
			int x = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the start point's Y position:");
			int y = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the width:");
			double width = G51OOPInput.readDouble();
			G51OOPInput.prompt("please input the height:");
			double height = G51OOPInput.readDouble();
			MyEllipse ellipse = new MyEllipse(x, y, width, height);
			childShapes.add(ellipse);
			G51OOPInput.prompt("add succeed!\n");
			processOptionsMenu();
		} else if (choice == 'E' || choice == 'e') {
			G51OOPInput.prompt("please input some information\n");
			G51OOPInput.prompt("please input the start point's X position:");
			int x = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the start point's Y position:");
			int y = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the radius:");
			double radius = G51OOPInput.readDouble();
			MyCircle ellipse = new MyCircle(x, y, radius);
			childShapes.add(ellipse);
			G51OOPInput.prompt("add succeed!\n");
			processOptionsMenu();
		} else if (choice == 'F' || choice == 'f') {
			CompositeShape compositeShape = new CompositeShape();
			childShapes.add(compositeShape);
			processAddMenu(0, compositeShape);
		} else if (choice == 'M' || choice == 'm') {
			processMainMenu();
		} else if (choice == 'Q' || choice == 'q') {
			exit();
		} else {
			G51OOPInput.prompt("Invalid Menu Option\n");
			processOptionsMenu();
		}
	}

	/**
	 * 打印所有消息
	 */
	private void printDetails() {
		childShapes.printDetails(0);
	}

	private void printAddMenu(int level, CompositeShape tempShape) {
		G51OOPInput
				.prompt("Add a Shape to level "
						+ level
						+ " composite "
						+ tempShape.getId()
						+ " \n  ------------\n A) Line\n B) Rectangle\n C) Square\n D) Ellipse\n E) Circle\n F) Composite Shape\n G) Back to Level "
						+ (level - 1)
						+ " Composite Menu\n M) Main Menu\n Q) Quit\n\n Enter Choice:");
	}

	/**
	 * 打印添加自图形菜单
	 * @param level 级别
	 * @param tempShape 父对象
	 */
	private void processAddMenu(int level, CompositeShape tempShape) {
		printAddMenu(level, tempShape);
		char choice = G51OOPInput.getChar();
		/**
		 * 菜单选项分级操作
		 */
		if (choice == 'A' || choice == 'a') {
			G51OOPInput.prompt("please input some information\n");
			G51OOPInput.prompt("please input the start point's X position:");
			int x1 = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the start point's Y position:");
			int y1 = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the end point's X position:");
			int x2 = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the end point's Y position:");
			int y2 = G51OOPInput.readInt();
			MyLine line = new MyLine(x1, y1, x2, y2);
			tempShape.add(line);
			G51OOPInput.prompt("add succeed!\n");
			processAddMenu(level,tempShape);
		} if (choice == 'B' || choice == 'b') {
			G51OOPInput.prompt("please input some information\n");
			G51OOPInput.prompt("please input the start point's X position:");
			int x = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the start point's Y position:");
			int y = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the width:");
			double width = G51OOPInput.readDouble();
			G51OOPInput.prompt("please input the height:");
			double height = G51OOPInput.readDouble();
			MyRectangle rectangle = new MyRectangle(x, y, width, height);
			tempShape.add(rectangle);
			G51OOPInput.prompt("add succeed!\n");
			processAddMenu(level,tempShape);
		} if (choice == 'C' || choice == 'c') {
			G51OOPInput.prompt("please input some information\n");
			G51OOPInput.prompt("please input the start point's X position:");
			int x = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the start point's Y position:");
			int y = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the height:");
			double height = G51OOPInput.readDouble();
			MySquare square = new MySquare(x, y, height);
			tempShape.add(square);
			G51OOPInput.prompt("add succeed!\n");
			processAddMenu(level,tempShape);
		} else if (choice == 'D' || choice == 'd') {
			G51OOPInput.prompt("please input some information\n");
			G51OOPInput.prompt("please input the start point's X position:");
			int x = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the start point's Y position:");
			int y = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the width:");
			double width = G51OOPInput.readDouble();
			G51OOPInput.prompt("please input the height:");
			double height = G51OOPInput.readDouble();
			MyEllipse ellipse = new MyEllipse(x, y, width, height);
			tempShape.add(ellipse);
			G51OOPInput.prompt("add succeed!\n");
			processAddMenu(level,tempShape);
		} else if (choice == 'E' || choice == 'e') {
			G51OOPInput.prompt("please input some information\n");
			G51OOPInput.prompt("please input the start point's X position:");
			int x = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the start point's Y position:");
			int y = G51OOPInput.readInt();
			G51OOPInput.prompt("please input the radius:");
			double radius = G51OOPInput.readDouble();
			MyCircle ellipse = new MyCircle(x, y, radius);
			tempShape.add(ellipse);
			G51OOPInput.prompt("add succeed!\n");
			processAddMenu(level,tempShape);
		} else if (choice == 'F' || choice == 'f') {
			CompositeShape compositeShape = new CompositeShape();
			tempShape.add(compositeShape);
			processAddMenu(level + 1, compositeShape);
		} else if (choice == 'G' || choice == 'g') {
			if (level == 0) {
				processOptionsMenu();
			} else {
				processAddMenu(level - 1, tempShape);
			}
		} else if (choice == 'M' || choice == 'm') {
			processMainMenu();
		} else if (choice == 'Q' || choice == 'q') {
			exit();
		} else {
			G51OOPInput.prompt("Invalid Menu Option\n");
			processAddMenu(level , tempShape);
		}
	}

	/**
	 * 退出并打印相关信息
	 */
	private void exit() {
		G51OOPInput
				.prompt("====================================================\nYou created a total of "
						+ childShapes.getChildShapes().size()
						+ " shapes\nQuitting, Thank you for using Shapes Creator Program\n====================================================");
		System.exit(0);
	}

}// class end
