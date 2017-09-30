package grids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import cells.Cell;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public abstract class Grid2D extends AbstractGrid {
	private Cell[][] cells;
	private ResourceBundle errors = ResourceBundle.getBundle("resources/ErrorBundle");

	protected List<Integer> neighborIDs;
	
	/**
	 * @param rows
	 *            height
	 * @param cols
	 *            width
	 */
	public Grid2D(int rows, int cols, List<Integer> neighbors) {
		cells = new Cell[rows][cols];
		neighborIDs = neighbors;
	}

	@Override
	public Cell get(int index) {
		return get(index / cells[0].length, index % cells[0].length);
	}

	@Override
	public void set(Cell input, int index) {
		set(input, index / cells[0].length, index % cells[0].length);
	}

	@Override
	public List<Cell> getNeighbors(int index) {
		return getNeighbors(index / cells[0].length, index % cells[0].length);
	}

	@Override
	public int getSize() {
		return cells.length * cells[0].length;
	}

	public int getWidth() {
		return cells[0].length;
	}

	public int getHeight() {
		return cells.length;
	}

	public Cell get(int row, int col) {
		try {
			return cells[row][col];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null; // Intentionally treats out of bounds as null cells
		} catch (NullPointerException e) {
			throw new NullPointerException(errors.getString("SGGetError"));
		}
	}

	public void set(Cell input, int row, int col) {
		try {
			cells[row][col] = input;
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			throw new IndexOutOfBoundsException(String.format(errors.getString("SGSetError"), row, col));
		}
	}

	public abstract List<Cell> getNeighbors(int row, int col);

	protected List<Cell> extractNeighbors(List<Cell> neighborhood) {
		List<Cell> newNeighbors = new ArrayList<Cell>();
		for(int i : neighborIDs)
			newNeighbors.add(neighborhood.get(i));
		return newNeighbors;
	}
	
	public String toString() {
		return Arrays.deepToString(cells);
	}

	public abstract Pane getView(double width, double height);
}
