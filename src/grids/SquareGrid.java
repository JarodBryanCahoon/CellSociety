package grids;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cells.Cell;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * Square tiled Grid2D -- statically sized unless extended
 * 
 * Depends on list, Pane, Cell, Rectangle, ResourceBundle, Grid2D
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public abstract class SquareGrid extends Grid2D {

	/**
	 * @param rows
	 * @param cols
	 * @param neighbors
	 */
	public SquareGrid(int rows, int cols, List<Integer> neighbors) {
		super(rows, cols, neighbors);
		if (neighbors.size() > 8) {
			throw new IllegalArgumentException(ResourceBundle.getBundle("ErrorBundle").getString("LargeNeighborhood"));
		}
	}

	/**
	 * @see grids.Grid2D#getNeighbors(int, int)
	 */
	@Override
	public List<Cell> getNeighbors(int row, int col) {
		List<Cell> neighbors = new ArrayList<Cell>();
		neighbors.add(get(row - 1, col - 1));
		neighbors.add(get(row - 1, col));
		neighbors.add(get(row - 1, col + 1));
		neighbors.add(get(row, col + 1));
		neighbors.add(get(row + 1, col + 1));
		neighbors.add(get(row + 1, col));
		neighbors.add(get(row + 1, col - 1));
		neighbors.add(get(row, col - 1));
		return extractNeighbors(neighbors);
	}

	/**
	 * @see grids.Grid2D#getView(double, double)
	 * 
	 * Represents Cells as rectangles
	 */
	public Pane getView(double width, double height) {
		double cellWidth = width / getTrueWidth();
		double cellHeight = height / getTrueHeight();
		Pane pane = new Pane();
		pane.setPrefSize(width, height);
		setOriginNonRelative(0,0);
		for (int row = 0; row < getHeight(); row++) {
			for (int col = 0; col < getWidth(); col++) {
				Rectangle rect = new Rectangle(cellWidth, cellHeight);
				rect.setLayoutX(cellWidth * col);
				rect.setLayoutY(cellHeight * row);
				get(row, col).acceptImage(rect);
				pane.getChildren().add(rect);
			}
		}
		return pane;
	}

	@Override
	public String getType() {
		return "Square";
	}
}
