package grids;

import java.util.List;

import cells.Cell;
import javafx.scene.layout.Pane;

/**
 * Extends HexGrid and is identical except it stretches when near the boundary
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class InfiniteHexGrid extends HexGrid {
	
	
	public InfiniteHexGrid(int rows, int cols, List<Integer> neighbors) {
		super(rows, cols, neighbors);
	}

	/**
	 * @see grids.HexGrid#getNeighbors(int, int)
	 * 
	 * Extends the grid as needed
	 */
	@Override 
	public List<Cell> getNeighbors(int row, int col) {
		if(get(row,col) == null || get(row,col).getState() != Cell.EMPTY) {
			stretchTo(row+2, col+2);
			stretchTo(row-2, col-2);
		}
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
		return "HexagonInf";
	}
}
