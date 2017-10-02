package cells;

import java.util.List;

import javafx.scene.paint.Color;

/**
 * A cell in the Fire simulation.
 * 
 * Dependent on List, Color, Cell, ParameterBundle
 * Assumes ParameterBundle holds a double representing the probability of a fire spreading
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class FireCell extends Cell {
	public static final int EMPTY = 0;
	public static final int TREE = 1;
	public static final int BURNING = 2;
	private static final Color[] COLORS = new Color[] { Color.DARKGRAY, Color.GREEN, Color.RED };

	/**
	 * @param initialState
	 * @param pars
	 *            Contains the probability that a tree neighboring fire will catch fire
	 */
	public FireCell(int initialState, ParameterBundle pars) {
		super(initialState, pars, COLORS);
	}

	/**
	 * @see cells.Cell#step(java.util.List)
	 */
	@Override
	public void step(List<Cell> neighborhood) {
		if (getState() == BURNING)
			nextState = EMPTY;
		else if (getState() == TREE) {
			for (Cell n : neighborhood) {
				if (n != null && n.getState() == BURNING) {
					if ((double) parameters.getParameter(0) > Math.random())
						nextState = BURNING;
					return;
				}
			}
		}
	}

	/**
	 * @see cells.Cell#cycle()
	 */
	@Override
	protected void cycle() {
		if (getState() == BURNING)
			nextState = EMPTY;
		else
			nextState = getState() + 1;
		update();
	}
	
	/**
	 * @see cells.Cell#getSimType()
	 */
	@Override
	public String getSimType() {
		return "Fire";
	}
}
