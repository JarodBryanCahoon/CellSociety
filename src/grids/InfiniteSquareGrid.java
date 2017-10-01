package grids;

import java.util.List;

import cells.Cell;
import javafx.scene.layout.Pane;

public class InfiniteSquareGrid extends GeneralSquareGrid {

	private Pane pane;
	private double paneWidth; 
	private double paneHeight;

	public InfiniteSquareGrid(int rows, int cols, List<Integer> neighbors) {
		super(rows, cols, neighbors);
	}

	@Override
	protected void stretchTo(int row, int col) {
		int width = getTrueWidth();
		int height = getTrueHeight();
		super.stretchTo(row, col);
		if (getTrueWidth() != width || getTrueHeight() != height) {
			pane.getChildren().clear();
			pane.getChildren().addAll(getView(paneWidth, paneHeight));
		}
	}
	
	@Override 
	public List<Cell> getNeighbors(int row, int col) {
		if(get(row,col).getState() != Cell.EMPTY)
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
