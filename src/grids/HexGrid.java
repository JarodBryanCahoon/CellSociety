package grids;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cells.Cell;
import javafx.scene.layout.Pane;

public class HexGrid extends Grid2D {

	public HexGrid(int rows, int cols, List<Integer> neighbors) {
		super(rows, cols, neighbors);
		if(neighbors.size() > 6) {
			throw new IllegalArgumentException(ResourceBundle.getBundle("ErrorBundle").getString("LargeNeighborhood"));
		}
	}

	@Override
	public List<Cell> getNeighbors(int row, int col) {
		List<Cell> neighbors = new ArrayList<Cell>();
		neighbors.add(get(row-1, col-1+row%2));
		neighbors.add(get(row-2, col));
		neighbors.add(get(row-1, col+row%2));
		neighbors.add(get(row+1, col+row%2));
		neighbors.add(get(row+2, col));
		neighbors.add(get(row+1, col-1+row%2));
		return extractNeighbors(neighbors);
	}

	@Override
	public Pane getView(double width, double height) {
		double cellWidth = width/widthPerCellWidth();
		double cellHeight = height/heightPerCellHeight(); 
		Pane pane = new Pane();
		pane.setPrefSize(width, height);
		setOriginNonRelative(0,0);
		for(int row = 0; row < getHeight(); row++) {
			for(int col = 0; col < getWidth(); col++) {
				double xLoc = cellWidth*(1.5*col+(row%2)*.75+.5); // Center location of hexagon in grid, from geometry
				double yLoc = cellHeight*(.5+row/2.0);            // Center location of hexagon in grid, from geometry
				Hexagon hex = new Hexagon(xLoc, yLoc, cellWidth, cellHeight);
				get(row,col).acceptImage(hex);
				pane.getChildren().add(hex);
			}
		}
		return pane;
	}

	private double widthPerCellWidth() {
		return getWidth()*1.5+.25; //These are NOT magic numbers -- they give the width in terms of cellWidths
	}
	
	private double heightPerCellHeight() {
		return (getHeight()+1)/2.0; //Again, not magic numbers -- This gives the height in terms of cellHeight
	}
}
