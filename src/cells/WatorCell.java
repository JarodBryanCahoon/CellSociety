package cells;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class WatorCell extends Cell {
	//The order of these is important -- each prioritizes the things below it
	public static final int EMPTY = 0;
	public static final int FISH = 1;
	public static final int SHARK = 2;

	private int sharkLifeSpan;
	private int sharkSpawnTime;
	private int fishSpawnTime;
	private int energy;
	private int toNextSpawn;

	public WatorCell(int initialState, int sharkLife, int sharkSpawn, int fishSpawn) {
		super(initialState);
		sharkLifeSpan = sharkLife;
		sharkSpawnTime = sharkSpawn;
		fishSpawnTime = fishSpawn;
		energy = sharkLifeSpan;
		toNextSpawn = initialSpawnTime();
	}

	private int initialSpawnTime() {
		return getState() == FISH ? fishSpawnTime : sharkSpawnTime;
	}

	/**
	 * @param neighborhood The immediate neighborhood
	 * This is not done in parallel, so it will call update as necessary
	 */
	@Override
	public void step(List<Cell> neighborhood) {
		if (getState() == EMPTY)
			return;
		toNextSpawn--;
		energy--;
		if(isDead())
			nextState = EMPTY;
		else
			moveTo(cellToMoveTo(neighborhood, getState()-1));
		update();
	}

	/**
	 * @param neighborhood
	 *            The immediate neighborhood (NWES) of the cell
	 * @param priority 
	 * 			  What state to move towards
	 * @return returns this cell when not moving
	 */
	private WatorCell cellToMoveTo(List<Cell> neighborhood, int priority) {
		if (priority == -1) {
			return this;
		}
		ArrayList<WatorCell> cells = new ArrayList<WatorCell>();
		for (Cell c : neighborhood) {
			if (c != null && c.getState() == priority)
				cells.add((WatorCell)c);
		}
		if (cells.size() == 0)
			return cellToMoveTo(neighborhood, priority - 1);
		// if looking for fish, try empty space. otherwise, done.
		else
			return cells.get((int) (Math.random() * cells.size()));
	}

	private boolean isDead() {
		return getState() == SHARK && energy <= 0;
	}
	
	private void moveTo(WatorCell cell) {
		if(cell == this)
			return;
		cell.nextState = getState();
		cell.energy = energy;
		if(toNextSpawn == 0) {
			toNextSpawn = initialSpawnTime();
			energy = sharkLifeSpan;
		}
		else 
			nextState = EMPTY;
		cell.toNextSpawn = toNextSpawn;
		cell.update();
	}
}