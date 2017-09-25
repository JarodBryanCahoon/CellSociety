package grids;

import java.util.List;

import cells.Cell;

/**
 * The same as SquareGrid, but the corner neighbors are removed.
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class FourNeighborSquareGrid extends SquareGrid {

	public FourNeighborSquareGrid(int rows, int cols) {
		super(rows, cols);
	}
	
	@Override
	public List<Cell> getNeighbors(int row, int col){
		List<Cell> neighbors = super.getNeighbors(row,col);
		neighbors.remove(7); // SE
		neighbors.remove(5); // SW
		neighbors.remove(2); // NW
		neighbors.remove(0); // NE
		return neighbors;
	}
}
