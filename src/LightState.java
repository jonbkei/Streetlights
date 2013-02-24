
public enum LightState {
	
	OFF(123),
	ON(124);
	
	
	
	private int blockID;
	
	LightState(int blockID) {
		this.blockID = blockID;
	}
	
	public int getBlockID() {
		return this.blockID;
	}
}
