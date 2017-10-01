package cells;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class AntCell extends Cell {

	// Parameters
	private static final int MAXANTS = 0;
	private static final int MAXP = 1;
	private static final int ANTS_PER_STEP = 2;
	private static final int ANT_LIFE = 3;

	// States
	private static final int EMPTY = 0;
	private static final int NEST = 1;
	private static final int FOOD = 2;
	private static final int OBSTACLE = 3;

	private static final Color[] COLORS = new Color[] { Color.WHITE, Color.CHOCOLATE, Color.CHARTREUSE, Color.SKYBLUE };

	private double homeP = 0;
	private double foodP = 0;
	private List<Ant> ants = new ArrayList<Ant>();

	public AntCell(int initialState, ParameterBundle pars) {
		super(initialState, pars, COLORS);
		if (initialState == NEST) {
			spawnAnts();
		}
	}

	private void spawnAnts() {
		for (int i = 0; i < (int) parameters.getParameter(ANTS_PER_STEP); i++) {
			ants.add(new Ant());
		}
	}

	@Override
	protected Color getColor() {
		if (getState() == EMPTY) {
			int intensity = 255 * ants.size() / (int) parameters.getParameter(MAXANTS);
			return Color.color(1, 1, 1, intensity);
		}
		return super.getColor();
	}

	@Override
	public void step(List<Cell> neighborhood) {
		if (getState() == NEST)
			spawnAnts();
		for (int i = ants.size() - 1; i >= 0; i--)
			ants.get(i).step(this, neighborhood);
	}

	public void update() {
		for (Ant a : ants)
			a.update();
		super.update();
	}

	@Override
	protected void cycle() {
		if (getState() == OBSTACLE)
			nextState = EMPTY;
		else
			nextState = OBSTACLE;
		update();
	}

	// Represents a single ant
	private class Ant {
		private int direction = -1;
		private AntCell moveFrom = AntCell.this;
		private AntCell moveTo = AntCell.this;
		private boolean hasFood = false;
		private int life = ANT_LIFE;

		private Ant() {

		}

		public void update() {
			moveFrom.ants.remove(this);
			if (life > 0)
				moveTo.ants.add(this);
			moveFrom = moveTo;
			if (getState() == NEST)
				hasFood = false;
			else if (getState() == FOOD)
				hasFood = true;
		}

		public void step(Cell origin, List<Cell> neighbors) {
			life--;
			if (neighbors == null || neighbors.size() == 0)
				return;
			if (direction == -1)
				direction = (int) (neighbors.size() * Math.random());
			if (hasFood)
				returnToNest(neighbors);
			else
				findFoodSource(neighbors);
		}

		private void findFoodSource(List<Cell> neighbors) {
			if (getState() == NEST) {
				direction = maxPh(neighbors, false, false);
			}
			List<Cell> front = getForwardThree(neighbors);
			int x = maxPh(front, false, true);
			if (x == -1) {
				x = maxPh(neighbors, false, true);
				if (x == -1)
					return;
				moveTo = (AntCell) neighbors.get(x);
			} else
				moveTo = (AntCell) front.get(x);
			direction = neighbors.indexOf(moveTo);
			dropHomePheromones(neighbors);
		}

		private void dropHomePheromones(List<Cell> neighbors) {
			if (getState() == NEST)
				homeP = (int) parameters.getParameter(MAXP);
			double D = maxPh(neighbors, true, false) - 2;
			homeP = Math.max(homeP, D);
		}

		private int maxPh(List<Cell> neighbors, boolean home, boolean checkForLegal) {
			int maxIndex = -1;
			double max = 0;
			for (int i = 0; i < neighbors.size(); i++) {
				if (neighbors.get(i) == null || checkForLegal && !isLegalMove(neighbors.get(i)))
					continue;
				AntCell cell = (AntCell) neighbors.get(i);
				double ph;
				if (home)
					ph = cell.homeP;
				else
					ph = cell.foodP;
				if (ph > max) {
					maxIndex = i;
					max = ph;
				}
			}
			return maxIndex;
		}

		private void returnToNest(List<Cell> neighbors) {
			if (getState() == FOOD)
				direction = maxPh(neighbors, true, false);
			List<Cell> front = getForwardThree(neighbors);
			int x = maxPh(front, true, true);
			if (x == -1) {
				x = maxPh(neighbors, true, true);
				if (x == -1)
					return;
				moveTo = (AntCell) neighbors.get(x);
			} else
				moveTo = (AntCell) front.get(x);
			direction = neighbors.indexOf(moveTo);
			dropFoodPheromones(neighbors);
		}

		private void dropFoodPheromones(List<Cell> neighbors) {
			if (getState() == FOOD)
				foodP = (int) parameters.getParameter(MAXP);
			double D = maxPh(neighbors, false, false) - 2;
			foodP = Math.max(foodP, D);
		}

		private List<Cell> getForwardThree(List<Cell> neighbors) {
			List<Cell> forward = new ArrayList<Cell>();
			for (int i = direction - 1; i <= direction + 1; i++) {
				if (i < 0)
					i = neighbors.size() - 1;
				else if (i >= neighbors.size())
					i = 0;
				forward.add(neighbors.get(i));
			}
			return forward;
		}

		private boolean isLegalMove(Cell neighbor) {
			if (neighbor == null)
				return false;
			AntCell cell = (AntCell) neighbor;
			return cell.getState() != OBSTACLE && cell.ants.size() < (int) parameters.getParameter(MAXANTS);
		}
	}
	
	@Override
	public String getSimType() {
		return "Ant";
	}
}
