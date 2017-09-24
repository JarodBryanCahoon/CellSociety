package grids;

import java.util.List;

import cells.Cell;

/**
 * Represents an arbitrarily shaped grid of cells, which must be countable (ie
 * they map to an index)
 * 
 * This is how a simulation stores its cells
 * 
 * The methods in this class are similar to those in a List
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public abstract class AbstractGrid implements Iterable<Cell> {
	public abstract Cell get(int index);

	public abstract void set(Cell input, int index);

	/**
	 * @return The neighbors of the cell assigned the given index. This differs based on the type of grid.
	 */
	public abstract List<Cell> getNeighbors(int index);

	/**
	 * @return The number (n) of legal indices, from 0 to n-1
	 */
	public abstract int getSize();
}
