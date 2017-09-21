package cellsociety_team01;

import java.util.ArrayList;
import java.util.List;
import cellsociety_team01.FireCell.State; // Admittedly, weird


public class FireCell extends Cell<State> {
	public enum State{EMPTY, TREE, BURNING}
	
	private double probCatch;
	
	public FireCell(State initialState, double probCatch) {
		super(initialState);
		this.probCatch = probCatch;
	}
	
	public FireCell(double probCatch) {
		this(State.TREE, probCatch);
	}
	
	@Override
	public void step(List<Cell<State>> neighborhood) {
		if(getState().equals(State.BURNING))
			nextState = State.EMPTY;
		else if(getState().equals(State.TREE)) {
			for(Cell<State> n : getImmediateNeighbors(neighborhood)) {
				if(n != null && n.getState() == State.BURNING) {
					if(probCatch > Math.random())
						nextState =  State.BURNING;
					return;
				}
			}
		}
	}

	private List<Cell<State>> getImmediateNeighbors(List<Cell<State>> neighborhood) {
		List<Cell<State>> neighbors = new ArrayList<Cell<State>>();
		neighbors.add(neighborhood.get(1)); // North
		neighbors.add(neighborhood.get(3)); // West
		neighbors.add(neighborhood.get(4)); // East
		neighbors.add(neighborhood.get(6)); // South
		return neighbors;
	}
}
