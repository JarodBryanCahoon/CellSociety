package grids;

import java.util.List;

import cells.Cell;
import javafx.scene.layout.Pane;

/**
 * Extends SquareGrid and is identical except it stretches when near the boundary
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class InfiniteSquareGrid extends SquareGrid {
	public InfiniteSquareGrid(int rows, int cols, List<Integer> neighbors) {
		super(rows, cols, neighbors);
	}
	
	/**
	 * @see grids.SquareGrid#getNeighbors(int, int)
	 * 
	 * Extends boundary as necessary
	 */
	@Override 
	public List<Cell> getNeighbors(int row, int col) {
		if(get(row,col) == null || get(row,col).getState() != Cell.EMPTY)
			stretchTo(row, col);
		return super.getNeighbors(row, col);
	}

	@Override
	public Pane getView(double width, double height) {
		paneWidth = width;
		paneHeight = height;
		pane = super.getView(width, height);
		return pane;
	}
	
	@Override
	public String getType() {
		return "SquareInf";
	}
}
