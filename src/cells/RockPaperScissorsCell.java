package cells;

import java.util.List;

import javafx.scene.paint.Color;

public class RockPaperScissorsCell extends Cell {

	public static final int EMPTY = 0;
	public static final int ROCK = 1;
	public static final int PAPER = 2;
	public static final int SCISSORS = 3;
	private static final Color[] COLORS = new Color[] { Color.WHITE, Color.BLUE, Color.GREEN, Color.RED };

	private int depth = 0;

	public RockPaperScissorsCell(int initialState, ParameterBundle pars) {
		super(initialState, pars, COLORS);
	}

	@Override
	public void step(List<Cell> neighborhood) {
		RockPaperScissorsCell other = (RockPaperScissorsCell) neighborhood
				.get((int) (Math.random() * neighborhood.size()));
		if(other.getState()==EMPTY)
			return;
		if(depth >= maxDepth()) {
			nextState = EMPTY;
		}
		if(getState()==EMPTY) {
			nextState = other.getState();
			depth = other.depth+1;
		}
		else if(beats(other) && other.depth < maxDepth()) {
			other.depth++;
			depth=Math.max(0, depth-1);
		}
	}
	
	private boolean beats(RockPaperScissorsCell other) {
		return other.getState() != EMPTY
				&& (getState() - other.getState() == 1 || (getState() == ROCK && other.getState() == SCISSORS));
	}
	
	private int maxDepth() {
		return (int)parameters.getParameter(0);
	}
}
