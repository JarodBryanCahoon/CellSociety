package simulations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cells.WatorCell;
import grids.AbstractGrid;
import grids.WatorGrid;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class WatorSimulation extends Simulation {
	public WatorSimulation(AbstractGrid cells) {
		super(cells);
	}
	
	/**
	 * Because of collisions, this cannot run in parallel with the rules as they are. 
	 * Thus, we simply run in a random order.
	 */
	@Override
	public void step() {
		WatorGrid wGrid = (WatorGrid)cells;
		List<Integer> cellIDs = new ArrayList<Integer>();
		for(int i = 0; i < cells.getSize(); i++)
			cellIDs.add(i);
		Collections.shuffle(cellIDs);
		for(Integer i : cellIDs) {
			WatorCell cell = (WatorCell)wGrid.get(i);
			cell.step(wGrid.getNeighbors(i));
		}
	}
}
