package simulations;

import java.util.ArrayList;
import java.util.List;

import cells.Cell;
import cells.ParameterBundle;
import cells.SegCell;
import grids.AbstractGrid;

/**
 * Like normal simulation, but handles motion of SegCells
 * 
 * @author Ian Eldridge-Allegra
 *
 */
public class SegSimulation extends Simulation {

	public SegSimulation(AbstractGrid cells, ParameterBundle pars) {
		super(cells, pars);
	}

	/**
	 * Checks whether cells are satisfied, then moves those that are not to empty locations
	 */
	@Override
	public void step() {
		stepAllCells();
		
		List<SegCell> emptyCells = getEmptyCells();
		
		for(Cell c:cells) {
			SegCell cell = (SegCell)c;
			if(cell.shouldMove()) {
				int targetIndex = (int)(Math.random()*emptyCells.size());
				cell.moveTo(emptyCells.get(targetIndex));
				emptyCells.remove(targetIndex);
				emptyCells.add(cell);
			}
		}
	}
	
	private List<SegCell> getEmptyCells(){
		List<SegCell> emptyCells = new ArrayList<SegCell>();
		for(Cell c : cells) {
			SegCell cell = (SegCell)c;
			if(cell.isEmpty()) {
				emptyCells.add(cell);
			}
		}
		return emptyCells;
	}
}
