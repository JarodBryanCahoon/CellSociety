package cells;

import java.util.List;

import javafx.scene.paint.Color;

/**
 * Represents a cell following the rules of a segregation simulation
 * 
 * Depends on List, Color, Cell, ParameterBundle
 * 
 * Assumes pars contains a double representing the threshold for satisfaction
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class SegCell extends Cell {
	public static final int EMPTY = 0;
	private static final int NUM_STATES = 3;
	private static final Color[] COLORS = new Color[] { Color.BLACK, Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE };
	private boolean satisfied = true;

	/**
	 * @param initialState
	 * @param bundle
	 *            contains The minimum fraction of non-empty neighbors that are in
	 *            the same state as this one for it to be satisfied
	 */
	public SegCell(int initialState, ParameterBundle bundle) {
		super(initialState, bundle, COLORS);
	}

	/**
	 * Checks if the cell is satisfied and stores that information
	 * 
	 * @see cells.Cell#step(java.util.List)
	 */
	@Override
	public void step(List<Cell> neighborhood) {
		if (getState() == EMPTY)
			return;
		int numFriends = 0;
		int numTotal = 0;
		for (Cell c : neighborhood) {
			if (c == null)
				continue;
			if (c.getState() == getState())
				numFriends++;
			if (c.getState() != EMPTY)
				numTotal++;
		}
		satisfied = numFriends >= numTotal * (double) parameters.getParameter(0);
	}

	/**
	 * Moves the state of this cell into destination
	 * 
	 * @param destination
	 */
	public void moveTo(SegCell destination) {
		destination.nextState = getState();
		destination.update();
		nextState = EMPTY;
		satisfied = true;
		update();
	}

	/**
	 * @return is dissatisfied?
	 */
	public boolean shouldMove() {
		return !satisfied;
	}

	public boolean isEmpty() {
		return getState() == EMPTY;
	}

	/**
	 * @see cells.Cell#cycle()
	 */
	@Override
	protected void cycle() {
		if (getState() >= NUM_STATES - 1)
			nextState = EMPTY;
		else
			nextState = getState() + 1;
		satisfied = true;
		update();
	}

	/**
	 * @see cells.Cell#getSimType()
	 */
	@Override
	public String getSimType() {
		return "Seg";
	}
}
