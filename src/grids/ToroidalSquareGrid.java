package grids;

import java.util.List;

import cells.Cell;

/**
 * FourNeighborSquareGrid, except the get(row,col) methods wraps around for inputs outside the grid
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class ToroidalSquareGrid extends SquareGrid {

	public ToroidalSquareGrid(int rows, int cols, List<Integer> neighbors) {
		super(rows, cols, neighbors);
	}

	@Override
	public Cell get(int row, int col) {
		return super.get(mod(row, getHeight()), mod(col, getWidth()));
	}
	
	private int mod(int a, int b) {
		return ((a%b)+b)%b;
	}
	
	@Override
	public String getType() {
		return "SquareTor";
	}
}
