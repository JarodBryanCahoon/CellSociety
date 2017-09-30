package cells;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class AntCell extends Cell {

	private static final int MAXANTS = 0;
	private static final int NESTP = 1;
	private static final int FOODLOCP = 2;

	private int homeP = 0;
	private int foodP = 0;
	private List<Ant> ants = new ArrayList<Ant>();

	public AntCell(int initialState, ParameterBundle pars) {
		super(initialState, pars, new Color[0]);
	}

	@Override
	protected Color getColor() {
		return Color.rgb(255 * ants.size() / (int) parameters.getParameter(MAXANTS),
				255 * foodP / (int) parameters.getParameter(NESTP),
				255 * homeP / (int) parameters.getParameter(FOODLOCP));
	}

	@Override
	public void step(List<Cell> neighborhood) {
		
	}
	
	private class Ant{
		private int direction;
		private Ant() {
			
		}
		
		private void step(List<Cell> neighbors) {
			
		}
	}
}
