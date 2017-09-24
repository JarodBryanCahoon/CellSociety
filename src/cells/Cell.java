package cells;

import java.util.List;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public abstract class Cell{
	private int state;
	protected int nextState;
	
	public Cell(int initialState) {
		state = nextState = initialState;
	}
	
	public abstract void step(List<Cell> neighborhood);
	
	public void update() {
		state = nextState;
	}
	
	public int getState() {
		return state;
	}
	
	@Override
	public String toString() {
		return ""+getState();
	}
}