package simulations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cells.ParameterBundle;
import cells.WatorCell;
import grids.AbstractGrid;

/**
 * Like normal simulation, but handles motion of cells and cannot run in parallel due to conflicting rules
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class WatorSimulation extends Simulation {
	public WatorSimulation(AbstractGrid cells, ParameterBundle pars) {
		super(cells, pars);
	}
	
	/**
	 * Because of collisions, this cannot run in parallel with the rules as they are. 
	 * Thus, we simply run in a random order.
	 */
	@Override
	public void step() {
		List<WatorCell> movedTo = new ArrayList<WatorCell>();
		List<Integer> cellIDs = new ArrayList<Integer>();
		for(int i = 0; i < cells.getSize(); i++)
			cellIDs.add(i);
		Collections.shuffle(cellIDs);
		for(Integer i : cellIDs) {
			WatorCell cell = (WatorCell)cells.get(i);
			if(movedTo.contains(cell))
				continue;
			cell.step(cells.getNeighbors(i));
			movedTo.add(cell.getMovedTo());
		}
	}
}
