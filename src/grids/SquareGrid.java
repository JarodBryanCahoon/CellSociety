package grids;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cells.Cell;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * Represents a 2D square grid of cells
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class SquareGrid extends Grid2D {
	
	public SquareGrid(int rows, int cols, List<Integer> neighbors) {
		super(rows,cols, neighbors);
		if(neighbors.size() > 4) {
			throw new IllegalArgumentException(ResourceBundle.getBundle("ErrorBundle").getString("LargeNeighborhood"));
		}
	}

	@Override
	public List<Cell> getNeighbors(int row, int col) {
		List<Cell> neighbors = new ArrayList<Cell>();
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				if (r != 1 || c != 1)
					neighbors.add(get(row - 1 + r, col - 1 + c));
			}
		}
		return extractNeighbors(neighbors);
	}
	
	

	public Pane getView(double width, double height) {
		double cellWidth = width/getWidth();
		double cellHeight = height/getHeight();
		Pane pane = new Pane();
		pane.setPrefSize(width, height);
		for(int row = 0; row < getHeight(); row++) {
			for(int col = 0; col < getWidth(); col++) {
				Rectangle rect = new Rectangle(cellWidth, cellHeight);
				rect.setLayoutX(cellWidth*col);
				rect.setLayoutY(cellHeight*row);
				get(row,col).acceptImage(rect);
				pane.getChildren().add(rect);
			}
		}
		return pane;
	}
}
