package cells;

import java.util.List;

/**
 * Represents individual cells in the simulation's grid. They are responsible
 * for most of the logic of the simulation, but may require assistance when
 * moving
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public abstract class Cell {
	private int state;
	protected int nextState;

	/**
	 * @param initialState
	 */
	public Cell(int initialState) {
		state = nextState = initialState;
	}

	/**
	 * Sets the next state of the cells based on the rules of the simulation
	 * 
	 * @param neighborhood
	 *            The cells surrounding this one -- depends on simulation
	 */
	public abstract void step(List<Cell> neighborhood);

	/**
	 * Updates state to nextState
	 */
	public void update() {
		state = nextState;
	}

	public int getState() {
		return state;
	}

	@Override
	public String toString() {
		return "" + getState();
	}
}