public class MyPoint   {
	
	private int x,y;
	
	public MyPoint() {
		this(0,0);
	}
	
	public MyPoint(int x,int y) {
		this.x=x;
		this.y=y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String toString() {
		return "(X:"+x+"|Y:"+y+")";
	}

}
