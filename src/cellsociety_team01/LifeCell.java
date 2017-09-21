package cellsociety_team01;

import java.util.List;

public class LifeCell extends Cell<Boolean> {

	public LifeCell(Boolean initialState) {
		super(initialState);
	}

	@Override
	public void step(List<Cell<Boolean>> neighborhood) {
		int livingNeighbors = numLiving(neighborhood);
		if(livingNeighbors < 2 || livingNeighbors > 3)
			nextState = false;
		else if(livingNeighbors == 3)
			nextState = true;
		// when 2, do nothing
	}
	
	private int numLiving(List<Cell<Boolean>> neighborhood) {
		int living = 0;
		for(Cell<Boolean> cell : neighborhood) {
			if(cell != null && cell.getState())
				living++;
		}
		return living;
	}
}
