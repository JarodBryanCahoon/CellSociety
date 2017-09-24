package simulations;

import cells.Cell;
import grids.AbstractGrid;

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

	public Simulation(AbstractGrid cells) {
		this.cells = cells;
	}

	/**
	 * Steps each cell, then updates their state
	 * 
	 */
	public void step() {
		for (int i = 0; i < cells.getSize(); i++)
			cells.get(i).step(cells.getNeighbors(i));
		for (Cell cell : cells)
			cell.update();
	}

	public String toString() {
		return cells.toString();
	}

	public AbstractGrid getGrid() {
		return cells;
	}
}
