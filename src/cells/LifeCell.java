package cells;

import java.util.List;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class LifeCell extends Cell {

	public static final int DEAD = 0;
	public static final int LIVING = 1;
	
	/**
	 * @param initialState
	 */
	public LifeCell(int initialState) {
		super(initialState);
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
}
