package grids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import cells.Cell;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class SquareGrid extends AbstractGrid{
	private Cell[][] cells;
	
	public SquareGrid(int rows, int cols) {
		cells = new Cell[rows][cols];
	}
	
	@Override
	public Cell get(int index) {
		return get(index/cells[0].length, index%cells[0].length);
	}
	
	@Override
	public void set(Cell input, int index) {
		set(input, index/cells[0].length, index%cells[0].length);
	}
	
	@Override
	public List<Cell> getNeighbors(int index){
		return getNeighbors(index/cells[0].length, index%cells[0].length);
	}

	@Override
	public int getSize(){
		return cells.length*cells[0].length;
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
		}catch(ArrayIndexOutOfBoundsException e) {
			return null; //Intentionally treats out of bounds as null cells
		} catch(NullPointerException e) {
			throw new NullPointerException("Cells not properly initialized.");
		}
	}
	
	public void set(Cell input, int row, int col) {
		try {
			cells[row][col] = input;
		}catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			throw new IndexOutOfBoundsException("Grid cannot set row " + row + " col " + col);
		}
	}
	
	public List<Cell> getNeighbors(int row, int col){
		List<Cell> neighbors = new ArrayList<Cell>();
		for(int r = 0; r < 3; r++) {
			for(int c = 0; c < 3; c++) {
				if(r != 1 || c != 1)
					neighbors.add(get(row-1+r, col-1+c));
			}
		}
		return neighbors;
	}
	
	@Override
	public Iterator<Cell> iterator() {
		return new Iterator<Cell>() {
			private int row = 0;
			private int col = 0;
			
			@Override
			public boolean hasNext() {
				return row < cells.length;
			}

			@Override
			public Cell next() {
				Cell next = cells[row][col];
				col++;
				if(col == cells[0].length) {
					col = 0;
					row++;
				}
				return next;
			}
			
		};
	}
	
	public String toString() {
		return Arrays.deepToString(cells);
	}
}
