package simulations;

import cells.Cell;
import cells.ParameterBundle;
import grids.AbstractGrid;
import grids.Grid2D;
import javafx.scene.layout.Pane;

/**
 * Generic Simulation -- handles most cases where cells don't move
 * 
 * Holds a grid of cells
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class Simulation {
	protected AbstractGrid cells;
	protected ParameterBundle parameters;

	public Simulation(AbstractGrid cells, ParameterBundle parBundle) {
		this.cells = cells;
		parameters = parBundle;
	}

	/**
	 * Steps each cell, then updates their state
	 * 
	 */
	public void step() {
		stepAllCells();
		updateAllCells();
	}

	protected void updateAllCells() {
		for (Cell cell : cells)
			cell.update();
	}

	protected void stepAllCells() {
		for (int i = 0; i < cells.getSize(); i++)
			cells.get(i).step(cells.getNeighbors(i));
	}

	public String toString() {
		return cells.toString();
	}
	
	public Pane getParameterPane(double width, double height) {
		return cells.getView(width, height);
	}

	public Pane getView(double width, double height) {
		return cells.getView(width, height);
	}
}
