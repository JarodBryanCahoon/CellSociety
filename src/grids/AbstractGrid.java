package grids;

import java.util.Iterator;
import java.util.List;

import cells.Cell;
import javafx.scene.layout.Pane;

/**
 * Represents an arbitrarily shaped grid of cells, which must be countable (ie
 * they map to an index)
 * 
 * This is how a simulation stores its cells
 * 
 * The methods in this class are similar to those in a List
 * 
 * Depends on List, Cell, Pane
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
	
	
	/**
	 * @param width  The width of the desired Pane
	 * @param height The height of the desired Pane
	 * @return A Pane containing the grid's image
	 */
	public abstract Pane getView(double width, double height);
	
	public Iterator<Cell> iterator() {
		return new Iterator<Cell>() {
			private int index = 0;

			@Override
			public boolean hasNext() {
				return index < getSize();
			}

			@Override
			public Cell next() {
				Cell next = get(index);
				index++;
				return next;
			}

		};
	}
	
	/**
	 * This saves this grid of cells as a loadable XML
	 * 
	 * @param file file name
	 * @param parameters the parameters of the simulation
	 */
	public abstract void save(String file, Object[] parameters);
	
	/**
	 * Used for file saving
	 */
	public abstract String getType();
}
