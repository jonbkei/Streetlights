
public class LightPosition {

	public int x;
	public int y;
	public int z;
	
	public LightPosition(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public LightPosition(Block b) {
		this.x = b.getX();
		this.y = b.getY();
		this.z = b.getZ();
	}
	
	public LightPosition(LightPosition lp) {
		this.x = lp.x;
		this.y = lp.y;
		this.z = lp.z;
	}
	
	public boolean equals(Object o) {
		if(!(o instanceof LightPosition)) {
			return false;
		} else {
			LightPosition lp = (LightPosition) o;
			return this.x == lp.x && this.y == lp.y && this.z == lp.z;
		}
	}
	
	public String toString() {
		return "[" + "x = " + x + ", y = " + y + ", z = " + z + "]"; 
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
}
