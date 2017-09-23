package cellsociety_team01;

import java.util.List;

public abstract class Cell<E>{
	private E state;
	protected E nextState;
	
	public Cell(E initialState) {
		state = nextState = initialState;
	}
	
	public abstract void step(List<Cell<E>> neighborhood);
	
	public void update() {
		state = nextState;
	}
	
	protected E getState() {
		return state;
	}
	
	@Override
	public String toString() {
		return getState().toString();
	}
}