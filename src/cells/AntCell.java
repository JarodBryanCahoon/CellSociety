package cells;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class AntCell extends Cell {

	private static final int MAXANTS = 0;
	private static final int NESTP = 1;
	private static final int FOODLOCP = 2;
	
	private static final int EMPTY = 0;
	private static final int NEST  = 1;
	private static final int FOOD  = 2;
	private static final int OBSTACLE = 3;
	
	private static final Color[] COLORS = new Color[] { Color.WHITE, Color.CHOCOLATE, Color.CHARTREUSE, Color.SKYBLUE};
	

	private double homeP = 0;
	private double foodP = 0;
	private List<Ant> ants = new ArrayList<Ant>();

	public AntCell(int initialState, ParameterBundle pars) {
		super(initialState, pars, COLORS);
	}

	@Override
	protected Color getColor() {
		if(getState() == EMPTY) {
			int intensity = 255 * ants.size() / (int) parameters.getParameter(MAXANTS);
			return Color.color(1, 1, 1, intensity);
		}
		return super.getColor();
	}

	@Override
	public void step(List<Cell> neighborhood) {
		for(int i = ants.size()-1; i >= 0; i--)
			ants.get(i).step(this, neighborhood);
	}
	
	public void update() {
		for(Ant a:ants)
			a.update();
		super.update();
	}
	
	private class Ant{
		private int direction = -1;
		AntCell moveFrom = AntCell.this;
		AntCell moveTo = AntCell.this;
		boolean hasFood = false;
		
		private Ant() {
			
		}
		
		public void update() {
			moveTo.ants.add(this);
			moveFrom.ants.remove(this);
			moveFrom = moveTo;
			if(getState() == NEST)
				hasFood = false;
			else if(getState() == FOOD)
				hasFood = true;
		}

		public void step(Cell origin, List<Cell> neighbors) {
			if(neighbors == null || neighbors.size()==0)
				return;
			if(direction == -1)
				direction = (int)(neighbors.size()*Math.random());
			if(hasFood)
				returnToNest(neighbors);
			else 
				findFoodSource(neighbors);
		}
		
		private void findFoodSource(List<Cell> neighbors) {
			if(getState()==NEST) {
				direction = maxPh(neighbors, false);
			}
			List<Cell> front = getForwardThree(neighbors);
			int x = maxPh(front, false);
			if(x == -1) {
				x = maxPh(neighbors, false);
				if(x == -1)
					return;
				moveTo = (AntCell)neighbors.get(x);
			}
			else
				moveTo = (AntCell)front.get(x);
			direction = neighbors.indexOf(moveTo);
			dropHomePheromones();
		}

		private void dropHomePheromones() {
			if(getState() == NEST)
				return; //TODO
		}
		
		private int maxPh(List<Cell> neighbors, boolean home) {
			int maxIndex = -1;
			double max = 0;
			for(int i = 0; i < neighbors.size(); i++) {
				AntCell cell = (AntCell)neighbors.get(i);
				double ph;
				if(home)
					ph = cell.homeP;
				else
					ph = cell.foodP;
				if(ph > max) {
					maxIndex = i;
					max = ph;
				}
			}
			return maxIndex;
		}

		private void returnToNest(List<Cell> neighbors) {
			
		}
		
		private List<Cell> getForwardThree(List<Cell> neighbors){
			List<Cell> forward = new ArrayList<Cell>();
			for(int i = direction - 1; i <= direction + 1; i++) {
				if(i < 0)
					i = neighbors.size()-1;
				else if(i >= neighbors.size())
					i = 0;
				if(isLegalMove(neighbors.get(i)))
					forward.add(neighbors.get(i));
			}
			return forward;
		}
		
		private boolean isLegalMove(Cell neighbor) {
			if(neighbor == null)
				return false;
			AntCell cell = (AntCell)neighbor;
			return cell.ants.size() < (int)parameters.getParameter(MAXANTS);
		}
	}

	@Override
	protected void cycle() {
		if(getState() == OBSTACLE)
			nextState = EMPTY;
		else
			nextState = OBSTACLE;
		update();
	}
}
