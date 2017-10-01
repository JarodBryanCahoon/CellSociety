package grids;

import java.util.Iterator;
import java.util.List;

import cells.Cell;
import cellsociety_team01.FileCreator;
import javafx.scene.layout.Pane;

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
	
	public abstract void save(String file, double[] parameters);
	
	public abstract String getType();
}
