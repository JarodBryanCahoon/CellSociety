package grids;

import cells.Cell;

/**
 * FourNeighborSquareGrid, except the get(row,col) methods wraps around for inputs outside the grid
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class WatorGrid extends FourNeighborSquareGrid {
	int numRows;
	int numCols;

	public WatorGrid(int rows, int cols) {
		super(rows, cols);
		numRows = rows;
		numCols = cols;
	}

	@Override
	public Cell get(int row, int col) {
		return super.get(mod(row, numRows), mod(col, numCols));
	}
	
	private int mod(int a, int b) {
		return ((a%b)+b)%b;
	}
}
