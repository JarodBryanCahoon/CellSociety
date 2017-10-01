package grids;

import java.util.List;

import cells.Cell;
import javafx.scene.layout.Pane;

public class InfiniteSquareGrid extends SquareGrid {
	public InfiniteSquareGrid(int rows, int cols, List<Integer> neighbors) {
		super(rows, cols, neighbors);
	}
	
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
}
