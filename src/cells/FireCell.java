package cells;

import java.util.List;


/**
 * @author Ian Eldridge-Allegra
 *
 */
public class FireCell extends Cell {
	public static final int EMPTY = 0;
	public static final int TREE = 1;
	public static final int BURNING = 2;
	
	private double probCatch;
	
	public FireCell(int initialState, double probCatch) {
		super(initialState);
		this.probCatch = probCatch;
	}
	
	public FireCell(double probCatch) {
		this(TREE, probCatch);
	}
	
	@Override
	public void step(List<Cell> neighborhood) {
		if(getState() == BURNING)
			nextState = EMPTY;
		else if(getState() == TREE) {
			for(Cell n : neighborhood) {
				if(n != null && n.getState() == BURNING) {
					if(probCatch > Math.random())
						nextState =  BURNING;
					return;
				}
			}
		}
	}
}
