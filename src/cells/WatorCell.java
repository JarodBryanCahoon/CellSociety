package cells;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class WatorCell extends Cell {
	// The order of these is important -- each prioritizes the things below it
	public static final int EMPTY = 0;
	public static final int FISH = 1;
	public static final int SHARK = 2;
	private static final Color[] COLORS = new Color[]{ Color.DARKBLUE, Color.GREEN, Color.ALICEBLUE};

	private int sharkLifeSpan;
	private int sharkSpawnTime;
	private int fishSpawnTime;
	private int energyGain;
	
	private int energy;
	private int toNextSpawn;
	private WatorCell movedTo;
	

	/**
	 * @param initialState
	 * @param sharkLife
	 *            The life-span, in steps, of a shark without food
	 * @param sharkSpawn
	 *            The number of steps for a shark to spawn
	 * @param fishSpawn
	 *            The number of steps for a fish to spawn
	 */
	public WatorCell(int initialState, int sharkLife, int sharkSpawn, int fishSpawn, int energyGain) {
		super(initialState);
		sharkLifeSpan = sharkLife;
		sharkSpawnTime = sharkSpawn;
		fishSpawnTime = fishSpawn;
		this.energyGain = energyGain;
		energy = sharkLifeSpan;
		toNextSpawn = initialSpawnTime();
		setColors(COLORS);
	}

	/**
	 * @return fishSpawnTime or sharkSpawnTime, depending on the state
	 */
	private int initialSpawnTime() {
		return getState() == FISH ? fishSpawnTime : sharkSpawnTime;
	}

	/**
	 * This is not done in parallel, so it will call update as necessary
	 * 
	 * @param neighborhood
	 *            The immediate neighborhood
	 * 
	 * @see cells.Cell#step(java.util.List)
	 */
	@Override
	public void step(List<Cell> neighborhood) {
		movedTo = this;
		if (getState() == EMPTY)
			return;
		toNextSpawn--;
		energy--;
		if (isDead()) {
			nextState = EMPTY;
		}
		else
			moveTo(cellToMoveTo(neighborhood, getState() - 1));
		update();
	}
	
	/**
	 * @return The cell this last moved to, to allow Simulation to prevent double motion
	 */
	public WatorCell getMovedTo() {
		return movedTo;
	}

	/**
	 * @param neighborhood
	 *            The immediate neighborhood (NWES) of the cell
	 * @param priority
	 *            What state to move towards
	 * @return returns this cell when not moving, otherwise gives destination
	 */
	private WatorCell cellToMoveTo(List<Cell> neighborhood, int priority) {
		if (priority == -1) {
			return this;
		}
		ArrayList<WatorCell> cells = new ArrayList<WatorCell>();
		for (Cell c : neighborhood) {
			if (c != null && c.getState() == priority)
				cells.add((WatorCell) c);
		}
		if (cells.size() == 0)
			return cellToMoveTo(neighborhood, priority - 1);
		// if looking for fish, try empty space. otherwise, done.
		else
			return cells.get((int) (Math.random() * cells.size()));
	}

	/**
	 * For sharks
	 */
	private boolean isDead() {
		return getState() == SHARK && energy <= 0;
	}

	/**
	 * Moves to the given cell and spawns if appropriate
	 * 
	 * @param cell
	 *            destination
	 * @return 
	 */
	private void moveTo(WatorCell cell) {
		movedTo = cell;
		if (cell == this)
			return;
		if(cell.getState() == FISH)
			energy+=energyGain;
		cell.nextState = getState();
		cell.energy = energy;
		if (toNextSpawn <= 0) {
			toNextSpawn = initialSpawnTime();
			energy = sharkLifeSpan;
		} else
			nextState = EMPTY;
		cell.toNextSpawn = toNextSpawn;
		cell.update();
	}
}
