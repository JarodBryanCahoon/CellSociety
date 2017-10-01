package grids;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cells.Cell;
import javafx.scene.layout.Pane;

public abstract class Grid2D extends AbstractGrid {
	private List<List<Cell>> cells;
	private int originRow = 0;
	private int originCol = 0;
	private ResourceBundle errors = ResourceBundle.getBundle("resources/ErrorBundle");

	protected List<Integer> neighborIDs;
	private boolean infinite;
	
	/**
	 * @param rows
	 *            height
	 * @param cols
	 *            width
	 */
	public Grid2D(int rows, int cols, List<Integer> neighbors, boolean infinite) {
		constructCells(rows, cols);
		neighborIDs = neighbors;
		this.infinite = infinite;
	}

	private void constructCells(int rows, int cols) {
		List<List<Cell>> grid = new ArrayList<List<Cell>>();
		for(int row = 0; row < rows; row++) {
			List<Cell> rowCells = new ArrayList<Cell>();
			for(int col = 0; col < cols; col++) {
				rowCells.add(col, null);
			}
			grid.add(rowCells);
		}
		cells = grid;
	}

	@Override
	public Cell get(int index) {
		return get(index / getWidth(), index % getWidth());
	}

	@Override
	public void set(Cell input, int index) {
		set(input, index / getWidth(), index % getWidth());
	}

	@Override
	public List<Cell> getNeighbors(int index) {
		return getNeighbors(index / getWidth(), index % getWidth());
	}

	@Override
	public int getSize() {
		return getWidth()*getHeight();
	}

	public int getWidth() {
		return cells.get(0).size();
	}

	public int getHeight() {
		return cells.size();
	}

	public Cell get(int row, int col) {
		try {
			return cells.get(row+originRow).get(col + originCol);
		} catch (IndexOutOfBoundsException e) {
			if(!infinite)
				return null; // Intentionally treats out of bounds as null cells
			else {
				stretchTo(row+originRow, col+originCol);
				return get(row, col);
			}
		} catch (NullPointerException e) {
			throw new NullPointerException(errors.getString("SGGetError"));
		}
	}
	
	private void stretchTo(int row, int col) {
		if(row > getHeight())
			extendRows(true);
		if(col > getWidth())
			extendCols(true);
		if(row < 0)
			extendRows(false);
		if(col < 0)
			extendCols(false);
	}

	private void extendCols(boolean right) {
		for(List<Cell> row : cells) {
			if(right)
				row.add(emptyCell());
			else {
				row.add(0, emptyCell());
				originCol++;
			}
		}
	}

	private void extendRows(boolean down) {
		List<Cell> newRow = new ArrayList<Cell>(getWidth());
		for(int i = 0; i < getWidth(); i++) {
			newRow.add(emptyCell());
		}
		if(down)
			cells.add(newRow);
		else {
			cells.add(0, newRow);
			originRow++;
		}
	}

	private Cell emptyCell() {
		return get(0).getEmptyInstance();
	}

	public void set(Cell input, int row, int col) {
		try {
			cells.get(row + originRow).set(col+originCol, input);
		} catch (IndexOutOfBoundsException e) {
			if(!infinite)
				throw new IndexOutOfBoundsException(String.format(errors.getString("SGSetError"), row, col));
			else {
				stretchTo(row + originRow, col+originCol);
				set(input, row, col);
			}
		}
	}

	public abstract List<Cell> getNeighbors(int row, int col);

	protected List<Cell> extractNeighbors(List<Cell> neighborhood) {
		List<Cell> newNeighbors = new ArrayList<Cell>();
		for(int i : neighborIDs)
			newNeighbors.add(neighborhood.get(i));
		return newNeighbors;
	}

	public abstract Pane getView(double width, double height);
}
