package grids;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import cells.Cell;
import cellsociety_team01.FileCreator;
import javafx.scene.layout.Pane;

public abstract class Grid2D extends AbstractGrid {
	private List<List<Cell>> cells;
	protected int originRow = 0;
	protected int originCol = 0;
	private ResourceBundle errors = ResourceBundle.getBundle("resources/ErrorBundle");
	protected Pane pane;
	protected double paneWidth;
	protected double paneHeight;

	protected List<Integer> neighborIDs;

	/**
	 * @param rows
	 *            height
	 * @param cols
	 *            width
	 */
	public Grid2D(int rows, int cols, List<Integer> neighbors) {
		constructCells(rows, cols);
		neighborIDs = neighbors;
	}

	private void constructCells(int rows, int cols) {
		List<List<Cell>> grid = new ArrayList<List<Cell>>();
		for (int row = 0; row < rows; row++) {
			List<Cell> rowCells = new ArrayList<Cell>();
			for (int col = 0; col < cols; col++) {
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
		return getWidth() * getHeight();
	}

	public int getTrueSize() {
		return getTrueWidth() * getTrueHeight();
	}

	public int getTrueWidth() {
		return cells.get(0).size();
	}

	public int getWidth() {
		return getTrueWidth() - originCol;
	}

	public int getTrueHeight() {
		return cells.size();
	}

	public int getHeight() {
		return getTrueHeight() - originRow;
	}

	public Cell get(int row, int col) {
		try {
			return cells.get(row + originRow).get(col + originCol);
		} catch (IndexOutOfBoundsException e) {
			return null; // Intentionally treats out of bounds as null
		} catch (NullPointerException e) {
			throw new NullPointerException(errors.getString("SGGetError"));
		}
	}

	// If on the border, extend
	protected void stretchTo(int row, int col) {
		int width = getTrueWidth();
		int height = getTrueHeight();
		if (row >= getTrueHeight() - 1)
			extendRows(true);
		if (row <= 0)
			extendRows(false);
		if (col <= 0)
			extendCols(false);
		if (col >= getTrueWidth() - 1)
			extendCols(true);
		if (pane != null && (getTrueWidth() != width || getTrueHeight() != height)) {
			pane.getChildren().clear();
			pane.getChildren().addAll(getView(paneWidth, paneHeight));
		}
	}

	private void extendCols(boolean right) {
		for (List<Cell> row : cells) {
			if (right)
				row.add(emptyCell());
			else {
				row.add(0, emptyCell());
			}
		}
		if (!right)
			originCol++;
	}

	private void extendRows(boolean down) {
		List<Cell> newRow = new ArrayList<Cell>(getTrueWidth());
		for (int i = 0; i < getTrueWidth(); i++) {
			newRow.add(emptyCell());
		}
		if (down)
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
			cells.get(row + originRow).set(col + originCol, input);
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException(String.format(errors.getString("SGSetError"), row, col));
		}
	}

	public void setOrigin(int row, int col) {
		setOriginNonRelative(row + originRow, col + originCol);
	}

	public void setOriginNonRelative(int row, int col) {
		if (isLegalOrigin(row, col)) {
			originRow = row;
			originCol = col;
		}
	}

	private boolean isLegalOrigin(int row, int col) {
		return row >= 0 && row <= getTrueHeight() && col >= 0 && col <= getTrueWidth();
	}

	public abstract List<Cell> getNeighbors(int row, int col);

	protected List<Cell> extractNeighbors(List<Cell> neighborhood) {
		List<Cell> newNeighbors = new ArrayList<Cell>();
		for (int i : neighborIDs)
			newNeighbors.add(neighborhood.get(i));
		return newNeighbors;
	}

	public abstract Pane getView(double width, double height);

	@Override
	public Iterator<Cell> iterator() {
		setOriginNonRelative(0, 0);
		return new Iterator<Cell>() {
			private int row = 0;
			private int col = 0;

			private int width = getWidth();
			private int height = getHeight();

			@Override
			public boolean hasNext() {
				return row < height;
			}

			@Override
			public Cell next() {
				Cell next = get(row, col);
				col++;
				if (col >= width) {
					col = 0;
					row++;
				}
				return next;
			}

		};
	}

	@Override
	public void save(String file, Object[] parameters) {
		List<Integer> states = new ArrayList<Integer>();
		for (Cell c : this)
			states.add(c.getState());
		FileCreator.xmlCreator(file, neighborIDs, get(0).getSimType(), getType(), getTrueHeight(), getTrueWidth(), parameters, states);
	}
}
