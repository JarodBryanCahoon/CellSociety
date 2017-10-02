package cells;

import java.util.List;

import javafx.scene.paint.Color;

/**
 * A cell in the well-known Game of Life
 * 
 * Dependent On List, Color, Cell, ParameterBundle
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class LifeCell extends Cell {

	public static final int DEAD = 0;
	public static final int LIVING = 1;
	private static final Color[] COLORS = new Color[] {Color.BLACK, Color.WHITE};
	
	/**
	 * @param initialState
	 * @param pars Empty
	 */
	public LifeCell(int initialState, ParameterBundle pars) {
		super(initialState, pars, COLORS);
	}

	/**
	 * @see cells.Cell#step(java.util.List)
	 */
	@Override
	public void step(List<Cell> neighborhood) {
		int livingNeighbors = numLiving(neighborhood);
		if(livingNeighbors < 2 || livingNeighbors > 3)
			nextState = DEAD;
		else if(livingNeighbors == 3)
			nextState = LIVING;
		// when 2, do nothing
	}
	
	private int numLiving(List<Cell> neighborhood) {
		int living = 0;
		for(Cell cell : neighborhood) {
			if(cell != null && cell.getState() == LIVING)
				living++;
		}
		return living;
	}

	/**
	 * @see cells.Cell#cycle()
	 */
	@Override
	protected void cycle() {
		nextState = getState() == DEAD? LIVING:DEAD;
		update();
	}
	

	/**
	 * @see cells.Cell#getSimType()
	 */
	@Override
	public String getSimType() {
		return "Life";
	}
}
