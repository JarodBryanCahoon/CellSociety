package grids;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import cells.Cell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a 2D square grid of cells
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class SquareGrid extends GeneralSquareGrid {

	public SquareGrid(int rows, int cols, List<Integer> neighbors) {
		super(rows, cols, neighbors);
	}


}
