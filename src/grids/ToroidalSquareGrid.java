package grids;

import cells.Cell;

/**
 * FourNeighborSquareGrid, except the get(row,col) methods wraps around for inputs outside the grid
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class ToroidalSquareGrid extends FourNeighborSquareGrid {

	public ToroidalSquareGrid(int rows, int cols) {
		super(rows, cols);
	}

	@Override
	public Cell get(int row, int col) {
		return super.get(mod(row, getHeight()), mod(col, getWidth()));
	}
	
	private int mod(int a, int b) {
		return ((a%b)+b)%b;
	}
}
